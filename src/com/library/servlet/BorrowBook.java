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
import com.library.dao.ReaderDao;

@WebServlet("/BorrowBook")
public class BorrowBook extends HttpServlet {
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
		String bookId = request.getParameter("bookId");
		// 判断是否允许借书
		boolean isPermitted = new ReaderDao().isPermitted(readerId);
		if (isPermitted) {
			try {
				int days = Integer.parseInt(request.getParameter("days"));
				boolean result = new BorrowDao().borrowBook(readerId, bookId,
						days);
				if (result) {
					jsonObject.setStatus(1);
					jsonObject.setMessage("借书成功");
				} else {
					jsonObject.setStatus(0);
					jsonObject.setMessage("借阅失败");
				}
			} catch (Exception e) {
				jsonObject.setStatus(0);
				jsonObject.setMessage("借阅天数必须是>=1的整数");
			}
		} else {
			jsonObject.setStatus(0);
			jsonObject.setMessage("你可能因为某种原因被禁止借书，请联系管理员");
		}

		writer.write(jsonObject.toString());
		writer.flush();
		writer.close();
	}
}
