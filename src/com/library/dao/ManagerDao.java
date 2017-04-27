package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.library.bean.ManagerBean;
import com.library.util.DBUtil;
import com.library.util.TableUtill;

public class ManagerDao {
	private Connection mConnection;
	private PreparedStatement mStatement;
	private ResultSet mResultSet;

	/**
	 * 根据id获取管理员信息
	 * 
	 * @param managerId
	 * @return
	 */
	public ManagerBean getManagerById(String managerId) {
		mConnection = DBUtil.getConnection();
		String sql = "select * from " + TableUtill.TABLE_MANAGER
				+ " where ManagerId =?";
		try {
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, managerId);
			mResultSet = mStatement.executeQuery();
			while (mResultSet.next()) {
				String account = mResultSet.getString("ManagerAccount");
				String password = mResultSet.getString("ManagerPassword");
				String name = mResultSet.getString("ManagerName");
				String phone = mResultSet.getString("ManagerPhone");
				String duty = mResultSet.getString("ManagerDuty");
				String registerTime = mResultSet.getString("RegisterTime");
				return new ManagerBean(managerId, account, password, name,
						phone, duty, registerTime);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(mStatement, mConnection, mResultSet);
		}
		return null;
	}

	/**
	 * 根据账号获取管理员信息
	 * 
	 * @param managerAccount
	 * @return
	 */
	public ManagerBean getManagerByAccount(String managerAccount) {
		mConnection = DBUtil.getConnection();
		String sql = "select * from " + TableUtill.TABLE_MANAGER
				+ " where ManagerAccount =?";
		try {
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, managerAccount);
			mResultSet = mStatement.executeQuery();
			while (mResultSet.next()) {
				String managerId = mResultSet.getString("ManagerId");
				String password = mResultSet.getString("ManagerPassword");
				String name = mResultSet.getString("ManagerName");
				String phone = mResultSet.getString("ManagerPhone");
				String duty = mResultSet.getString("ManagerDuty");
				String registerTime = mResultSet.getString("RegisterTime");
				return new ManagerBean(managerId, managerAccount, password,
						name, phone, duty, registerTime);
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
	 * @param name
	 * @param phone
	 * @param duty
	 * @return
	 */
	public boolean register(String account, String password, String name,
			String phone, String duty) {
		mConnection = DBUtil.getConnection();
		String sql = "insert into "
				+ TableUtill.TABLE_MANAGER
				+ "(ManagerAccount,ManagerPassword,ManagerName,ManagerPhone,ManagerDuty,RegisterTime) values(?,?,?,?,?,?)";
		try {
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, account);
			mStatement.setString(2, password);
			mStatement.setString(3, name);
			mStatement.setString(4, phone);
			mStatement.setString(5, duty);
			mStatement.setString(6, "" + System.currentTimeMillis());
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
	 * 获取所有管理员
	 * 
	 * @param page
	 *            页数
	 * @return
	 */
	public List<ManagerBean> getAllManagers() {
		List<ManagerBean> managerBeans = new ArrayList<ManagerBean>();
		mConnection = DBUtil.getConnection();
		try {
			// 查询读者信息
			String readerSql = "select * from " + TableUtill.TABLE_MANAGER;
			mStatement = mConnection.prepareStatement(readerSql);
			mResultSet = mStatement.executeQuery();
			while (mResultSet.next()) {
				String managerId = mResultSet.getString("ManagerId");
				String account = mResultSet.getString("ManagerAccount");
				String password = mResultSet.getString("ManagerPassword");
				String name = mResultSet.getString("ManagerName");
				String phone = mResultSet.getString("ManagerPhone");
				String duty = mResultSet.getString("ManagerDuty");
				String registerTime = mResultSet.getString("RegisterTime");
				managerBeans.add(new ManagerBean(managerId, account, password,
						name, phone, duty, registerTime));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(mStatement, mConnection, mResultSet);
		}
		return managerBeans;
	}

	/**
	 * 修改管理员信息
	 * 
	 * @param account
	 * @param newName
	 * @param newPhone
	 * @param newDuty
	 * @return
	 */
	public boolean modifyManager(String account, String newName,
			String newPhone, String newDuty) {
		mConnection = DBUtil.getConnection();
		String sql = "update "
				+ TableUtill.TABLE_MANAGER
				+ " set ManagerName=?,ManagerPhone=? ,ManagerDuty=? where ManagerAccount =?";
		try {
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, newName);
			mStatement.setString(2, newPhone);
			mStatement.setString(3, newDuty);
			mStatement.setString(4, account);
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
	 * 删除管理员
	 * 
	 * @param account
	 * @return
	 */
	public boolean deleteManager(String account) {
		mConnection = DBUtil.getConnection();
		String sql = "delete from " + TableUtill.TABLE_MANAGER
				+ " where ManagerAccount =?";
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
	 * 管理员登录
	 * 
	 * @param account
	 * @param password
	 * @return
	 */
	public boolean login(String account, String password) {
		mConnection = DBUtil.getConnection();
		String sql = "select * from " + TableUtill.TABLE_MANAGER
				+ " where ManagerAccount =? and ManagerPassword=?";
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
		String sql = "select * from " + TableUtill.TABLE_MANAGER
				+ " where ManagerAccount =?";
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
	 * 修改管理员密码
	 * 
	 * @param account
	 *            账号
	 * @param newPassword
	 *            新的密码
	 * @return
	 */
	public boolean changePassword(String account, String newPassword) {
		mConnection = DBUtil.getConnection();
		String sql = "update " + TableUtill.TABLE_MANAGER
				+ " set ManagerPassword=? where ManagerAccount =?";
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
