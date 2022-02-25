package com.gzc.cloud.controller;

import com.gzc.cloud.domain.CommonResult;
import com.gzc.cloud.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserRibbonController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-user-service}")
    private String userServiceUrl;

    @PostMapping("/create")
    public CommonResult create(@RequestBody User user) {
        return restTemplate.postForObject(userServiceUrl + "/user/create", user, CommonResult.class);
    }

    @GetMapping("/{id}")
    public CommonResult getUser(@PathVariable("id") Long id) {
        return restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
    }

    @GetMapping("/getUserByIds")
    public CommonResult getEntityByUsername(@RequestParam String username) {
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(userServiceUrl + "/user/getByUsername?username={1}", CommonResult.class, username);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        } else {
            return new CommonResult("操作失败", 500);
        }
    }

    @GetMapping("/getByUsername")
    public CommonResult<User> getByUsername(@RequestParam String username) {
        return restTemplate.getForObject(userServiceUrl + "/user/getByUsername?username={1}", CommonResult.class, username);
    }

    @PostMapping("/update")
    public CommonResult update(@RequestBody User user) {
        return restTemplate.postForObject(userServiceUrl + "/user/update", user, CommonResult.class);
    }

    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        return restTemplate.postForObject(userServiceUrl + "/user/{1}", null, CommonResult.class, id);
    }

}
