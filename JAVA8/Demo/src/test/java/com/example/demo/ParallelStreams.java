package com.example.demo;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class ParallelStreams {

    //spilts source data into multiple parts
    //Process them parallely
    //Combine the result and give back
    //use Fork Join framework introduced 1.7
    // Number of threads created= number of processors available of machine
    @Test
    void ParallelStream()
    {
        long start=System.currentTimeMillis();
        int sum = IntStream.rangeClosed(0, 30)
                .parallel() // breaks the stream into multiple parts and porcess concurrently
                .sum();
        long end=System.currentTimeMillis();
        System.out.println("Time:"+ (end-start)+" Parallel Sum:"+sum
         );

        long start1=System.currentTimeMillis();
        int sum1 = IntStream.rangeClosed(0, 30)
               // .parallel() // breaks the stream into multiple parts and porcess concurrently
                .sum();
        long end1=System.currentTimeMillis();
        System.out.println("Time:"+ (end1-start1) +" Sequential Sum:"+sum1
        );

        // Check number of processors
        System.out.println(Runtime.getRuntime().availableProcessors());

        // in some of the cases parallel stream is not going to perform better
        // ex: above,where unboxing is reuired etc
        //if you have any mutable variable , then do not use parallel stream at all
        //ex:update a instace varibale of a class
        // it can give yo wrong result
    }
}
