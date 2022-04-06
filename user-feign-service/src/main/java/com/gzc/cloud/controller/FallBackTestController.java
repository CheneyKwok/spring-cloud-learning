package com.gzc.cloud.controller;

import com.gzc.cloud.domain.CommonResult;
import com.gzc.cloud.service.UserFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class FallBackTestController {

    private final UserFeignClient userFeignClient;

    @GetMapping("/fallBackTest/{id}")
    private CommonResult fallBackTest(@PathVariable("id") Long id) {
        return userFeignClient.getUser(id);
    }
}
