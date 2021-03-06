package com.account.servlet.account;

import com.account.entity.UsersEx;
import com.account.util.DateJudgmentUtil;
import com.account.util.ExportExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * 将本月账目导出Excel
 *
 * @author Neal
 */
public class ExportAccountByMonthServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        // 获取session中的user对象
        UsersEx usersEx = (UsersEx) request.getSession().getAttribute("user");
        try {
            // 调用Excel工具类导出方法
            HSSFWorkbook wb = ExportExcelUtil.export(usersEx.getUserId(),
                    new Date(DateJudgmentUtil.getPeriodOfWeek(DateJudgmentUtil.dayForMonth()).getTime()),
                    new Timestamp(System.currentTimeMillis()), "本月账本");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=myAccountOfMonth.xls");
            OutputStream ouputStream = response.getOutputStream();
            wb.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
