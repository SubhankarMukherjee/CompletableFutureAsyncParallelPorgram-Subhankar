package com.learnjava.completableFuture;

import com.learnjava.domain.Product;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceUsingCompletableFutureTest {
    private ProductInfoService productInfoService= new ProductInfoService();
    private ReviewService reviewService= new ReviewService();
    private InventoryService is= new InventoryService();
    ProductServiceUsingCompletableFuture productServiceUsingCompletableFuture
            = new ProductServiceUsingCompletableFuture(productInfoService,reviewService,is);
    @Test
    void retrieveProductDetails() {
        String productId="ABC123";

        Product product = productServiceUsingCompletableFuture.retrieveProductDetails(productId);


        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size()>0);
        assertNotNull(product.getReview());
    }

    @Test
    void retrieveProductDetails_apporach2() {

        String productId="ABC123";
        CompletableFuture<Product> cfProduct = productServiceUsingCompletableFuture.retrieveProductDetails_apporach2(productId);

        cfProduct.thenAccept(product -> {
            assertNotNull(product);
            assertTrue(product.getProductInfo().getProductOptions().size()>0);
            assertNotNull(product.getReview());
        });

    }

    @Test
    void retrieveProductDetailsWithInventory() {

        String productId="ABC123";
        Product product1 = productServiceUsingCompletableFuture.retrieveProductDetailsWithInventory(productId);


            assertNotNull(product1);
            assertTrue(product1.getProductInfo().getProductOptions().size()>0);
        product1.getProductInfo().getProductOptions().forEach(productOption -> {
                assertNotNull(productOption.getInventory());
            });
            assertNotNull(product1.getReview());

    }

    @Test
    void retrieveProductDetailsWithInventory_approach2() {

        String productId="ABC123";
        Product product1 = productServiceUsingCompletableFuture.retrieveProductDetailsWithInventory_approach2(productId);


        assertNotNull(product1);
        assertTrue(product1.getProductInfo().getProductOptions().size()>0);
        product1.getProductInfo().getProductOptions().forEach(productOption -> {
            assertNotNull(productOption.getInventory());
        });
        assertNotNull(product1.getReview());

    }
}