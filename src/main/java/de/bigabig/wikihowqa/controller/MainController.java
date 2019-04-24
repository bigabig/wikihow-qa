package de.bigabig.wikihowqa.controller;

import de.bigabig.wikihowqa.model.SearchQuery;
import de.bigabig.wikihowqa.model.WikihowArticle;
import de.bigabig.wikihowqa.service.ElasticSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    ElasticSearchService elasticSearch;

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
        }

        return "main";
    }

}