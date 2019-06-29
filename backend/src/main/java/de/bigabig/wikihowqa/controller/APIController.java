package de.bigabig.wikihowqa.controller;

import com.google.gson.Gson;
import de.bigabig.wikihowqa.model.rest.ElasticArticleRequest;
import de.bigabig.wikihowqa.model.rest.ElasticArticleResponse;
import de.bigabig.wikihowqa.model.rest.SummarizationRequest;
import de.bigabig.wikihowqa.model.rest.SummarizationResponse;
import de.bigabig.wikihowqa.model.service.*;
import de.bigabig.wikihowqa.service.ElasticSearchService;
import de.bigabig.wikihowqa.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIController {

    @Autowired
    RestService restService;

    @Autowired
    ElasticSearchService elasticSearch;

    private Gson gson = new Gson();

    @PostMapping("/summarize")
    public ResponseEntity<SummarizationResponse> summarize(@RequestBody SummarizationRequest request) {
        switch (request.getMethod()) {
            case "textrank":
                // try to get summarization from wikihow-textrank
                WikihowTextrankRequest textrankRequest = new WikihowTextrankRequest(request.getText(), 3);
                String textrankResponse = restService.sendPostRequest("http://localhost:5000/summarize", gson.toJson(textrankRequest));
                if(textrankResponse != null) {
                    return ResponseEntity.ok(new SummarizationResponse(textrankResponse));
                }
                break;
            case "network":
                // try to get summarization from wikihow-network
                WikihowNetworkRequest networkRequest = new WikihowNetworkRequest("tim", request.getText());
                String networkResponse = restService.sendPostRequest("http://localhost:5001/summarize", gson.toJson(networkRequest));
                if(networkResponse != null) {
                    return ResponseEntity.ok(new SummarizationResponse(networkResponse));
                }
                break;
            case "bertsum":
                // try to get summarization from wikihow-bertsum
                WikihowBertsumRequest request3 = new WikihowBertsumRequest(request.getText());
                WikihowBertsumResponse bertsumResponse = gson.fromJson(restService.sendPostRequest("http://localhost:5002/summarize", gson.toJson(request3)), WikihowBertsumResponse.class);
                if(bertsumResponse!= null) {
                    return ResponseEntity.ok(new SummarizationResponse(bertsumResponse.getResult()));
                }
                break;
            default:
                return ResponseEntity.badRequest().body(new SummarizationResponse("Please use 'textrank', 'network' or 'bertsum' as method :)"));
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/keywords")
    public ResponseEntity<WikihowKeywordResponse> keywords(@RequestBody WikihowKeywordsRequest request) {
        WikihowKeywordResponse keywordResponse = gson.fromJson(restService.sendPostRequest("http://localhost:8090/extractKeywords", gson.toJson(request)), WikihowKeywordResponse.class);

        if(keywordResponse != null) {
            return ResponseEntity.ok(keywordResponse);
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/ner")
    public ResponseEntity<WikihowNERResponse> ner(@RequestBody WikihowNERRequest request) {
        WikihowNERResponse nerResponse = gson.fromJson(restService.sendPostRequest("http://localhost:5003/ner", gson.toJson(request)), WikihowNERResponse.class);

        if(nerResponse != null) {
            return ResponseEntity.ok(nerResponse);
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/articles")
    public ResponseEntity<ElasticArticleResponse> articles(@RequestBody ElasticArticleRequest request) {
        if(request.getCount() <= 0 ) {
            return ResponseEntity.badRequest().body(new ElasticArticleResponse());
        }

        ElasticArticleResponse response = new ElasticArticleResponse(elasticSearch.findDocumentsForTopic(request.getTopic(), request.getCount(), request.getElasticindex()));
        if(!response.getArticles().isEmpty()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.noContent().build();
    }
}
