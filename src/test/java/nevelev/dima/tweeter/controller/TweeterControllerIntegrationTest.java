package nevelev.dima.tweeter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import nevelev.dima.tweeter.TweeterApplication;
import nevelev.dima.tweeter.domain.Tweet;
import nevelev.dima.tweeter.repository.TweeterRepository;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static net.javacrumbs.jsonunit.JsonAssert.whenIgnoringPaths;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TweeterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TweeterControllerIntegrationTest {

    @MockBean
    private TweeterRepository repository;

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @Test
    public void getFeed() throws JSONException {

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

        Mockito.when(repository.findAll(PageRequest.of(0, 10, Sort.Direction.DESC, "createdDate")))
                .thenReturn(tweets);
        
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/feed"),
                HttpMethod.GET, entity, String.class);

        String expected = "[{\"id\":\"1\",\"createdDate\":\"2018-12-17T19:11:31.538+0000\",\"name\":\"foo\",\"tweet\":\"foo-tweet\"},{\"id\":\"2\",\"createdDate\":\"2018-12-17T19:11:31.538+0000\",\"name\":\"foo1\",\"tweet\":\"foo-tweet1\"},{\"id\":\"3\",\"createdDate\":\"2018-12-17T19:11:31.538+0000\",\"name\":\"foo2\",\"tweet\":\"foo-tweet2\"},{\"id\":\"4\",\"createdDate\":\"2018-12-17T19:11:31.538+0000\",\"name\":\"foo3\",\"tweet\":\"foo-tweet3\"},{\"id\":\"5\",\"createdDate\":\"2018-12-17T19:11:31.538+0000\",\"name\":\"foo4\",\"tweet\":\"foo-tweet4\"},{\"id\":\"6\",\"createdDate\":\"2018-12-17T19:11:31.538+0000\",\"name\":\"foo5\",\"tweet\":\"foo-tweet5\"},{\"id\":\"7\",\"createdDate\":\"2018-12-17T19:11:31.538+0000\",\"name\":\"foo6\",\"tweet\":\"foo-tweet6\"},{\"id\":\"8\",\"createdDate\":\"2018-12-17T19:11:31.538+0000\",\"name\":\"foo7\",\"tweet\":\"foo-tweet7\"},{\"id\":\"9\",\"createdDate\":\"2018-12-17T19:11:31.538+0000\",\"name\":\"foo8\",\"tweet\":\"foo-tweet8\"},{\"id\":\"10\",\"createdDate\":\"2018-12-17T19:11:31.538+0000\",\"name\":\"foo9\",\"tweet\":\"foo-tweet9\"}]";

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertJsonEquals(expected, response.getBody(), whenIgnoringPaths("[*].createdDate"));
    }

    @Test
    public void save() throws JSONException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        Tweet tweet = Tweet.builder().name("foo").tweet("foo-tweet").build();
        Mockito.when(repository.save(any(Tweet.class)))
                .thenAnswer((Answer<Tweet>) invocation -> {
                    Object[] arguments = invocation.getArguments();
                    if (arguments != null && arguments.length > 0 && arguments[0] != null){
                        Tweet tweet1 = (Tweet) arguments[0];
                        tweet1.setId("foo1");
                        return tweet1;
                    }
                    return null;
                });
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(tweet), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/tweet"),
                HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Gson gson = new Gson();
        Tweet found = gson.fromJson(response.getBody(), Tweet.class);
        assertNotNull(found.getId());
        assertNotNull(found.getCreatedDate());
        assertEquals(tweet.getName(), found.getName());
        assertEquals(tweet.getTweet(), found.getTweet());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}