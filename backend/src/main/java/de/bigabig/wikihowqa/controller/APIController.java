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
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

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


    @Value("${servicener}")
    private String NER_URL;

    @Value("${servicekeyword}")
    private String KEYWORD_URL;

    @Value("${servicetextrank}")
    private String TEXTRANK_URL;

    @Value("${servicebertsum}")
    private String BERTSUM_URL;

    @Value("${servicenetwork}")
    private String NETWORK_URL;

    @Value("${serviceeval}")
    private String EVAL_URL;

    private Gson gson = new Gson();
    private StanfordCoreNLP pipeline;


    @PostConstruct
    public void init() {
        // set up pipeline properties
        Properties props = new Properties();
        // set the list of annotators to run
//        props.setProperty("annotators", "tokenize,ssplit,truecase,pos,lemma,ner");
        props.setProperty("annotators", "tokenize,ssplit,truecase");
        // set a property for an annotator, in this case the coref annotator is being set to use the neural algorithm
        props.setProperty("truecase.overwriteText", "true");
        // build pipeline
        pipeline = new StanfordCoreNLP(props);
    }

    @CrossOrigin
    @PostMapping("/summarize")
    public ResponseEntity<SummarizationResponse> summarize(@RequestBody SummarizationRequest request) {
        switch (request.getMethod()) {
            case "textrank":
                // try to get summarization from wikihow-textrank
                WikihowTextrankRequest textrankRequest = new WikihowTextrankRequest(request.getText(), 3);
                String textrankResponse = restService.sendPostRequest(TEXTRANK_URL + "/summarize", gson.toJson(textrankRequest));
                if(textrankResponse != null) {
                    return ResponseEntity.ok(new SummarizationResponse(textrankResponse));
                }
                break;
            case "network":
                // try to get summarization from wikihow-network
                WikihowNetworkRequest networkRequest = new WikihowNetworkRequest("tim", request.getText());
                String networkResponse = restService.sendPostRequest(NETWORK_URL + "/summarize", gson.toJson(networkRequest));

                if(networkResponse != null) {
                    return ResponseEntity.ok(new SummarizationResponse(postProcessNetwork(networkResponse)));
                }
                break;
            case "bertsum":
                // try to get summarization from wikihow-bertsum
                WikihowBertsumRequest request3 = new WikihowBertsumRequest(request.getText());
                WikihowBertsumResponse bertsumResponse = gson.fromJson(restService.sendPostRequest(BERTSUM_URL + "/summarize", gson.toJson(request3)), WikihowBertsumResponse.class);
                if(bertsumResponse!= null) {
                    return ResponseEntity.ok(new SummarizationResponse(postProcessNetwork(bertsumResponse.getResult())));
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
        WikihowKeywordResponse keywordResponse = gson.fromJson(restService.sendPostRequest(KEYWORD_URL + "/extractKeywords", gson.toJson(request)), WikihowKeywordResponse.class);

        if(keywordResponse != null) {
            return ResponseEntity.ok(keywordResponse);
        }

        return ResponseEntity.ok(new WikihowKeywordResponse());
    }

    @CrossOrigin
    @PostMapping("/ner")
    public ResponseEntity<WikihowNERResponse> ner(@RequestBody WikihowNERRequest request) {
        WikihowNERResponse nerResponse = gson.fromJson(restService.sendPostRequest(NER_URL + "/ner", gson.toJson(request)), WikihowNERResponse.class);

        if(nerResponse != null) {
            return ResponseEntity.ok(nerResponse);
        }

        return ResponseEntity.ok(new WikihowNERResponse());
    }

    @CrossOrigin
    @PostMapping("/evaluate")
    public ResponseEntity<WikihowEvalResponse> evaluate(@RequestBody WikihowEvalRequest request) {
        WikihowEvalResponse evalResponse = gson.fromJson(restService.sendPostRequest(EVAL_URL + "/evaluate", gson.toJson(request)), WikihowEvalResponse.class);

        if(evalResponse != null) {
            return ResponseEntity.ok(evalResponse);
        }

        return ResponseEntity.ok(new WikihowEvalResponse());
    }

    @CrossOrigin
    @PostMapping("/articles")
    public ResponseEntity<ElasticArticleResponse> articles(@RequestBody ElasticArticleRequest request) {
        if(request.getCount() <= 0 ) {
            return ResponseEntity.badRequest().body(new ElasticArticleResponse());
        }

        ElasticArticleResponse response;
        if(request.getElasticindex() != null) {
            response = new ElasticArticleResponse(elasticSearch.findDocumentsForTopic(request.getTopic(), request.getCount(), request.getElasticindex()));
        } else {
            response = new ElasticArticleResponse(elasticSearch.findDocumentsForTopic(request.getTopic(), request.getCount()));
        }

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
        List<Object[]> ratings = ratingDao.findAverage(method);


//        List<Rating> ratings = ratingDao.findAllByMethod(method);
        List<Date> labels = new ArrayList<Date>();
        List<DatePoint> data = new ArrayList<DatePoint>();
//        for(Rating rating : ratings) {
//            labels.add(rating.getTimestamp());
//            data.add(new DatePoint(rating.getTimestamp(), rating.getRating()));
//        }


        for(Object[] rating : ratings) {
            Calendar c = Calendar.getInstance();
            c.set((int) rating[3], (int) rating[2] - 1, (int) rating[1], 0, 0);
            labels.add(c.getTime());
            data.add(new DatePoint(c.getTime(), (double) rating[0]));
        }
        labels.sort(Date::compareTo);
        data.sort(Comparator.comparing(DatePoint::getT));
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
        return ResponseEntity.ok(elasticSearch.findSuggestions(text, count));
    }

    private String fixStuff(String text) {
        // Fix Stuff
        String result = text.replaceAll("\\s’\\s", "’");
        result = result.replaceAll("\\s,\\s", ", ");
        result = result.replaceAll("\\s:\\s", ": ");
        result = result.replaceAll("\\sn't", "n't");
        result = result.replaceAll("\\s``\\s", " \"");
        result = result.replaceAll("\\s''\\s", "\" ");
        result = result.replaceAll("\\s'", "'");
        result = result.replaceAll("\\s%", "%");

        // fix brakets
        result = result.replaceAll("\\s\\(\\s", " (");
        result = result.replaceAll("\\s\\[\\s", " [");
        result = result.replaceAll("\\s\\{\\s", " {");
        result = result.replaceAll("\\s\\)\\s*", ") ");
        result = result.replaceAll("\\s]\\s*", "] ");
        result = result.replaceAll("\\s}\\s*", "} ");

        // Fix dots
        result = result.replaceAll("\\s+\\.\\s+", ".");
        result = result.replaceAll("\\s+\\.", ".");
        result = result.replaceAll("\\.\\s+", ".");
        result = result.replaceAll("\\.", ". ");

        // Fix question marks
        result = result.replaceAll("\\s+\\?\\s+", "?");
        result = result.replaceAll("\\s+\\?", "?");
        result = result.replaceAll("\\?\\s+", "?");
        result = result.replaceAll("\\?", "? ");

        // Fix exclamation marks
        result = result.replaceAll("\\s+!\\s+", "!");
        result = result.replaceAll("\\s+!", "!");
        result = result.replaceAll("!\\s+", "!");
        result = result.replaceAll("!", "! ");

        return result.trim();
    }

    private String postProcessNetwork(String summary) {

        // Fix input
        String result = fixStuff(summary);
        System.out.println("Fixed Input:");
        System.out.println(result);

        // Truecase everything
        CoreDocument document = new CoreDocument(result);
        pipeline.annotate(document);

        List<String> collect = document.sentences().stream().map(CoreSentence::tokens)
                .flatMap(coreLabels -> coreLabels.stream().map(coreLabel ->
                    coreLabel.word().length() > coreLabel.originalText().length() ? coreLabel.originalText() : coreLabel.word()
                ))
                .collect(Collectors.toList());
        System.out.println("All stuff:");
        System.out.println(collect);
        String truecaseResult = String.join(" ", collect);

        List<String> t = document.sentences().stream().map(CoreSentence::tokens)
                .flatMap(coreLabels -> coreLabels.stream().map(CoreLabel::word))
                .collect(Collectors.toList());
        System.out.println("Tokens:");
        System.out.println(t);

        // Fix output
        truecaseResult = fixStuff(truecaseResult);
        System.out.println("Fixed Output:");
        System.out.println(truecaseResult);



//        // list of the part-of-speech tags for the second sentence
//        List<CoreLabel> tokens = sentence.tokens();
//        System.out.println("Example: tokens");
//        System.out.println(tokens);
//        System.out.println();
//
//        // list of the part-of-speech tags for the second sentence
//        List<String> posTags = sentence.posTags();
//        System.out.println("Example: pos tags");
//        System.out.println(posTags);
//        System.out.println();
//
//        // list of the ner tags for the second sentence
//        List<String> nerTags = sentence.nerTags();
//        System.out.println("Example: ner tags");
//        System.out.println(nerTags);
//        System.out.println();

        return truecaseResult;
    }
}
