package nevelev.dima.tweeter.service;

import nevelev.dima.tweeter.domain.Tweet;
import nevelev.dima.tweeter.repository.TweeterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;

@Service
public class TweeterService {

    @Autowired
    private TweeterRepository tweeterRepository;

    public Tweet save(@Valid @RequestBody Tweet tweet) {
        Calendar cal = Calendar.getInstance();
        tweet.setCreatedDate(cal.getTime());
        return tweeterRepository.save(tweet);
    }

    public List<Tweet> getLastTenTweets() {
        Page<Tweet> page = tweeterRepository.findAll(PageRequest.of(0, 10, Sort.Direction.DESC, "createdDate"));
        return page.getContent();
    }
}
