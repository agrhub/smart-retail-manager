package com.agrhub.app.smart_retail.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {
    @GetMapping(path = "/hello")
    public String hello() {
        return "Hello, World!";
    }
}
