package com.glb.cloud.module.promotion.controller.app.seckill;

import com.glb.cloud.framework.common.enums.CommonStatusEnum;
import com.glb.cloud.framework.common.pojo.CommonResult;
import com.glb.cloud.module.promotion.controller.app.seckill.vo.config.AppSeckillConfigRespVO;
import com.glb.cloud.module.promotion.convert.seckill.seckillconfig.SeckillConfigConvert;
import com.glb.cloud.module.promotion.dal.dataobject.seckill.SeckillConfigDO;
import com.glb.cloud.module.promotion.service.seckill.SeckillConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;

import static com.glb.cloud.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 App - 秒杀时间段")
@RestController
@RequestMapping("/promotion/seckill-config")
@Validated
public class AppSeckillConfigController {
    @Resource
    private SeckillConfigService configService;

    @GetMapping("/list")
    @Operation(summary = "获得秒杀时间段列表")
    public CommonResult<List<AppSeckillConfigRespVO>> getSeckillConfigList() {
        List<SeckillConfigDO> list = configService.getSeckillConfigListByStatus(CommonStatusEnum.ENABLE.getStatus());
        return success(SeckillConfigConvert.INSTANCE.convertList2(list));
    }

}
