package com.qg.util;

import com.alibaba.fastjson.JSONObject;
import com.qg.constant.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 描述：json工具集
 * 创建人: Sangby
 * 创建时间: 2024/04/13
 */

public class JsonUtil {
    /**
     * 设置json格式并返回
     *
     * @param result   结果
     * @param response 响应
     */
    public static void toJson(Result result, HttpServletResponse response){
        //设置json格式
        response.setContentType("text/json");

        response.setHeader("Cache-Control","no-cache");

        //设置编码
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer;
        try {
            writer = response.getWriter();
            writer.write(JSONObject.toJSONString(result));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
