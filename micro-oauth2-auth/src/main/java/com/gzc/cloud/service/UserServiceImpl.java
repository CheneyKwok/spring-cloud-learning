package com.gzc.cloud.service;

import cn.hutool.core.collection.CollUtil;
import com.gzc.cloud.domain.SecurityUser;
import com.gzc.cloud.domain.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService {

    private List<UserDTO> userList;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initData() {
        String password = passwordEncoder.encode("123");
        userList = new ArrayList<>();
        userList.add(new UserDTO(1L,"aaa", password,1, CollUtil.toList("ADMIN")));
        userList.add(new UserDTO(2L,"bbb", password,1, CollUtil.toList("TEST")));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = userList.stream()
                .filter(e -> e.getUsername().equals(username))
                .findAny()
                .orElseThrow(() -> new UsernameNotFoundException("用户名或密码错误"));
        SecurityUser securityUser = new SecurityUser(userDTO);
        if (!securityUser.isEnabled()) {
            throw new DisabledException("该账户已被禁用，请联系管理员");
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定，请联系管理员");
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new AccountExpiredException("该账号已过期，请联系管理员!");
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("该账户的登录凭证已过期，请重新登录!");
        }
        return securityUser;
    }
}
