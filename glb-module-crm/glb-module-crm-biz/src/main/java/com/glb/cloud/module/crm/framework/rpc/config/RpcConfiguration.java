package com.glb.cloud.module.crm.framework.rpc.config;

import com.glb.cloud.module.bpm.api.task.BpmProcessInstanceApi;
import com.glb.cloud.module.system.api.dept.DeptApi;
import com.glb.cloud.module.system.api.dept.PostApi;
import com.glb.cloud.module.system.api.user.AdminUserApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {AdminUserApi.class, DeptApi.class, PostApi.class,
        BpmProcessInstanceApi.class})
public class RpcConfiguration {
}
