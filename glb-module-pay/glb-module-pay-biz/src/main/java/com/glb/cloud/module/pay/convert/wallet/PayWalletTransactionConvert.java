package com.glb.cloud.module.pay.convert.wallet;

import com.glb.cloud.framework.common.pojo.PageResult;
import com.glb.cloud.module.pay.controller.admin.wallet.vo.transaction.PayWalletTransactionRespVO;
import com.glb.cloud.module.pay.controller.app.wallet.vo.transaction.AppPayWalletTransactionRespVO;
import com.glb.cloud.module.pay.dal.dataobject.wallet.PayWalletTransactionDO;
import com.glb.cloud.module.pay.service.wallet.bo.WalletTransactionCreateReqBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayWalletTransactionConvert {

    PayWalletTransactionConvert INSTANCE = Mappers.getMapper(PayWalletTransactionConvert.class);

    PageResult<AppPayWalletTransactionRespVO> convertPage(PageResult<PayWalletTransactionDO> page);

    PageResult<PayWalletTransactionRespVO> convertPage2(PageResult<PayWalletTransactionDO> page);

    PayWalletTransactionDO convert(WalletTransactionCreateReqBO bean);

}
