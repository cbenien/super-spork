package com.superspork.web;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    static int counter = 0;

    @GetMapping("/")
    public String index(Model model) {
        counter ++;
        logger.info("Main action invoked, current counter is {}", counter);
        model.addAttribute("name", "Harry Potter");
        model.addAttribute("counter", counter);
        return "home";
    }

}

