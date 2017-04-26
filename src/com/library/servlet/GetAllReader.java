package com.library.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.library.bean.MyJsonObject;
import com.library.dao.ReaderDao;

@WebServlet("/GetAllReader")
public class GetAllReader extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		MyJsonObject jsonObject = new MyJsonObject();

		try {
			int page = Integer.parseInt(request.getParameter("page"));
			Map<String, Object> map = new ReaderDao().getAllReaders(page);
			if (map == null) {
				jsonObject.setStatus(0);
				jsonObject.setMessage("查询失败");
			} else {
				jsonObject.setStatus(1);
				jsonObject.setMessage("查询成功");
				jsonObject.setData(map);// 返回读者信息
			}
		} catch (Exception e) {
			jsonObject.setStatus(0);
			jsonObject.setMessage("查询失败，页数必须是>=1的数字");
		}
		writer.write(jsonObject.toString());
		writer.flush();
		writer.close();
	}
}
