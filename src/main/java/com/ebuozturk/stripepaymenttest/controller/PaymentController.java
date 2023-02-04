package com.ebuozturk.stripepaymenttest.controller;


import com.ebuozturk.stripepaymenttest.dto.CreatePayment;
import com.ebuozturk.stripepaymenttest.dto.CreatePaymentResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {


    static int calculateOrderAmount(Object[] items) {
        // Replace this constant with a calculation of the order's amount
        // Calculate the order total on the server to prevent
        // people from directly manipulating the amount on the client
        return 1400;
    }

    @PostMapping("/create-payment-intent")
    public CreatePaymentResponse createPayment(@RequestBody CreatePayment createPayment) throws StripeException {

//        staticFiles.externalLocation(Paths.get("public").toAbsolutePath().toString());


            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setAmount(15*100L)
                            .setCurrency("usd")
                            .setAutomaticPaymentMethods(
                                    PaymentIntentCreateParams.AutomaticPaymentMethods
                                            .builder()
                                            .setEnabled(true)
                                            .build()
                            )
                            .build();

            // Create a PaymentIntent with the order amount and currency
            PaymentIntent paymentIntent = PaymentIntent.create(params);

        return new CreatePaymentResponse(paymentIntent.getClientSecret());

    }
}
