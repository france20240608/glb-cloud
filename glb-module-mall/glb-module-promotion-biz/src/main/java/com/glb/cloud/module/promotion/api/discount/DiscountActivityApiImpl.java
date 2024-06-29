package com.glb.cloud.module.promotion.api.discount;

import com.glb.cloud.framework.common.pojo.CommonResult;
import com.glb.cloud.module.promotion.api.discount.dto.DiscountProductRespDTO;
import com.glb.cloud.module.promotion.convert.discount.DiscountActivityConvert;
import com.glb.cloud.module.promotion.service.discount.DiscountActivityService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static com.glb.cloud.framework.common.pojo.CommonResult.success;

/**
 * 限时折扣 API 实现类
 *
 * @author 芋道源码
 */
@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class DiscountActivityApiImpl implements DiscountActivityApi {

    @Resource
    private DiscountActivityService discountActivityService;

    @Override
    public CommonResult<List<DiscountProductRespDTO>> getMatchDiscountProductList(Collection<Long> skuIds) {
        return success(DiscountActivityConvert.INSTANCE.convertList02(discountActivityService.getMatchDiscountProductList(skuIds)));
    }

}