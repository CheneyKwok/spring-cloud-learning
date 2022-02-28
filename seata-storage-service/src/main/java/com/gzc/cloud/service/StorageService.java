package com.gzc.cloud.service;


import java.math.BigDecimal;

public interface StorageService {

    /**
     * 扣减库存
     */
    void decrease(Long productId, Integer count);
}
