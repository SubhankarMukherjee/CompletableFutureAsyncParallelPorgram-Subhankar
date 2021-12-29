package com.learnjava.domain.parallelStream;

import com.learnjava.parallelStream.ParallelStream;
import com.learnjava.util.DataSet;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.learnjava.util.CommonUtil.*;
import static org.junit.jupiter.api.Assertions.*;

class ParallelStreamTest {

    ParallelStream parallelStream = new ParallelStream();

   // @Test
    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void stringTransform(boolean isBoolean) {
        List<String> inputList = DataSet.namesList();
        stopWatch.reset();
        startTimer();
        List<String> resultList = parallelStream.stringTransform(inputList,isBoolean);
        timeTaken();
        assertEquals(4, resultList.size());
        resultList.forEach(name -> {
            assertTrue(name.contains("-"));

        });
    }
}