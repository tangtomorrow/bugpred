package org.tym.bugpred.mybatis.dao;

import java.util.List;

import org.tym.bugpred.mybatis.bean.FileOp;

public interface IFileOpDAO {
	public void insertFileOp(FileOp fileOp);
	public List<FileOp> getFileOpsByCommitId(int commitId);
}
