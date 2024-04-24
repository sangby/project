package com.qg.controller;

import com.alibaba.fastjson.JSON;
import com.qg.bean.SingletonFactory;
import com.qg.constant.Result;
import com.qg.constant.ResultEnum;
import com.qg.po.Firm;
import com.qg.service.impl.FirmServiceImpl;
import com.qg.util.JsonUtil;
import com.sun.javafx.binding.StringFormatter;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/file/*")
public class FileServlet extends BaseServlet{
    /**
     * 上传头像
     *
     * @param req  请求
     * @param resp 响应
     */

    public void uploadAvatar(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        //如果传递数据格式错误,抛出异常
        if(!ServletFileUpload.isMultipartContent(req)){
            throw new ServletException("文件上传格式错误");
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);

        try {
//            解析请求，并返回一个包含所有表单字段和文件项的列表。
            List<FileItem> items = servletFileUpload.parseRequest(req);
            for (FileItem item : items) {
                //不是表单
                if (!item.isFormField()){
                    //从完整路径中获取文件名
                    String fileName = new File(item.getName()).getName();
                    String filePath = "D:/code/idea/project/project/src/main/webapp/img/"+fileName;
                    File storeFile = new File(filePath);
                    //如果
                    if(!storeFile.exists()){
                        item.write(storeFile);
                    }
                    Result<String> urlResult = new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), "http://localhost:8080/QG_war_exploded/img/"+fileName);
                    JsonUtil.toJson(urlResult,resp);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
