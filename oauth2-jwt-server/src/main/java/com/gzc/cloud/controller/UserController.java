package com.gzc.cloud.controller;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication) {
        return authentication.getPrincipal();
//        String authorization = request.getHeader("Authorization");
//        String token = StrUtil.subAfter(authorization, "bearer", false);
//        return Jwts.parser()
//                .setSigningKey("test_key".getBytes(StandardCharsets.UTF_8))
//                .parseClaimsJws(token)
//                .getBody();
    }
}
