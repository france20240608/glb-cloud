package com.glb.cloud.framework.apilog.config;

import com.glb.cloud.framework.apilog.core.filter.ApiAccessLogFilter;
import com.glb.cloud.framework.apilog.core.interceptor.ApiAccessLogInterceptor;
import com.glb.cloud.framework.apilog.core.service.ApiAccessLogFrameworkService;
import com.glb.cloud.framework.apilog.core.service.ApiAccessLogFrameworkServiceImpl;
import com.glb.cloud.framework.apilog.core.service.ApiErrorLogFrameworkService;
import com.glb.cloud.framework.apilog.core.service.ApiErrorLogFrameworkServiceImpl;
import com.glb.cloud.framework.common.enums.WebFilterOrderEnum;
import com.glb.cloud.framework.web.config.WebProperties;
import com.glb.cloud.framework.web.config.GlbWebAutoConfiguration;
import com.glb.cloud.module.infra.api.logger.ApiAccessLogApi;
import com.glb.cloud.module.infra.api.logger.ApiErrorLogApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.Filter;

@AutoConfiguration(after = GlbWebAutoConfiguration.class)
public class GlbApiLogAutoConfiguration implements WebMvcConfigurer {

    @Bean
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public ApiAccessLogFrameworkService apiAccessLogFrameworkService(ApiAccessLogApi apiAccessLogApi) {
        return new ApiAccessLogFrameworkServiceImpl(apiAccessLogApi);
    }

    @Bean
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public ApiErrorLogFrameworkService apiErrorLogFrameworkService(ApiErrorLogApi apiErrorLogApi) {
        return new ApiErrorLogFrameworkServiceImpl(apiErrorLogApi);
    }

    /**
     * 创建 ApiAccessLogFilter Bean，记录 API 请求日志
     */
    @Bean
    @ConditionalOnProperty(prefix = "glb.access-log", value = "enable", matchIfMissing = true) // 允许使用 glb.access-log.enable=false 禁用访问日志
    public FilterRegistrationBean<ApiAccessLogFilter> apiAccessLogFilter(WebProperties webProperties,
                                                                         @Value("${spring.application.name}") String applicationName,
                                                                         ApiAccessLogFrameworkService apiAccessLogFrameworkService) {
        ApiAccessLogFilter filter = new ApiAccessLogFilter(webProperties, applicationName, apiAccessLogFrameworkService);
        return createFilterBean(filter, WebFilterOrderEnum.API_ACCESS_LOG_FILTER);
    }

    private static <T extends Filter> FilterRegistrationBean<T> createFilterBean(T filter, Integer order) {
        FilterRegistrationBean<T> bean = new FilterRegistrationBean<>(filter);
        bean.setOrder(order);
        return bean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ApiAccessLogInterceptor());
    }

}
