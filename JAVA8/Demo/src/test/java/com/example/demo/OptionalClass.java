package com.example.demo;

import org.junit.jupiter.api.Test;

import javax.swing.text.html.Option;
import java.util.Optional;

public class OptionalClass {
    //to represent a non null value
    //Avoid null pointer
    @Test
    void OptionalTets() {
//        String name=getStudentName();
//        if(name!=null)
//        System.out.println(name.length());
//        else
//            System.out.println("Name not found");

        Optional<String> studentName = getStudentName();
        if(studentName.isPresent())
            System.out.println(studentName.get().length());
    }

    Optional<String> getStudentName()
    {
       // Student student = StudentDB.studentSupplier.get();
//        Student student=null;
//        if (student != null)
//            return student.getName();
//        return null;

        Optional<Student> optionalStudent = Optional.ofNullable(StudentDB.studentSupplier.get());
        if(optionalStudent.isPresent())
            return optionalStudent.map(Student::getName);
        return Optional.empty(); // returns a optional object with No value
        //use this empty method as exception return block to adhere method return type as Optional
    }

    @Test
    void ofNullableTest()
    {
        // offnullable method returns option<T> which was sent
        //  public static <T> Optional<T> ofNullable(T value) {
        //        return value == null ? empty() : of(value);
        //    }
        Optional<String> hello = Optional.ofNullable("Hello");
        System.out.println(hello.isPresent());  //true
        System.out.println(hello.get());        //Hello

        Optional<String> hello1 = Optional.ofNullable(null);
        System.out.println(hello1.isPresent());  //false

    }

    @Test
    void ofTest()
    {
        // of method always expect you to send a valid value, for null it will throw null pointer
        //  public static <T> Optional<T> of(T value) {
        //        return  of(value);
        //    }
        Optional<String> hello = Optional.of("Hello");
        System.out.println(hello.isPresent());  //true
        System.out.println(hello.get());        //Hello

        Optional<String> hello1 = Optional.of(null);
        System.out.println(hello1.isPresent());  //Null Pointer Excepton

    }

    @Test
    void OrElse()
    {

        Optional<Student> studentOptional= Optional.ofNullable(StudentDB.studentSupplier.get());
        String name = studentOptional.map(Student::getName).orElse("Default");
        System.out.println(name);  //sam

        Optional<Student> studentOptional1= Optional.ofNullable(null);
        String name1 = studentOptional1.map(Student::getName).orElse("Default");
        System.out.println(name1);  //Default
    }
    @Test
    void OrElseGet()
    {
// Similar to orElse, orElse will take simple String, orElseGet will take supplier
              Optional<Student> studentOptional1= Optional.ofNullable(null);
        String name1 = studentOptional1.map(Student::getName).orElseGet(()->"Default");
        System.out.println(name1);  //Default
    }
    @Test
    void OrElseThrow()
    {

        Optional<Student> studentOptional= Optional.ofNullable(StudentDB.studentSupplier.get());
        String name = studentOptional.map(Student::getName).orElseThrow(()->new RuntimeException(("No Name Found")));
        System.out.println(name);  //sam

        Optional<Student> studentOptional1= Optional.ofNullable(null);
        String name1 = studentOptional1.map(Student::getName).orElseThrow(()->new RuntimeException(("No Name Found")));
        System.out.println(name1);  //java.lang.RuntimeException: No Name Found
    }
    @Test
    void isPresent()
    {
        //whether optional has valid value or not
Optional<String> optional= Optional.ofNullable("Hello Optional");
        boolean present = optional.isPresent(); //true
        System.out.println(optional.get());//Hello Optional
    }
    @Test
    void ifPresent()
    {
// if present takes a consumer
        // first it check if it has a valid value(isPresent)
        // then it give you the access of actual value(get)
        Optional<String> optional= Optional.ofNullable("Hello Optional");
         optional.ifPresent(System.out::println);  ///Hello Optional
    }

    @Test
    void Filter()
    {
        Optional<Student> studentOptional= Optional.ofNullable(StudentDB.studentSupplier.get());
        studentOptional.filter(student -> student.getGrade() >=75)
                .ifPresent(System.out::println);
        //{name='Sam', activities=[painting, basketball], age=20, grade=78.3}
        studentOptional.filter(student -> student.getGrade() >=79)
                .ifPresent(System.out::println); // nothing will be printed as by filter everything will be filtered out

    }

    @Test
    void map()
    {
        Optional<Student> studentOptional= Optional.ofNullable(StudentDB.studentSupplier.get());
        studentOptional.filter(student -> student.getGrade() >=75)
                .map(Student::getName)
                .ifPresent(System.out::println);
        //Sam
        studentOptional.filter(student -> student.getGrade() >=79)
                .map(Student::getName)
                .ifPresent(System.out::println); // nothing will be printed as by filter everything will be filtered out

    }

    @Test
    void flatMap()
    {

//        if there is Optional object inside optional object the only way to get is using flatmap

        Optional<Student> studentOptional= Optional.ofNullable(StudentDB.studentSupplier.get());
        Optional<String> bikeModel = studentOptional.filter(s -> s.getAge() == 20)// Optional Student-->Optional<bike>
                    .flatMap(Student::getBikes) //Optional<String>
                .map(Bike::getModel);
        System.out.println(bikeModel); //Optional[Caliber 123]
        bikeModel.ifPresent(System.out::println); //Caliber 123
    }

    @Test
    void IfPresentOrElse()
    {
//        public void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction) {
//        if (value != null) {
//            action.accept(value);
//        } else {
//            emptyAction.run`();
//        }
//    }

        //Optional<Student> studentOptional= Optional.ofNullable(StudentDB.studentSupplier.get());
        Optional<Student> studentOptional= Optional.ofNullable(null);
        Optional<String> bikeModel = studentOptional.filter(s -> s.getAge() == 20)// Optional Student-->Optional<bike>
                .flatMap(Student::getBikes) //Optional<String>
                .map(Bike::getModel);
        bikeModel.ifPresentOrElse(System.out::println,
                ()-> System.out.println("Not Found"));

    }

}
