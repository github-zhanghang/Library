package com.library.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.library.bean.MyJsonObject;
import com.library.dao.ManagerDao;

@WebServlet("/ManagerLogin")
public class ManagerLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		MyJsonObject jsonObject = new MyJsonObject();

		String account = request.getParameter("account");
		String password = request.getParameter("password");

		boolean isSuccess = new ManagerDao().login(account, password);
		if (isSuccess) {
			jsonObject.setStatus(1);
			jsonObject.setMessage("登录成功");
		} else {
			jsonObject.setStatus(0);
			jsonObject.setMessage("用户名或密码错误");
		}
		writer.write(jsonObject.toString());
		writer.flush();
		writer.close();
	}
}
