package com.learnjava.completableFuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureHelloWorldTest {
    HelloWorldService helloWorldService=
            new HelloWorldService();
    CompletableFutureHelloWorld completableFutureHelloWorld
            = new CompletableFutureHelloWorld(helloWorldService);
    @Test
    void helloWorld() {


        CompletableFuture<String> completableFuture
                = completableFutureHelloWorld.helloWorld();

        completableFuture
                .thenAccept(s->{
                    assertEquals("HELLO WORLD -11",s);
                })
                .join(); // to execute above code; back presure
    }


    @Test
    void hello_world_multiple_aysnc_callss() {
        String hello_world = completableFutureHelloWorld.hello_world_multiple_aysnc_callss();


                    assertEquals("HELLO WORLD!",hello_world);

    }

    @Test
    void hello_world_hi_aysnc_calls() {
        String hello_world_hi = completableFutureHelloWorld.hello_world_3_aysnc_callss();


        assertEquals("HI!HELLO WORLD!",hello_world_hi);

    }
    @Test
    void hello_world_3_aysnc_calls_CommonThreadPool() {
        String hello_world_hi = completableFutureHelloWorld.hello_world_3_aysnc_calls_CommonThreadPool();

/*
[pool-1-thread-2] - inside world
[pool-1-thread-1] - inside hello
 */
        assertEquals("HI!HELLO WORLD!",hello_world_hi);

    }

    @Test
    void helloWorldThenCompose() {

        startTimer();
        CompletableFuture<String> completableFuture
                = completableFutureHelloWorld.helloWorldThenCompose();

        completableFuture
                .thenAccept(s->{
                    assertEquals("HELLO WORLD!",s);
                })
                .join(); // to execute above code; back presure
        timeTaken(); //2111

        // The reason because it takes 2 sec is it is dependent, first hello will finish and then output is passed to
        //world future method

    }

    @Test
    void anyOf() {

        String s = completableFutureHelloWorld.anyOf();
        assertEquals("Hello World",s);//Response from DB
    }
}