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

@WebServlet("/ModifyManager")
public class ModifyManager extends HttpServlet {
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
		String newName = request.getParameter("newName");
		String newPhone = request.getParameter("newPhone");
		String newDuty = request.getParameter("newDuty");
		boolean isSuccess = new ManagerDao().modifyManager(account, newName,
				newPhone, newDuty);
		if (!isSuccess) {
			jsonObject.setStatus(0);
			jsonObject.setMessage("修改失败");
		} else {
			jsonObject.setStatus(1);
			jsonObject.setMessage("修改成功");
		}
		writer.write(jsonObject.toString());
		writer.flush();
		writer.close();
	}
}
