package com.glb.cloud.module.pay.convert.wallet;

import com.glb.cloud.framework.common.pojo.PageResult;
import com.glb.cloud.framework.common.util.collection.MapUtils;
import com.glb.cloud.module.member.api.user.dto.MemberUserRespDTO;
import com.glb.cloud.module.pay.controller.admin.wallet.vo.wallet.PayWalletRespVO;
import com.glb.cloud.module.pay.controller.app.wallet.vo.wallet.AppPayWalletRespVO;
import com.glb.cloud.module.pay.dal.dataobject.wallet.PayWalletDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper
public interface PayWalletConvert {

    PayWalletConvert INSTANCE = Mappers.getMapper(PayWalletConvert.class);

    AppPayWalletRespVO convert(PayWalletDO bean);

    PayWalletRespVO convert02(String nickname,String avatar, PayWalletDO bean);

    PageResult<PayWalletRespVO> convertPage(PageResult<PayWalletDO> page);

    default PageResult<PayWalletRespVO> convertPage(PageResult<PayWalletDO> page, Map<Long, MemberUserRespDTO> userMap){
        PageResult<PayWalletRespVO> pageResult = convertPage(page);
        pageResult.getList().forEach( wallet -> MapUtils.findAndThen(userMap, wallet.getUserId(),
                user -> {
            // TODO @jason：可以链式调用哈；
                    wallet.setNickname(user.getNickname());
                    wallet.setAvatar(user.getAvatar());
                }));
        return pageResult;
    }

}
