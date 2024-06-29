package com.glb.cloud.module.crm.controller.admin.operatelog.vo;

import com.glb.cloud.framework.common.pojo.PageParam;
import com.glb.cloud.framework.common.validation.InEnum;
import com.glb.cloud.module.crm.enums.common.CrmBizTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - CRM 操作日志 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmOperateLogPageReqVO extends PageParam {

    @Schema(description = "数据类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @InEnum(CrmBizTypeEnum.class)
    @NotNull(message = "数据类型不能为空")
    private Integer bizType;

    @Schema(description = "数据编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "数据编号不能为空")
    private Long bizId;

}
