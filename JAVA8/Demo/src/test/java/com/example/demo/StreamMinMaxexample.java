package com.example.demo;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class StreamMinMaxexample {

    @Test
    void testMinMaxOfInteger() {
        //Min and Max returns Optional
        List<Integer> list = Arrays.asList(1, 4, 5, 133, 564, 3777);
        System.out.println("Min:" + list.stream().min((a, b) -> a.compareTo(b)).get());
        System.out.println("Max:" + list.stream().max((a, b) -> a.compareTo(b)).get());

        //using Reduce
        System.out.println("Using Reduce result");

        System.out.println("Max:"+list.stream().reduce(0, (x, y) -> x > y ? x : y));
        System.out.println("Min:"+list.stream().reduce(0, (x, y) -> x < y ? x : y));
    }

}