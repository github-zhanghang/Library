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

@WebServlet("/UserRegister")
public class UserRegister extends HttpServlet {
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
		String phone = request.getParameter("phone");

		boolean isRegistered = new ReaderDao().isRegistered(account);
		if (isRegistered) {
			jsonObject.setStatus(0);
			jsonObject.setMessage("该账号已注册");
		} else {
			boolean isSuccess = new ReaderDao().register(account, password,
					phone);
			if (isSuccess) {
				jsonObject.setStatus(1);
				jsonObject.setMessage("注册成功");
			} else {
				jsonObject.setStatus(0);
				jsonObject.setMessage("注册失败");
			}
		}
		writer.write(jsonObject.toString());
		writer.flush();
		writer.close();
	}
}
