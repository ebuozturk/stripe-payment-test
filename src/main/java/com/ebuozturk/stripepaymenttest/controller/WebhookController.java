package com.ebuozturk.stripepaymenttest.controller;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.*;
import com.stripe.net.Webhook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookController {

    @Value("${stripe.webSecretKey}")
    private String endpointSecret;

    private Logger logger = LoggerFactory.getLogger(WebhookController.class);


    @PostMapping("/stripe/events")
    public String handleCheckout(@RequestHeader("Stripe-Signature") String sigHeader, @RequestBody String payload){


            if(sigHeader == null) {
                return "";
            }

            Event event;

                try {
                    event = Webhook.constructEvent(
                            payload, sigHeader, endpointSecret
                    );
                } catch (SignatureVerificationException e) {
                    // Invalid signature
                    logger.error("⚠️  Webhook error while validating signature.");
                    return "";
                }

            // Deserialize the nested object inside the event
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            StripeObject stripeObject = null;
            if (dataObjectDeserializer.getObject().isPresent()) {
                stripeObject = dataObjectDeserializer.getObject().get();
            } else {
                // Deserialization failed, probably due to an API version mismatch.
                // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
                // instructions on how to handle this case, or return an error here.
            }
            // Handle the event
            switch (event.getType()) {
                case "payment_intent.succeeded":
                    PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
                    logger.info("Payment for {} succeeded.",paymentIntent.getAmount());
                    // Then define and call a method to handle the successful payment intent.
                    // handlePaymentIntentSucceeded(paymentIntent);
                    break;
//                case "payment_method.attached":
//                    PaymentMethod paymentMethod = (PaymentMethod) stripeObject;
//                    // Then define and call a method to handle the successful attachment of a PaymentMethod.
//                    // handlePaymentMethodAttached(paymentMethod);
//                    break;
                default:
                    logger.warn("Unhandled event type: {}", event.getType());
                    break;
            }
            return "";

    }

}
