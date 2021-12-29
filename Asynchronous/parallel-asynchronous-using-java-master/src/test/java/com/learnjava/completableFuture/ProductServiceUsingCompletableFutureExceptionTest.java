package com.learnjava.completableFuture;

import com.learnjava.domain.Product;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceUsingCompletableFutureExceptionTest {

    @Mock
    ProductInfoService productInfoService;
    @Mock
    ReviewService reviewService;
    @Mock
    InventoryService inventoryService;

    @InjectMocks
    ProductServiceUsingCompletableFuture psf;

    @Test
    void retrieveProductDetailsWithInventory_approach2() {
when(productInfoService.retrieveProductInfo(any())).thenCallRealMethod();
        when(reviewService.retrieveReviews(any())).thenThrow(new RuntimeException("Exception Occurs at Review Service"));
        when(inventoryService.retrieveInventory(any())).thenCallRealMethod();
        Product product = psf.retrieveProductDetailsWithInventory_approach2("TestProduct");
        assertNotNull(product);
        assertEquals(0,product.getReview().getNoOfReviews());
        assertTrue(product.getProductInfo().getProductOptions().size()>0);
        assertEquals(0.0,product.getReview().getOverallRating());
        product.getProductInfo().getProductOptions().forEach(productOption -> {
            assertNotNull(productOption.getInventory());
        });
    }


    @Test
    void productInfoServiceError() {
        when(productInfoService.retrieveProductInfo(any())).thenThrow(new RuntimeException("Exception Occurs at Product Service"));
        when(reviewService.retrieveReviews(any())).thenCallRealMethod();

        //UnnecessaryStubbingException: because invenory wll not get called wif prouct has error
       // when(inventoryService.retrieveInventory(any())).thenCallRealMethod();
        //Product product = psf.retrieveProductDetailsWithInventory_approach2("TestProduct");
        assertThrows(RuntimeException.class,()->psf.retrieveProductDetailsWithInventory_approach2("TestProduct"));



    }
}