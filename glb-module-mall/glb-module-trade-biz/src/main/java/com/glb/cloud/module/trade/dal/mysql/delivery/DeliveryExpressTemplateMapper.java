package com.glb.cloud.module.trade.dal.mysql.delivery;


import com.glb.cloud.framework.common.pojo.PageResult;
import com.glb.cloud.framework.mybatis.core.mapper.BaseMapperX;
import com.glb.cloud.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.glb.cloud.module.trade.controller.admin.delivery.vo.expresstemplate.DeliveryExpressTemplatePageReqVO;
import com.glb.cloud.module.trade.dal.dataobject.delivery.DeliveryExpressTemplateDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeliveryExpressTemplateMapper extends BaseMapperX<DeliveryExpressTemplateDO> {

    default PageResult<DeliveryExpressTemplateDO> selectPage(DeliveryExpressTemplatePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DeliveryExpressTemplateDO>()
                .likeIfPresent(DeliveryExpressTemplateDO::getName, reqVO.getName())
                .eqIfPresent(DeliveryExpressTemplateDO::getChargeMode, reqVO.getChargeMode())
                .betweenIfPresent(DeliveryExpressTemplateDO::getCreateTime, reqVO.getCreateTime())
                .orderByAsc(DeliveryExpressTemplateDO::getSort));
    }

    default DeliveryExpressTemplateDO selectByName(String name) {
        return selectOne(DeliveryExpressTemplateDO::getName,name);
    }

}
