package com.gzc.cloud.dao;

import com.gzc.cloud.domain.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao {

    void create(Order order);

    void update(@Param("userId") Long userId, @Param("status") Integer status);
}
