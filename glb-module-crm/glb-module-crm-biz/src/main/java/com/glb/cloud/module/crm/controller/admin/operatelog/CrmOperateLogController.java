package com.glb.cloud.module.crm.controller.admin.operatelog;

import com.glb.cloud.framework.common.pojo.CommonResult;
import com.glb.cloud.framework.common.pojo.PageResult;
import com.glb.cloud.framework.common.util.object.BeanUtils;
import com.glb.cloud.module.crm.controller.admin.operatelog.vo.CrmOperateLogPageReqVO;
import com.glb.cloud.module.crm.controller.admin.operatelog.vo.CrmOperateLogRespVO;
import com.glb.cloud.module.crm.enums.LogRecordConstants;
import com.glb.cloud.module.crm.enums.common.CrmBizTypeEnum;
import com.glb.cloud.module.system.api.logger.OperateLogApi;
import com.glb.cloud.module.system.api.logger.dto.OperateLogPageReqDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.glb.cloud.framework.common.pojo.CommonResult.success;
import static com.glb.cloud.framework.common.pojo.PageParam.PAGE_SIZE_NONE;
import static com.glb.cloud.module.crm.enums.LogRecordConstants.*;

@Tag(name = "管理后台 - CRM 操作日志")
@RestController
@RequestMapping("/crm/operate-log")
@Validated
public class CrmOperateLogController {

    @Resource
    private OperateLogApi operateLogApi;

    /**
     * {@link CrmBizTypeEnum} 与 {@link LogRecordConstants} 的映射关系
     */
    private static final Map<Integer, String> BIZ_TYPE_MAP = new HashMap<>();

    static {
        BIZ_TYPE_MAP.put(CrmBizTypeEnum.CRM_CLUE.getType(), CRM_CLUE_TYPE);
        BIZ_TYPE_MAP.put(CrmBizTypeEnum.CRM_CUSTOMER.getType(), CRM_CUSTOMER_TYPE);
        BIZ_TYPE_MAP.put(CrmBizTypeEnum.CRM_CONTACT.getType(), CRM_CONTACT_TYPE);
        BIZ_TYPE_MAP.put(CrmBizTypeEnum.CRM_BUSINESS.getType(), CRM_BUSINESS_TYPE);
        BIZ_TYPE_MAP.put(CrmBizTypeEnum.CRM_CONTRACT.getType(), CRM_CONTRACT_TYPE);
        BIZ_TYPE_MAP.put(CrmBizTypeEnum.CRM_PRODUCT.getType(), CRM_PRODUCT_TYPE);
        BIZ_TYPE_MAP.put(CrmBizTypeEnum.CRM_RECEIVABLE.getType(), CRM_RECEIVABLE_TYPE);
        BIZ_TYPE_MAP.put(CrmBizTypeEnum.CRM_RECEIVABLE_PLAN.getType(), CRM_RECEIVABLE_PLAN_TYPE);
    }

    @GetMapping("/page")
    @Operation(summary = "获得操作日志")
    public CommonResult<PageResult<CrmOperateLogRespVO>> getCustomerOperateLog(@Valid CrmOperateLogPageReqVO pageReqVO) {
        OperateLogPageReqDTO reqDTO = new OperateLogPageReqDTO();
        reqDTO.setPageSize(PAGE_SIZE_NONE); // 默认不分页，需要分页需注释
        reqDTO.setType(BIZ_TYPE_MAP.get(pageReqVO.getBizType())).setBizId(pageReqVO.getBizId());
        return success(BeanUtils.toBean(operateLogApi.getOperateLogPage(reqDTO).getCheckedData(), CrmOperateLogRespVO.class));
    }

}
