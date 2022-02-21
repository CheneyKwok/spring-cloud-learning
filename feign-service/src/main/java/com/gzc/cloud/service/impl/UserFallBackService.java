package com.gzc.cloud.service.impl;

import com.gzc.cloud.domain.CommonResult;
import com.gzc.cloud.domain.User;
import com.gzc.cloud.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserFallBackService implements UserService {

    @Override
    public CommonResult create(User user) {
        return new CommonResult(new User(-1L, "defaultUser", "123456"));
    }

    @Override
    public CommonResult<User> getUser(Long id) {
        return new CommonResult(new User(-1L, "defaultUser", "123456"));
    }

    @Override
    public CommonResult<User> getByUsername(String username) {
        return new CommonResult(new User(-1L, "defaultUser", "123456"));
    }

    @Override
    public CommonResult update(User user) {
        return new CommonResult("调用失败，服务降级", 500);
    }

    @Override
    public CommonResult delete(Long id) {
        return new CommonResult("调用失败，服务降级", 500);
    }
}
