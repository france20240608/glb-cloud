package com.glb.cloud.module.crm.dal.mysql.contract;

import com.glb.cloud.framework.mybatis.core.mapper.BaseMapperX;
import com.glb.cloud.framework.mybatis.core.query.QueryWrapperX;
import com.glb.cloud.module.crm.dal.dataobject.contract.CrmContractConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 合同配置 Mapper
 *
 * @author Wanwan
 */
@Mapper
public interface CrmContractConfigMapper extends BaseMapperX<CrmContractConfigDO> {

    default CrmContractConfigDO selectOne() {
        return selectOne(new QueryWrapperX<CrmContractConfigDO>().limitN(1));
    }

}