package com.gzc.cloud.service.impl;

import com.gzc.cloud.dao.OrderDao;
import com.gzc.cloud.domain.Order;
import com.gzc.cloud.service.AccountService;
import com.gzc.cloud.service.OrderService;
import com.gzc.cloud.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private StorageService storageService;

    @Autowired
    private AccountService accountService;

    @GlobalTransactional(name = "fsp-create-order", rollbackFor = Exception.class)
    @Override
    public void create(Order order) {
        log.info("----->订单开始");
        // 本应用创建订单
        orderDao.create(order);

        // 远程调用库存服务扣减库存
        log.info("----->order-service 中减库存开始");
        storageService.decrease(order.getProductId(), order.getCount());
        log.info("----->order-service 中减库存结束");

        // 远程调用账号服务扣减余额
        log.info("----->order-service 中扣减余额开始");
        accountService.decrease(order.getUserId(), order.getMoney());
        log.info("----->order-service 中扣减余额结束");

        // 修改订单状态为完成
        log.info("------->order-service 中修改订单状态开始");
        orderDao.update(order.getUserId(), 0);
        log.info("------->order-service 中修改订单状态结束");

        log.info("--------> 下单结束");
    }
}
