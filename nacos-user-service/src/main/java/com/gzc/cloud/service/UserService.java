package com.gzc.cloud.service;

import com.gzc.cloud.domain.User;

import java.util.List;

public interface UserService {

    void create(User user);

    User getUser(Long id);

    void update(User user);

    void delete(Long id);

    User getByUsername(String username);

    List<User> listByIds(List<Long> ids);
}
