package com.learnjava.domain.parallelStream;

import com.learnjava.parallelStream.LinkedListSpliteratorExample;
import com.learnjava.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListSpliteratorExampleTest {
LinkedListSpliteratorExample linkedListSpliteratorExample= new LinkedListSpliteratorExample();
    @RepeatedTest(5)
    void multiplyEachValue() {
        LinkedList<Integer> inputList = DataSet.generateIntegerLinkedList(100000);
        List<Integer> resultList = linkedListSpliteratorExample
                .multiplyEachValue(inputList, 2,false);

        assertEquals(100000,resultList.size());
    }

    @RepeatedTest(5)
    void multiplyEachValueParallel() {
        System.out.println("*************************************************************");
        LinkedList<Integer> inputList = DataSet.generateIntegerLinkedList(100000);
        List<Integer> resultList = linkedListSpliteratorExample
                .multiplyEachValue(inputList, 2,true);

        assertEquals(100000,resultList.size());
    }

}