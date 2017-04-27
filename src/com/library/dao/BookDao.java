package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.library.bean.BookBean;
import com.library.bean.ReaderBean;
import com.library.util.DBUtil;
import com.library.util.TableUtill;

public class BookDao {
	// 分页时，每页的数量
	private static final int PAGE_NUM = 15;

	private Connection mConnection;
	private PreparedStatement mStatement;
	private ResultSet mResultSet;

	/**
	 * 根据id获取书籍信息
	 * 
	 * @param bookId
	 * @return
	 */
	public BookBean getBookById(String bookId) {
		mConnection = DBUtil.getConnection();
		String sql = "select * from " + TableUtill.TABLE_BOOK
				+ " where BookId =?";
		try {
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, bookId);
			mResultSet = mStatement.executeQuery();
			while (mResultSet.next()) {
				String name = mResultSet.getString("BookName");
				String author = mResultSet.getString("BookAuthor");
				String type = mResultSet.getString("BookType");
				String image = mResultSet.getString("BookImage");
				String address = mResultSet.getString("BookAddress");
				String description = mResultSet.getString("BookDescription");
				String press = mResultSet.getString("BookPress");
				boolean isBorrowed = mResultSet.getBoolean("IsBorrowed");
				int borrowedTimes = mResultSet.getInt("BorrowedTimes");
				boolean isEnable = mResultSet.getBoolean("IsEnable");
				String createTime = mResultSet.getString("CreateTime");
				return new BookBean(bookId, name, author, type, image, address,
						description, press, isBorrowed, borrowedTimes,
						isEnable, createTime);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(mStatement, mConnection, mResultSet);
		}
		return null;
	}

	/**
	 * 根据书名获取图书信息
	 * 
	 * @param bookName
	 * @return
	 */
	public ReaderBean getReaderByAccount(String bookName) {
		mConnection = DBUtil.getConnection();
		String sql = "select * from " + TableUtill.TABLE_BOOK
				+ " where ReaderAccount =?";
		try {
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, bookName);
			mResultSet = mStatement.executeQuery();
			while (mResultSet.next()) {
				String readerId = mResultSet.getString("ReaderId");
				String readerPassword = mResultSet.getString("ReaderPassword");
				String readerName = mResultSet.getString("ReaderName");
				String readerPhone = mResultSet.getString("ReaderPhone");
				boolean isPermitted = mResultSet.getBoolean("IsPermitted");
				boolean isEnable = mResultSet.getBoolean("IsEnable");
				String registerTime = mResultSet.getString("RegisterTime");
				return new ReaderBean(readerId, bookName, readerPassword,
						readerPhone, readerName, registerTime, isPermitted,
						isEnable);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(mStatement, mConnection, mResultSet);
		}
		return null;
	}

	/**
	 * 添加书籍
	 * 
	 * @param bookName
	 * @param bookAuthor
	 * @param bookType
	 * @param bookImage
	 * @param bookAddress
	 * @param description
	 * @param bookPress
	 * @param count
	 * @return
	 */
	public boolean addBook(String bookName, String bookAuthor, String bookType,
			String bookImage, String bookAddress, String description,
			String bookPress, int count) {
		mConnection = DBUtil.getConnection();
		String sql = "insert into "
				+ TableUtill.TABLE_BOOK
				+ "(BookName,BookAuthor,BookType,BookImage,BookAddress,BookDescription,BookPress,CreateTime) values";
		for (int i = 0; i < count; i++) {
			sql += "(?,?,?,?,?,?,?,?)";
			if (i != count - 1) {
				sql += ",";
			}
		}
		try {
			mStatement = mConnection.prepareStatement(sql);
			for (int i = 0; i < count; i++) {
				for (int j = 1; j <= 8; j++) {
					mStatement.setString(i * 8 + 1, bookName);
					mStatement.setString(i * 8 + 2, bookAuthor);
					mStatement.setString(i * 8 + 3, bookType);
					mStatement.setString(i * 8 + 4, bookImage);
					mStatement.setString(i * 8 + 5, bookAddress);
					mStatement.setString(i * 8 + 6, description);
					mStatement.setString(i * 8 + 7, bookPress);
					mStatement.setString(i * 8 + 8,
							"" + System.currentTimeMillis());
				}
			}
			int lines = mStatement.executeUpdate();
			if (lines == count) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(mStatement, mConnection, mResultSet);
		}
		return false;
	}

	/**
	 * 修改书籍信息
	 * 
	 * @return
	 */
	public boolean modifyBook(String name, String newName, String newAuthor,
			String newType, String newImage, String newAddress,
			String newDescription, String newPress) {
		mConnection = DBUtil.getConnection();
		String sql = "update " + TableUtill.TABLE_BOOK;
		if (newImage != null) {
			sql += " set BookName=?,BookAuthor=?,BookType=?,BookImage=?,BookAddress=?,BookDescription=?,BookPress=? where BookName=?";
		} else {
			sql += " set BookName=?,BookAuthor=?,BookType=?,BookAddress=?,BookDescription=?,BookPress=? where BookName=?";
		}
		try {
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, newName);
			mStatement.setString(2, newAuthor);
			mStatement.setString(3, newType);
			if (newImage != null) {
				mStatement.setString(4, newImage);
				mStatement.setString(5, newAddress);
				mStatement.setString(6, newDescription);
				mStatement.setString(7, newPress);
				mStatement.setString(8, name);
			} else {
				mStatement.setString(4, newAddress);
				mStatement.setString(5, newDescription);
				mStatement.setString(6, newPress);
				mStatement.setString(7, name);
			}
			int lines = mStatement.executeUpdate();
			if (lines >= 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(mStatement, mConnection, mResultSet);
		}
		return false;
	}

	/**
	 * 删除书籍
	 * 
	 * @param bookName
	 * @return
	 */
	public boolean deleteBook(String bookName) {
		mConnection = DBUtil.getConnection();
		String sql = "delete from " + TableUtill.TABLE_BOOK
				+ " where BookName =?";
		try {
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, bookName);
			int lines = mStatement.executeUpdate();
			if (lines >= 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(mStatement, mConnection, mResultSet);
		}
		return false;
	}

	/**
	 * 判断是否存在此书籍
	 * 
	 * @param bookName
	 * @return
	 */
	public boolean isBookExist(String bookName) {
		mConnection = DBUtil.getConnection();
		String sql = "select 1 from " + TableUtill.TABLE_BOOK
				+ " where BookName =? limit 1";
		try {
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, bookName);
			mResultSet = mStatement.executeQuery();
			if (mResultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(mStatement, mConnection, mResultSet);
		}
		return false;
	}
}
