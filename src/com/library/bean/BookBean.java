package com.library.bean;

import java.io.Serializable;

/**
 * 书籍信息对象
 * 
 * @author 张航
 * 
 */
public class BookBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String bookId;// 图书主键ID
	private String bookName;// 图书名称
	private String bookAuthor;// 图书作者
	private String bookType;// 图书类型
	private String bookImage;// 图片链接
	private String bookAddress;// 图书所属书架位置
	private String description;// 简介
	private String bookPress;// 出版社
	private boolean isBorrowed;// 是否已借出
	private int borrowedTimes;// 借阅次数
	private boolean isEnable;// 是否可借
	private String createTime;// 图书入库时间

	public BookBean(String bookId, String bookName, String bookAuthor,
			String bookType, String bookImage, String bookAddress,
			String description, String bookPress, boolean isBorrowed,
			int borrowedTimes, boolean isEnable, String createTime) {
		super();
		this.bookId = bookId;
		this.bookName = bookName;
		this.bookAuthor = bookAuthor;
		this.bookType = bookType;
		this.bookImage = bookImage;
		this.bookAddress = bookAddress;
		this.description = description;
		this.bookPress = bookPress;
		this.isBorrowed = isBorrowed;
		this.borrowedTimes = borrowedTimes;
		this.isEnable = isEnable;
		this.createTime = createTime;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	public String getBookImage() {
		return bookImage;
	}

	public void setBookImage(String bookImage) {
		this.bookImage = bookImage;
	}

	public String getBookAddress() {
		return bookAddress;
	}

	public void setBookAddress(String bookAddress) {
		this.bookAddress = bookAddress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBookPress() {
		return bookPress;
	}

	public void setBookPress(String bookPress) {
		this.bookPress = bookPress;
	}

	public boolean isBorrowed() {
		return isBorrowed;
	}

	public void setBorrowed(boolean isBorrowed) {
		this.isBorrowed = isBorrowed;
	}

	public int getBorrowedTimes() {
		return borrowedTimes;
	}

	public void setBorrowedTimes(int borrowedTimes) {
		this.borrowedTimes = borrowedTimes;
	}


	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "BookBean [bookId=" + bookId + ", bookName=" + bookName
				+ ", bookAuthor=" + bookAuthor + ", bookType=" + bookType
				+ ", bookImage=" + bookImage + ", bookAddress=" + bookAddress
				+ ", description=" + description + ", bookPress=" + bookPress
				+ ", isBorrowed=" + isBorrowed + ", borrowedTimes="
				+ borrowedTimes + ", isEnable=" + isEnable + ", createTime="
				+ createTime + "]";
	}

}