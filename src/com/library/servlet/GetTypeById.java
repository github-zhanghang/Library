package com.library.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.library.bean.MyJsonObject;
import com.library.bean.TypeBean;
import com.library.dao.TypeDao;

@WebServlet("/GetTypeById")
public class GetTypeById extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		MyJsonObject jsonObject = new MyJsonObject();

		String typeId = request.getParameter("typeId");
		TypeBean typeBean = new TypeDao().getTypeById(typeId);
		if (typeBean == null) {
			jsonObject.setStatus(0);
			jsonObject.setMessage("查询失败，无此分类");
		} else {
			jsonObject.setStatus(1);
			jsonObject.setMessage("查询成功");
			jsonObject.setData(typeBean);// 返回分类信息
		}
		writer.write(jsonObject.toString());
		writer.flush();
		writer.close();
	}
}
