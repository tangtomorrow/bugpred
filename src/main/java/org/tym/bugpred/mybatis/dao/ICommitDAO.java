package org.tym.bugpred.mybatis.dao;

import org.tym.bugpred.mybatis.bean.Commit;

public interface ICommitDAO {
	public void insertCommit(Commit commit);
	public Commit findCommitByRevisionNo(String revisionNo);
}
