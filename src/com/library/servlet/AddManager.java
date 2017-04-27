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

@WebServlet("/AddManager")
public class AddManager extends HttpServlet {
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
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String duty = request.getParameter("duty");

		boolean isRegistered = new ManagerDao().isRegistered(account);
		if (isRegistered) {
			jsonObject.setStatus(0);
			jsonObject.setMessage("该账号已注册");
		} else {
			boolean isSuccess = new ManagerDao().register(account, password,
					name, phone, duty);
			if (isSuccess) {
				jsonObject.setStatus(1);
				jsonObject.setMessage("添加管理员成功");
			} else {
				jsonObject.setStatus(0);
				jsonObject.setMessage("添加管理员失败");
			}
		}
		writer.write(jsonObject.toString());
		writer.flush();
		writer.close();
	}
}
