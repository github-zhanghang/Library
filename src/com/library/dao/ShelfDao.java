package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.library.bean.ShelfBean;
import com.library.util.DBUtil;
import com.library.util.TableUtill;

public class ShelfDao {
	// 分页时，每页的数量
	private static final int PAGE_NUM = 15;

	private Connection mConnection;
	private PreparedStatement mStatement;
	private ResultSet mResultSet;

	/**
	 * 根据书架id获取书架信息
	 * 
	 * @param shelfId
	 * @return
	 */
	public ShelfBean getShelfById(String shelfId) {
		mConnection = DBUtil.getConnection();
		String sql = "select * from " + TableUtill.TABLE_SHELF
				+ " where ShelfId =?";
		try {
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, shelfId);
			mResultSet = mStatement.executeQuery();
			while (mResultSet.next()) {
				String shelfName = mResultSet.getString("ShelfName");
				String createTime = mResultSet.getString("CreateTime");
				return new ShelfBean(shelfId, shelfName, createTime);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(mStatement, mConnection, mResultSet);
		}
		return null;
	}

	/**
	 * 根据书架名称获取书架信息
	 * 
	 * @param shelfName
	 * @return
	 */
	public ShelfBean getShelfByName(String shelfName) {
		mConnection = DBUtil.getConnection();
		String sql = "select * from " + TableUtill.TABLE_SHELF
				+ " where ShelfName =?";
		try {
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, shelfName);
			mResultSet = mStatement.executeQuery();
			while (mResultSet.next()) {
				String shelfId = mResultSet.getString("ShelfId");
				String createTime = mResultSet.getString("CreateTime");
				return new ShelfBean(shelfId, shelfName, createTime);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(mStatement, mConnection, mResultSet);
		}
		return null;
	}

	/**
	 * 添加书架，可同时插入多条记录，名称之间以分号隔开
	 * 
	 * @param shelfName
	 */
	public boolean addShelf(String shelfName) {
		mConnection = DBUtil.getConnection();
		String sql = "insert into " + TableUtill.TABLE_SHELF
				+ "(ShelfName,CreateTime) values";
		String[] shelfNames = shelfName.split(";");
		for (int i = 0; i < shelfNames.length; i++) {
			sql += "(?,+" + System.currentTimeMillis() + ")";
			if (i != shelfNames.length - 1) {
				sql += ",";
			}
		}
		try {
			mStatement = mConnection.prepareStatement(sql);
			for (int i = 0; i < shelfNames.length; i++) {
				mStatement.setString(i + 1, shelfNames[i]);
			}
			int lines = mStatement.executeUpdate();
			if (lines == shelfNames.length) {
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
	 * 获取所有书架(分页)
	 * 
	 * @param page页数
	 * @return
	 */
	public Map<String, Object> getAllShelfs(int page) {
		Map<String, Object> map = new HashMap<String, Object>();
		mConnection = DBUtil.getConnection();
		try {
			mConnection.setAutoCommit(false);// 取消自动提交

			// 查询书架信息
			String shelfSql = "select * from " + TableUtill.TABLE_SHELF
					+ " limit ?,?";
			mStatement = mConnection.prepareStatement(shelfSql);
			mStatement.setInt(1, PAGE_NUM * (page - 1));
			mStatement.setInt(2, PAGE_NUM * page);
			mResultSet = mStatement.executeQuery();
			List<ShelfBean> shelfBeans = new ArrayList<ShelfBean>();
			while (mResultSet.next()) {
				String shelfId = mResultSet.getString("ShelfId");
				String shelfName = mResultSet.getString("ShelfName");
				String createTime = mResultSet.getString("CreateTime");
				shelfBeans.add(new ShelfBean(shelfId, shelfName, createTime));
			}
			map.put("shelfs", shelfBeans);

			// 查询总页数
			String pageSql = "select count(*) from " + TableUtill.TABLE_SHELF;
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
	 * 修改书架信息
	 * 
	 * @param oldName旧名称
	 * @param newName新名称
	 * @return
	 */
	public boolean modifyShelf(String oldName, String newName) {
		mConnection = DBUtil.getConnection();
		String sql = "update " + TableUtill.TABLE_SHELF
				+ " set ShelfName=? where ShelfName =?";
		try {
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, newName);
			mStatement.setString(2, oldName);
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
	 * 删除书架
	 * 
	 * @param shelfName
	 * @return
	 */
	public boolean deleteShelf(String shelfName) {
		mConnection = DBUtil.getConnection();
		String sql = "delete from " + TableUtill.TABLE_SHELF
				+ " where ShelfName =?";
		try {
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, shelfName);
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
