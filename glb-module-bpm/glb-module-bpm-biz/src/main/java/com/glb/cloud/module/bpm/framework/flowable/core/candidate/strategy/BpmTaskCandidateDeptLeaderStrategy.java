package com.glb.cloud.module.bpm.framework.flowable.core.candidate.strategy;

import com.glb.cloud.framework.common.util.string.StrUtils;
import com.glb.cloud.module.bpm.framework.flowable.core.candidate.BpmTaskCandidateStrategy;
import com.glb.cloud.module.bpm.framework.flowable.core.enums.BpmTaskCandidateStrategyEnum;
import com.glb.cloud.module.system.api.dept.DeptApi;
import com.glb.cloud.module.system.api.dept.dto.DeptRespDTO;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Set;

import static com.glb.cloud.framework.common.util.collection.CollectionUtils.convertSet;

/**
 * 部门的负责人 {@link BpmTaskCandidateStrategy} 实现类
 *
 * @author kyle
 */
@Component
public class BpmTaskCandidateDeptLeaderStrategy implements BpmTaskCandidateStrategy {

    @Resource
    private DeptApi deptApi;

    @Override
    public BpmTaskCandidateStrategyEnum getStrategy() {
        return BpmTaskCandidateStrategyEnum.DEPT_LEADER;
    }

    @Override
    public void validateParam(String param) {
        Set<Long> deptIds = StrUtils.splitToLongSet(param);
        deptApi.validateDeptList(deptIds);
    }

    @Override
    public Set<Long> calculateUsers(DelegateExecution execution, String param) {
        Set<Long> deptIds = StrUtils.splitToLongSet(param);
        List<DeptRespDTO> depts = deptApi.getDeptList(deptIds).getCheckedData();
        return convertSet(depts, DeptRespDTO::getLeaderUserId);
    }

}