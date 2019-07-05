package de.bigabig.wikihowqa.controller;

import com.google.gson.Gson;
import de.bigabig.wikihowqa.dao.RatingDao;
import de.bigabig.wikihowqa.dao.SummaryDao;
import de.bigabig.wikihowqa.model.BetterSummary;
import de.bigabig.wikihowqa.model.DatePoint;
import de.bigabig.wikihowqa.model.Rating;
import de.bigabig.wikihowqa.model.RatingsOverTimeResponse;
import de.bigabig.wikihowqa.model.rest.*;
import de.bigabig.wikihowqa.model.service.*;
import de.bigabig.wikihowqa.service.ElasticSearchService;
import de.bigabig.wikihowqa.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class APIController {

    @Autowired
    RestService restService;

    @Autowired
    ElasticSearchService elasticSearch;

    @Autowired
    RatingDao ratingDao;

    @Autowired
    SummaryDao summaryDao;

    private Gson gson = new Gson();

    @CrossOrigin
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
        return ResponseEntity.ok(new SummarizationResponse());
    }

    @CrossOrigin
    @PostMapping("/keywords")
    public ResponseEntity<WikihowKeywordResponse> keywords(@RequestBody WikihowKeywordsRequest request) {
        WikihowKeywordResponse keywordResponse = gson.fromJson(restService.sendPostRequest("http://localhost:8090/extractKeywords", gson.toJson(request)), WikihowKeywordResponse.class);

        if(keywordResponse != null) {
            return ResponseEntity.ok(keywordResponse);
        }

        return ResponseEntity.ok(new WikihowKeywordResponse());
    }

    @CrossOrigin
    @PostMapping("/ner")
    public ResponseEntity<WikihowNERResponse> ner(@RequestBody WikihowNERRequest request) {
        WikihowNERResponse nerResponse = gson.fromJson(restService.sendPostRequest("http://localhost:5003/ner", gson.toJson(request)), WikihowNERResponse.class);

        if(nerResponse != null) {
            return ResponseEntity.ok(nerResponse);
        }

        return ResponseEntity.ok(new WikihowNERResponse());
    }

    @CrossOrigin
    @PostMapping("/articles")
    public ResponseEntity<ElasticArticleResponse> articles(@RequestBody ElasticArticleRequest request) {
        if(request.getCount() <= 0 ) {
            return ResponseEntity.badRequest().body(new ElasticArticleResponse());
        }

        ElasticArticleResponse response = new ElasticArticleResponse(elasticSearch.findDocumentsForTopic(request.getTopic(), request.getCount(), request.getElasticindex()));
        if(!response.getArticles().isEmpty()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok(new ElasticArticleResponse());
    }

    @CrossOrigin
    @PostMapping("/rate")
    public ResponseEntity<MessageResponse> rate(@RequestBody Rating rating) {
        rating.setTimestamp(new Date());
        ratingDao.save(rating);
        return ResponseEntity.ok(new MessageResponse("Rating added!"));
    }

    @CrossOrigin
    @GetMapping("/ratings")
    public ResponseEntity getRatings(@RequestParam String method) {
        List<Rating> ratings = ratingDao.findAllByMethod(method);
        int[] result = new int[5];
        for(Rating rating : ratings) {
            if(rating.getRating() > 0 && rating.getRating() <= 5) {
                result[rating.getRating()-1]++;
            }
        }
        return ResponseEntity.ok(result);
    }

    @CrossOrigin
    @GetMapping("/ratingsOverTime")
    public ResponseEntity<RatingsOverTimeResponse> getRatingsOverTime(@RequestParam String method) {
        List<Rating> ratings = ratingDao.findAllByMethod(method);
        List<Date> labels = new ArrayList<Date>();
        List<DatePoint> data = new ArrayList<DatePoint>();
        for(Rating rating : ratings) {
            labels.add(rating.getTimestamp());
            data.add(new DatePoint(rating.getTimestamp(), rating.getRating()));
        }
        return ResponseEntity.ok(new RatingsOverTimeResponse(labels, data));
    }

    @CrossOrigin
    @PostMapping("/sum")
    public ResponseEntity<MessageResponse> sum(@RequestBody BetterSummary summary) {
        summary.setTimestamp(new Date());
        summaryDao.save(summary);
        return ResponseEntity.ok(new MessageResponse("Summary added!"));
    }

    @CrossOrigin
    @GetMapping("/summaries")
    public ResponseEntity getSummaries(@RequestParam String method) {
        List<BetterSummary> summaries = summaryDao.findAllByMethod(method);
        return ResponseEntity.ok(summaries);
    }

    @CrossOrigin
    @GetMapping("/suggest")
    public ResponseEntity suggest(@RequestParam String text, @RequestParam int count) {
        return ResponseEntity.ok(elasticSearch.findSuggestions(text, count, "autohow"));
    }
}
