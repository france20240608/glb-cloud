package com.glb.cloud.module.promotion.api.reward;

import com.glb.cloud.framework.common.pojo.CommonResult;
import com.glb.cloud.module.promotion.api.reward.dto.RewardActivityMatchRespDTO;
import com.glb.cloud.module.promotion.service.reward.RewardActivityService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static com.glb.cloud.framework.common.pojo.CommonResult.success;

/**
 * 满减送活动 API 实现类
 *
 * @author 芋道源码
 */
@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class RewardActivityApiImpl implements RewardActivityApi {

    @Resource
    private RewardActivityService rewardActivityService;

    @Override
    public CommonResult<List<RewardActivityMatchRespDTO>> getMatchRewardActivityList(Collection<Long> spuIds) {
        return success(rewardActivityService.getMatchRewardActivityList(spuIds));
    }

}
