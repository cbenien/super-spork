package com.superspork.web;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

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

    @GetMapping("/summary")
    public String summary(Model model)
    {
        int total = counters
                .values()
                .stream()
                .reduce(0, (a,b) -> a + b);

        model.addAttribute("total", total);
        return "summary";
    }

    @GetMapping("/callapi")
    public String callApi(Model model)
    {
        RestTemplate rest = new RestTemplate();

        Object[] data = rest.getForObject(
                URI.create("http://api:8080/"),
                Object[].class  );

        String items = Arrays
                .stream(data)
                .map(x -> (String)x)
                .reduce("", (a,b) -> a + ", " + b);

        model.addAttribute("value", items);

        return "callapi";
    }

    private static int IncrementByName(String name)
    {
        counters.putIfAbsent(name, 0);
        return counters.compute(name, (key, value) -> value + 1);
    }


}

