package com.learnjava.completableFuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompletableFutureHelloWorldExceptionTest {

    // mock helloworld service
    @Mock
    HelloWorldService helloWorldService= mock(HelloWorldService.class);

    //inject the mocked service to CompletableFutureHelloWorldException
//    class

    @InjectMocks
    CompletableFutureHelloWorldException hwcfe;

    @Test
    void hello_world_3_aysnc_calls_handle()
    {

        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occured"));
        when(helloWorldService.world()).thenCallRealMethod();

        String result = hwcfe.hello_world_3_aysnc_calls_handle();
        assertEquals(" WORLD!",result);

    }

    @Test
    void hello_world_3_aysnc_calls_handle2()
    {

        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occured"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception Occured"));

        String result = hwcfe.hello_world_3_aysnc_calls_handle();
        assertEquals("",result);

    }
    @Test
    void hello_world_3_aysnc_calls_handle3()
    {

        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();

        String result = hwcfe.hello_world_3_aysnc_calls_handle();
        assertEquals("HI!HELLO WORLD!",result);

    }

    @Test
    void exceptionally_hello()
    {

        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occured"));
        when(helloWorldService.world()).thenCallRealMethod();

        String result = hwcfe.exceptionally();
        assertEquals(" WORLD!",result);

    }

    @Test
    void exceptionally_hello_world_both()
    {

        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occured"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception Occured"));

        String result = hwcfe.exceptionally();
        assertEquals("",result);
//
//        if you want to have values from other function
//            refer them as another string and from exception concat the global string

    }
    @Test
    void exceptionally_happy_path()
    {

        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();

        String result = hwcfe.exceptionally();
        assertEquals("HI!HELLO WORLD!",result);

    }
    @Test
    void WhenComplete_happy_path()
    {

        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();

        String result = hwcfe.whenCompleteTest();
        assertEquals("HI!HELLO WORLD!",result);

    }
    @Test
    void whenComplete_hello()
    {

        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occured"));
        when(helloWorldService.world()).thenCallRealMethod();

        String result = hwcfe.whenCompleteTest();
        // Excepton propagates and ultimately we will get in clinet

//        to recover use exceptionally
        assertEquals("EXCEPTION HANDLED",result);

    }

    @Test
    void whenComplete_hello_world_both()
    {

        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occured"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception Occured"));

        String result = hwcfe.whenCompleteTest();
        // Excepton propagates and ultimately we will get in clinet

//        to recover use exceptionally
        assertEquals("EXCEPTION HANDLED",result);
    }

}