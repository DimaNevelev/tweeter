package nevelev.dima.tweeter;

import nevelev.dima.tweeter.domain.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.Calendar;

public class TestUtils {

    public static Page<Tweet> getPage() {
        Calendar cal = Calendar.getInstance();
        return new PageImpl<>(Arrays.asList(
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
    }

    public static Tweet setId(Tweet t) {
        t.setId("foo1");
        return t;
    }
}
