package nevelev.dima.tweeter.configuration;

import com.mongodb.MongoClient;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;

@Configuration
@Profile("dev")
@EnableMongoRepositories(value = "nevelev.dima.tweeter.repository")
public class DevMongoConfiguration extends AbstractMongoConfiguration {

    private static final String MONGO_DB_URL = "localhost";
    private static final String MONGO_DB_NAME = "embeded_db";

    @Override
    public MongoClient mongoClient() {
        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
        mongo.setBindIp(MONGO_DB_URL);
        MongoClient mongoClient;
        try {
            mongoClient = mongo.getObject();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to start in memory mongoDb instance", e);
        }
        return mongoClient;
    }

    @Override
    protected String getDatabaseName() {
        return MONGO_DB_NAME;
    }
}


