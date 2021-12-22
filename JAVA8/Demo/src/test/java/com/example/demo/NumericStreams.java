package com.example.demo;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class NumericStreams {

//    intStream
//    LongStream
//            DoubleStream

    @Test
    void sumOfNumbersFromList()
    {
        List<Integer> list= Arrays.asList(1,2,3,4,5,6,7);
        // First it is performing unboxing of Integer to int
        System.out.println(list.stream().reduce(0,(x,y)->x+y));
        IntStream.rangeClosed(1,6) //1,2,3,4,5,6
                .sum();  // rangeClosed is creating stream and send it to next method hence we didnt call stream
        //range excludes last element
        //ranegclosed takes wholeboundary inclues last element aslo
        System.out.println(IntStream.rangeClosed(0,100).count());
        System.out.println(IntStream.range(0,100).count());
        IntStream.rangeClosed(0,4).forEach(System.out::println);

//        LongStream supports range and ranegClosed

        //DoubleStream does mot support range an drange closed

        //Solution
        double sum = IntStream.rangeClosed(1, 50).asDoubleStream().sum();
        System.out.println(sum);
    }
    @Test
    void numericStreamAggregateExample()
    {
       System.out.println(IntStream.rangeClosed(0,10).sum()); // returns int

      IntStream.rangeClosed(0, 10).max().ifPresent(s-> System.out.println(s)); // max returns OptionalInt
        OptionalInt min = IntStream.rangeClosed(0, 10).min();
        if(min.isPresent())
            System.out.println(min.getAsInt());

        OptionalDouble average = IntStream.rangeClosed(0, 10).average();
        System.out.println(average.getAsDouble());
    }
    @Test
    void boxing_unboxing()
    {

        //int to Integer
        List<Integer> collect = IntStream.rangeClosed(0, 10)
                .boxed()
                .collect(Collectors.toList());

        // Integer to int
        //Sum:
       // collect.stream().sum() // not getting sum

        int sum = collect.stream().mapToInt(Integer::intValue) // mapToInt is giving the intstream to sum
                .sum();


        // map to Obj: Convert each element numeric stream to some object
        //map to Lomg: numeric stream to Long Stream

        // Int to Integer with Map to OBJ
        List<Integer> integers = IntStream.rangeClosed(0, 10)
                .mapToObj(Integer::new)
                .collect(Collectors.toList());
    // map to Long

        int sum1 = IntStream.rangeClosed(0, 100).sum();
//        if I want long
        IntStream.rangeClosed(0, 10).mapToLong(i -> i).sum();

    }
}
