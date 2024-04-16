package com.qg.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 描述：总控制
 * 创建人: Sangby
 * 创建时间: 2024/04/13
 */

public class BaseServlet extends HttpServlet {

    /**
     * 服务
     *
     * @param req
     * @param resp
     */

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        //先获取请求路径
        String requestUrl = req.getRequestURI();

        //截取请求路径中的方法名
        String methodName = requestUrl.substring(requestUrl.lastIndexOf("/") + 1);

        //获取字节码
        Class<? extends BaseServlet> cla = this.getClass();
        try {
            Method method = cla.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this,req, resp);

        } catch (NoSuchMethodException |InvocationTargetException |IllegalAccessException e) {
            e.printStackTrace();

        }
    }
}
