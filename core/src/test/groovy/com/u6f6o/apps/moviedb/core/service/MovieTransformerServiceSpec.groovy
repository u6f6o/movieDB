package com.u6f6o.apps.moviedb.core.service

import com.u6f6o.apps.moviedb.core.api.movie.Genre
import com.u6f6o.apps.moviedb.core.api.movie.Movie
import com.u6f6o.apps.moviedb.core.api.movie.cast.Actor
import com.u6f6o.apps.moviedb.core.api.movie.cast.Cast
import com.u6f6o.apps.moviedb.core.api.movie.cast.CrewMember
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain.TheMovieDBActor
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain.TheMovieDBCast
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain.TheMovieDBCrewMember
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain.TheMovieDBGenre
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain.TheMovieDBMovie
import com.u6f6o.apps.moviedb.samples.CastSample
import com.u6f6o.apps.moviedb.samples.MovieSample;
import org.apache.commons.lang.math.RandomUtils
import org.joda.time.DateTime
import spock.lang.Specification
import spock.lang.Unroll

import static com.u6f6o.apps.moviedb.core.api.movie.Genre.*;

/**
 * @author u6f6o
 */
class MovieTransformerServiceSpec extends Specification{

    final static MovieTransformerService TRANSFORMER_SERVICE = new MovieTransformerService()


    def "should transform movie 'Machete'"(){

        given:
            Movie result = Mock(Movie)
            MovieTransformerService service = new MovieTransformerService(){
                @Override
                protected Movie createMovie(Long id) {
                    return result
                }
            }
            movie.cast = cast
        when:
            service.transform(movie)
        then:
            1 * result.setTitle("Machete")
            1 * result.setDescription({it.startsWith("Former Mexican Federale Machete")})
            1 * result.setReleaseDate(new DateTime().withDate(2010, 9, 2).toLocalDate().toDate())
            1 * result.setRuntime(105)
            1 * result.setGenres(new LinkedHashSet<Genre>([ACTION, COMEDY, THRILLER]))
            1 * result.setCast({it.actors.size() == 13 && it.crew.size() == 18})
            1 * result.setWebSite("http://www.vivamachete.com/")
            0 * _
        movie != null

        where:
            movie = MovieSample.MACHETE.readMovieFromJSON(TheMovieDBMovie.class)
            cast = CastSample.MACHETE.readCastFromJSON(TheMovieDBCast.class)
    }

    def "should transform empty movie"(){

        given:
            Movie result = Mock(Movie)
            MovieTransformerService service = new MovieTransformerService(){
                @Override
                protected Movie createMovie(Long id) {
                    return result
                }
            }
        when:
            def movie = service.transform(new TheMovieDBMovie())
        then:
            1 * result.setTitle(null)
            1 * result.setDescription(null)
            1 * result.setReleaseDate(null)
            1 * result.setRuntime(null)
            1 * result.setGenres(null)
            1 * result.setCast(null)
            1 * result.setWebSite(null)
            0 * _
            movie != null
    }


    @Unroll("should transform TheMovieDBGenre '#genre'")
    def "should transform TheMovieDBGenre"(){

        given:
            TheMovieDBGenre theMovieDBGenre = new TheMovieDBGenre()
            theMovieDBGenre.name = genre
        when:
            def result = TRANSFORMER_SERVICE.transformGenre(theMovieDBGenre)
        then:
            result == expectedGenre
        where:
            genre << [ "Thriller", "Road Movie", "Neo-noir", "Sporting Event" ]
            expectedGenre << [ THRILLER, ROAD_MOVIE, NEO_NOIR, SPORTING_EVENT ]
    }

    def "should transform empty TheMovieDBGenre list"(){

        expect:
            TRANSFORMER_SERVICE.transformGenres(null) == null
            TRANSFORMER_SERVICE.transformGenres(new LinkedHashSet<TheMovieDBGenre>()) == null
    }

    def "should transform TheMovieDBGenre list"(){

        given:
            def theMovieDBGenres = new LinkedHashSet<TheMovieDBGenre>(input)
        when:
            def result = TRANSFORMER_SERVICE.transformGenres(theMovieDBGenres)
            def resultAsList = new ArrayList<Genre>(result)
        then:
            result.size() == 3
            resultAsList[0] == THRILLER
            resultAsList[1] == ROAD_MOVIE
            resultAsList[2] == NEO_NOIR
        where:
            input << [[ newGenre("Thriller"), newGenre("Road Movie"), newGenre("Neo-noir") ]]
    }

    def "should transform empty TheMovieDBCast"(){

        expect:
            TRANSFORMER_SERVICE.transformCast(null) == null
    }

    def "should transform TheMovieDBCast"(){

        given:
            def theMovieDBCast = new TheMovieDBCast()
            theMovieDBCast.actors = new LinkedHashSet<TheMovieDBActor>(actors)
            theMovieDBCast.crew = new LinkedHashSet<TheMovieDBCrewMember>(crew)
        when:
            Cast cast = TRANSFORMER_SERVICE.transformCast(theMovieDBCast)
        then:
            cast.actors.size() == 2
            cast.crew.size() == 1
        where:
            actors << [[ newActor("Leonardo DiCaprio", "Dominic Cobb", 1), newActor("Ellen Page", "Ariadne", 2) ]]
            crew <<  [[ newCrewMember("Robert Rodriguez", "Directing", "Director") ]]
    }

    def "should transform empty TheMovieDBCrewMember list"(){

        expect:
            TRANSFORMER_SERVICE.transformCrew(null) == null
            TRANSFORMER_SERVICE.transformCrew(new LinkedHashSet<TheMovieDBCrewMember>()) == null
    }

    def "should transform TheMovieDBCrewMember list"(){

        given:
            def theMovieDBCrewMembers = new LinkedHashSet<TheMovieDBCrewMember>(input)
        when:
            def output = TRANSFORMER_SERVICE.transformCrew(theMovieDBCrewMembers)
            def outputAsList = new ArrayList<CrewMember>(output)
        then:
            output.size() == 2

            outputAsList[0].realName == "Robert Rodriguez"
            outputAsList[0].department == "Directing"
            outputAsList[0].job == "Director"

            outputAsList[1].realName == "Ethan Mainguis"
            outputAsList[1].department == "Directing"
            outputAsList[1].job == "Director"
        where:
            input = [   newCrewMember("Robert Rodriguez", "Directing", "Director"),
                        newCrewMember("Ethan Mainguis", "Directing", "Director")]
    }

    @Unroll("should transform TheMovieDBCrewMember '#name'")
    def "transform TheMovieDBCrewMember"(){

        given:
            def theMovieDBCrewMember = new TheMovieDBCrewMember()
            theMovieDBCrewMember.name = name
            theMovieDBCrewMember.department = department
            theMovieDBCrewMember.job = job
        when:
            def internalCrewMember = TRANSFORMER_SERVICE.transformCrewMember(theMovieDBCrewMember)
        then:
            internalCrewMember.realName == name
            internalCrewMember.department == department
            internalCrewMember.job == job
        where:
            name                | department    | job
            null                | null          | null
            "Robert Rodriguez"  | "Directing"   | "Director"
            "Ethan Maniquis"    | "Directing"   | "Director"
            "Elizabeth Avellan" | "Production"  | "Producer"
    }


    def "should transform empty TheMovieDBActor list"(){

        expect:
            TRANSFORMER_SERVICE.transformActors(new LinkedHashSet<TheMovieDBActor>()) == null
            TRANSFORMER_SERVICE.transformActors(null) == null
    }


    @Unroll("should transform TheMovieDBActor list")
    def "should transform TheMovieDBActor list"() {

        given:
            def theMovieDBActors = new LinkedHashSet(input)
        when:
            def output = TRANSFORMER_SERVICE.transformActors(theMovieDBActors)
            def outputAsList = new ArrayList<Actor>(output)
        then:
            output.size() == 2

            outputAsList[0].characterName == "Dominic Cobb"
            outputAsList[0].realName == "Leonardo DiCaprio"
            outputAsList[0].order == 1

            outputAsList[1].characterName == "Ariadne"
            outputAsList[1].realName == "Ellen Page"
            outputAsList[1].order == 2

        where:
            input = [   newActor("Leonardo DiCaprio", "Dominic Cobb", 1),
                        newActor("Ellen Page", "Ariadne", 2) ]
    }


    @Unroll("should transform TheMovieDBActor '#actor'")
    def "transform TheMovieDBActor"(){

        given:
            def theMovieDBActor = new TheMovieDBActor()
            theMovieDBActor.setCharacter(character)
            theMovieDBActor.setName(actor)
            theMovieDBActor.setOrder(order)
        when:
            def internalActor = TRANSFORMER_SERVICE.transformActor(theMovieDBActor)
        then:
            internalActor.characterName == character
            internalActor.realName == actor
            internalActor.order == order
        where:
            actor                  | character      | order
            null                   | null           | null
            "Leonardo DiCaprio"    | "Dominic Cobb" | 1
            "Christian Bale"       | "Bruce Wayne"  | 3
            "Al Pacino"            | "Tony Montana" | 99
    }


    def TheMovieDBGenre newGenre(String name){

        TheMovieDBGenre genre = new TheMovieDBGenre()
        genre.name = name
        return genre
    }

    def TheMovieDBActor newActor(String name, String character, Integer order) {

        TheMovieDBActor actor = new TheMovieDBActor()
        actor.name = name
        actor.character = character
        actor.order = order
        actor.id = RandomUtils.nextInt()
        actor.castId = RandomUtils.nextInt()
        actor.profilePath = "/123fewgwer23fgwg123loelogrw.jpg"
        return actor
    }


    def TheMovieDBCrewMember newCrewMember(String name, String department, String job) {

        TheMovieDBCrewMember crewMember = new TheMovieDBCrewMember()
        crewMember.name = name
        crewMember.department = department
        crewMember.job = job
        crewMember.profilePath = "/123fewgwer23fgwg123loelogrw.jpg"
        return crewMember
    }
}
