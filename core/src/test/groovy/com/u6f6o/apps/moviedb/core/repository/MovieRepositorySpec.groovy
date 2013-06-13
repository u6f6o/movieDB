package com.u6f6o.apps.moviedb.core.repository

import com.u6f6o.apps.moviedb.core.api.movie.Movie
import com.u6f6o.apps.moviedb.core.api.movie.cast.CrewMember
import com.u6f6o.apps.moviedb.core.config.persistence.PersistenceLayerConfig
import com.u6f6o.apps.moviedb.samples.MovieSample;
import org.joda.time.Interval
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Unroll

import static com.u6f6o.apps.moviedb.core.api.movie.Genre.*
import static com.u6f6o.apps.moviedb.samples.MovieSample.*


/**
 * TODO ULF add test for updating movies
 * @author u6f6o
 */

@Stepwise
@ActiveProfiles( "dev" )
@ContextConfiguration( classes = [ PersistenceLayerConfig.class ])
class MovieRepositorySpec extends Specification {

    static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern( "yyyy-MM-dd" )


    @Autowired
    private MovieRepository movieRepository;

    @Shared dbIdsByEnums = [:]


    @Unroll("should insert '#movieEnum.getTitle()'")
    def "insert all given movie samples"(){

        when:
            movieRepository.save(movie)
            dbIdsByEnums.putAt(movieEnum, movie.id)
        then:
            dbIdsByEnums.get(movieEnum) != null
        where:
            movie << MovieSample.values().collect{ it.readMovieFromJSON(Movie.class) }
            movieEnum << MovieSample.values()
    }


    @Unroll("should have #expMovieCount movies in database")
    def "count inserted movies"(){

        expect:
            dbIdsByEnums.size() == expMovieCount
            movieRepository.count() == expMovieCount
        where:
            expMovieCount = MovieSample.values().size()
    }


    @Unroll("should find '#dbIdByEnum.getKey().getTitle()' for given movie id")
    def "find all movies by id"(){

        when:
            def movie = movieRepository.fetchMovieById(dbIdByEnum.value)
        then:
            movie.title.equals(dbIdByEnum.key.title)
        where:
            dbIdByEnum << dbIdsByEnums.entrySet()
    }


    @Unroll("should find 1 movie(s) for title '#movieTitle'")
    def "find movies by title"(){

        when:
            def movies = movieRepository.fetchMoviesByTitle(movieTitle)
        then:
            movies.size() == 1
            movies[0].title.equals(movieTitle)
            movies[0].id == dbIdsByEnums.get(movieEnum)
        where:
            movieTitle      |   movieEnum
            "Bad Taste"     |   BAD_TASTE
            "Dead Alive"    |   BRAINDEAD
            "Inception"     |   INCEPTION
    }


    @Unroll("should find #movieEnums.size() movie(s) for author '#author'")
    def "find movies by author"() {

        given:
            def crewMember = new CrewMember()
            crewMember.setRealName(author)
            crewMember.setJob("Writing")
        when:
            def movies = movieRepository.fetchMoviesByAuthor(crewMember)
        then:
            movies.size() == movieEnums.size()
            movies.each{ movieEnums.contains(MovieSample.byTitle(it.title)) }
        where:
            author              |   movieEnums
            "Peter Jackson"     |   [ BRAINDEAD, BAD_TASTE, MEET_THE_FEEBLES ]
            "Christopher Nolan" |   [ INCEPTION, THE_DARK_KNIGHT ]
            "George Lucas"      |   [ STAR_WARS ]

    }


    @Unroll("should find #movieEnums.size() movie(s) for genre '#genre'")
    def "find movies by genre"() {

        when:
            def movies = movieRepository.fetchMoviesByGenre(genre)
        then:
            movies.size() == movieEnums.size()
            movies.each{ movieEnums.contains(MovieSample.byTitle(it.title)) }
        where:
            genre       |   movieEnums
            ACTION      |   [ BAD_TASTE, FEARLESS, INCEPTION, MACHETE, STAR_WARS, THE_DARK_KNIGHT ]
            THRILLER    |   [ INCEPTION, MACHETE, SHINING ]
            DRAMA       |   [ BEN_HUR, FEARLESS, SHINING, THE_DARK_KNIGHT, THE_GREEN_MILE ]
            HISTORY     |   [ FEARLESS ]
    }


    @Unroll("should find #movieEnums.size() movie(s) for interval '#from' to '#to'")
    def "find movies by interval"() {

        given:
            def fromDate = DATE_FORMATTER.parseDateTime(from).toDateMidnight()
            def toDate = DATE_FORMATTER.parseDateTime(to).toDateMidnight()
            def interval = new Interval(fromDate, toDate)
        when:
            def movies = movieRepository.fetchMoviesByInterval(interval)
        then:
            movies.size() == movieEnums.size()
            movies.each{ movieEnums.contains(MovieSample.byTitle(it.title)) }
        where:
            from            |   to              |   movieEnums
            "1907-01-01"    |   "1907-12-31"    |   [ BEN_HUR ]
            "2010-01-01"    |   "2010-12-31"    |   [ INCEPTION, MACHETE ]
            "1900-01-01"    |   "2000-12-31"    |   [ BAD_TASTE, BEN_HUR, BRAINDEAD, MEET_THE_FEEBLES, SHINING, STAR_WARS, THE_GREEN_MILE]
            "2006-01-26"    |   "2006-01-26"    |   [ FEARLESS ]
    }


    @Unroll("should delete movie '#dbIdByEnum.key.title'")
    def "delete movies"(){

        given:
            def movieToDelete = movieRepository.fetchMovieById(dbIdByEnum.value)
            assert movieToDelete != null
        when:
            movieRepository.remove(movieToDelete)
        then:
            movieRepository.fetchMovieById(dbIdByEnum.value) == null
        where:
            dbIdByEnum << dbIdsByEnums.entrySet()
    }


    @Unroll("should have no more movies in the dataase")
    def "check no movies left"(){

        expect:
            movieRepository.count() == 0
    }
}
