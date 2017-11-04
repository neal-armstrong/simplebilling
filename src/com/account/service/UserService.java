package com.account.service;

import com.account.entity.UsersEx;

import java.sql.SQLException;

/**
 * 用户服务接口
 *
 * @author Neal
 */
public interface UserService {

    /**
     * 处理登录
     *
     * @param usersEx 用户扩展类对象
     * @return UsersEx
     * @throws SQLException SQLException
     */
    UsersEx login(UsersEx usersEx) throws SQLException;

    /**
     * 用户注册
     *
     * @param usersEx 用户
     * @throws SQLException SQLException
     */
    void saveUser(UsersEx usersEx) throws SQLException;

    /**
     * 用户资料修改
     *
     * @param usersEx 用户
     * @throws SQLException SQLException
     */
    void update(UsersEx usersEx) throws SQLException;

    /**
     * 检查电话号码是否存在
     *
     * @param usersEx 用户
     * @return boolean
     * @throws SQLException SQLException
     */
    int existPhoneNum(UsersEx usersEx) throws SQLException;

    /**
     * 检查用户名是否存在
     *
     * @param usersEx 用户
     * @return boolean
     * @throws SQLException SQLException
     */
    int existUserName(UsersEx usersEx) throws SQLException;

    /**
     * 检查邮箱是否已经存在
     *
     * @param usersEx 用户
     * @return boolean
     * @throws SQLException SQLException
     */
    int existEmail(UsersEx usersEx) throws SQLException;
}
