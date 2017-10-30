package com.account.dao.impl;

import com.account.dao.AccountDao;
import com.account.entity.AccountsEx;
import com.account.util.DBPoolUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 账目DAO接口实现类
 *
 * @author Neal
 */
public class AccountDaoImpl implements AccountDao {
    private static Connection conn;

    static {
        try {
            conn = DBPoolUtil.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<AccountsEx> listAccounts(int id) throws SQLException {
        List<AccountsEx> accountsExList = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE Book_id IN (SELECT Book_id FROM rs_users_books WHERE User_id IN (SELECT User_id FROM users WHERE User_id= ?));";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                AccountsEx accountsEx = new AccountsEx();
                accountsEx.setRecordId(resultSet.getInt(index++));
                accountsEx.setBookId(resultSet.getInt(index++));
                accountsEx.setRecordName(resultSet.getInt(index++));
                accountsEx.setRecordType(resultSet.getInt(index++));
                accountsEx.setRecordMode(resultSet.getInt(index++));
                accountsEx.setMoney(resultSet.getBigDecimal(index++));
                accountsEx.setRecordDate(resultSet.getTimestamp(index++));
                accountsEx.setRecordRemark(resultSet.getString(index));
                accountsExList.add(accountsEx);
            }
        }
        return accountsExList;
    }

    @Override
    public AccountsEx getAccounts(int id, Date date) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE Book_id IN (SELECT Book_id FROM rs_users_books WHERE User_id IN (SELECT User_id FROM users WHERE User_id= ?)) AND Record_date=?;";
        AccountsEx accountsEx = new AccountsEx();
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setDate(1, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                accountsEx.setRecordId(resultSet.getInt(index++));
                accountsEx.setBookId(resultSet.getInt(index++));
                accountsEx.setRecordName(resultSet.getInt(index++));
                accountsEx.setRecordType(resultSet.getInt(index++));
                accountsEx.setRecordMode(resultSet.getInt(index++));
                accountsEx.setMoney(resultSet.getBigDecimal(index++));
                accountsEx.setRecordDate(resultSet.getTimestamp(index++));
                accountsEx.setRecordRemark(resultSet.getString(index));
            }
            resultSet.close();
            preparedStatement.close();
        }
        if (accountsEx.getRecordId() != null) {
            return accountsEx;
        }
        return null;
    }

    @Override
    public List<AccountsEx> listAccountsByDate(int id, Date date, Date date2) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE Book_id IN (SELECT Book_id FROM rs_users_books WHERE User_id IN (SELECT User_id FROM users WHERE User_id=?)) AND Record_date BETWEEN (?,?);";
        List<AccountsEx> accountsExList = new ArrayList<>();
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            int index2 = 1;
            preparedStatement.setInt(index2++, id);
            preparedStatement.setDate(index2++, date);
            preparedStatement.setDate(index2, date2);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                AccountsEx accountsEx = new AccountsEx();
                accountsEx.setRecordId(resultSet.getInt(index++));
                accountsEx.setBookId(resultSet.getInt(index++));
                accountsEx.setRecordName(resultSet.getInt(index++));
                accountsEx.setRecordType(resultSet.getInt(index++));
                accountsEx.setRecordMode(resultSet.getInt(index++));
                accountsEx.setMoney(resultSet.getBigDecimal(index++));
                accountsEx.setRecordDate(resultSet.getTimestamp(index++));
                accountsEx.setRecordRemark(resultSet.getString(index));
            }
            resultSet.close();
            preparedStatement.close();
        }
        return accountsExList;
    }

    @Override
    public void saveAccounts(AccountsEx accountsEx) throws SQLException {
        String sql = "insert into accounts VALUES (?,?,?,?,?,?,?);";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            int index = 1;
            preparedStatement.setInt(index++, accountsEx.getBookId());
            preparedStatement.setInt(index++, accountsEx.getRecordName());
            preparedStatement.setInt(index++, accountsEx.getRecordType());
            preparedStatement.setInt(index++, accountsEx.getRecordMode());
            preparedStatement.setBigDecimal(index++, accountsEx.getMoney());
            preparedStatement.setTimestamp(index++, accountsEx.getRecordDate());
            preparedStatement.setString(index, accountsEx.getRecordRemark());
            preparedStatement.execute();
            preparedStatement.close();
        }
    }

    @Override
    public void updateAccounts(AccountsEx accountsEx) throws SQLException {
        String sql = "update accounts SET Book_id=?,Record_name=?,Record_type=?,Record_mode=?,money=?,Record_date=?,Record_remark=? WHERE Record_id=?;";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            int index = 1;
            preparedStatement.setInt(index++, accountsEx.getBookId());
            preparedStatement.setInt(index++, accountsEx.getRecordName());
            preparedStatement.setInt(index++, accountsEx.getRecordType());
            preparedStatement.setInt(index++, accountsEx.getRecordMode());
            preparedStatement.setBigDecimal(index++, accountsEx.getMoney());
            preparedStatement.setTimestamp(index++, accountsEx.getRecordDate());
            preparedStatement.setString(index++, accountsEx.getRecordRemark());
            preparedStatement.setInt(index, accountsEx.getRecordId());
            preparedStatement.execute();
            preparedStatement.close();
        }
    }

    @Override
    public void removeAccounts(int id) throws SQLException {
        String sql = "DELETE FROM accounts WHERE Record_id=?;";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
        }
    }
}
