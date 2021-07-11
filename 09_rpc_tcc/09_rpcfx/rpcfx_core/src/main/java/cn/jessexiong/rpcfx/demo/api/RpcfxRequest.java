package cn.jessexiong.rpcfx.demo.api;

import lombok.Data;

@Data
public class RpcfxRequest {
    private String serviceClass;
    private String method;
    private Object[] params;
    private String group = "default";
    private String version = "1";
}
