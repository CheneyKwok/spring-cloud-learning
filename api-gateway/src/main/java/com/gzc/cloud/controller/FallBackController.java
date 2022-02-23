package com.gzc.cloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FallBackController {

    @GetMapping("/fallback")
    public Object fallback() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 500);
        map.put("message", "Get Request Fallback");
        map.put("data", null);
        return map;
    }
}
