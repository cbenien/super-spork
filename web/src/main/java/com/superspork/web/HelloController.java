package com.superspork.web;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Controller
public class HelloController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static ConcurrentMap<String,Integer> counters = new ConcurrentHashMap<String, Integer>();

    @GetMapping("/")
    public String index(Model model) {
        String defaultName = "Harry Potter";
        int currentCount = IncrementByName(defaultName);
        logger.info("Main action invoked, current default counter is {}", currentCount);
        model.addAttribute("name", "Harry Potter");
        model.addAttribute("counter", currentCount);
        return "home";
    }

    @GetMapping("/name/{name}")
    public String name(@PathVariable("name") String name, Model model) {
        int currentCount = IncrementByName(name);
        logger.info("Main action invoked, current counter for {} is {}", name, currentCount);
        model.addAttribute("name", name);
        model.addAttribute("counter", currentCount);
        return "home";
    }

    private static int IncrementByName(String name)
    {
        counters.putIfAbsent(name, 0);
        return counters.compute(name, (key, value) -> value + 1);
    }


}

