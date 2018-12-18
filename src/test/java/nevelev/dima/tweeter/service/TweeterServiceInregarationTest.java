package nevelev.dima.tweeter.service;

import nevelev.dima.tweeter.TestUtils;
import nevelev.dima.tweeter.domain.Tweet;
import nevelev.dima.tweeter.repository.TweeterRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
public class TweeterServiceInregarationTest {

    @TestConfiguration
    static class TweeterServiceTestContextConfiguration {

        @Bean
        public TweeterService tweeterService() {
            return new TweeterService();
        }
    }

    @Autowired
    private TweeterService tweeterService;

    @MockBean
    private TweeterRepository tweeterRepository;

    @Test
    public void save() {
        Tweet tweet = Tweet.builder().name("foo").tweet("foo-tweet").build();
        Mockito.when(tweeterRepository.save(tweet))
                .thenReturn(TestUtils.setId(tweet));

        Tweet found = tweeterService.save(tweet);
        assertNotNull(found.getId());
        assertNotNull(found.getCreatedDate());
        assertEquals(tweet.getName(), found.getName());
        assertEquals(tweet.getTweet(), found.getTweet());
    }

    @Test
    public void getLastTenTweets() {
        Calendar cal = Calendar.getInstance();
        Page<Tweet> tweets = new PageImpl<>(Arrays.asList(
                Tweet.builder().id("1").name("foo").tweet("foo-tweet").createdDate(cal.getTime()).build(),
                Tweet.builder().id("2").name("foo1").tweet("foo-tweet1").createdDate(cal.getTime()).build(),
                Tweet.builder().id("3").name("foo2").tweet("foo-tweet2").createdDate(cal.getTime()).build(),
                Tweet.builder().id("4").name("foo3").tweet("foo-tweet3").createdDate(cal.getTime()).build(),
                Tweet.builder().id("5").name("foo4").tweet("foo-tweet4").createdDate(cal.getTime()).build(),
                Tweet.builder().id("6").name("foo5").tweet("foo-tweet5").createdDate(cal.getTime()).build(),
                Tweet.builder().id("7").name("foo6").tweet("foo-tweet6").createdDate(cal.getTime()).build(),
                Tweet.builder().id("8").name("foo7").tweet("foo-tweet7").createdDate(cal.getTime()).build(),
                Tweet.builder().id("9").name("foo8").tweet("foo-tweet8").createdDate(cal.getTime()).build(),
                Tweet.builder().id("10").name("foo9").tweet("foo-tweet9").createdDate(cal.getTime()).build()));

        Mockito.when(tweeterRepository.findAll(PageRequest.of(0, 10, Sort.Direction.DESC, "createdDate")))
                .thenReturn(tweets);

        List<Tweet> found = tweeterService.getLastTenTweets();
        assertEquals(tweets.getTotalElements(), found.size());
    }
}