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

@WebServlet("/IsReaderPermitted")
public class IsReaderPermitted extends HttpServlet {
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
		boolean isPermitted = new ReaderDao().isPermitted(readerId);
		jsonObject.setStatus(1);
		jsonObject.setMessage("");
		jsonObject.setData(isPermitted);

		writer.write(jsonObject.toString());
		writer.flush();
		writer.close();
	}
}
