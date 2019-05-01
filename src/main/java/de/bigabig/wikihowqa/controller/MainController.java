package de.bigabig.wikihowqa.controller;

import com.google.gson.Gson;
import de.bigabig.wikihowqa.model.SearchQuery;
import de.bigabig.wikihowqa.model.WikihowArticle;
import de.bigabig.wikihowqa.model.WikihowTextrankRequest;
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
        }

        return "main";
    }

}