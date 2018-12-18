package nevelev.dima.tweeter.repository;

import nevelev.dima.tweeter.domain.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface TweeterRepository extends MongoRepository<Tweet, String> {
}
