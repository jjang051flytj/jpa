package com.jjang051.jpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping({"/index/index","/home","/"})
    public String index() {
        return "index/index";
    }
}
