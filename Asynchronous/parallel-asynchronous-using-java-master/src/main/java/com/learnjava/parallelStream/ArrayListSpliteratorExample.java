package com.learnjava.parallelStream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
// not much difference in stream and parallel
public class ArrayListSpliteratorExample {

    public List<Integer>  multiplyEachValue(ArrayList<Integer> inputList, int multiplyValue,boolean isParallel){

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
