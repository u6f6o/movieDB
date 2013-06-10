package com.u6f6o.apps.moviedb.ext_apis.themoviedb.sandbox

import com.u6f6o.apps.moviedb.ext_apis.base.config.ServiceLayerConfig
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.config.TheMovieDBConfig
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.client.RestTemplate
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author u6f6o
 */
@ActiveProfiles( "dev" )
@ContextConfiguration( classes = ServiceLayerConfig.class )
class TheMovieDBApiSpec extends Specification {

    @Autowired
    def TheMovieDBConfig config;
    @Autowired
    def RestTemplate restTemplate;


    @Unroll("search for '#movieTitle' should return #resultSize results")
    def "search movie and check result size"() {

        setup:
            def url = "${config.apiURL}search/movie?api_key=${config.apiKey}&query=${movieTitle}"
        when:
            def resultAsJSON = new JsonSlurper().parseText(url.toURL().text)
        then:
            resultAsJSON.total_results == resultSize
            resultAsJSON.results.any{it.title.equals(movieTitle)}
        where:
            movieTitle  | resultSize
            "Machete"   | 6
    }

    @Unroll("fetch by id '#movieId' should return '#movieTitle'")
    def "fetch movie by id and check content"(){

        setup:
            def url = "${config.apiURL}movie/${movieId}?api_key=${config.apiKey}"
        when:
            def resultAsJSON = new JsonSlurper().parseText(url.toURL().text)
        then:
            movieTitle.equals(resultAsJSON.title)
        where:
            movieId | movieTitle
            "1422"  | "The Departed"
            "922"   | "Dead Man"
    }

    @Unroll("'#title' should have '#mainActor' as main actor")
    def "fetch actors for given movie"(){

        setup:
            def url = "${config.apiURL}movie/${movieId}/casts?api_key=${config.apiKey}"
        expect:
            def castAsJSON = new JsonSlurper().parseText(url.toURL().text)

            castAsJSON.cast.size() == nrOfActors
            castAsJSON.crew.size() == nrOfCrewMembers
            castAsJSON.cast.any{ it.name == mainActor }
        where:
            movieId | title        | nrOfActors | nrOfCrewMembers | mainActor
            "1422"  | "Inception"  | 16         | 10              | "Leonardo DiCaprio"
            "23631" | "Machete"    | 13         | 18              | "Danny Trejo"
            "7549"  | "Fearless"   | 15         | 9               | "Jet Li"
    }

    @Unroll("fetch for upcoming movies should return more than #expectedMinResultSize movies")
    def "fetch upcoming movies"(){

        setup:
            def url = "${config.apiURL}movie/upcoming?api_key=${config.apiKey}"
        expect:
            def upcomingMoviesAsJSON = new JsonSlurper().parseText(url.toURL().text)

            upcomingMoviesAsJSON != null
            upcomingMoviesAsJSON.results.size == resultCountFirstPage
            upcomingMoviesAsJSON.total_results > expectedMinResultSize
        where:
            resultCountFirstPage = 20
            expectedMinResultSize = 50
    }
}
