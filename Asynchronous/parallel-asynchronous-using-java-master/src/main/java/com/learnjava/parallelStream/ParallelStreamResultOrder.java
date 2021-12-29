package com.learnjava.parallelStream;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.learnjava.util.LoggerUtil.log;

public class ParallelStreamResultOrder {
    public static List<Integer> listOrder(List<Integer> inputList)
    {
        return inputList.stream()
                .map(integer -> integer*2)
                .collect(Collectors.toList());
    }

    public static Set<Integer> setOrder(Set<Integer> inputList)
    {
        return inputList.stream().parallel()
                .map(integer -> integer*2)
                .collect(Collectors.toSet());
    }

    public static void main(String[] args) {

        //Ordered output
//        List<Integer> inputList= List.of(1,2,3,4,5,6);
//        log("InputList"+ inputList);
//        List<Integer> resultList = listOrder(inputList);
//        log("ResultList"+ resultList);

        //Unordered Output
        //means
        //when you use a unordered Collection Parallel stream is not going to gurantee the order
        Set<Integer> inputSet= Set.of(1,2,3,4,5,6);
        log("inputSet"+ inputSet);
        Set<Integer> resultSet = setOrder(inputSet);
        log("resultSet"+ resultSet);
//        [main] - inputSet[6, 5, 4, 3, 2, 1]
//[main] - resultSet[2, 4, 6, 8, 10, 12]
    }
}
