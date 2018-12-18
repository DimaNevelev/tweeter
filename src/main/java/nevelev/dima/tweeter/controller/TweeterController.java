package nevelev.dima.tweeter.controller;

import nevelev.dima.tweeter.domain.Tweet;
import nevelev.dima.tweeter.service.TweeterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
public class TweeterController {

    private final Logger log = LoggerFactory.getLogger(TweeterController.class);

    @Autowired
    private TweeterService tweeterService;

    @PostMapping("/tweet")
    public Tweet save(@Valid @RequestBody Tweet tweet) {
        log.info("REST request to save tweet: " + tweet.toString());
        tweet = tweeterService.save(tweet);
        log.debug("Saved tweet: " + tweet.toString());
        return tweet;
    }

    @GetMapping("/feed")
    public List<Tweet> getLastTenTweets() {
        log.debug("REST request to get 10 tweets");
        List<Tweet> tweets = tweeterService.getLastTenTweets();
        log.debug("Returning top 10 tweets: " + Arrays.toString(tweets.toArray()));
        return tweets;
    }
}
