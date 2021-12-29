package com.learnjava.service;

import com.learnjava.checkout.Cart;
import com.learnjava.checkout.CheckoutResponse;
import com.learnjava.checkout.CheckoutStatus;
import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutServiceTest {

    PriceValidatorService priceValidatorService= new PriceValidatorService();
    CheckoutService checkoutService = new CheckoutService(priceValidatorService);
    @Test
    void checkout_6_items() {
        Cart cart = DataSet.createCart(6);
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);
        assertEquals(CheckoutStatus.SUCCESS,checkoutResponse.getCheckoutStatus());
        assertTrue(checkoutResponse.getFinalRate()>0);
    }


    @Test
    void checkout_13_items() {
        Cart cart = DataSet.createCart(13);
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);
        assertEquals(CheckoutStatus.FAILURE,checkoutResponse.getCheckoutStatus());
    }
    @Test
    void test_Core()
    {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }


    @Test
    void parallelism()
    {
        System.out.println("Parallelism"+ ForkJoinPool.getCommonPoolParallelism()); //3
        // it is equal to number of core in your machine -1

    }


    @Test
    void modifyingParallelism() {

        // now modify the parallelism
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","100"); //819

        Cart cart = DataSet.createCart(100);
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);
        assertEquals(CheckoutStatus.FAILURE,checkoutResponse.getCheckoutStatus());

        //running this code with default parallelism it took:-15880
//        Modify
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","100");
//        or
//        by argument
//        -Djava.util.concurrent.ForkJoinPool.common.parallelism=100
    }

}