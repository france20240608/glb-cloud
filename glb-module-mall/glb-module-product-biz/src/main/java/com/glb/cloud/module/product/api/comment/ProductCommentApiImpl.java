package com.glb.cloud.module.product.api.comment;

import com.glb.cloud.framework.common.pojo.CommonResult;
import com.glb.cloud.module.product.api.comment.dto.ProductCommentCreateReqDTO;
import com.glb.cloud.module.product.service.comment.ProductCommentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

import static com.glb.cloud.framework.common.pojo.CommonResult.success;

/**
 * 商品评论 API 实现类
 *
 * @author HUIHUI
 */
@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class ProductCommentApiImpl implements ProductCommentApi {

    @Resource
    private ProductCommentService productCommentService;

    @Override
    public CommonResult<Long> createComment(ProductCommentCreateReqDTO createReqDTO) {
        return success(productCommentService.createComment(createReqDTO));
    }

}
