package com.learnjava.parallelStream;

import com.learnjava.util.DataSet;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;

public class ParallelStream {

    public List<String>  stringTransform(List<String> namesList, boolean parallel){
//      return   namesList.stream()
//                .map(this::addNameLengthTransform)
//                .collect(Collectors.toList()); //2292 s

        Stream<String> nameStream = namesList.stream();
        if(parallel)
            nameStream.parallel();


        return  nameStream
                .map(this::addNameLengthTransform)
//                .sequential()
//                .parallel() //override sequential behaviour
                //the last function called is controlling overall pipeline
                .collect(Collectors.toList()); //755 s

    }

    private  String addNameLengthTransform(String name) {
        delay(500);
        return name.length()+" - "+name ;
    }




    public static void main(String[] args) {
        List<String> namesList = DataSet.namesList();
        ParallelStream parallelStream= new ParallelStream();
        stopWatch.start();
        List<String> list = parallelStream.stringTransform(namesList,false);

        log("Result is:-"+ list);
        timeTaken();

    }
}
