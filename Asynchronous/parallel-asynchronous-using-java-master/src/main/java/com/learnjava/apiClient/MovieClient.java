package com.learnjava.apiClient;

import com.learnjava.domain.movie.Movie;
import com.learnjava.domain.movie.MovieInfo;
import com.learnjava.domain.movie.Review;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MovieClient {

private final WebClient webClient;

    public MovieClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Movie retriveMovie(Long movieId){
        //MovieInfo
        MovieInfo movieInfo = invokeMovieInfoService(movieId);
        //review
        List<Review> reviews = invokeReviewService(movieId);
        return new Movie(movieInfo,reviews);

    }

    public CompletableFuture<Movie> retriveMovie_cf(Long movieId){
        //MovieInfo

        CompletableFuture<MovieInfo> movieInfoCompletableFuture = CompletableFuture.supplyAsync(() -> invokeMovieInfoService(movieId));
        CompletableFuture<List<Review>> reviewCompletableFuture = CompletableFuture.supplyAsync(() -> invokeReviewService(movieId));

      return   movieInfoCompletableFuture.thenCombine(reviewCompletableFuture,(movieInfo,reviews)->
      {
          return new Movie(movieInfo,reviews);
      });

    }
    public List<Movie> retriveMoviesList(List<Long> movieInfoIds){

       return movieInfoIds.stream()
                .map(this::retriveMovie)
                .collect(Collectors.toList());

    }

    public List<Movie> retriveMoviesList_CF(List<Long> movieInfoIds){

        return movieInfoIds.stream()
                .map(this::retriveMovie_cf)
                .collect(Collectors.toList())
                 .stream()
                 .map(CompletableFuture::join)// blocking call one by one
                 .collect(Collectors.toList());

    }

    public List<Movie> retriveMoviesList_All_OF(List<Long> movieInfoIds){

//        all of is overcoming blocking call one by one
//        still we are using join but when all result is done then
        List<CompletableFuture<Movie>> moviesFuture = movieInfoIds.stream()
                .map(this::retriveMovie_cf)
                .collect(Collectors.toList());
                 // this function will return CF<void> when all future will complete

        CompletableFuture<Void> allOf = CompletableFuture.allOf(moviesFuture.toArray(new CompletableFuture[moviesFuture.size()]));
       return allOf
                .thenApply(v->
                    moviesFuture
                            .stream()
                            . map(CompletableFuture::join)
                            .collect(Collectors.toList()))

               .join();


    }

    private List<Review> invokeReviewService(Long movieId) {


        String reviewURI = UriComponentsBuilder.fromUriString("/v1/reviews")
                .queryParam("movieInfoId", movieId)
                .buildAndExpand().toString();
       return webClient.get()
                .uri(reviewURI)
                .retrieve()
                .bodyToFlux(Review.class)
               .collect(Collectors.toList())
               .block();
    }
    private MovieInfo invokeMovieInfoService(Long movieId) {

        String uri="/v1/movie_infos/{movie_info_id}";
       return webClient.get()
                .uri(uri,movieId)
                .retrieve()
                .bodyToMono(MovieInfo.class)
                .block();
    }
}
