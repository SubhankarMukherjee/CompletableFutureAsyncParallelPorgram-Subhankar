package com.learnjava.service;

import com.learnjava.checkout.Cart;
import com.learnjava.checkout.CartItem;
import com.learnjava.checkout.CheckoutResponse;
import com.learnjava.checkout.CheckoutStatus;

import java.util.List;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static com.learnjava.util.LoggerUtil.log;

public class CheckoutService {
    private PriceValidatorService priceValidatorService;

    public CheckoutService(PriceValidatorService priceValidatorService) {
        this.priceValidatorService = priceValidatorService;
    }

    public CheckoutResponse checkout(Cart cart)
    {
        startTimer();
        // create a list if price is expired
        List<CartItem> priceValidationList = cart.getCartItemList()
                //.stream()//3070
                .parallelStream()
                .map(cartItem -> {
                    boolean isPriceInvalid = priceValidatorService.isCartItemInvalid(cartItem);
                    cartItem.setExpired(isPriceInvalid);
                    return cartItem;
                })
                .filter(CartItem::isExpired)
                .collect(Collectors.toList());
        timeTaken();
        if(priceValidationList.size()>0)
        {
            return new CheckoutResponse(CheckoutStatus.FAILURE,priceValidationList);
        }
// calculate over all price


        double finalPrice=calculateFinalPriceUsingReduce(cart);
        log("checkout complete and final result is :+"+ finalPrice);
        return  new CheckoutResponse(CheckoutStatus.SUCCESS,finalPrice);
    }

    private double calculateFinalPrice(Cart cart) {
    return cart.getCartItemList().parallelStream()
                .map(cartItem -> cartItem.getQuantity()*cartItem.getRate())
                .collect(Collectors.summingDouble(Double::doubleValue));
    }
    private double calculateFinalPriceUsingReduce(Cart cart) {
        return cart.getCartItemList().parallelStream()
                .map(cartItem -> cartItem.getQuantity() * cartItem.getRate())
                .reduce(0.0, Double::sum);

    }
}
