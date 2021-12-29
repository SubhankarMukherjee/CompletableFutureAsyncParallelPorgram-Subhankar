package com.learnjava.domain.parallelStream;

import com.learnjava.parallelStream.ArrayListSpliteratorExample;
import com.learnjava.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArrayListSpliteratorExampleTest {
    ArrayListSpliteratorExample arrayListSpliteratorExample
            =new ArrayListSpliteratorExample();
    //@Test
    @RepeatedTest(5)
    void multiplyEachValue() {
        ArrayList<Integer> dataList = DataSet.generateArrayList(100000);
        List<Integer> resultList = arrayListSpliteratorExample
                .multiplyEachValue(dataList, 2,false);

        assertEquals(100000,resultList.size());
    }

    @RepeatedTest(5)
    void multiplyEachValueParallel() {
        ArrayList<Integer> dataList = DataSet.generateArrayList(100000);
        List<Integer> resultList = arrayListSpliteratorExample
                .multiplyEachValue(dataList, 2,true);

        assertEquals(100000,resultList.size());
    }

}