package de.bigabig.wikihowqa.service;

import com.google.gson.Gson;
import de.bigabig.wikihowqa.model.WikihowArticle;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
}
