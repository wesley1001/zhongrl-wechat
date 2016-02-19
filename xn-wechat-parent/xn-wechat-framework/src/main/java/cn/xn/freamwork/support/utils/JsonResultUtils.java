package cn.xn.freamwork.support.utils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回给前端JSON数据模型工具类
 *
 * @author lcl 2014/07/11
 * @version 1.0.0
 */
public class JsonResultUtils
{

    /** ================== 返回状态编码 ================== */
    public final static Integer R_SUCCESS_CODE = 200;
    public final static boolean R_SUCCESS_MS   = true;

    public final static Integer R_FAIL_CODE = 500;
    public final static boolean R_FAIL_MS   = false;


    public static Object successResult(Map<String, Object> modelMap, Object data) {
        if (null == modelMap)
            modelMap = new HashMap<String, Object>();

        modelMap.put("code", R_SUCCESS_CODE);
        modelMap.put("success", R_SUCCESS_MS);
        //组合数据模型
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("rows", data);
        modelMap.put("data", dataMap);
        return JSON.toJSON(modelMap);
    }

    public static Object successStatusR() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("code", R_SUCCESS_CODE);
        modelMap.put("success", R_SUCCESS_MS);
        return JSON.toJSON(modelMap);
    }

    public static Object failStatusR() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("code", R_FAIL_CODE);
        modelMap.put("success", R_FAIL_MS);
        return JSON.toJSON(modelMap);
    }


    public static Object failResult(Map<String, Object> modelMap){
        if (null == modelMap)
            modelMap = new HashMap<String, Object>();

        modelMap.put("code", R_FAIL_CODE);
        modelMap.put("success", R_FAIL_MS);
        modelMap.put("data", null);
        return JSON.toJSON(modelMap);
    }


    public static Object customResult(Map<String, Object> modelMap, Integer code, String message, Object data){
        if (null == modelMap)
            modelMap = new HashMap<String, Object>();

        modelMap.put("code", code);
        modelMap.put("success", message);
        modelMap.put("data", data);
        return JSON.toJSON(modelMap);
    }

}
