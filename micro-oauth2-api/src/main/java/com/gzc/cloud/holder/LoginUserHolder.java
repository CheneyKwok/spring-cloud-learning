package com.gzc.cloud.holder;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import com.gzc.cloud.domain.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class LoginUserHolder {

    public UserDTO getCurrentUser() {
        // 从 Header 中获取用户信息
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String userStr = request.getHeader("user");
        JSONObject json = new JSONObject(userStr);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(json.getLong("id"));
        userDTO.setUsername(json.getStr("user_name"));
        userDTO.setRoles(Convert.toList(String.class, json.get("authorities")));
        return userDTO;
    }
}
