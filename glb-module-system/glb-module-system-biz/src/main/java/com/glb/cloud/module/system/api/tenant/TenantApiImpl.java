package com.glb.cloud.module.system.api.tenant;

import com.glb.cloud.framework.common.pojo.CommonResult;
import com.glb.cloud.module.system.service.tenant.TenantService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;

import static com.glb.cloud.framework.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class TenantApiImpl implements TenantApi {

    @Resource
    private TenantService tenantService;

    @Override
    public CommonResult<List<Long>> getTenantIdList() {
        return success(tenantService.getTenantIdList());
    }

    @Override
    public CommonResult<Boolean> validTenant(Long id) {
        tenantService.validTenant(id);
        return success(true);
    }

}
