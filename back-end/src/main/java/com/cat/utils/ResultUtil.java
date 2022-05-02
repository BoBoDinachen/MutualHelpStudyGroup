package com.cat.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * date: 2021/12/29 21:53
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
public class ResultUtil {

    /**
     * 定义成功的结果数据
     * @param msg
     * @param data
     */
    public static String success(String msg,Object data){
        JSONObject result = new JSONObject();
        result.put("status",true);
        result.put("msg",msg);
        result.put("data",data);
        return JSON.toJSONString(result);
    }

    /**
     * 定义失败时的结果数据
     * @param msg
     * @return
     */
    public static String failed(String msg){
        JSONObject result = new JSONObject();
        result.put("status",false);
        result.put("msg",msg);
        result.put("data",null);
        return JSON.toJSONString(result);
    }
}
