package com.Lidigu.Stripe.payment.service;

import com.Lidigu.Stripe.payment.dto.ProductRequest;
import com.Lidigu.Stripe.payment.dto.StripeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {
    //product name,price,quantity,currency
    //return session id and url
    @Value("${stripe.secretKey}")
    private String secretKey;
    public StripeResponse checkOutProducts(ProductRequest productRequest){
        //stripe api
        Stripe.apiKey= secretKey;
      SessionCreateParams.LineItem.PriceData.ProductData productData =
              SessionCreateParams.LineItem.PriceData.ProductData.builder()
               .setName(productRequest.getName()).build();

        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(productRequest.getCurrency() == null? "USD":productRequest.getCurrency())
                .setUnitAmount(productRequest.getAmount())
                        .setProductData(productData)
                .build();
        SessionCreateParams.LineItem lineItem =   SessionCreateParams.LineItem.builder()
               .setQuantity(productRequest.getQuantity())
               .setPriceData(priceData)
               .build();
        SessionCreateParams params =   SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl("http://localhost:8080/success")
            .setCancelUrl("http://localhost:8080/cancel")
            .addLineItem(lineItem)
            .build();

        Session session = null;
        try {
            session = Session.create(params);
        }catch (StripeException e){
            System.out.println(e.getMessage());
        }
        assert session != null;
        return StripeResponse.builder()
                .status("SUCCESS")
                .message("Payment Session created")
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();
    }
}
