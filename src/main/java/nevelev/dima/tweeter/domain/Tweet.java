package nevelev.dima.tweeter.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;


@Builder
@Data
@org.springframework.data.mongodb.core.mapping.Document(collection = "tweet")
public class Tweet implements Serializable {

    @Id
    private String id;

    @Indexed
    private Date createdDate;

    @Field("name")
    private String name;

    @Field("tweet")
    private String tweet;
}
