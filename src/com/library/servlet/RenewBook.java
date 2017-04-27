package com.library.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.library.bean.MyJsonObject;
import com.library.dao.BorrowDao;

@WebServlet("/RenewBook")
public class RenewBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		MyJsonObject jsonObject = new MyJsonObject();

		String borrowId = request.getParameter("borrowId");
		try {
			int days = Integer.parseInt(request.getParameter("days"));
			boolean result = new BorrowDao().renewBook(borrowId, days);
			if (result) {
				jsonObject.setStatus(1);
				jsonObject.setMessage("续借成功");
			} else {
				jsonObject.setStatus(0);
				jsonObject.setMessage("续借失败");
			}
		} catch (Exception e) {
			jsonObject.setStatus(0);
			jsonObject.setMessage("续借天数必须是>=1的整数");
		}

		writer.write(jsonObject.toString());
		writer.flush();
		writer.close();
	}
}
