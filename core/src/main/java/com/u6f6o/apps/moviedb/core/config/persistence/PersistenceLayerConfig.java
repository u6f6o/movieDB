package com.u6f6o.apps.moviedb.core.config.persistence;

import com.u6f6o.apps.moviedb.core.api.movie.Movie;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@Import({ MongoDevConfig.class, MongoProdConfig.class })
@ComponentScan( basePackageClasses = {
        /* entities location */ Movie.class,
        /* validators location */ com.u6f6o.apps.moviedb.core.validation.BeforeSafeValidator.class,
        /* repositories location */ com.u6f6o.apps.moviedb.core.repository.Repository.class
        })
public class PersistenceLayerConfig {

    @Bean( name = "entityValidator", autowire = Autowire.BY_NAME )
    public LocalValidatorFactoryBean entityValidator() {
        return new LocalValidatorFactoryBean();
    }
}
