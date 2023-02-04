package com.ebuozturk.stripepaymenttest;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StripePaymentTestApplication {

    @Value("${stripe.apiKey}")
    private String apiKey;

    @PostConstruct
    public void setStripeKey(){
        System.out.println("Stripe version: "+Stripe.API_VERSION);
        Stripe.apiKey = apiKey ;

    }

    public static void main(String[] args) {
        SpringApplication.run(StripePaymentTestApplication.class, args);
    }

}
