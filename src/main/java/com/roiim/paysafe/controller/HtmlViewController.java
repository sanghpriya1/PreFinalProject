package com.roiim.paysafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HtmlViewController {
	@RequestMapping( "/" )
    public String getCheckoutForm(){

        return "Chec";
    }

}
