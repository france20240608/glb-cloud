package com.glb.cloud.module.system.framework.rpc.config;

import com.glb.cloud.module.infra.api.file.FileApi;
import com.glb.cloud.module.infra.api.websocket.WebSocketSenderApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {FileApi.class, WebSocketSenderApi.class})
public class RpcConfiguration {
}
