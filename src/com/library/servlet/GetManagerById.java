package com.library.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.library.bean.ManagerBean;
import com.library.bean.MyJsonObject;
import com.library.bean.ReaderBean;
import com.library.dao.ManagerDao;

@WebServlet("/GetManagerById")
public class GetManagerById extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		MyJsonObject jsonObject = new MyJsonObject();

		String managerId = request.getParameter("managerId");
		ManagerBean managerBean = new ManagerDao().getManagerById(managerId);
		if (managerBean == null) {
			jsonObject.setStatus(0);
			jsonObject.setMessage("该用户不存在");
		} else {
			jsonObject.setStatus(1);
			jsonObject.setMessage("查询成功");
			jsonObject.setData(managerBean);// 返回用户信息
		}
		writer.write(jsonObject.toString());
		writer.flush();
		writer.close();
	}
}
