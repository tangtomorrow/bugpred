package org.tym.bugpred.mybatis.bean;

import java.util.List;

public class Commit {
	private int id;
	private String revisionNo;
	private String author;
	private String timeStamp;
	private String message;
	private List<FileOp> files;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRevisionNo() {
		return revisionNo;
	}

	public void setRevisionNo(String revisionNo) {
		this.revisionNo = revisionNo;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<FileOp> getFiles() {
		return files;
	}

	public void setFiles(List<FileOp> files) {
		this.files = files;
	}
}
