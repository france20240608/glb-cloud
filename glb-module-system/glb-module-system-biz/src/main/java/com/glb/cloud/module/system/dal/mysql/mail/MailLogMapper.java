package com.glb.cloud.module.system.dal.mysql.mail;

import com.glb.cloud.framework.common.pojo.PageResult;
import com.glb.cloud.framework.mybatis.core.mapper.BaseMapperX;
import com.glb.cloud.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.glb.cloud.module.system.controller.admin.mail.vo.log.MailLogPageReqVO;
import com.glb.cloud.module.system.dal.dataobject.mail.MailLogDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MailLogMapper extends BaseMapperX<MailLogDO> {

    default PageResult<MailLogDO> selectPage(MailLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MailLogDO>()
                .eqIfPresent(MailLogDO::getUserId, reqVO.getUserId())
                .eqIfPresent(MailLogDO::getUserType, reqVO.getUserType())
                .likeIfPresent(MailLogDO::getToMail, reqVO.getToMail())
                .eqIfPresent(MailLogDO::getAccountId, reqVO.getAccountId())
                .eqIfPresent(MailLogDO::getTemplateId, reqVO.getTemplateId())
                .eqIfPresent(MailLogDO::getSendStatus, reqVO.getSendStatus())
                .betweenIfPresent(MailLogDO::getSendTime, reqVO.getSendTime())
                .orderByDesc(MailLogDO::getId));
    }

}