package com.superspork.web;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    static int counter = 0;

    @RequestMapping("/")
    public String index() {
        counter ++;
        return "Greetings from Spring Boot! You are visitor number " + counter;
    }

}

