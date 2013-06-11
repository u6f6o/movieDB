package com.u6f6o.apps.moviedb.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created with IntelliJ IDEA.
 * Author: u6f6o
 */
@Configuration
@Import( WebLayerConfig.class )
@EnableWebMvc
public class ServletConfig {
}
