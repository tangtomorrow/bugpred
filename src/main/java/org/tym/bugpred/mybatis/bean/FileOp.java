package org.tym.bugpred.mybatis.bean;

public class FileOp {
	private int id;
	private String type;
	private String path;
	private int commitId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getCommitId() {
		return commitId;
	}

	public void setCommitId(int commitId) {
		this.commitId = commitId;
	}
}
