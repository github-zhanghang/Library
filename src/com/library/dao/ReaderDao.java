package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.library.bean.ReaderBean;
import com.library.util.DBUtil;
import com.library.util.TableUtill;

public class ReaderDao {
	// 分页时，每页的数量
	private static final int PAGE_NUM = 15;

	private Connection mConnection;
	private PreparedStatement mStatement;
	private ResultSet mResultSet;

	/**
	 * 根据id获取读者信息
	 * 
	 * @param readerId
	 * @return
	 */
	public ReaderBean getReaderById(String readerId) {
		mConnection = DBUtil.getConnection();
		String sql = "select * from " + TableUtill.TABLE_READER
				+ " where ReaderId =?";
		try {
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, readerId);
			mResultSet = mStatement.executeQuery();
			while (mResultSet.next()) {
				String readerAccount = mResultSet.getString("ReaderAccount");
				String ReaderPassword = mResultSet.getString("ReaderPassword");
				String readerName = mResultSet.getString("ReaderName");
				String readerPhone = mResultSet.getString("ReaderPhone");
				boolean isPermitted = mResultSet.getBoolean("IsPermitted");
				boolean isEnable = mResultSet.getBoolean("IsEnable");
				String registerTime = mResultSet.getString("RegisterTime");
				return new ReaderBean(readerId, readerAccount, ReaderPassword,
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
	 * 根据账号获取读者信息
	 * 
	 * @param readerAccount
	 * @return
	 */
	public ReaderBean getReaderByAccount(String readerAccount) {
		mConnection = DBUtil.getConnection();
		String sql = "select * from " + TableUtill.TABLE_READER
				+ " where ReaderAccount =?";
		try {
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, readerAccount);
			mResultSet = mStatement.executeQuery();
			while (mResultSet.next()) {
				String readerId = mResultSet.getString("ReaderId");
				String readerPassword = mResultSet.getString("ReaderPassword");
				String readerName = mResultSet.getString("ReaderName");
				String readerPhone = mResultSet.getString("ReaderPhone");
				boolean isPermitted = mResultSet.getBoolean("IsPermitted");
				boolean isEnable = mResultSet.getBoolean("IsEnable");
				String registerTime = mResultSet.getString("RegisterTime");
				return new ReaderBean(readerId, readerAccount, readerPassword,
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
	 * 注册
	 * 
	 * @param account
	 * @param password
	 * @param phone
	 * @return
	 */
	public boolean register(String account, String password, String phone) {
		mConnection = DBUtil.getConnection();
		String sql = "insert into "
				+ TableUtill.TABLE_READER
				+ "(ReaderAccount,ReaderPassword,ReaderPhone,RegisterTime) values(?,?,?,?)";
		try {
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, account);
			mStatement.setString(2, password);
			mStatement.setString(3, phone);
			mStatement.setString(4, "" + System.currentTimeMillis());
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
	 * 获取所有读者(分页)
	 * 
	 * @param page
	 *            页数
	 * @return
	 */
	public Map<String, Object> getAllReaders(int page) {
		Map<String, Object> map = new HashMap<String, Object>();
		mConnection = DBUtil.getConnection();
		try {
			mConnection.setAutoCommit(false);// 取消自动提交

			// 查询读者信息
			String readerSql = "select * from " + TableUtill.TABLE_READER
					+ " limit ?,?";
			mStatement = mConnection.prepareStatement(readerSql);
			mStatement.setInt(1, PAGE_NUM * (page - 1));
			mStatement.setInt(2, PAGE_NUM * page);
			mResultSet = mStatement.executeQuery();
			List<ReaderBean> readerBeans = new ArrayList<ReaderBean>();
			while (mResultSet.next()) {
				String readerId = mResultSet.getString("ReaderId");
				String readerAccount = mResultSet.getString("ReaderAccount");
				String readerPassword = mResultSet.getString("ReaderPassword");
				String readerName = mResultSet.getString("ReaderName");
				String readerPhone = mResultSet.getString("ReaderPhone");
				boolean isPermitted = mResultSet.getBoolean("IsPermitted");
				boolean isEnable = mResultSet.getBoolean("IsEnable");
				String registerTime = mResultSet.getString("RegisterTime");
				readerBeans.add(new ReaderBean(readerId, readerAccount,
						readerPassword, readerPhone, readerName, registerTime,
						isPermitted, isEnable));
			}
			map.put("readers", readerBeans);

			// 查询总页数
			String pageSql = "select count(*) from " + TableUtill.TABLE_READER;
			mStatement = mConnection.prepareStatement(pageSql);
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

	/**
	 * 修改读者信息
	 * 
	 * @param account
	 *            账号
	 * @param newName
	 *            新的昵称
	 * @param newPhone
	 *            新的联系方式
	 * @return
	 */
	public boolean modifyReader(String account, String newName, String newPhone) {
		mConnection = DBUtil.getConnection();
		String sql = "update " + TableUtill.TABLE_READER
				+ " set ReaderName=?,ReaderPhone=? where ReaderAccount =?";
		try {
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, newName);
			mStatement.setString(2, newPhone);
			mStatement.setString(3, account);
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
	 * 删除读者
	 * 
	 * @param account
	 * @return
	 */
	public boolean deleteReader(String account) {
		mConnection = DBUtil.getConnection();
		String sql = "delete from " + TableUtill.TABLE_READER
				+ " where ReaderAccount =?";
		try {
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, account);
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
	 * 读者登录
	 * 
	 * @param account
	 * @param password
	 * @return
	 */
	public boolean login(String account, String password) {
		mConnection = DBUtil.getConnection();
		String sql = "select * from " + TableUtill.TABLE_READER
				+ " where ReaderAccount =? and ReaderPassword=?";
		try {
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, account);
			mStatement.setString(2, password);
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

	/**
	 * 是否已注册
	 * 
	 * @param account
	 * @return
	 */
	public boolean isRegistered(String account) {
		mConnection = DBUtil.getConnection();
		String sql = "select * from " + TableUtill.TABLE_READER
				+ " where ReaderAccount =?";
		try {
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, account);
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

	/**
	 * 修改读者密码
	 * 
	 * @param account
	 *            账号
	 * @param newPassword
	 *            新的密码
	 * @return
	 */
	public boolean changePassword(String account, String newPassword) {
		mConnection = DBUtil.getConnection();
		String sql = "update " + TableUtill.TABLE_READER
				+ " set ReaderPassword=? where ReaderAccount =?";
		try {
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, newPassword);
			mStatement.setString(2, account);
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
}
