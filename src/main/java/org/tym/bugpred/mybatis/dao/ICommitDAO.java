package org.tym.bugpred.mybatis.dao;

import org.tym.bugpred.mybatis.bean.Commit;

public interface ICommitDAO {
	public Commit findCommitByRevisionNo(String revisionNo);
	public void insertCommit(Commit commit);
}
