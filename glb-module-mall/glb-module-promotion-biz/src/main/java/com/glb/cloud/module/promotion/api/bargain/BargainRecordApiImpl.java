package com.glb.cloud.module.promotion.api.bargain;

import com.glb.cloud.framework.common.pojo.CommonResult;
import com.glb.cloud.module.promotion.api.bargain.dto.BargainValidateJoinRespDTO;
import com.glb.cloud.module.promotion.service.bargain.BargainRecordService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

import static com.glb.cloud.framework.common.pojo.CommonResult.success;

/**
 * 砍价活动 API 实现类
 *
 * @author HUIHUI
 */
@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class BargainRecordApiImpl implements BargainRecordApi {

    @Resource
    private BargainRecordService bargainRecordService;

    @Override
    public CommonResult<BargainValidateJoinRespDTO> validateJoinBargain(Long userId, Long bargainRecordId, Long skuId) {
        return success(bargainRecordService.validateJoinBargain(userId, bargainRecordId, skuId));
    }

    @Override
    public CommonResult<Boolean> updateBargainRecordOrderId(Long id, Long orderId) {
        bargainRecordService.updateBargainRecordOrderId(id, orderId);
        return success(true);
    }

}
