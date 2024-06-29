package com.glb.cloud.module.member.framework.security.config;

import com.glb.cloud.framework.security.config.AuthorizeRequestsCustomizer;
import com.glb.cloud.module.member.enums.ApiConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * Member 模块的 Security 配置
 */
@Configuration("memberSecurityConfiguration")
public class SecurityConfiguration {

    @Bean("memberAuthorizeRequestsCustomizer")
    public AuthorizeRequestsCustomizer authorizeRequestsCustomizer() {
        return new AuthorizeRequestsCustomizer() {

            @Override
            public void customize(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
                // Swagger 接口文档
                registry.requestMatchers("/v3/api-docs/**").permitAll() // 元数据
                        .requestMatchers("/swagger-ui.html").permitAll(); // Swagger UI
                // Spring Boot Actuator 的安全配置
                registry.requestMatchers("/actuator").anonymous()
                        .requestMatchers("/actuator/**").anonymous();
                // Druid 监控
                registry.requestMatchers("/druid/**").anonymous();
                // RPC 服务的安全配置
                registry.requestMatchers(ApiConstants.PREFIX + "/**").permitAll();
            }

        };
    }

}
