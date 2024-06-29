package com.glb.cloud.module.system.api.logger;

import com.glb.cloud.framework.common.pojo.CommonResult;
import com.glb.cloud.framework.common.pojo.PageResult;
import com.glb.cloud.framework.common.util.object.BeanUtils;
import com.glb.cloud.module.system.api.logger.dto.OperateLogCreateReqDTO;
import com.glb.cloud.module.system.api.logger.dto.OperateLogPageReqDTO;
import com.glb.cloud.module.system.api.logger.dto.OperateLogRespDTO;
import com.glb.cloud.module.system.dal.dataobject.logger.OperateLogDO;
import com.glb.cloud.module.system.service.logger.OperateLogService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

import static com.glb.cloud.framework.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class OperateLogApiImpl implements OperateLogApi {

    @Resource
    private OperateLogService operateLogService;

    @Override
    public CommonResult<Boolean> createOperateLog(OperateLogCreateReqDTO createReqDTO) {
        operateLogService.createOperateLog(createReqDTO);
        return success(true);
    }

    @Override
    public CommonResult<PageResult<OperateLogRespDTO>> getOperateLogPage(OperateLogPageReqDTO pageReqVO) {
        PageResult<OperateLogDO> operateLogPage = operateLogService.getOperateLogPage(pageReqVO);
        return success(BeanUtils.toBean(operateLogPage, OperateLogRespDTO.class));
    }

}
