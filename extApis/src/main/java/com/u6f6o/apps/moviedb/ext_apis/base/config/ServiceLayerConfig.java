package com.u6f6o.apps.moviedb.ext_apis.base.config;

import com.u6f6o.apps.moviedb.ext_apis.themoviedb.config.TheMovieDBConfig;
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.service.TheMovieDBService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author u6f6o
 */
@Configuration
@ComponentScan( basePackageClasses = {
        TheMovieDBConfig.class,
        TheMovieDBService.class
})
public class ServiceLayerConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
