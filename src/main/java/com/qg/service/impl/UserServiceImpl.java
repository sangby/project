package com.qg.service.impl;

import com.qg.bean.SingletonFactory;
import com.qg.bean.UserFund;
import com.qg.constant.Result;
import com.qg.constant.ResultEnum;
import com.qg.dao.UserDao;
import com.qg.dao.impl.OtherDaoImpl;
import com.qg.dao.impl.UserDaoImpl;
import com.qg.po.User;
import com.qg.service.UserService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：用户服务实现
 * 创建人: Sangby
 * 创建时间: 2024/04/13
 */

public class UserServiceImpl implements UserService {
    private UserServiceImpl(){

    }

    @Override
    public Result<User> login(String username, String password)  {

        UserDao userDao = SingletonFactory.getUserDaoSingleton();

        User user = userDao.selectByNameAndPassword(username, password);
        if (user==null){
            //用户名或者密码错误
            return new Result<>(ResultEnum.USER_OR_PASSWORD_ERROR.getCode(),ResultEnum.USER_OR_PASSWORD_ERROR.getMsg(),null);
        }else{
            //不能把密码返回
            user.setPassWord("");
            return new Result<User>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),user);
        }
    }

    @Override
    public Result<User> select(String userName) {
        UserDao userDao = SingletonFactory.getUserDaoSingleton();

        User user = userDao.selectByName(userName);
        if(user==null){

            return new Result<>(ResultEnum.USER_ALREADY_EXIST.getCode(),ResultEnum.USER_ALREADY_EXIST.getMsg(),null);
        }else{
            return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),user);
        }
    }

    @Override
    public Result<User> register(String userName, String passWord) throws SQLException {
        UserDao userDao = SingletonFactory.getUserDaoSingleton();
        //如果用户已经存在那么注册失败
        User user = userDao.selectByName(userName);
        if(user!=null){
            return new Result<>(ResultEnum.USER_ALREADY_EXIST.getCode(),ResultEnum.USER_ALREADY_EXIST.getMsg(),null);
        }
        int i = userDao.insertNewOne(userName,passWord);
        if (i>0){

            return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg());
        }else{
            return new Result<>(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }
    }

    @Override
    public Result<User> loginInit(String userName) {
        UserDaoImpl userDaoSingleton = SingletonFactory.getUserDaoSingleton();
        User user = userDaoSingleton.selectByName(userName);
        //不返回敏感信息
        user.setPassWord("");
        //这个时候用户一般存在,除非突然去数据把数据删了,我就不考虑先
       return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),user);

    }

    @Override
    public Result<Object> updatePersonInfo(User user) throws SQLException {

        UserDaoImpl userDaoSingleton = SingletonFactory.getUserDaoSingleton();
        String userName = user.getUserName();
        int uid = user.getUid();
        //假如名称已存在且是他人的
        User u = userDaoSingleton.selectByName(userName);
        if(u!=null&&uid!=u.getUid()){
            return new Result<>(ResultEnum.USER_ALREADY_EXIST.getCode(),ResultEnum.USER_ALREADY_EXIST.getMsg());
        }

        userDaoSingleton.update(user);
            //不用返回数据
            return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg());

    }

    @Override
    public Result<Object> blockUser(int id) {
        UserDaoImpl userDaoSingleton = SingletonFactory.getUserDaoSingleton();
        userDaoSingleton.setBlockById(id);

        return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg());
    }

    /**
     * 获得用户群组余额信息
     *
     * @param uid uid
     *
     * @return 后果列表用户基金
     */

    public Result<List<UserFund>> getUserMoneyInfo(int uid){

        OtherDaoImpl otherDaoSingleton = SingletonFactory.getOtherDaoSingleton();
        FirmServiceImpl firmServiceSingleton = SingletonFactory.getFirmServiceSingleton();

        int[] memberFirmId = otherDaoSingleton.findMemberFidByUid(uid);
        int[] responseFirmId = otherDaoSingleton.findResponseFidByUid(uid);
        List<UserFund> userFunds = new ArrayList<>();
        for (int i : memberFirmId) {
            int m1 = otherDaoSingleton.memberFirmFindMoneyByFidAndUid(i, uid);

            userFunds.add(new UserFund(i,m1));
        }
        for (int i : responseFirmId) {
            int m2 = otherDaoSingleton.adminFirmFindMoneyByFidAndUid(i,uid);

            userFunds.add(new UserFund(i,m2));
        }

        return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),userFunds);
    }

    public Result<Object> recharge(String userName,int aid,int pid,int money,String pass){

        OtherDaoImpl otherDaoSingleton = SingletonFactory.getOtherDaoSingleton();
        UserDaoImpl userDaoSingleton = SingletonFactory.getUserDaoSingleton();
        // 判断密码是否正确
        if (userDaoSingleton.selectByNameAndPassword(userName,pass)==null){
            return new Result<>(ResultEnum.PASSWORD_ERROR.getCode(),ResultEnum.PASSWORD_ERROR.getMsg());
        }
        int indentId;
        //生成订单,扣钱
        synchronized (this) {

            if(otherDaoSingleton.insertIndentAndUpdateUserMoney(pid, aid, money)==0){
                //异常
                return new Result<>(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
            }
            indentId = otherDaoSingleton.getIndentId(aid, pid, money);
        }
        //转钱,设置订单状态
        if(otherDaoSingleton.transMoneyToFirmAndUpdateIndentState(aid, money, indentId)==0){
            return new Result<>(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }
        //给自己的管理员分配钱
        otherDaoSingleton.addAdminFund(pid,aid,money);


        return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),money);


    }
    /**
     * 刷新余额
     *
     * @param uid uid
     *
     * @return 结果<integer>
     */

    public Result<Integer> refreshMoney(int uid){
        UserDaoImpl userDaoSingleton = SingletonFactory.getUserDaoSingleton();

        int money = userDaoSingleton.selectMoneyById(uid);

        return new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),money);

    }
}
