package com.learnjava.completableFuture;

import com.learnjava.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;

public class CompletableFutureHelloWorldException {
    private HelloWorldService hws;

    public CompletableFutureHelloWorldException(HelloWorldService hws) {
        this.hws = hws;
    }

    public String hello_world_3_aysnc_calls_handle()
    {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());


        String hello_world_hi= CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return "Hi!";
        })
        .thenCombine(hello, (h, w) -> h + w).handle((res, e) -> {
            if(e!=null)
            {
                log("Exception is from Hello Function :" + e);
                return ""; //recoverable value
            }
            else
            {
                return res;
            }

        }).thenCombine(world, (h, w) -> h + w).handle((res, e) -> {
                    if(e!=null)
                    {
                        log("Exception is from Hello Function :" + e);
                        return ""; //recoverable value
                    }
                    else
                    {
                        return res;
                    }

        }).thenApply(String::toUpperCase)
                .join();// join blocking the thread and get the String

        timeTaken();
        return hello_world_hi;
    }

    // handle gets invoke even if there is no error and null pointer
    //is from e.getMesage()



    public String exceptionally()
    {
//        takes function
//                takes 1 nput and return 1 output
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());

        String hello_world_hi= CompletableFuture.supplyAsync(() -> {
                    delay(1000);
                    return "Hi!";
                })
                .thenCombine(hello, (h, w) -> h + w).exceptionally(( e) -> {

                        log("Exception is from Hello Function :" + e);
                        return ""; //recoverable value

                }).thenCombine(world, (h, w) -> h + w).exceptionally(( e) -> {

                        log("Exception is from Hello Function :" + e);
                        return ""; //recoverable value

                }).thenApply(String::toUpperCase)
                .join();// join blocking the thread and get the String

        timeTaken();
        return hello_world_hi;
    }

    // handle gets invoke even if there is no error and null pointer
    //is from e.getMesage()

    public String whenCompleteTest()
    {
//        takes function
//                takes 1 nput and return 1 output
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());

        String hello_world_hi= CompletableFuture.supplyAsync(() -> {
                    delay(1000);
                    return "Hi!";
                })
                .thenCombine(hello, (h, w) -> h + w).whenComplete(( res,e) -> {

                    log("Exception is from Hello Function :" + e);
                   // return ""; //recoverable value

                }).thenCombine(world, (h, w) -> h + w).whenComplete(( res,e) -> {

                    log("Exception is from Hello Function :" + e);
                   // return ""; //recoverable value

                }).exceptionally(e->{
                    return "Exception Handled";
                }).thenApply(String::toUpperCase)
                .join();// join blocking the thread and get the String

        timeTaken();
        return hello_world_hi;
    }
}
