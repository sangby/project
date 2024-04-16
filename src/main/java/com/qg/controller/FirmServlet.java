package com.qg.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FirmServlet extends BaseServlet{

    /**
     * 创建公司,传入用户名和群组名,向群组请求表插入一条请求
     *
     * @param req  请求
     * @param resp 响应
     */

    public void createFirm(HttpServletRequest req, HttpServletResponse resp){

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

    public void findFirm(HttpServletRequest req, HttpServletResponse resp){

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
