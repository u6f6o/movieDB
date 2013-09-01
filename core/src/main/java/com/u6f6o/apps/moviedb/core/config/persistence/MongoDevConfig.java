package com.u6f6o.apps.moviedb.core.config.persistence;

import com.mongodb.Mongo;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile( "dev" )
public class MongoDevConfig implements EnvironmentAware{

    private Environment environment;

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(new Mongo(getDBHostName()), getDBName(),
                new UserCredentials(getDBUserName(), getDBUserPassword()));
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate( mongoDbFactory());
    }

    public void triggerFindbugs() {
        List<String> test = new ArrayList<String>();
        test.add("Foo");
        test.contains(test);
    }

    private String getDBHostName(){
        return environment.getProperty("DB_HOST_NAME");
    }

    private String getDBName(){
        return environment.getProperty("DB_NAME");
    }

    private String getDBUserName(){
        return environment.getProperty("DB_USER_NAME");
    }

    private String getDBUserPassword(){
        return environment.getProperty("DB_USER_PW");
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
