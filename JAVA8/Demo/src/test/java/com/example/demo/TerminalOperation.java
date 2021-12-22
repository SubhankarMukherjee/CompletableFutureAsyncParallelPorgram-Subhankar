package com.example.demo;

import com.jayway.jsonpath.internal.function.numeric.Average;
import com.jayway.jsonpath.internal.function.numeric.Sum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TerminalOperation {
    // max,min.collect,forEach,reduce

    // Joining Collector perfomred stream concatenation

    @Test
    void Joining()
    {
        String names = StudentDB.getStudents().stream()
                .map(Student::getName)
         //       .collect(Collectors.joining()); //String SamBamjamTamNamAam not readble
                 //.collect(Collectors.joining(",")); //Sam,Bam,jam,Tam,Nam,Aam

        //to add prefix and suffix to the generated String
                .collect(Collectors.joining(",","(",")"));//(Sam,Bam,jam,Tam,Nam,Aam)
        System.out.println(names);
    }

    @Test
    void Counting()
    {
        // Counting collectors return number of element in the list

        Long collect = StudentDB.getStudents().stream()
                .collect(Collectors.counting());
        System.out.println(collect);
    }
    @Test
    void mapping()
    {
//        mapping collector applies a transformation function first and then collects the data in a collection

        List<Double> collect = StudentDB.getStudents().stream()
                .collect(Collectors.mapping(Student::getGrade, Collectors.toList()));
// same as using a map
        System.out.println(collect);
    }
    @Test
    void max_byAndmin_by()
    {
//        maxby and min by takes comparator as input and returns Optional
        // Get the Student which has highest Grade
        Optional<Student> collect = StudentDB.getStudents()
                .stream()
                .collect(Collectors.maxBy(Comparator.comparing(Student::getGrade)));
        if(collect.isPresent())
            System.out.println(collect.get());
    }
    @Test
    void SummingIntAndAveraingInt()
    {
//        Sum and Average as a result

        int sum = StudentDB.getStudents().stream()

                .collect(Collectors.summingInt(Student::getAge));
        System.out.println(sum);

        Double collect = StudentDB.getStudents().stream()
                .collect(Collectors.averagingInt(Student::getAge));
        System.out.println(collect);
    }
    @Test
    void groupingBy()
    {

        //output is Map<k,v>
        //groupingBy(Classifier)
        //groupingBy(Classifer,Downtream(any kind of Collector)
        //groupingBy(Classifer,Supplier,Downtream(any kind of Collector)

        //group by age
        Map<Integer, List<Student>> collect = StudentDB.getStudents()
                .stream()
                .collect(Collectors.groupingBy(Student::getAge));
        System.out.println(collect);

        // group by customizedgrouping by
        // Use Case: If GPA is >85 categorized as Great
//        80-85 :Good
//            <80 Average
        Function<Student,String> f= student -> {
            if(student.getGrade()>85) return "Great";
            if(student.getGrade()<85 && student.getGrade()>80) return "Good";
            else return "Average";
        };

        Map<String, List<Student>> collect1 = StudentDB.getStudents().stream()
                .collect(Collectors.groupingBy(f));

        System.out.println(collect1);

        //2 level mapping

        Map<Integer, Map<String, List<Student>>> collect2 = StudentDB.getStudents().stream()
                .collect(Collectors.groupingBy(Student::getAge, Collectors.groupingBy(f)));

        System.out.println(collect2);

        
        // Printname and grade

        Map<String, Integer> collect3 = StudentDB.getStudents().stream()
                .collect(Collectors.groupingBy(Student::getName, Collectors.summingInt(Student::getAge)));
        System.out.println(collect3);

     //   this can be done by little differntly with this
//Function<Student,Map<String,String>> f1= student -> {
//  Map<String,String > map= new HashMap<>();
//  map.put(student.getName(),String.valueOf(student.getAge()));
//  return map;
//};
//
//        List<Map<String, String>> collect4 = StudentDB.getStudents().stream()
//                .map(f1)
//                // .flatMap()
//                .collect(Collectors.toList());
//        System.out.println(collect4);

        // 3 Argumnets

        LinkedHashMap<Integer, Set<Student>> collect4 = StudentDB.getStudents().stream()
                .collect(Collectors.groupingBy(Student::getAge, LinkedHashMap::new, Collectors.toSet()));

        System.out.println(collect4);


        System.out.println("*******************************");

        //max by will give Optional

        Map<Integer, Optional<Student>> collect5 = StudentDB.getStudents().stream()
                .collect(Collectors.groupingBy(Student::getAge,
                        Collectors.maxBy(Comparator.comparing(Student::getAge))));

        // to avoid this we can use CollectingAndThen

        Map<Integer, Student> collect6 = StudentDB.getStudents().stream()
                .collect(Collectors.groupingBy(Student::getAge,
                        Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(Student::getGrade))
                                , Optional::get
                        )));

        System.out.println(collect6 );
        // Age wise top grdade student

        //similar minBy
    }

    @Test
    void PartioningBy()
    {
        // Similar to groupby collector
        // Accept a predicate as input
        //output is Map<k,v>
        // key of return type will always be a boolean
        // versions
        //partioningBy(predcate)
        //partioningBy(preicate,downstream) downstream is another collector
        Predicate<Student> p1 = p->p.getAge() >15;

        Map<Boolean, List<Student>> collect = StudentDB.getStudents().stream()
                .collect(Collectors.partitioningBy(p1));

        //whatever will match the predicate will be under true key whatever will not match will be under false key

        System.out.println(collect);
        
        //2nd Argumnet version

        //just instead of list o set
        StudentDB.getStudents().stream()
                .collect(Collectors.partitioningBy(p1, Collectors.toSet()));


    }
}
