package com.example.demo.controller;

import com.example.demo.service.userService;
import com.example.demo.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class restControllerTest {
    private final com.example.demo.service.userService userService;

    @PostMapping(value = "/info1")
    public ResponseEntity<User> info1(@RequestBody User user) {
        return ResponseEntity.ok(userService.retrieveUserInfo(user));
    }

    @PostMapping(value = "/info2")
    public ResponseEntity<User> info2(@RequestParam(value = "userName", required = true) String userName) {
        User user = userService.retrieveUserInfo(userName);

        if (user == null) {
            return ResponseEntity.noContent().build();
        }
        System.out.println("no-content");
        // return ResponseEntity.ok(user);
        return ResponseEntity.noContent().build();
        // return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/info3")
    public ResponseEntity<User> info3(@RequestParam(value = "userName", required = true) String userName) {
        return Optional.ofNullable(userService.retrieveUserInfo(userName)).map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.noContent().build());
    }
}
