package com.glb.cloud.module.infra.service.logger;

import cn.hutool.core.util.StrUtil;
import com.glb.cloud.framework.common.pojo.PageResult;
import com.glb.cloud.framework.common.util.object.BeanUtils;
import com.glb.cloud.module.infra.api.logger.dto.ApiAccessLogCreateReqDTO;
import com.glb.cloud.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogPageReqVO;
import com.glb.cloud.module.infra.dal.dataobject.logger.ApiAccessLogDO;
import com.glb.cloud.module.infra.dal.mysql.logger.ApiAccessLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;

import static com.glb.cloud.module.infra.dal.dataobject.logger.ApiAccessLogDO.REQUEST_PARAMS_MAX_LENGTH;
import static com.glb.cloud.module.infra.dal.dataobject.logger.ApiAccessLogDO.RESULT_MSG_MAX_LENGTH;

/**
 * API 访问日志 Service 实现类
 *
 * @author 芋道源码
 */
@Slf4j
@Service
@Validated
public class ApiAccessLogServiceImpl implements ApiAccessLogService {

    @Resource
    private ApiAccessLogMapper apiAccessLogMapper;

    @Override
    public void createApiAccessLog(ApiAccessLogCreateReqDTO createDTO) {
        ApiAccessLogDO apiAccessLog = BeanUtils.toBean(createDTO, ApiAccessLogDO.class);
        apiAccessLog.setRequestParams(StrUtil.maxLength(apiAccessLog.getRequestParams(), REQUEST_PARAMS_MAX_LENGTH));
        apiAccessLog.setResultMsg(StrUtil.maxLength(apiAccessLog.getResultMsg(), RESULT_MSG_MAX_LENGTH));
        apiAccessLogMapper.insert(apiAccessLog);
    }

    @Override
    public PageResult<ApiAccessLogDO> getApiAccessLogPage(ApiAccessLogPageReqVO pageReqVO) {
        return apiAccessLogMapper.selectPage(pageReqVO);
    }

    @Override
    @SuppressWarnings("DuplicatedCode")
    public Integer cleanAccessLog(Integer exceedDay, Integer deleteLimit) {
        int count = 0;
        LocalDateTime expireDate = LocalDateTime.now().minusDays(exceedDay);
        // 循环删除，直到没有满足条件的数据
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            int deleteCount = apiAccessLogMapper.deleteByCreateTimeLt(expireDate, deleteLimit);
            count += deleteCount;
            // 达到删除预期条数，说明到底了
            if (deleteCount < deleteLimit) {
                break;
            }
        }
        return count;
    }

}