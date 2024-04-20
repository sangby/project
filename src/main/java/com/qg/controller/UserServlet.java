package com.qg.controller;

import com.alibaba.fastjson.JSON;
import com.qg.bean.SingletonFactory;
import com.qg.constant.Result;
import com.qg.constant.ResultEnum;
import com.qg.po.User;
import com.qg.service.UserService;
import com.qg.service.impl.UserServiceImpl;
import com.qg.util.JsonUtil;
import com.qg.util.Md5Util;
import com.qg.util.impl.PoolUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 描述：用户控制层
 * 创建人: Sangby
 * 创建时间: 2024/04/13
 */

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    /**
     * 登录
     *
     * @param req  请求
     * @param resp 响应
     */

    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String userStr = reader.readLine();
        User user = JSON.parseObject(userStr,User.class);
        String username = user.getUserName();
        String password = user.getPassWord();


        if (username.equals("")||password.equals("")){
            System.out.println("数据校验");

            return ;
        }

        //加密密码
        user.setPassWord(Md5Util.encrypt(password));




        UserService userService = SingletonFactory.getUserServiceSingleton();
        Result<User> userResult = null;
        try {
            userResult = userService.login(user.getUserName(),user.getPassWord());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //保存用户名到session,多次赋值同一个键会覆盖
            HttpSession session = req.getSession();
            session.setAttribute("userName", user.getUserName());

        //封装为json对象,返回前端后,通过code判断登录情况
        JsonUtil.toJson(userResult,resp);




    }

    /**
     * 登记
     *
     * @param req  请求
     * @param resp  响应
     */

    public void register(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
//        String username = req.getParameter("username");
//        String password = req.getParameter("password");
        BufferedReader reader = req.getReader();
        String userStr = reader.readLine();
        User user = JSON.parseObject(userStr,User.class);
        String username = user.getUserName();
        String password = user.getPassWord();
        //密码加密
        user.setPassWord(Md5Util.encrypt(password));

        user.setUserName(username);

        if (username.equals("")||password.equals("")){
            System.out.println("数据校验");
            return;
        }
        UserService userService = SingletonFactory.getUserServiceSingleton();
        Result<User> registerResult = userService.register(user.getUserName(),user.getPassWord());

        JsonUtil.toJson(registerResult,resp);
    }

    /**
     * 获取用户表的所有信息
     *
     * @param req  请求
     * @param resp 响应
     */

    public void getUser(HttpServletRequest req, HttpServletResponse resp){

        HttpSession session = req.getSession(false);
        Object userName = session.getAttribute("userName");

        UserServiceImpl userServiceSingleton = SingletonFactory.getUserServiceSingleton();
        Result<User> initResult = userServiceSingleton.loginInit(userName.toString());

        JsonUtil.toJson(initResult,resp);

    }

    public void logout(HttpServletRequest req, HttpServletResponse resp){
        //销毁session
        HttpSession session = req.getSession(false);
        session.invalidate();
    }

    /**
     * 初始化普通用户主页面
     *
     * @param req  请求
     * @param resp 响应
     */

    public void initCommonIndex(HttpServletRequest req, HttpServletResponse resp){

    }

    /**
     * 初始化管理指数
     *
     * @param req  请求
     * @param resp 响应
     */

    public void initAdminIndex(HttpServletRequest req, HttpServletResponse resp){

    }

    /**
     * 初始化来访者指数
     *
     * @param req  请求
     * @param resp 响应
     */

    public void initVisitorIndex(HttpServletRequest req, HttpServletResponse resp){

    }



    /**
     * 更新个人信息
     *
     * @param request  请求
     * @param response 响应
     */

    public void updatePersonInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();


    }

    /**
     * 基金,传入id,传回其他群组资金,负责群组资金,个人余额,
     *
     * @param request  请求
     * @param response 响应
     */

    public void fund(HttpServletRequest request,HttpServletResponse response){

    }

    /**
     * 转账,传入付款人,和收款人,金额,传回
     *
     * @param request  请求
     * @param response 响应
     */

    public void transferMoney(HttpServletRequest request,HttpServletResponse response){
        //任何对资金有增删的操作都要有同步和事务
    }

    /**
     * 给钱到群组,传入负责人名称,群组名称,金额,返回
     *
     * @param request  请求
     * @param response 响应
     */

    public void giveMoneyToFirm(HttpServletRequest request,HttpServletResponse response){

    }

    /**
     * 群组收款,传入发起群收款的人,群组名称,金额
     *
     * @param request  请求
     * @param response 响应
     */

    public void firmCollection(HttpServletRequest request,HttpServletResponse response){

    }

    /**
     * 我的流水明细
     *
     * @param request  请求
     * @param response 响应
     */

    public void dayToDayAccount(HttpServletRequest request,HttpServletResponse response){

    }




}
