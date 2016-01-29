package org.tym.bugpred.service;

import org.tym.bugpred.mybatis.bean.Commit;

/**
 * 操作Commit实体的Service接口
 * 
 * @author tangtomorrow
 *
 */
public interface ICommitService {
	/*
	 * 增加一个完整的Commit，包含FileOp列表
	 */
	public boolean addCommit(Commit commit);
	
	/*
	 * 读取一个完整的Commit，包含FileOp列表
	 */
	public Commit findCommitByRevisionNo(String revisionNo);
}
