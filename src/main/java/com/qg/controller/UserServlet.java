package com.qg.controller;

import com.alibaba.fastjson.JSON;
import com.qg.bean.PayInfo;
import com.qg.bean.SingletonFactory;
import com.qg.bean.TransInfo;
import com.qg.bean.UserFund;
import com.qg.constant.Result;
import com.qg.constant.ResultEnum;
import com.qg.po.User;
import com.qg.service.UserService;
import com.qg.service.impl.FirmServiceImpl;
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
import java.util.List;

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
        //如果session被清掉了
        if (session == null){
            JsonUtil.toJson(new Result<>(ResultEnum.USER_LOST.getCode(),ResultEnum.USER_LOST.getMsg()),resp);
        }
        Object userName = session.getAttribute("userName");
        UserServiceImpl userServiceSingleton = SingletonFactory.getUserServiceSingleton();
        Result<User> initResult = userServiceSingleton.loginInit(userName.toString());

        //上面用用户名来标记用户(session)不太安全,刚好可以在这里补个id
        //之后就用这个来标记用户
        User data = initResult.getData();
        session.setAttribute("uid",data.getUid());

        JsonUtil.toJson(initResult,resp);

    }

    public void logout(HttpServletRequest req, HttpServletResponse resp){
        //销毁session
        HttpSession session = req.getSession(false);
        session.invalidate();

        try {
            //关闭数据库连接
            PoolUtil.closeMyPool();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获得用户钱信息
     *
     * @param request  请求
     * @param response 响应
     */

    public  void  getUserMoneyInfo(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        if (session == null){
            JsonUtil.toJson(new Result<>(ResultEnum.USER_LOST.getCode(),ResultEnum.USER_LOST.getMsg()),response);
        }
        Object uid = session.getAttribute("uid");

        UserServiceImpl userServiceSingleton = SingletonFactory.getUserServiceSingleton();
        Result<List<UserFund>> moneyInfoResult = userServiceSingleton.getUserMoneyInfo((int)uid);

        JsonUtil.toJson(moneyInfoResult,response);

    }





    /**
     * 更新个人信息
     *
     * @param request  请求
     * @param response 响应
     */

    public void updatePersonInfo(HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException {
        //处理post数据,得到User对象
        BufferedReader reader = request.getReader();
        String userStr = reader.readLine();
        User user = JSON.parseObject(userStr, User.class);

        //获取UserService实例,传入User对象
        UserServiceImpl userServiceSingleton = SingletonFactory.getUserServiceSingleton();
        Result<Object> updateResult = userServiceSingleton.updatePersonInfo(user);


        //返回结果
        JsonUtil.toJson(updateResult, response);

    }

    /**
     * 登录时检测封禁
     *
     * @param request  请求
     * @param response 响应
     */

    public void block(HttpServletRequest request,HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        String blockIdStr = reader.readLine();
        Integer blockId = JSON.parseObject(blockIdStr, Integer.class);

        //服务层
        UserServiceImpl userServiceSingleton = SingletonFactory.getUserServiceSingleton();
        Result<Object> blockUserResult = userServiceSingleton.blockUser(blockId);

        JsonUtil.toJson(blockUserResult, response);

    }


    /**
     * 转账,传入付款人,和收款人,金额,传回
     *
     * @param request  请求
     * @param response 响应
     */

    public void transferAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取交易信息
        BufferedReader reader = request.getReader();
        String transferStr = reader.readLine();
        TransInfo transInfo = JSON.parseObject(transferStr, TransInfo.class);
        PayInfo payInfo = transInfo.getPayInfo();
        Boolean isPayFirm = transInfo.getIsPayFirm();

        HttpSession session = request.getSession();
        String userName = session.getAttribute("userName").toString();
        int uid = (int)session.getAttribute("uid");

        int pid;
        int aid = payInfo.getAid();
        String pass = Md5Util.encrypt(payInfo.getPass());
        int money = payInfo.getMoney();
        Result<Object> transferResult;
//是否以群组付款
        if(isPayFirm){

            pid = payInfo.getPid();
            FirmServiceImpl firmServiceSingleton = SingletonFactory.getFirmServiceSingleton();
            transferResult = firmServiceSingleton.transfer(userName, pid, aid, money, uid, pass);

        }else {
            pid = uid;
            UserServiceImpl userServiceSingleton = SingletonFactory.getUserServiceSingleton();
            transferResult = userServiceSingleton.transfer(aid, pid, money, pass, userName);
        }

        JsonUtil.toJson(transferResult,response);

    }

    public void recharge(HttpServletRequest request,HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null){
            JsonUtil.toJson(new Result<>(ResultEnum.USER_LOST.getCode(),ResultEnum.USER_LOST.getMsg()),response);
        }
        Object uid = session.getAttribute("uid");
        String userName = session.getAttribute("userName").toString();


        BufferedReader reader = request.getReader();
        String str = reader.readLine();
        PayInfo payInfo = JSON.parseObject(str, PayInfo.class);

        int aid = payInfo.getAid();
        int money = payInfo.getMoney();
        String pass =  Md5Util.encrypt(payInfo.getPass());

        UserServiceImpl userServiceSingleton = SingletonFactory.getUserServiceSingleton();
        Result<Object> rechargeResult = userServiceSingleton.recharge(userName, aid, (int) uid, money, pass);

        JsonUtil.toJson(rechargeResult,response);

    }

    /**
     * 刷新余额
     *
     * @param request  请求
     * @param response 响应
     */

    public void refreshMoney(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        if (session == null){
            JsonUtil.toJson(new Result<>(ResultEnum.USER_LOST.getCode(),ResultEnum.USER_LOST.getMsg()),response);
        }
        Object uid = session.getAttribute("uid");

        UserServiceImpl userServiceSingleton = SingletonFactory.getUserServiceSingleton();

        Result<Integer> integerResult = userServiceSingleton.refreshMoney((int) uid);

        JsonUtil.toJson(integerResult,response);


    }

    /**
     * 群组收款,传入发起群收款的人,群组名称,金额
     *
     * @param request  请求
     * @param response 响应
     */

    public void firmCollection(HttpServletRequest request,HttpServletResponse response) throws IOException {

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
