package com.gzc.cloud.domain;


import lombok.Builder;
import lombok.Data;

/**
 * Oauth2 获取 token 返回信息封装
 */

@Data
@Builder
public class Oauth2TokenDTO {

    /**
     * 访问令牌
     */
    private String token;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 访问令牌前缀
     */
    private String tokenHead;

    /**
     * 有效时间（秒）
     */
    private int expireIn;
}
