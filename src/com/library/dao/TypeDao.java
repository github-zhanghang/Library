package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.library.bean.TypeBean;
import com.library.util.DBUtil;
import com.library.util.TableUtill;

public class TypeDao {
	private Connection mConnection;
	private PreparedStatement mStatement;
	private ResultSet mResultSet;

	/**
	 * 根据类别id获取分类信息
	 * 
	 * @param typeId
	 * @return
	 */
	public TypeBean getTypeById(String typeId) {
		mConnection = DBUtil.getConnection();
		String sql = "select * from " + TableUtill.TABLE_TYPE
				+ " where TypeId =?";
		try {
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, typeId);
			mResultSet = mStatement.executeQuery();
			while (mResultSet.next()) {
				String typeName = mResultSet.getString("TypeName");
				String createTime = mResultSet.getString("CreateTime");
				return new TypeBean(typeId, typeName, createTime);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(mStatement, mConnection, mResultSet);
		}
		return null;
	}

	/**
	 * 根据类别名称获取分类信息
	 * 
	 * @param typeName
	 * @return
	 */
	public TypeBean getTypeByName(String typeName) {
		mConnection = DBUtil.getConnection();
		String sql = "select * from " + TableUtill.TABLE_TYPE
				+ " where TypeName =?";
		try {
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, typeName);
			mResultSet = mStatement.executeQuery();
			while (mResultSet.next()) {
				String typeId = mResultSet.getString("typeId");
				String createTime = mResultSet.getString("CreateTime");
				return new TypeBean(typeId, typeName, createTime);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(mStatement, mConnection, mResultSet);
		}
		return null;
	}

	/**
	 * 添加分类，可同时插入多条记录，名称之间以分号隔开
	 * 
	 * @param typeName
	 */
	public boolean addType(String typeName) {
		mConnection = DBUtil.getConnection();
		String sql = "insert into " + TableUtill.TABLE_TYPE
				+ "(TypeName,CreateTime) values";
		String[] typeNames = typeName.split(";");
		for (int i = 0; i < typeNames.length; i++) {
			sql += "(?,+" + System.currentTimeMillis() + ")";
			if (i != typeNames.length - 1) {
				sql += ",";
			}
		}
		try {
			mStatement = mConnection.prepareStatement(sql);
			for (int i = 0; i < typeNames.length; i++) {
				mStatement.setString(i + 1, typeNames[i]);
			}
			int lines = mStatement.executeUpdate();
			if (lines == typeNames.length) {
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
	 * 获取所有分类
	 * 
	 * @return
	 */
	public List<TypeBean> getAllType() {
		List<TypeBean> typeBeans = new ArrayList<TypeBean>();
		mConnection = DBUtil.getConnection();
		try {
			// 查询书架信息
			String shelfSql = "select * from " + TableUtill.TABLE_TYPE;
			mStatement = mConnection.prepareStatement(shelfSql);
			mResultSet = mStatement.executeQuery();
			while (mResultSet.next()) {
				String typeId = mResultSet.getString("TypeId");
				String typeName = mResultSet.getString("TypeName");
				String createTime = mResultSet.getString("CreateTime");
				typeBeans.add(new TypeBean(typeId, typeName, createTime));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DBUtil.close(mStatement, mConnection, mResultSet);
		}
		return typeBeans;
	}

	/**
	 * 修改分类
	 * 
	 * @param oldName旧名称
	 * @param newName新名称
	 * @return
	 */
	public boolean modifyType(String oldName, String newName) {
		mConnection = DBUtil.getConnection();
		String sql = "update " + TableUtill.TABLE_TYPE
				+ " set TypeName=? where TypeName =?";
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
	 * 删除分类
	 * 
	 * @param typeName
	 * @return
	 */
	public boolean deleteType(String typeName) {
		mConnection = DBUtil.getConnection();
		String sql = "delete from " + TableUtill.TABLE_TYPE
				+ " where TypeName =?";
		try {
			mStatement = mConnection.prepareStatement(sql);
			mStatement.setString(1, typeName);
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
