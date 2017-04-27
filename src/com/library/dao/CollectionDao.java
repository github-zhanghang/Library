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

public class CollectionDao {
	// 分页时，每页的数量
	private static final int PAGE_NUM = 15;

	private Connection mConnection;
	private PreparedStatement mStatement;
	private ResultSet mResultSet;

	/**
	 * 收藏书籍
	 * 
	 * @param readerId
	 *            读者id
	 * @param bookName
	 *            书籍名称
	 * @return
	 */
	public boolean collectBook(String readerId, String bookName) {
		mConnection = DBUtil.getConnection();
		String sql = "insert into " + TableUtill.TABLE_COLLECTION
				+ "(ReaderId,BookName,CreateTime) values(?,?,?)";
		try {
			mConnection = DBUtil.getConnection();
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, readerId);
			mStatement.setString(2, bookName);
			mStatement.setString(3, "" + System.currentTimeMillis());
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
	 * 判断是否已收藏
	 * 
	 * @param readerId
	 *            读者id
	 * @param bookName
	 *            书籍名称
	 * @return
	 */
	public boolean isCollected(String readerId, String bookName) {
		mConnection = DBUtil.getConnection();
		String sql = "select 1 from " + TableUtill.TABLE_COLLECTION
				+ " where ReaderId=? and BookName=? limit 1";
		try {
			mConnection = DBUtil.getConnection();
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, readerId);
			mStatement.setString(2, bookName);
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
	 * 取消收藏
	 * 
	 * @param collectionId
	 * @return
	 */
	public boolean cancelCollection(String collectionId) {
		mConnection = DBUtil.getConnection();
		String sql = "delete from " + TableUtill.TABLE_COLLECTION
				+ " where CollectionId=?";
		try {
			mConnection = DBUtil.getConnection();
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, collectionId);
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
	 * 获取收藏记录（分页）
	 * 
	 * @param readerId
	 * @return
	 */
	public Map<String, Object> getCollections(String readerId, int page) {
		Map<String, Object> map = new HashMap<String, Object>();

		mConnection = DBUtil.getConnection();
		try {
			mConnection = DBUtil.getConnection();
			mConnection.setAutoCommit(false);

			// 查询收藏记录
			String collectionSql = "select * from "
					+ TableUtill.TABLE_COLLECTION
					+ " where ReaderId=? limit ?,?";
			mStatement = mConnection.prepareStatement(collectionSql);
			mStatement.setString(1, readerId);
			mStatement.setInt(2, PAGE_NUM * (page - 1));
			mStatement.setInt(3, PAGE_NUM * page);
			mResultSet = mStatement.executeQuery();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			while (mResultSet.next()) {
				Map<String, Object> datas = new HashMap<String, Object>();
				datas.put("collectionId", mResultSet.getString("CollectionId"));
				datas.put("bookName", mResultSet.getString("BookName"));
				datas.put("createTime", mResultSet.getString("CreateTime"));
				list.add(datas);
			}
			map.put("collectionInfo", list);

			// 查询总页数
			String pageSql = "select count(*) from "
					+ TableUtill.TABLE_COLLECTION + " where ReaderId=?";
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
			return null;
		} finally {
			DBUtil.close(mStatement, mConnection, mResultSet);
		}
		return map;
	}
}
