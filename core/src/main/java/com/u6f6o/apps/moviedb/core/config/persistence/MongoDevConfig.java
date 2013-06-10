package com.u6f6o.apps.moviedb.core.config.persistence;

import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
@Profile( "dev" )
public class MongoDevConfig {
    private static final String DB_NAME = "devMovieDB";
    private static final String DB_HOST = "127.0.0.1";

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory( new Mongo( DB_HOST ), DB_NAME );
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate( mongoDbFactory());
    }
}
