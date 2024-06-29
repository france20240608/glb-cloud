package com.glb.cloud.module.crm.service.statistics;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjUtil;
import com.glb.cloud.framework.ip.core.Area;
import com.glb.cloud.framework.ip.core.enums.AreaTypeEnum;
import com.glb.cloud.framework.ip.core.utils.AreaUtils;
import com.glb.cloud.module.crm.controller.admin.statistics.vo.portrait.*;
import com.glb.cloud.module.crm.dal.mysql.statistics.CrmStatisticsPortraitMapper;
import com.glb.cloud.module.system.api.dept.DeptApi;
import com.glb.cloud.module.system.api.dept.dto.DeptRespDTO;
import com.glb.cloud.module.system.api.user.AdminUserApi;
import com.glb.cloud.module.system.api.user.dto.AdminUserRespDTO;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.glb.cloud.framework.common.util.collection.CollectionUtils.convertList;
import static com.glb.cloud.framework.common.util.collection.CollectionUtils.convertMap;

/**
 * CRM 客户画像 Service 实现类
 *
 * @author HUIHUI
 */
@Service
public class CrmStatisticsPortraitServiceImpl implements CrmStatisticsPortraitService {

    @Resource
    private CrmStatisticsPortraitMapper portraitMapper;

    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private DeptApi deptApi;

    @Override
    public List<CrmStatisticCustomerAreaRespVO> getCustomerSummaryByArea(CrmStatisticsPortraitReqVO reqVO) {
        // 1. 获得用户编号数组
        List<Long> userIds = getUserIds(reqVO);
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        reqVO.setUserIds(userIds);

        // 2. 获取客户地区统计数据
        List<CrmStatisticCustomerAreaRespVO> list = portraitMapper.selectSummaryListGroupByAreaId(reqVO);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyList();
        }

        // 3. 拼接数据
        List<Area> areaList = AreaUtils.getByType(AreaTypeEnum.PROVINCE, area -> area);
        Map<Integer, Area> areaMap = convertMap(areaList, Area::getId);
        return convertList(list, item -> {
            Integer parentId = AreaUtils.getParentIdByType(item.getAreaId(), AreaTypeEnum.PROVINCE);
            if (parentId != null) {
                Area area = areaMap.get(parentId);
                if (area != null) {
                    item.setAreaId(parentId).setAreaName(area.getName());
                    return item;
                }
            }
            // 找不到，归到未知
            return item.setAreaId(null).setAreaName("未知");
        });
    }

    @Override
    public List<CrmStatisticCustomerIndustryRespVO> getCustomerSummaryByIndustry(CrmStatisticsPortraitReqVO reqVO) {
        // 1. 获得用户编号数组
        List<Long> userIds = getUserIds(reqVO);
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        reqVO.setUserIds(userIds);

        // 2. 获取客户行业统计数据
        return portraitMapper.selectCustomerIndustryListGroupByIndustryId(reqVO);
    }

    @Override
    public List<CrmStatisticCustomerSourceRespVO> getCustomerSummaryBySource(CrmStatisticsPortraitReqVO reqVO) {
        // 1. 获得用户编号数组
        List<Long> userIds = getUserIds(reqVO);
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        reqVO.setUserIds(userIds);

        // 2. 获取客户行业统计数据
        return portraitMapper.selectCustomerSourceListGroupBySource(reqVO);
    }

    @Override
    public List<CrmStatisticCustomerLevelRespVO> getCustomerSummaryByLevel(CrmStatisticsPortraitReqVO reqVO) {
        // 1. 获得用户编号数组
        List<Long> userIds = getUserIds(reqVO);
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        reqVO.setUserIds(userIds);

        // 2. 获取客户级别统计数据
        return portraitMapper.selectCustomerLevelListGroupByLevel(reqVO);
    }

    /**
     * 获取用户编号数组。如果用户编号为空, 则获得部门下的用户编号数组，包括子部门的所有用户编号
     *
     * @param reqVO 请求参数
     * @return 用户编号数组
     */
    private List<Long> getUserIds(CrmStatisticsPortraitReqVO reqVO) {
        // 情况一：选中某个用户
        if (ObjUtil.isNotNull(reqVO.getUserId())) {
            return ListUtil.of(reqVO.getUserId());
        }
        // 情况二：选中某个部门
        // 2.1 获得部门列表
        List<Long> deptIds = convertList(deptApi.getChildDeptList(reqVO.getDeptId()).getCheckedData(), DeptRespDTO::getId);
        deptIds.add(reqVO.getDeptId());
        // 2.2 获得用户编号
        return convertList(adminUserApi.getUserListByDeptIds(deptIds).getCheckedData(), AdminUserRespDTO::getId);
    }

}
