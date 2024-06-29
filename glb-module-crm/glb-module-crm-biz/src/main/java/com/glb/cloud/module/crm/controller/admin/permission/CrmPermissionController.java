package com.glb.cloud.module.crm.controller.admin.permission;

import cn.hutool.core.collection.CollUtil;
import com.glb.cloud.framework.common.pojo.CommonResult;
import com.glb.cloud.framework.common.util.collection.CollectionUtils;
import com.glb.cloud.framework.common.util.collection.MapUtils;
import com.glb.cloud.framework.common.util.object.BeanUtils;
import com.glb.cloud.module.crm.controller.admin.permission.vo.CrmPermissionRespVO;
import com.glb.cloud.module.crm.controller.admin.permission.vo.CrmPermissionSaveReqVO;
import com.glb.cloud.module.crm.controller.admin.permission.vo.CrmPermissionUpdateReqVO;
import com.glb.cloud.module.crm.dal.dataobject.permission.CrmPermissionDO;
import com.glb.cloud.module.crm.enums.permission.CrmPermissionLevelEnum;
import com.glb.cloud.module.crm.framework.permission.core.annotations.CrmPermission;
import com.glb.cloud.module.crm.service.permission.CrmPermissionService;
import com.glb.cloud.module.system.api.dept.DeptApi;
import com.glb.cloud.module.system.api.dept.PostApi;
import com.glb.cloud.module.system.api.dept.dto.DeptRespDTO;
import com.glb.cloud.module.system.api.dept.dto.PostRespDTO;
import com.glb.cloud.module.system.api.user.AdminUserApi;
import com.glb.cloud.module.system.api.user.dto.AdminUserRespDTO;
import com.google.common.collect.Multimaps;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.glb.cloud.framework.common.pojo.CommonResult.success;
import static com.glb.cloud.framework.common.util.collection.CollectionUtils.convertSet;
import static com.glb.cloud.framework.common.util.collection.CollectionUtils.convertSetByFlatMap;
import static com.glb.cloud.framework.common.util.collection.MapUtils.findAndThen;
import static com.glb.cloud.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - CRM 数据权限")
@RestController
@RequestMapping("/crm/permission")
@Validated
public class CrmPermissionController {

    @Resource
    private CrmPermissionService permissionService;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private DeptApi deptApi;
    @Resource
    private PostApi postApi;

    @PostMapping("/create")
    @Operation(summary = "创建数据权限")
    public CommonResult<Boolean> create(@Valid @RequestBody CrmPermissionSaveReqVO reqVO) {
        permissionService.createPermission(reqVO, getLoginUserId());
        return success(true);
    }

    @PutMapping("/update")
    @Operation(summary = "编辑数据权限")
    @CrmPermission(bizTypeValue = "#updateReqVO.bizType", bizId = "#updateReqVO.bizId"
            , level = CrmPermissionLevelEnum.OWNER)
    public CommonResult<Boolean> updatePermission(@Valid @RequestBody CrmPermissionUpdateReqVO updateReqVO) {
        permissionService.updatePermission(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除数据权限")
    @Parameter(name = "ids", description = "数据权限编号", required = true, example = "1024")
    public CommonResult<Boolean> deletePermission(@RequestParam("ids") Collection<Long> ids) {
        permissionService.deletePermissionBatch(ids, getLoginUserId());
        return success(true);
    }

    @DeleteMapping("/delete-self")
    @Operation(summary = "删除自己的数据权限")
    @Parameter(name = "id", description = "数据权限编号", required = true, example = "1024")
    public CommonResult<Boolean> deleteSelfPermission(@RequestParam("id") Long id) {
        permissionService.deleteSelfPermission(id, getLoginUserId());
        return success(true);
    }

    @GetMapping("/list")
    @Operation(summary = "获得数据权限列表")
    @Parameters({
            @Parameter(name = "bizType", description = "CRM 类型", required = true, example = "2"),
            @Parameter(name = "bizId", description = "CRM 类型数据编号", required = true, example = "1024")
    })
    public CommonResult<List<CrmPermissionRespVO>> getPermissionList(@RequestParam("bizType") Integer bizType,
                                                                     @RequestParam("bizId") Long bizId) {
        List<CrmPermissionDO> permissions = permissionService.getPermissionListByBiz(bizType, bizId);
        if (CollUtil.isEmpty(permissions)) {
            return success(Collections.emptyList());
        }

        // 查询相关数据
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(
                convertSet(permissions, CrmPermissionDO::getUserId));
        Map<Long, DeptRespDTO> deptMap = deptApi.getDeptMap(convertSet(userMap.values(), AdminUserRespDTO::getDeptId));
        Map<Long, PostRespDTO> postMap = postApi.getPostMap(
                convertSetByFlatMap(userMap.values(), AdminUserRespDTO::getPostIds,
                        item -> item != null ? item.stream() : Stream.empty()));
        // 拼接数据
        return success(CollectionUtils.convertList(BeanUtils.toBean(permissions, CrmPermissionRespVO.class), item -> {
            findAndThen(userMap, item.getUserId(), user -> {
                item.setNickname(user.getNickname());
                findAndThen(deptMap, user.getDeptId(), deptRespDTO -> item.setDeptName(deptRespDTO.getName()));
                if (CollUtil.isEmpty(user.getPostIds())) {
                    item.setPostNames(Collections.emptySet());
                    return;
                }
                List<PostRespDTO> postList = MapUtils.getList(Multimaps.forMap(postMap), user.getPostIds());
                item.setPostNames(CollectionUtils.convertSet(postList, PostRespDTO::getName));
            });
            return item;
        }));
    }

}
