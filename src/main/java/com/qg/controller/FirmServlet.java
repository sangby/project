package com.qg.controller;

import com.alibaba.fastjson.JSON;
import com.mysql.cj.Session;
import com.qg.bean.SingletonFactory;
import com.qg.constant.Result;
import com.qg.po.Firm;
import com.qg.service.impl.FirmServiceImpl;
import com.qg.util.JsonUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/firm/*")
public class FirmServlet extends BaseServlet{
    public void joinFirm(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String firmStr = reader.readLine();
        Firm firm = JSON.parseObject(firmStr, Firm.class);
        int fid = firm.getFid();

        //获取session中id
        int userId = (int) req.getSession(false).getAttribute("uid");

        FirmServiceImpl firmServiceSingleton = SingletonFactory.getFirmServiceSingleton();
        Result<Object> joinFirmResult = firmServiceSingleton.joinFirm(userId,fid);

        JsonUtil.toJson(joinFirmResult, resp);

    }

    /**
     * 创建公司
     *
     * @param req  请求
     * @param resp 响应
     */

    public void createFirm(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String FirmStr = reader.readLine();
        Firm firm = JSON.parseObject(FirmStr, Firm.class);
        String firmName = firm.getFirmName();
        String introduction = firm.getIntroduction();

        HttpSession session = req.getSession(false);

        int uid = (int) session.getAttribute("uid");

        FirmServiceImpl firmServiceSingleton = SingletonFactory.getFirmServiceSingleton();
        Result<Object> createFimrResult = firmServiceSingleton.createFirm(firmName,introduction,uid);

        JsonUtil.toJson(createFimrResult, resp);

    }


    /**
     * 用户群组,传入用户名,在用户成员表和用户负责表中查询群组,打包为链表带回
     *
     * @param req  请求
     * @param resp 响应
     */

    public void firmOfUser(HttpServletRequest req, HttpServletResponse resp){

    }


    /**
     * 搜索商行,通过群组名称,在群组表中查找,返回
     *
     * @param req  请求
     * @param resp 响应
     */

    public void findFirm(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String findFirmNameStr = reader.readLine();
        //很奇怪,单传一个中文字符串,JSON数据乱码了
        Firm firm = JSON.parseObject(findFirmNameStr, Firm.class);
        String findFirmName = firm.getFirmName();

        FirmServiceImpl firmServiceSingleton = SingletonFactory.getFirmServiceSingleton();
        Result<List<Firm>> findFirmResult = firmServiceSingleton.findFirm(findFirmName);

        JsonUtil.toJson(findFirmResult, resp);

    }

    /**
     * 注销公司,通过群组在群组负责表中删除一条信息
     * 在群组请求表中删除请求记录(可能要),
     * 在群组成员表中删除所有成员
     * 在群组表中删除群组记录
     *
     * @param req  请求
     * @param resp 响应
     */

    public void closeFirm(HttpServletRequest req, HttpServletResponse resp){

    }

    /**
     * 退出公司,把自己的字段删除掉
     *
     * @param req  请求
     * @param resp 响应
     */

    public void quitFirm(HttpServletRequest req, HttpServletResponse resp){

    }

    /**
     * 更新公司信息,传入公司信息,更新数据库
     *
     * @param req  请求
     * @param resp 响应
     */

    public  void updateFirmInfo(HttpServletRequest req, HttpServletResponse resp){

    }

    /**
     * 分配群组资金,传入群组名和资金和成员名,在群组成员表中修改
     *
     * @param req  请求
     * @param resp 响应
     */

    public void assignFirmFund(HttpServletRequest req, HttpServletResponse resp){

    }



}
