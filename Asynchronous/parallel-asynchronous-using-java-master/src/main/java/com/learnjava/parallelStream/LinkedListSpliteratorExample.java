package com.learnjava.parallelStream;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;

// Linked List is difficult to split, hence parallel programming is slow compared to sequential


public class LinkedListSpliteratorExample {


        public List<Integer> multiplyEachValue(LinkedList<Integer> inputList, int multiplyValue, boolean isParallel){

            startTimer();
            Stream<Integer> streamInteger = inputList.stream();

            if(isParallel)
                streamInteger.parallel();


            List<Integer> resultList = streamInteger.map(integer -> integer * multiplyValue)
                    .collect(Collectors.toList());
            timeTaken();
            return resultList;

        }
}
