package com.library.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.library.bean.MyJsonObject;
import com.library.dao.ShelfDao;

@WebServlet("/DeleteShelf")
public class DeleteShelf extends HttpServlet {
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
		boolean isSuccess = new ShelfDao().deleteShelf(shelfName);
		if (!isSuccess) {
			jsonObject.setStatus(0);
			jsonObject.setMessage("删除失败");
		} else {
			jsonObject.setStatus(1);
			jsonObject.setMessage("删除成功");
		}
		writer.write(jsonObject.toString());
		writer.flush();
		writer.close();
	}
}
