package com.example.demo;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class StreamTest {



    @Test
    void stream_example()
    {
        // student name and activities in a map
        Map<String, List<String>> collect = StudentDB.getStudents().stream()
                .collect(Collectors.toMap(Student::getName, Student::getActivities));

        System.out.println(collect);

        // Only filter those students whose grade is less than 80 and age is less than 18

        Predicate<Student> p= student ->student.getGrade()<80;
        Predicate<Student> p1= student ->student.getAge()<18;

        System.out.println(StudentDB.getStudents().stream()
                .filter(p).// Stream<Students>
                filter(p1)
                        .peek(student -> { // with peek method we can see how many students got passed from one level to another
                            System.out.println(student);
                        })// Stream<Students>
                .collect(Collectors.toMap(Student::getName,Student::getActivities)));

        // Terminal method is catually cretaing back presure, without it nothing wll happen
        // which is why it is called Streams are lazy

        // Collection can add/remove/modify elements, Stream cant
        // Stream can be accessed only in sequcme
        //Strems are lazy but collections are eagerly constructed
        // you can traverse the stream only once

        List<Integer> list= Arrays.asList(1,2,3,4,5,6);
        Stream<Integer> stream = list.stream();
        stream.forEach(System.out::println);
       // stream.forEach(System.out::println); //stream has already been operated upon or closed you will get error
        //Collection perform external interation
        //Stream performs internal itenration


    }

    @Test
    void map_test()
    {
       //map is converting student object to Stream<String>
        System.out.println(StudentDB.getStudents().stream().map(Student::getName).collect(Collectors.toList()));
        //can be exported to set
      //  System.out.println(StudentDB.getStudents().stream().map(Student::getName).collect(Collectors.toSet()));
    }

    @Test
    void test_flatMap()
    {
        // converts one type tp another type like map
        // used as stream of multiple elements
        //like
        //Stream<List>
        //Stream<Arrays>
        List<String> collect = StudentDB.getStudents().stream().map(Student::getActivities) //Stream<List<String>>
                .flatMap(List::stream) // Stream<String>
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    void distinctAndCountTest()
    {
        List<String> distinct = StudentDB.getStudents().stream()
                .map(Student::getActivities)
                .flatMap(List::stream)
                .distinct()  // remove deuplicate
                        .collect(Collectors.toList());
        System.out.println(distinct);
       Long count = StudentDB.getStudents().stream().map(Student::getActivities).flatMap(List::stream).count();
        System.out.println(count);

    }
    @Test
    void SortedTest()
    {
        List<String> collect = StudentDB.getStudents().stream()
                .map(Student::getActivities)
                .flatMap(List::stream)
                .distinct()
                .sorted() // Sort by aplhabetica order
                .collect(Collectors.toList());

        System.out.println(collect);
    }
    @Test
    void SortedComprator()
    {


        System.out.println( StudentDB.getStudents().stream()
                .sorted(Comparator.comparing(Student::getGrade)
                        .thenComparing(Comparator.comparing(Student::getAge)))
                .collect(Collectors.toList()))  ;

    }
    @Test
    void filter()
    {
        // filter if gpa is beterrn 75 o 80

        Predicate<Student> predicate= (student)->{
            if(student.getGrade() >75 && student.getGrade() <=80)
                return true;
            return false;
        };
       // System.out.println(StudentDB.getStudents().stream().filter(s->predicate.test(s)).collect(Collectors.toList()));
     //   System.out.println(StudentDB.getStudents().stream().filter(predicate::test).collect(Collectors.toList()));
        System.out.println(StudentDB.getStudents().stream().filter(predicate).collect(Collectors.toList()));

        //filter will call predicate test method no need to give test method separately
    }

    @Test
    void reduce()
    {
        // Terminal operation used to convert strea to a single value ex: sum/count
        //takes 2 param 1) default /initial value 2) Binary opertaor
        Integer multiplication = Arrays.asList(1, 3, 5, 7).stream()
                //1
                //3
                //5
                //7
                // a= default 1 and b=1 from Stream return 1
                // a=1 from previous result and b =3 from stream

                .reduce(1, (a, b) -> a * b);
        System.out.println(multiplication);


        // without initianl value/identity
        Optional<Integer> reduce = Arrays.asList(1, 3, 5, 7).stream().reduce((a, b) -> a * b);
        System.out.println(reduce.isPresent());
        System.out.println(reduce.get());


    }
    @Test
    void reduce_Student() {

        // get the student who is having highest grade

        Optional<Student> studentOptional = StudentDB
                .getStudents()
                .stream()
                .reduce((student, student2) -> {
                    return student.getGrade() > student2.getGrade() ? student : student2;
                });

        System.out.println(studentOptional);
    }

    @Test
    void map_filter_reduce_together()
    {
        // what is the total number of note books whole collecton has?

        Integer books = StudentDB.getStudents().stream()
                .filter(student -> student.getGrade()>80)
                //.map(f)   // Function<Student,Integer> f= (student)-> student.getNotebooks();
                .map(Student::getNotebooks)
                //.reduce(0, (a, b) -> a + b);
        .reduce(0, Integer::sum);
        System.out.println(books);

    }

    @Test
    void limitAndSkipTest()
    {
        List<Integer> list= Arrays.asList(1,2,4,5,6,7);
        // limit(4) means take only 1st 4 values and sum
        System.out.println("Limiting 1st 4 sum:"+list.stream().limit(4).reduce(Integer::sum).get());

        // Skip(4) means take skip 1st 4 values and sum remianing values
        System.out.println("Skipping 1st 4 sum:"+list.stream().skip(4).reduce(Integer::sum).get());
    }

    @Test
    void any_all_none_match()
    {
        // anymatch/allmatch/none match takes a predicate as aninput and returns a boolean

        // anymatch: true if any one of the element matches the predicate
        // all match: true if all the element matches the predicate
        // none match: true if no elments matches predicate

        boolean b = StudentDB.getStudents()
                .stream()
                .anyMatch(student -> student.getGrade() >= 75);
        System.out.println(b);


        boolean b1 = StudentDB.getStudents()
                .stream()
                .allMatch(student -> student.getGrade() >= 75);

        System.out.println(b1);

        boolean b2 = StudentDB.getStudents()
                .stream()
                .noneMatch(student -> student.getGrade() >= 99);

        System.out.println(b2);
    }
    @Test
    void fina_first_and_find_any()
    {
        // used to find an element in the stream
        // all/any/none match vs this is match return boolean and this one retruns element itself
        //R is optional
        //findFirst() returns first element in the stream
        //findAny() returns first encountered element in the stream
        Optional<Student> optionalStudent = StudentDB.getStudents()
                .stream()
                .filter(student -> student.getGrade() >= 75).findAny();
        System.out.println(optionalStudent.get());

        Optional<Student> optionalStudent_FF = StudentDB.getStudents()
                .stream()
                .filter(student -> student.getGrade() >= 75).findFirst();
        System.out.println(optionalStudent_FF.get());
    }
    @Test
    void short_circuiting()
    {
       // if(boolean1 && bollean2) // if 1st one is false then 2nd one will not execute
        // if(boolean1 ||  bollean2) // if 1st one is true then 2nd one will not execute
        // this concept is called short circuiting
        // short cirucit functions in streams are
//        1) limit
//            2) findAny and findFirst
//            3) Anymatch /all match/ none match
//        all this functons does not have to iterate whole stream to get the result
    }
}
