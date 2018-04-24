package com.fengtao.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class CheckAccount extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req,resp);
    }
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        AccountBean account = new AccountBean();
        String username = req.getParameter("username");
        String pwd = req.getParameter("pwd");
        account.setPassword(pwd);
        account.setUsername(username);

        ServletContext app01 = this.getServletContext();
        app01.setAttribute("name", "ServletContext's value");
        System.out.println(app01.getAttribute("name"));

        if((username != null)&&(username.trim().equals("123"))) {
            if((pwd != null)&&(pwd.trim().equals("123"))) {
                System.out.println("success");
                session.setAttribute("account", account);
                String login_suc = "success.jsp";
                resp.sendRedirect(login_suc);
                return;
            }
        }
        String login_fail = "fail.jsp";
//        resp.sendRedirect(login_fail);
        RequestDispatcher rd=req.getRequestDispatcher(login_fail);
        rd.include(req, resp);
        return;
    }

}

//总结：
//
//        1.作用域： request session application
//        *    使用作用域传递数据和存储数据
//        *    使用作用域传递数据时，必须掌握作用域对应的生命周期和作用范围
//        *
//        2. * 生命周期
//        * (1)  request:只限于一次请求
//        * (2)  session：一次会话（多次请求）
//        *    开始
//        *      用户向服务器发送请求的时候
//        *      结束
//        *        客户端
//        *         丢失JsessionId值的时候（关闭浏览器）
//        *        服务器端
//        *            关闭服务器
//        *            超过会话的不活动周期时间
//        *  (3) application:项目的加载到卸载
//        *3.作用范围
//        * (1) requset：所有被请求转发的Servlet
//        * (2) session:所有的Servlet
//        * (3) application：所有的Servlet(换一个浏览器演示，跟session作用域区分)
//        *
//        4.*如何正确的选择作用域
//        *(1)  request：跟当前操作功能相关
//        * (2) session: 跟用户信息相关
//        * (3) application：跟项目全局信息相关----》京东配送地址
//        *
//        * 5.如何正确的选择作用域不正确，会出现什么情况
//        *   内存浪费