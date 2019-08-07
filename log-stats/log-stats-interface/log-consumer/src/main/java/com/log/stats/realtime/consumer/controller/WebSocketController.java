package com.log.stats.realtime.consumer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WebSocketController {

    @GetMapping("/index")
    public String home(Model model) {
        return "index";
    }
}
