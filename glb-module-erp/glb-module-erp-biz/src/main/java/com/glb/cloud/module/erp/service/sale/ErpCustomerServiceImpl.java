package com.glb.cloud.module.erp.service.sale;

import com.glb.cloud.framework.common.enums.CommonStatusEnum;
import com.glb.cloud.framework.common.pojo.PageResult;
import com.glb.cloud.framework.common.util.object.BeanUtils;
import com.glb.cloud.module.erp.controller.admin.sale.vo.customer.ErpCustomerPageReqVO;
import com.glb.cloud.module.erp.controller.admin.sale.vo.customer.ErpCustomerSaveReqVO;
import com.glb.cloud.module.erp.dal.dataobject.sale.ErpCustomerDO;
import com.glb.cloud.module.erp.dal.mysql.sale.ErpCustomerMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static com.glb.cloud.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.glb.cloud.module.erp.enums.ErrorCodeConstants.CUSTOMER_NOT_ENABLE;
import static com.glb.cloud.module.erp.enums.ErrorCodeConstants.CUSTOMER_NOT_EXISTS;

/**
 * ERP 客户 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ErpCustomerServiceImpl implements ErpCustomerService {

    @Resource
    private ErpCustomerMapper customerMapper;

    @Override
    public Long createCustomer(ErpCustomerSaveReqVO createReqVO) {
        // 插入
        ErpCustomerDO customer = BeanUtils.toBean(createReqVO, ErpCustomerDO.class);
        customerMapper.insert(customer);
        // 返回
        return customer.getId();
    }

    @Override
    public void updateCustomer(ErpCustomerSaveReqVO updateReqVO) {
        // 校验存在
        validateCustomerExists(updateReqVO.getId());
        // 更新
        ErpCustomerDO updateObj = BeanUtils.toBean(updateReqVO, ErpCustomerDO.class);
        customerMapper.updateById(updateObj);
    }

    @Override
    public void deleteCustomer(Long id) {
        // 校验存在
        validateCustomerExists(id);
        // 删除
        customerMapper.deleteById(id);
    }

    private void validateCustomerExists(Long id) {
        if (customerMapper.selectById(id) == null) {
            throw exception(CUSTOMER_NOT_EXISTS);
        }
    }

    @Override
    public ErpCustomerDO getCustomer(Long id) {
        return customerMapper.selectById(id);
    }

    @Override
    public ErpCustomerDO validateCustomer(Long id) {
        ErpCustomerDO customer = customerMapper.selectById(id);
        if (customer == null) {
            throw exception(CUSTOMER_NOT_EXISTS);
        }
        if (CommonStatusEnum.isDisable(customer.getStatus())) {
            throw exception(CUSTOMER_NOT_ENABLE, customer.getName());
        }
        return customer;
    }

    @Override
    public List<ErpCustomerDO> getCustomerList(Collection<Long> ids) {
        return customerMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ErpCustomerDO> getCustomerPage(ErpCustomerPageReqVO pageReqVO) {
        return customerMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ErpCustomerDO> getCustomerListByStatus(Integer status) {
        return customerMapper.selectListByStatus(status);
    }

}