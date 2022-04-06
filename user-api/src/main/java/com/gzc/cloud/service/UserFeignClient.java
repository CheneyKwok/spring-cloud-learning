package com.gzc.cloud.service;

import com.gzc.cloud.domain.CommonResult;
import com.gzc.cloud.domain.User;
import com.gzc.cloud.service.impl.UserFallBackFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(value = "user-service", fallback = UserFallBackFeignClient.class)
public interface UserFeignClient {

    @PostMapping("/user/create")
    CommonResult create(@RequestBody User user);

    @GetMapping("/user/{id}")
    CommonResult<User> getUser(@PathVariable("id") Long id);

    @GetMapping("/user/getByUsername")
    CommonResult<User> getByUsername(@RequestParam String username);

    @PostMapping("/user/update")
    CommonResult update(@RequestBody User user);

    @PostMapping("/user/delete/{id}")
    CommonResult delete(@PathVariable Long id);
}
