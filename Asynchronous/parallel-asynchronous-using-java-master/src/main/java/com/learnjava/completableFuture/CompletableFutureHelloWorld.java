package com.learnjava.completableFuture;

import com.learnjava.service.HelloWorldService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;
//###########################################
//        FactoryMethod
//        takes Supplier
//        initiate async computation
//        returns CompletableFuture<T>
//thenAccept()
//        ###########################################
//        CompletonStageMethod
//        Takes Consumer
//        chain async computaion
//        returns CompletableFuture<Void>
public class CompletableFutureHelloWorld {
    private HelloWorldService hws;

    public CompletableFutureHelloWorld(HelloWorldService hws) {
        this.hws = hws;
    }


    public CompletableFuture<String> helloWorld()
    {
        return CompletableFuture.supplyAsync(()->hws.helloWorld())
                .thenApply(String::toUpperCase)
                .thenApply(s->s+" -"+s.length());
               // .thenAccept((result)-> log("Result is:"+ result))
              //  .join(); // join block themain thread untill the program completed and then prints next done



//        here you will only see Done. beacuse call returns and main ends before 1 sec elay at helloworld method

        // delay(2000); // to check result
    }

    public String hello_world_multiple_aysnc_callss()
    {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());
        String helloworld = hello
                .thenCombine(world, (h, w) -> h + w)
                .thenApply(String::toUpperCase)
                .join();// join blocking the thread and get the String

        timeTaken();
        return helloworld;
    }

    public String hello_world_3_aysnc_callss()
    {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());


        String hello_world_hi = CompletableFuture.supplyAsync(() -> {
                    delay(1000);
                    return "Hi!";
                })
                .thenCombine(hello, (h, w) -> h + w)
                .thenCombine(world, (h, w) -> h + w)
                .thenApply(String::toUpperCase)
                .join();// join blocking the thread and get the String

        timeTaken();
        return hello_world_hi;
    }


    public CompletableFuture<String> helloWorldThenCompose() {

        return CompletableFuture.supplyAsync(hws::hello)
                .thenCompose(previous -> hws.worldFuture(previous))
                .thenApply(String::toUpperCase);

    }
    public String anyOf()
    {
        // DB Call
        CompletableFuture<String> dbCall = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            log("Response from DB");
            return "Hello World";
        });
        //Rest Call

        CompletableFuture<String> restCall = CompletableFuture.supplyAsync(() -> {
            delay(2000);
            log("Response from Rest Call");
            return "Hello World";
        });
        //Soap Call
        CompletableFuture<String> soapCall = CompletableFuture.supplyAsync(() -> {
            delay(3000);
            log("Response from Soap Call");
            return "Hello World";
        });
        // Make these calls parallel but takes from which gives faster result
        List<CompletableFuture<String>> cfList = List.of(dbCall, restCall, soapCall);
        CompletableFuture<Object> objectCompletableFuture = CompletableFuture.anyOf(cfList.toArray(new CompletableFuture[cfList.size()]));

        String result =(String) objectCompletableFuture.thenApply(v -> {
            if (v instanceof String)
                return v;
            else return null;
        }).join();
        return result;
    }

    public String hello_world_3_aysnc_calls_CommonThreadPool()
    {
        startTimer();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
// Pass as 2nd argument
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello(),executorService);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world(),executorService);


        String hello_world_hi = CompletableFuture.supplyAsync(() -> {
                    delay(1000);
                    return "Hi!";
                },executorService)
                .thenCombineAsync(hello, (h, w) -> h + w,executorService)
                .thenCombineAsync(world, (h, w) -> h + w,executorService)
                .thenApplyAsync(String::toUpperCase)
                .join();// join blocking the thread and get the String

        timeTaken();
        return hello_world_hi;
    }



    }
