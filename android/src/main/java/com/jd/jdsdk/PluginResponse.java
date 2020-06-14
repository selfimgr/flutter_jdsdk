package com.jd.jdsdk;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static com.jd.jdsdk.PluginConstants.ERROR_CODE_EXCEPTION;

public class PluginResponse implements Serializable {

    private String errorCode;
    private String errorMessage;
    private Object data;

    public static PluginResponse success(Object obj){
        return new PluginResponse("0", "成功", obj);
    }

    public static PluginResponse failed(Exception e){
        return new PluginResponse(ERROR_CODE_EXCEPTION, "异常中止: " + e.getMessage(), null);
    }

    public PluginResponse(String errorCode, String errorMessage, Object data) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("errorCode", errorCode);
        map.put("errorMessage", errorMessage);
        map.put("data", data);
        return map;
    }
}
