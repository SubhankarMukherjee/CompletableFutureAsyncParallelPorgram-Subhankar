package com.example.demo;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamFactoryMethod {
    @Test
    void factory_method()
    {
        //of
//        cretes a stream of certain values passed to this method
        Stream<String> stringStream = Stream.of("Hello", "Adam", "subh");
        // System.out.println(stringStream.collect(Collectors.toList()));
        stringStream.forEach(System.out::println);

//        iterate and generate are used to create infinite streams
         Stream.iterate(1, x -> x * 2).limit(10).forEach(System.out::println);
        //Generate takes asupplier

        Supplier<Integer> supplier = new Random()::nextInt;
       Stream.generate(supplier).limit(5).forEach(System.out::println);;
    }
}
