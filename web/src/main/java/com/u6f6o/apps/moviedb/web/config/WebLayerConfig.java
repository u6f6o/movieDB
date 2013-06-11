package com.u6f6o.apps.moviedb.web.config;

import com.u6f6o.apps.moviedb.core.config.service.ServiceLayerConfig;
import com.u6f6o.apps.moviedb.web.rest.MovieController;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created with IntelliJ IDEA.
 * Author: u6f6o
 */
@Configuration
@Import( ServiceLayerConfig.class )
@ComponentScan( basePackageClasses =  {
    MovieController.class
})
public class WebLayerConfig {

}
