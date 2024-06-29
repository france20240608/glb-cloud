package com.glb.cloud.module.pay.dal.mysql.transfer;

import com.glb.cloud.framework.mybatis.core.mapper.BaseMapperX;
import com.glb.cloud.module.pay.dal.dataobject.transfer.PayTransferDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayTransferMapper extends BaseMapperX<PayTransferDO> {

    default int updateByIdAndStatus(Long id, List<Integer> status, PayTransferDO updateObj) {
        return update(updateObj, new LambdaQueryWrapper<PayTransferDO>()
                .eq(PayTransferDO::getId, id).in(PayTransferDO::getStatus, status));
    }

}




