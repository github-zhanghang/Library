package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.library.util.DBUtil;
import com.library.util.TableUtill;

public class BorrowDao {
	// 分页时，每页的数量
	private static final int PAGE_NUM = 15;

	private Connection mConnection;
	private PreparedStatement mStatement;
	private ResultSet mResultSet;

	/**
	 * 借书
	 * 
	 * @param readerId
	 *            读者id
	 * @param bookId
	 *            书籍id
	 * @param days
	 *            借书天数
	 * @return
	 */
	public boolean borrowBook(String readerId, String bookId, int days) {
		mConnection = DBUtil.getConnection();
		try {
			mConnection.setAutoCommit(false);

			// 修改书籍状态
			String bookSql = "update "
					+ TableUtill.TABLE_BOOK
					+ " set IsBorrowed=true,BorrowedTimes=BorrowedTimes+1 where BookId=? and IsBorrowed=false";
			mStatement = mConnection.prepareStatement(bookSql);
			mStatement.setString(1, bookId);
			int line1 = mStatement.executeUpdate();
			if (line1 < 1) {
				mConnection.rollback();
				return false;
			}

			// 添加借阅记录
			String borrowSql = "insert into " + TableUtill.TABLE_BORROW
					+ "(ReaderId,BookId,BorrowTime,BorrowDays) values(?,?,?,?)";
			mStatement = mConnection.prepareStatement(borrowSql);
			mStatement.setString(1, readerId);
			mStatement.setString(2, bookId);
			mStatement.setString(3, "" + System.currentTimeMillis());
			mStatement.setInt(4, days);
			int line2 = mStatement.executeUpdate();
			if (line2 < 1) {
				mConnection.rollback();
				return false;
			}

			mConnection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			if (mConnection != null) {
				try {
					mConnection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			return false;
		} finally {
			DBUtil.close(mStatement, mConnection, mResultSet);
		}
		return true;
	}

	/**
	 * 还书
	 * 
	 * @param readerId
	 *            读者id
	 * @param bookId
	 *            书籍id
	 * @return
	 */
	public boolean returnBook(String readerId, String bookId) {
		mConnection = DBUtil.getConnection();
		try {
			mConnection.setAutoCommit(false);

			// 修改借阅记录
			String borrowSql = "update "
					+ TableUtill.TABLE_BORROW
					+ " set IsReturned=true,ReturnTime=? where BookId=? and ReaderId=? and IsReturned=false";
			mStatement = mConnection.prepareStatement(borrowSql);
			mStatement.setString(1, "" + System.currentTimeMillis());
			mStatement.setString(2, bookId);
			mStatement.setString(3, readerId);
			int line1 = mStatement.executeUpdate();
			if (line1 < 1) {
				mConnection.rollback();
				return false;
			}

			// 修改书籍状态
			String bookSql = "update " + TableUtill.TABLE_BOOK
					+ " set IsBorrowed=false where BookId=?";
			mStatement = mConnection.prepareStatement(bookSql);
			mStatement.setString(1, bookId);
			int line2 = mStatement.executeUpdate();
			if (line2 < 1) {
				mConnection.rollback();
				return false;
			}

			mConnection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			if (mConnection != null) {
				try {
					mConnection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			return false;
		} finally {
			DBUtil.close(mStatement, mConnection, mResultSet);
		}
		return true;
	}

	/**
	 * 续借
	 * 
	 * @param borrowId
	 *            借阅id
	 * @param days
	 *            续借天数
	 * @return
	 */
	public boolean renewBook(String borrowId, int days) {
		mConnection = DBUtil.getConnection();
		String borrowSql = "update "
				+ TableUtill.TABLE_BORROW
				+ " set BorrowDays=BorrowDays+? where BorrowId=? and IsReturned=false";
		try {
			mStatement = mConnection.prepareStatement(borrowSql);
			mStatement.setInt(1, days);
			mStatement.setString(2, borrowId);
			int lines = mStatement.executeUpdate();
			if (lines == 1) {
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
	 * 查询读者的借阅记录（分页）
	 * 
	 * @param readerId
	 *            读者id
	 * @param page
	 *            页数
	 * @return
	 */
	public Map<String, Object> getBorrowsById(String readerId, int page) {
		Map<String, Object> map = new HashMap<String, Object>();
		mConnection = DBUtil.getConnection();
		try {
			mConnection.setAutoCommit(false);// 取消自动提交

			// 查询借阅信息
			String borrowSql = "select B.BorrowTime,B.ReturnTime,B.BorrowDays,B.IsReturned,R.ReaderAccount,K.BookName from "
					+ TableUtill.TABLE_BORROW
					+ " as B join "
					+ TableUtill.TABLE_READER
					+ " as R on R.ReaderId=B.ReaderId join "
					+ TableUtill.TABLE_BOOK
					+ " as K on K.BookId=B.BookId where R.ReaderId=? limit ?,?";
			mStatement = mConnection.prepareStatement(borrowSql);
			mStatement.setString(1, readerId);
			mStatement.setInt(2, PAGE_NUM * (page - 1));
			mStatement.setInt(3, PAGE_NUM * page);
			mResultSet = mStatement.executeQuery();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			while (mResultSet.next()) {
				Map<String, Object> datas = new HashMap<String, Object>();
				datas.put("borrowTime", mResultSet.getString("BorrowTime"));
				datas.put("returnTime", mResultSet.getString("ReturnTime"));
				datas.put("borrowDays", mResultSet.getInt("BorrowDays"));
				datas.put("isReturned", mResultSet.getBoolean("IsReturned"));
				datas.put("readerAccount",
						mResultSet.getString("ReaderAccount"));
				datas.put("bookName", mResultSet.getString("BookName"));
				list.add(datas);
			}
			map.put("borrowInfo", list);

			// 查询总页数
			String pageSql = "select count(*) from " + TableUtill.TABLE_BORROW
					+ " where ReaderId=?";
			mStatement = mConnection.prepareStatement(pageSql);
			mStatement.setString(1, readerId);
			mResultSet = mStatement.executeQuery();
			int totalPage = 0;
			while (mResultSet.next()) {
				int count = mResultSet.getInt(1);
				if (count % PAGE_NUM == 0) {
					totalPage = count / PAGE_NUM;
				} else {
					totalPage = count / PAGE_NUM + 1;
				}
			}
			map.put("totalPage", totalPage);

			mConnection.commit();// 提交事物
		} catch (SQLException e) {
			e.printStackTrace();
			if (mConnection != null) {
				try {
					mConnection.rollback();// 有一条执行失败，回滚
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			return null;
		} finally {
			DBUtil.close(mStatement, mConnection, mResultSet);
		}
		return map;
	}
}
