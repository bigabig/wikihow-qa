package de.bigabig.wikihowqa.controller;

import com.google.gson.Gson;
import de.bigabig.wikihowqa.model.*;
import de.bigabig.wikihowqa.model.service.*;
import de.bigabig.wikihowqa.service.ElasticSearchService;
import de.bigabig.wikihowqa.service.RestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MainController {

    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    ElasticSearchService elasticSearch;

    @Autowired
    RestService restService;

    private Gson gson = new Gson();

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("welcome", "Hello World! :D");
        model.addAttribute("query", new SearchQuery());
        return "main";
    }

    @PostMapping("/")
    public String postQuery(@ModelAttribute("query") SearchQuery searchQuery, Model model) {
        model.addAttribute("welcome", "Hello World! :D");
        model.addAttribute("query", searchQuery);

        List<WikihowArticle> result = elasticSearch.findDocumentsForTopic(searchQuery.getTopic(), 10);
        if(result.size() > 0) {
            model.addAttribute("result", result.get(0));

            WikihowArticle article = result.get(0);

            // try to get summarization from wikihow-textrank
            WikihowTextrankRequest request = new WikihowTextrankRequest(article.getArticle(), 3);
            String response = restService.sendPostRequest("http://localhost:5000/summarize", gson.toJson(request));

            if(response != null) {
                logger.info("Wikihow-Textrank Response: " + response);
                model.addAttribute("wikihowtextrank", response);
            }

//            // try to get summarization from wikihow-network
//            WikihowNetworkRequest request2 = new WikihowNetworkRequest("tim", article.getArticle());
//            String response2 = restService.sendPostRequest("http://localhost:5001/summarize", gson.toJson(request2));
//
//            if(response2 != null) {
//                logger.info("Wikihow-Network Response: " + response2);
//                model.addAttribute("wikihownetwork", response2);
//            }
//
//            // try to get summarization from wikihow-bertsum
//            WikihowBertsumRequest request3 = new WikihowBertsumRequest(article.getArticle());
//            WikihowBertsumResponse bertsumResponse = gson.fromJson(restService.sendPostRequest("http://localhost:5002/summarize", gson.toJson(request3)), WikihowBertsumResponse.class);
//
//            if(bertsumResponse!= null) {
//                logger.info("Wikihow-Bertsum Response: " + bertsumResponse.getResult());
//                model.addAttribute("wikihowbertsum", bertsumResponse.getResult());
//            }

            // try to get keywords from wikihow-keywords
            WikihowKeywordsRequest request4 = new WikihowKeywordsRequest(article.getArticle(), "eng", 5);
            WikihowKeywordResponse keywordResponse = gson.fromJson(restService.sendPostRequest("http://localhost:8090/extractKeywords", gson.toJson(request4)), WikihowKeywordResponse.class);

            if(keywordResponse != null) {
                logger.info("Wikihow-Keyword Response: " + keywordResponse.getKeywords());
                model.addAttribute("wikihowkeywords", keywordResponse.getKeywords());
            }

            // try to get named entities from wikihow-ner
            WikihowNERRequest request5 = new WikihowNERRequest(article.getArticle());
            WikihowNERResponse nerResponse = gson.fromJson(restService.sendPostRequest("http://localhost:5003/ner", gson.toJson(request5)), WikihowNERResponse.class);

            if(nerResponse != null) {
                model.addAttribute("wikihowentities", nerResponse.getEntities());
            }
        }

        return "main";
    }

}
