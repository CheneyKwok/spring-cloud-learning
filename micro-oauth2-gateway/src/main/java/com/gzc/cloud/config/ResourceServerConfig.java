package com.gzc.cloud.config;

import cn.hutool.core.util.ArrayUtil;
import com.gzc.cloud.component.AuthorizationManager;
import com.gzc.cloud.component.RestAuthenticationEntryPoint;
import com.gzc.cloud.component.RestfulAccessDeniedHandler;
import com.gzc.cloud.filter.IgnoreUrlsRemoveJwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 资源服务器配置
 */
@RequiredArgsConstructor
@EnableWebFluxSecurity
@Configuration
public class ResourceServerConfig {

    private final AuthorizationManager authorizationManager;

    private final IgnoreUrlsConfig ignoreUrlsConfig;

    private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    private final IgnoreUrlsRemoveJwtFilter ignoreUrlsRemoveJwtFilter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        httpSecurity
                .authorizeExchange()
                // 白名单配置
                .pathMatchers(ArrayUtil.toArray(ignoreUrlsConfig.getUrls(), String.class)).permitAll()
                // 鉴权管理器配置
                .anyExchange().access(authorizationManager)
                .and()
                .exceptionHandling()
                // 处理未授权
                .accessDeniedHandler(restfulAccessDeniedHandler)
                // 处理未认证
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and().csrf().disable()
                // 对白名单路径，直接移除JWT请求头
                .addFilterBefore(ignoreUrlsRemoveJwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .oauth2ResourceServer()
                //自定义处理JWT请求头过期或签名错误的结果
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
        return httpSecurity.build();

    }

    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthorityPrefix("ROLE_");
        converter.setAuthoritiesClaimName("authorities");
        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(converter);
        return new ReactiveJwtAuthenticationConverterAdapter(authenticationConverter);
    }
}
