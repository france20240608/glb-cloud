package com.glb.cloud.module.trade.framework.rpc.config;

import com.glb.cloud.module.member.api.address.MemberAddressApi;
import com.glb.cloud.module.member.api.config.MemberConfigApi;
import com.glb.cloud.module.member.api.level.MemberLevelApi;
import com.glb.cloud.module.member.api.point.MemberPointApi;
import com.glb.cloud.module.member.api.user.MemberUserApi;
import com.glb.cloud.module.pay.api.order.PayOrderApi;
import com.glb.cloud.module.pay.api.refund.PayRefundApi;
import com.glb.cloud.module.product.api.category.ProductCategoryApi;
import com.glb.cloud.module.product.api.comment.ProductCommentApi;
import com.glb.cloud.module.product.api.sku.ProductSkuApi;
import com.glb.cloud.module.product.api.spu.ProductSpuApi;
import com.glb.cloud.module.promotion.api.bargain.BargainActivityApi;
import com.glb.cloud.module.promotion.api.bargain.BargainRecordApi;
import com.glb.cloud.module.promotion.api.combination.CombinationRecordApi;
import com.glb.cloud.module.promotion.api.coupon.CouponApi;
import com.glb.cloud.module.promotion.api.discount.DiscountActivityApi;
import com.glb.cloud.module.promotion.api.reward.RewardActivityApi;
import com.glb.cloud.module.promotion.api.seckill.SeckillActivityApi;
import com.glb.cloud.module.system.api.notify.NotifyMessageSendApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {
        BargainActivityApi.class, BargainRecordApi.class, CombinationRecordApi.class,
        CouponApi.class, DiscountActivityApi.class, RewardActivityApi.class, SeckillActivityApi.class,
        MemberUserApi.class, MemberPointApi.class, MemberLevelApi.class, MemberAddressApi.class, MemberConfigApi.class,
        ProductSpuApi.class, ProductSkuApi.class, ProductCommentApi.class, ProductCategoryApi.class,
        PayOrderApi.class, PayRefundApi.class,
        NotifyMessageSendApi.class
})
public class RpcConfiguration {
}
