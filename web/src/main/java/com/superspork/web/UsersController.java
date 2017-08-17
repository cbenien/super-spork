package com.superspork.web;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class UsersController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RestTemplate userApi = new RestTemplate();

    @GetMapping("/addUser")
    public String addUser(Model model)
    {
        model.addAttribute("user", new UserData());
        return "addUser";
    }

    @PostMapping("/addUser")
    public String addUserSubmit(@ModelAttribute UserData user)
            throws IOException
    {
        UUID id = UUID.randomUUID();
        URI putUri = URI.create("http://api:8080/users/" + id);

        logger.info("Putting user {} to {}", user, putUri);
        userApi.put(putUri, user);

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String users(Model model)
    {
        logger.info("/users was called...");

        UserData[] data = userApi.getForObject(
                URI.create("http://api:8080/users"),
                UserData[].class);

        List<UserData> users = Arrays
                .stream(data)
                .map(x -> (UserData)x)
                .collect(Collectors.toList());

        model.addAttribute("users", users);
        return "users";
    }
}

