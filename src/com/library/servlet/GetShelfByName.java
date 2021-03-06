package com.library.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.library.bean.MyJsonObject;
import com.library.bean.ShelfBean;
import com.library.dao.ShelfDao;

@WebServlet("/GetShelfByName")
public class GetShelfByName extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		MyJsonObject jsonObject = new MyJsonObject();

		String shelfName = request.getParameter("shelfName");
		ShelfBean shelfBean = new ShelfDao().getShelfByName(shelfName);
		if (shelfBean == null) {
			jsonObject.setStatus(0);
			jsonObject.setMessage("查询失败，无此书架");
		} else {
			jsonObject.setStatus(1);
			jsonObject.setMessage("查询成功");
			jsonObject.setData(shelfBean);// 返回书架信息
		}
		writer.write(jsonObject.toString());
		writer.flush();
		writer.close();
	}
}
