package com.gzc.cloud.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.gzc.cloud.domain.CommonResult;
import com.gzc.cloud.handler.CustomBlockHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 限流功能
 */
@RequestMapping("/rateLimit")
@RestController
public class RateLimitController {

    /**
     * 按资源名称限流，需要指定限流处理逻辑
     * @return
     */
    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    public CommonResult byResource() {
        return new CommonResult("按资源名称限流", 200);
    }

    /**
     * 按Url限流，有默认的限流处理逻辑
     * @return
     */
    @GetMapping("/byUrl")
    @SentinelResource(value = "byUrl", blockHandler = "handleException")
    public CommonResult byUrl() {
        return new CommonResult("按Url名称限流", 200);
    }

    /**
     * 自定义通用的限流处理逻辑
     */
    @GetMapping("/customBlockHandler")
    @SentinelResource(value = "customBlockHandler", blockHandler = "handleException", blockHandlerClass = CustomBlockHandler.class)
    public CommonResult blockHandler() {
        return new CommonResult("限流成功", 200);
    }

    public CommonResult handleException(BlockException e) {
        return new CommonResult(e.getClass().getCanonicalName(), 200);
    }

}
