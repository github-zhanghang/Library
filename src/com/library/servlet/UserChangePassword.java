package com.library.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.library.bean.MyJsonObject;
import com.library.dao.ReaderDao;

@WebServlet("/UserChangePassword")
public class UserChangePassword extends HttpServlet {
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
		String newPassword = request.getParameter("newPassword");

		boolean isSuccess = new ReaderDao().login(account, password);
		if (isSuccess) {
			boolean result = new ReaderDao().changePassword(account,
					newPassword);
			if (result) {
				jsonObject.setStatus(1);
				jsonObject.setMessage("修改密码成功");
			} else {
				jsonObject.setStatus(0);
				jsonObject.setMessage("修改密码失败");
			}
		} else {
			jsonObject.setStatus(0);
			jsonObject.setMessage("密码错误");
		}
		writer.write(jsonObject.toString());
		writer.flush();
		writer.close();
	}
}
