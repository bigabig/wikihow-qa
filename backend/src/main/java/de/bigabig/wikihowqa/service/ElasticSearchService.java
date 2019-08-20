package de.bigabig.wikihowqa.service;

import com.google.gson.Gson;
import de.bigabig.wikihowqa.model.service.WikihowArticle;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class ElasticSearchService {

    private static Logger logger = LoggerFactory.getLogger(ElasticSearchService.class);

    private RestHighLevelClient client;

    public String elastichostname;
    public int elasticport;
    public String elasticindex;

    public ElasticSearchService(@Value("${elastichostname}") String elastichostname, @Value("${elasticport}") int elasticport, @Value("${elasticindex}") String elasticindex) {
        this.elastichostname = elastichostname;
        this.elasticport = elasticport;
        this.elasticindex = elasticindex;
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(elastichostname, elasticport, "http")));
    }

    public List<WikihowArticle> findDocumentsForTopic(String topic, int count) {
        return findDocumentsForTopic(topic, count, this.elasticindex);
    }

    public List<WikihowArticle> findDocumentsForTopic(String topic, int count, String elasticindex) {
        List<WikihowArticle> result = new ArrayList<>();
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));

        logger.info("Query Elasticsearch: " + topic);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchPhraseQuery("title", topic));
        searchSourceBuilder.size(count);

        SearchRequest searchRequest = new SearchRequest(elasticindex);
        searchRequest.scroll(scroll);
        searchRequest.source(searchSourceBuilder);
        searchSourceBuilder.explain(false);
        searchSourceBuilder.fetchSource(true);

        Gson gson = new Gson();

        String scrollId = "";
        try {
            SearchResponse searchResponse = client.search(searchRequest);
            scrollId = searchResponse.getScrollId();
            SearchHit[] searchHits = searchResponse.getHits().getHits();

            int counter = 0;
            while (searchHits != null && searchHits.length > 0 && counter < count) {
                SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                scrollRequest.scroll(scroll);
                searchResponse = client.searchScroll(scrollRequest);
                scrollId = searchResponse.getScrollId();

                // process search hits
                for(SearchHit hit : searchHits) {
                    if(counter < count) {
                        WikihowArticle article = gson.fromJson(hit.getSourceAsString(), WikihowArticle.class);
                        article.setScore(hit.getScore());
                        result.add(article);
                    }
                    logger.info(""+hit.getScore());
                    counter++;
                }

                searchHits = searchResponse.getHits().getHits();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
            clearScrollRequest.addScrollId(scrollId);
            try {
                client.clearScroll(clearScrollRequest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public List<String> findSuggestions(String text, int count) {
        logger.info("Query Autocomplete: " + text);
        List<String> result = new ArrayList<>();

        // Build Suggestion
        CompletionSuggestionBuilder titleSuggest = new CompletionSuggestionBuilder("suggest_title");
        titleSuggest.prefix(text);
        titleSuggest.size(count);

        // Build search
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.suggest(new SuggestBuilder().addSuggestion("titleSuggester", titleSuggest));

        // Build search request
        SearchRequest searchRequest = new SearchRequest(this.elasticindex);
        searchRequest.source(searchSourceBuilder);
        searchSourceBuilder.explain(false);
        searchSourceBuilder.fetchSource(false);

        try {
            // Search
            SearchResponse response = client.search(searchRequest);

            // Extract the result
            CompletionSuggestion suggestions = response.getSuggest().getSuggestion("titleSuggester");

            List<CompletionSuggestion.Entry> entryList = suggestions.getEntries();
            if(entryList != null) {
                for(CompletionSuggestion.Entry entry : entryList) {
                    List<CompletionSuggestion.Entry.Option> options = entry.getOptions();
                    if(options != null)  {
                        for(CompletionSuggestion.Entry.Option option : options) {
                            result.add(option.getText().string());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
