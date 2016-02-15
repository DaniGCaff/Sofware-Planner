package com.danigcaff.springframework.samples.spring_samples_oauth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController 
{
    @RequestMapping("/")
    @ResponseBody
    public String index() {
    	return "Hola mundo!!";
    }
    
    @RequestMapping(value = "/event", method = RequestMethod.POST)
    @ResponseBody
    public String eventListener(@RequestBody String evento) {
    	System.out.println(evento);
    	return "OK";
    }
}
