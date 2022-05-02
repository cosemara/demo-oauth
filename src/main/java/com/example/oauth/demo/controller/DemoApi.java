package com.example.oauth.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class DemoApi {

    @GetMapping("/test")
    public String index() {
        return "hello";
    }

    @GetMapping("/oauth2/redirect")
    public String authorize(@RequestParam Map param) {
        log.debug("code:"+param.get("code"));
        log.debug("token:"+param.get("token"));
        return "redirect";
    }
}
