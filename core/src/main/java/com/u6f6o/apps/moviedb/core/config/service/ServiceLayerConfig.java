package com.u6f6o.apps.moviedb.core.config.service;


import com.u6f6o.apps.moviedb.core.config.persistence.PersistenceLayerConfig;
import com.u6f6o.apps.moviedb.core.service.MovieLoaderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 * Author: u6f6o
 */
@Configuration
@Import({ com.u6f6o.apps.moviedb.ext_apis.base.config.ServiceLayerConfig.class,
        PersistenceLayerConfig.class})
@ComponentScan( basePackageClasses = { MovieLoaderService.class })
public class ServiceLayerConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
