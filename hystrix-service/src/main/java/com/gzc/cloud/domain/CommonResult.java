package com.gzc.cloud.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> {

    private T data;

    private String message;

    private Integer code;

    public CommonResult(String message, Integer code) {
        this(null, message, code);
    }

    public CommonResult(T data) {
        this(data, "操作成功", 200);
    }
}

