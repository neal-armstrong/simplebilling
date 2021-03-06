package com.account.dao.impl;

import com.account.dao.AccountDao;
import com.account.entity.AccountsEx;
import com.account.util.DbPoolUtil;

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
            conn = DbPoolUtil.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<AccountsEx> listAccounts(int id) throws SQLException {
        List<AccountsEx> accountsExList = new ArrayList<>();
        String sql = "SELECT * from accounts WHERE User_id=?;";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                AccountsEx accountsEx = new AccountsEx();
                accountsEx.setRecordId(resultSet.getInt(index++));
                accountsEx.setUserId(resultSet.getInt(index++));
                accountsEx.setRecordName(resultSet.getString(index++));
                accountsEx.setRecordType(resultSet.getString(index++));
                accountsEx.setRecordMode(resultSet.getString(index++));
                accountsEx.setMoney(resultSet.getBigDecimal(index++));
                accountsEx.setRecordDate(resultSet.getTimestamp(index++));
                accountsEx.setRecordRemark(resultSet.getString(index));
                accountsExList.add(accountsEx);
            }
            resultSet.close();
        }
        return accountsExList;
    }

    @Override
    public List<AccountsEx> listAccountsBySearchDate(int id, Date date) throws SQLException {
        String sql = "SELECT * from accounts WHERE User_id=? AND DATE(Record_date)=?;";
        List<AccountsEx> accountsExList = new ArrayList<>();
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setDate(2, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                AccountsEx accountsEx = new AccountsEx();
                accountsEx.setRecordId(resultSet.getInt(index++));
                accountsEx.setUserId(resultSet.getInt(index++));
                accountsEx.setRecordName(resultSet.getString(index++));
                accountsEx.setRecordType(resultSet.getString(index++));
                accountsEx.setRecordMode(resultSet.getString(index++));
                accountsEx.setMoney(resultSet.getBigDecimal(index++));
                accountsEx.setRecordDate(resultSet.getTimestamp(index++));
                accountsEx.setRecordRemark(resultSet.getString(index));
                accountsExList.add(accountsEx);
            }
            resultSet.close();
        }
        return accountsExList;
    }

    @Override
    public List<AccountsEx> listAccountsByDate(int id, Date date, Timestamp date2) throws SQLException {
        String sql = "SELECT * from accounts WHERE User_id=? AND Record_date >= DATE_FORMAT(?,'%Y-%m-%d ') AND Record_date <= DATE_FORMAT(?,'%Y-%m-%d %H:%i:%S') ORDER BY Record_date DESC;";
        List<AccountsEx> accountsExList = new ArrayList<>();
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            int index2 = 1;
            preparedStatement.setInt(index2++, id);
            preparedStatement.setDate(index2++, date);
            preparedStatement.setTimestamp(index2, date2);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                AccountsEx accountsEx = new AccountsEx();
                accountsEx.setRecordId(resultSet.getInt(index++));
                accountsEx.setUserId(resultSet.getInt(index++));
                accountsEx.setRecordName(resultSet.getString(index++));
                accountsEx.setRecordType(resultSet.getString(index++));
                accountsEx.setRecordMode(resultSet.getString(index++));
                accountsEx.setMoney(resultSet.getBigDecimal(index++));
                accountsEx.setRecordDate(resultSet.getTimestamp(index++));
                accountsEx.setRecordRemark(resultSet.getString(index));
                accountsExList.add(accountsEx);
            }
            resultSet.close();
        }
        return accountsExList;
    }

    @Override
    public void saveAccounts(AccountsEx accountsEx) throws SQLException {
        String sql = "insert into accounts(User_id,Record_name,Record_type,Record_mode,money,Record_date,Record_remark) VALUES (?,?,?,?,?,?,?);";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            int index = 1;
            preparedStatement.setInt(index++, accountsEx.getUserId());
            preparedStatement.setString(index++, accountsEx.getRecordName());
            preparedStatement.setString(index++, accountsEx.getRecordType());
            preparedStatement.setString(index++, accountsEx.getRecordMode());
            preparedStatement.setBigDecimal(index++, accountsEx.getMoney());
            preparedStatement.setTimestamp(index++, accountsEx.getRecordDate());
            preparedStatement.setString(index, accountsEx.getRecordRemark());
            preparedStatement.execute();
        }
    }

    @Override
    public void updateAccounts(AccountsEx accountsEx) throws SQLException {
        String sql = "update accounts SET User_id=?,Record_name=?,Record_type=?,Record_mode=?,money=?,Record_date=?,Record_remark=? WHERE Record_id=?;";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            int index = 1;
            preparedStatement.setInt(index++, accountsEx.getUserId());
            preparedStatement.setString(index++, accountsEx.getRecordName());
            preparedStatement.setString(index++, accountsEx.getRecordType());
            preparedStatement.setString(index++, accountsEx.getRecordMode());
            preparedStatement.setBigDecimal(index++, accountsEx.getMoney());
            preparedStatement.setTimestamp(index++, accountsEx.getRecordDate());
            preparedStatement.setString(index++, accountsEx.getRecordRemark());
            preparedStatement.setInt(index, accountsEx.getRecordId());
            preparedStatement.execute();
        }
    }

    @Override
    public void removeAccounts(int id) throws SQLException {
        String sql = "DELETE FROM accounts WHERE Record_id=?;";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        }
    }


    @Override
    public int counts(int id) throws SQLException {
        String sql = "SELECT COUNT(Record_id) FROM accounts WHERE User_id =?;";
        int count;
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);
            resultSet.close();
        }
        return count;
    }

    @Override
    public List<AccountsEx> listLimitAccounts(int id, int startPage, int size) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE User_id=? ORDER BY Record_date DESC LIMIT ?,?;";
        List<AccountsEx> list = new ArrayList<>();
        try (PreparedStatement parameterMetaData = conn.prepareStatement(sql)) {
            int index2 = 1;
            parameterMetaData.setInt(index2++, id);
            parameterMetaData.setInt(index2++, startPage);
            parameterMetaData.setInt(index2, size);
            ResultSet resultSet = parameterMetaData.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                AccountsEx accountsEx = new AccountsEx();
                accountsEx.setRecordId(resultSet.getInt(index++));
                accountsEx.setUserId(resultSet.getInt(index++));
                accountsEx.setRecordName(resultSet.getString(index++));
                accountsEx.setRecordType(resultSet.getString(index++));
                accountsEx.setRecordMode(resultSet.getString(index++));
                accountsEx.setMoney(resultSet.getBigDecimal(index++));
                accountsEx.setRecordDate(resultSet.getTimestamp(index++));
                accountsEx.setRecordRemark(resultSet.getString(index));
                list.add(accountsEx);
            }
            resultSet.close();
        }
        return list;
    }

    @Override
    public AccountsEx getAccounts(int id) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE Record_id=?";
        AccountsEx accountsEx = new AccountsEx();
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int index = 1;
                accountsEx.setRecordId(resultSet.getInt(index++));
                accountsEx.setUserId(resultSet.getInt(index++));
                accountsEx.setRecordName(resultSet.getString(index++));
                accountsEx.setRecordType(resultSet.getString(index++));
                accountsEx.setRecordMode(resultSet.getString(index++));
                accountsEx.setMoney(resultSet.getBigDecimal(index++));
                accountsEx.setRecordDate(resultSet.getTimestamp(index++));
                accountsEx.setRecordRemark(resultSet.getString(index));
            }
            resultSet.close();
        }
        if (accountsEx.getRecordId() != null) {
            return accountsEx;
        }
        return null;
    }
}
