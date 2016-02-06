package com.danigcaff.springframework.samples.spring_samples_oauth;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController 
{
    @RequestMapping("/")
    @ResponseBody
    public String index() {
    	return "Hola mundo!!";
    }
}
