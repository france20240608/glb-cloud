package com.glb.cloud.module.pay.api.refund;

import com.glb.cloud.framework.common.pojo.CommonResult;
import com.glb.cloud.module.pay.api.refund.dto.PayRefundCreateReqDTO;
import com.glb.cloud.module.pay.api.refund.dto.PayRefundRespDTO;
import com.glb.cloud.module.pay.convert.refund.PayRefundConvert;
import com.glb.cloud.module.pay.service.refund.PayRefundService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

import static com.glb.cloud.framework.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class PayRefundApiImpl implements PayRefundApi {

    @Resource
    private PayRefundService payRefundService;

    @Override
    public CommonResult<Long> createRefund(PayRefundCreateReqDTO reqDTO) {
        return success(payRefundService.createPayRefund(reqDTO));
    }

    @Override
    public CommonResult<PayRefundRespDTO> getRefund(Long id) {
        return success(PayRefundConvert.INSTANCE.convert02(payRefundService.getRefund(id)));
    }

}
