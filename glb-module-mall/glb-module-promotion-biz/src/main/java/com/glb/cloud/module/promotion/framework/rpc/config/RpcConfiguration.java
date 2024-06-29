package com.glb.cloud.module.promotion.framework.rpc.config;

import com.glb.cloud.module.member.api.user.MemberUserApi;
import com.glb.cloud.module.product.api.category.ProductCategoryApi;
import com.glb.cloud.module.product.api.sku.ProductSkuApi;
import com.glb.cloud.module.product.api.spu.ProductSpuApi;
import com.glb.cloud.module.trade.api.order.TradeOrderApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {ProductSkuApi.class, ProductSpuApi.class, ProductCategoryApi.class,
        MemberUserApi.class, TradeOrderApi.class})
public class RpcConfiguration {
}
