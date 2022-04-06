package com.gzc.cloud.service.impl;

import com.gzc.cloud.domain.User;
import com.gzc.cloud.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private List<User> userList;

    @PostConstruct
    public void initData() {
        userList = new ArrayList<>();
        userList.add(new User(1L, "aaa", "123456"));
        userList.add(new User(2L, "bbb", "123456"));
        userList.add(new User(3L, "ccc", "123456"));
    }

    @Override
    public void create(User user) {
        userList.add(user);
    }

    @Override
    public User getUser(Long id) {
        Optional<User> userOptional = userList.stream().filter(e -> e.getId().equals(id)).findAny();

        return userOptional.orElse(null);
    }

    @Override
    public void update(User user) {
        userList.stream()
                .filter(e -> e.equals(user))
                .forEach(e -> {
                    e.setUsername(user.getUsername());
                    e.setPassword(user.getPassword());
                });
    }

    @Override
    public void delete(Long id) {
        userList.removeIf(e -> e.getId().equals(id));
    }

    @Override
    public User getByUsername(String username) {
        return userList.stream()
                .filter(e -> e.getUsername().equals(username))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<User> listByIds(List<Long> ids) {
        Set<Long> idSet = new HashSet<>(ids);
        return userList.stream()
                .filter(e -> idSet.contains(e.getId()))
                .collect(Collectors.toList());
    }
}
