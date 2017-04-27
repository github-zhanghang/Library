package com.library.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.library.bean.MyJsonObject;
import com.library.dao.BookDao;

@WebServlet("/AddBook")
public class AddBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		MyJsonObject jsonObject = new MyJsonObject();

		String name = null, author = null, type = null, address = null, image = null, description = null, press = null, count = null;

		// 获得磁盘文件条目工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 获取文件需要上传到的路径
		String path = request.getSession().getServletContext()
				.getRealPath("/images");
		// 如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
		// 设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
		/**
		 * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上， 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem
		 * 格式的 然后再将其真正写到 对应目录的硬盘上
		 */
		factory.setRepository(new File(path));
		// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
		factory.setSizeThreshold(1024 * 1024);
		// 高水平的API文件上传处理
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			// 可以上传多个文件
			List<FileItem> list = (List<FileItem>) upload.parseRequest(request);
			for (FileItem item : list) {
				// 获取表单的属性名字
				String filedName = item.getFieldName();
				// 如果获取的 表单信息是普通的 文本 信息
				if (item.isFormField()) {
					// 获取用户具体输入的字符串
					String value = item.getString("utf-8");
					if (filedName.equals("name")) {
						name = value;
					} else if (filedName.equals("author")) {
						author = value;
					} else if (filedName.equals("type")) {
						type = value;
					} else if (filedName.equals("address")) {
						address = value;
					} else if (filedName.equals("count")) {
						count = value;
					} else if (filedName.equals("press")) {
						press = value;
					}
				} else {
					// 获取路径名
					String value = item.getName();
					// 索引到最后一个反斜杠
					int start = value.lastIndexOf("\\");
					// 截取 上传文件的 字符串名字，加1是 去掉反斜杠，
					String filename = value.substring(start + 1);
					if (filename.endsWith(".jpg") || filename.endsWith(".png")) {
						// 真正写到磁盘上
						item.write(new File(path, filename));
						image = "http://localhost:8080"
								+ request.getContextPath() + "/images/"
								+ filename;
						boolean result = new BookDao().addBook(name, author,
								type, image, address, description, press,
								Integer.parseInt(count));
						if (result) {
							jsonObject.setStatus(1);
							jsonObject.setMessage("添加图书成功");
						} else {
							jsonObject.setStatus(0);
							jsonObject.setMessage("添加图书失败");
						}
					} else {
						// 上传的图片不合法
						jsonObject.setStatus(0);
						jsonObject.setMessage("上传的图片不合法");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.setStatus(0);
			jsonObject.setMessage("添加图书失败");
		}

		writer.write(jsonObject.toString());
		writer.flush();
		writer.close();
	}
}
