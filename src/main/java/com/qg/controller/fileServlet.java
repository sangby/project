package com.qg.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet("/file/*")
public class fileServlet extends BaseServlet{
    /**
     * 上传文件
     *
     * @param req  请求
     * @param resp 响应
     */

    public void upload(HttpServletRequest req,HttpServletRequest resp){

    }
}
