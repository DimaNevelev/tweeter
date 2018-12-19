package nevelev.dima.tweeter.configuration;

import com.mongodb.MongoClient;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.xbill.DNS.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@Configuration
@Profile("prod")
@EnableMongoRepositories(value = "nevelev.dima.tweeter.repository")
public class CustomMongoConfiguration extends AbstractMongoConfiguration {

    @Value("${spring.data.mongodb.database}")
    private String mongoDB;

    @Value("${spring.data.mongodb.port:#{null}}")
    private Integer port;

    @Value("${spring.consul.mongodnsname}")
    private String dnsName;

    @Override
    public MongoMappingContext mongoMappingContext()
            throws ClassNotFoundException {
        return super.mongoMappingContext();
    }

    @Override
    protected String getDatabaseName() {
        return mongoDB;
    }

    @Override
    public MongoClient mongoClient() {
        Lookup lookup;
        try {
            lookup = new Lookup(dnsName, Type.A);
        } catch (TextParseException e) {
            throw new IllegalArgumentException("Failed to parse DNS name: " + dnsName, e);
        }
        Record[] records = lookup.run();
        return getMongoClientFromRecords(records);
    }

    private MongoClient getMongoClientFromRecords(Record[] records) {
        if (ArrayUtils.isEmpty(records)) {
            throw new IllegalStateException("Failed to find address using the provided DNS: " + dnsName);
        }
        for (Record record : records) {
            String address = ((ARecord)record).getAddress().getHostAddress();
            if (pingHost(address, getPort(),5000)) {
                return new MongoClient(address, getPort());
            }
        }
        // Return first record client
        return new MongoClient(((ARecord)records[0]).getAddress().getHostAddress(), getPort());
    }

    private Integer getPort() {
        return port == null ? MongoProperties.DEFAULT_PORT : this.port;
    }

    public static boolean pingHost(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeout);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}


