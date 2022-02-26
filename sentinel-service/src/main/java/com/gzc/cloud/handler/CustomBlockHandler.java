package com.gzc.cloud.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.gzc.cloud.domain.CommonResult;
import org.springframework.stereotype.Component;

@Component
public class CustomBlockHandler {

    public CommonResult handleException(BlockException e) {
        return new CommonResult("自定义限流信息", 200);
    }
}
