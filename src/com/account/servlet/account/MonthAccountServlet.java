package com.account.servlet.account;


import com.account.entity.AccountsEx;
import com.account.entity.UsersEx;
import com.account.service.AccountService;
import com.account.service.impl.AccountServiceImpl;
import com.account.util.DateJudgmentUtil;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * 每个月账目记录servlet，用于可视化数据显示
 *
 * @author Neal
 */
public class MonthAccountServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        AccountService accountService = new AccountServiceImpl();
        // 获取session的用户，取得id
        UsersEx usersEx = (UsersEx) request.getSession().getAttribute("user");
        try {
            // 调用查询方法，查询这个时间段的数据
            List<AccountsEx> accountsExList =
                    accountService.listAccountsByDate(
                            usersEx.getUserId(),
                            new Date(DateJudgmentUtil.getPeriodOfWeek(DateJudgmentUtil.dayForMonth()).getTime()),
                            new Timestamp(System.currentTimeMillis()));
            Gson gson = new Gson();
            // 将数据封装成json字符串
            String monthAccountsJson = gson.toJson(accountsExList);
            // 返回数据给请求页
            response.getWriter().write(monthAccountsJson);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
