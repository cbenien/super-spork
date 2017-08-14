package com.superspork.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
public class ApiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
    public @ResponseBody List<String> index() {
        logger.info("index was called...");
        return Arrays.asList("a", "b", "c");
    }

}

