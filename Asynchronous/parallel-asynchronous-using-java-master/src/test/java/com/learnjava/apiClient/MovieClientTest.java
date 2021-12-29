package com.learnjava.apiClient;

import com.learnjava.domain.movie.Movie;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.*;

class MovieClientTest {

    WebClient webClient= WebClient.builder().baseUrl("http://localhost:8080/movies").build();

   MovieClient movieClient= new MovieClient(webClient);

    //@Test
    @RepeatedTest(10)
    void retriveMoview() {

        startTimer();
        var movie = movieClient.retriveMovie(1L);
        assert movie!=null;
        assertEquals("Batman Begins",movie.getMovieInfo().getName());
        assert movie.getReviewList().size()==1;

        timeTaken(); //Total Time Taken : 1250
    }

//    @Test
@RepeatedTest(10)
    void retriveMoview_cf() {
        startTimer();
        var movie = movieClient.retriveMovie_cf(1L).join();
        assert movie!=null;
        assertEquals("Batman Begins",movie.getMovieInfo().getName());
        assert movie.getReviewList().size()==1;
        timeTaken(); //Total Time Taken : 68
    }

    @Test
    void retriveMoviesList() {
        startTimer();

        var movieInfoIdList= List.of(1L,2L,3L,4L,5L,6L,7L);
        var movieList = movieClient.retriveMoviesList(movieInfoIdList);
        movieList.forEach(movie-> {
                    assert movie != null;
                   assert movie.getReviewList().size() == 1;
                });
        timeTaken(); //Total Time Taken : 68
    }

    @Test
    void retriveMoviesList_CF() {
        startTimer();

        var movieInfoIdList= List.of(1L,2L,3L,4L,5L,6L,7L);
        var movieList = movieClient.retriveMoviesList_CF(movieInfoIdList);
        movieList.forEach(movie-> {
            assert movie != null;
            assert movie.getReviewList().size() == 1;
        });
        timeTaken(); //Total Time Taken : 68
    }
    @Test
    void retriveMoviesList_CF_ALL_OF() {
        startTimer();

        var movieInfoIdList= List.of(1L,2L,3L,4L,5L,6L,7L);
        var movieList = movieClient.retriveMoviesList_All_OF(movieInfoIdList);
        movieList.forEach(movie-> {
            assert movie != null;
            assert movie.getReviewList().size() == 1;
        });
        timeTaken(); //Total Time Taken : 68
    }
}