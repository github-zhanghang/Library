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

@WebServlet("/SetReaderEnable")
public class SetReaderEnable extends HttpServlet {
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
		try {
			boolean isEnable = Boolean.parseBoolean(request
					.getParameter("isEnable"));
			boolean isSuccess = new ReaderDao().setEnable(account, isEnable);
			if (isSuccess) {
				jsonObject.setStatus(1);
				jsonObject.setMessage("修改成功");
			} else {
				jsonObject.setStatus(1);
				jsonObject.setMessage("修改失败");
			}
		} catch (Exception e) {
			jsonObject.setStatus(1);
			jsonObject.setMessage("修改失败，isEnable必须是true或false");
		}

		writer.write(jsonObject.toString());
		writer.flush();
		writer.close();
	}
}
