package com.library.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.library.bean.MyJsonObject;
import com.library.dao.BookDao;
import com.library.dao.CollectionDao;

@WebServlet("/CollectBook")
public class CollectBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		MyJsonObject jsonObject = new MyJsonObject();

		String readerId = request.getParameter("readerId");
		String bookName = request.getParameter("bookName");

		boolean isBookExist = new BookDao().isBookExist(bookName);
		if (!isBookExist) {
			jsonObject.setStatus(0);
			jsonObject.setMessage("该书不存在");
		} else {
			boolean isCollected = new CollectionDao().isCollected(readerId,
					bookName);
			if (isCollected) {
				jsonObject.setStatus(0);
				jsonObject.setMessage("该书已经收藏");
			} else {
				boolean isSuccess = new CollectionDao().collectBook(readerId,
						bookName);
				if (isSuccess) {
					jsonObject.setStatus(1);
					jsonObject.setMessage("收藏成功");
				} else {
					jsonObject.setStatus(0);
					jsonObject.setMessage("收藏失败");
				}
			}
		}
		writer.write(jsonObject.toString());
		writer.flush();
		writer.close();
	}
}
