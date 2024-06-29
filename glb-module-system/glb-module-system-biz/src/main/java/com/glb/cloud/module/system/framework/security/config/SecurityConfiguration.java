package com.glb.cloud.module.system.framework.security.config;

import com.glb.cloud.framework.security.config.AuthorizeRequestsCustomizer;
import com.glb.cloud.module.system.enums.ApiConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * System 模块的 Security 配置
 */
@Configuration(proxyBeanMethods = false, value = "systemSecurityConfiguration")
public class SecurityConfiguration {

    @Bean("systemAuthorizeRequestsCustomizer")
    public AuthorizeRequestsCustomizer authorizeRequestsCustomizer() {
        return new AuthorizeRequestsCustomizer() {

            @Override
            public void customize(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
                // TODO 芋艿：这个每个项目都需要重复配置，得捉摸有没通用的方案
                // Swagger 接口文档
                registry.requestMatchers("/v3/api-docs/**").permitAll() // 元数据
                        .requestMatchers("/swagger-ui.html").permitAll(); // Swagger UI
                // Druid 监控
                registry.requestMatchers("/druid/**").anonymous();
                // Spring Boot Actuator 的安全配置
                registry.requestMatchers("/actuator").anonymous()
                        .requestMatchers("/actuator/**").anonymous();
                // RPC 服务的安全配置
                registry.requestMatchers(ApiConstants.PREFIX + "/**").permitAll();
            }

        };
    }

}
