package com.example.demo.controller;

import com.example.demo.service.userService;
import com.example.demo.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor // @RequiredArgsConstructor > @Autowired 대신 Bean 주입 가능
public class ControllerTest {
    private final userService userService;

    @PostMapping(value = "/info")
    public @ResponseBody User info(@RequestBody User user) {
        return userService.retrieveUserInfo(user);
    }

    @GetMapping(value = "infoView")
    public String infoView(Model model, @RequestParam(value = "userName", required = true) String userName) {
        User user = userService.retrieveUserInfo(userName);
        // model.addAttribute("user", user);
        return "infoView";
    }
}
