package com.ebuozturk.stripepaymenttest.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @Value("${stripe.stripePublicKey}")
    private String stripePublicKey;

    @GetMapping("/")
    public String index(Model model){

        model.addAttribute("stripePublicKey",stripePublicKey);
        return "index";
    }
}
