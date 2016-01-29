package org.tym.bugpred.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.tym.bugpred.mybatis.bean.Commit;
import org.tym.bugpred.mybatis.bean.FileOp;
import org.tym.bugpred.mybatis.dao.ICommitDAO;
import org.tym.bugpred.mybatis.dao.IFileOpDAO;
import org.tym.bugpred.service.ICommitService;
import org.tym.bugpred.util.DBUtils;

public class CommitServiceImpl implements ICommitService {

	public boolean addCommit(Commit commit) {
		boolean isSuccess = true;

		SqlSession session = DBUtils.getSession();
		try {
			ICommitDAO commitOpDAO = session.getMapper(ICommitDAO.class);
			commitOpDAO.insertCommit(commit);

			IFileOpDAO fileOpDAO = session.getMapper(IFileOpDAO.class);
			for (FileOp fp : commit.getFiles()) {
				// 为每个FileOp设置所属Commit的id，来自自增的主键
				fp.setCommitId(commit.getId());

				fileOpDAO.insertFileOp(fp);
			}
			session.commit();

		} catch (Exception e) {
			isSuccess = false;
			session.rollback();
		} finally {
			session.close();
		}

		return isSuccess;
	}

	public Commit findCommitByRevisionNo(String revisionNo) {
		Commit commit = null;
		SqlSession session = DBUtils.getSession();
		try {
			ICommitDAO commitOpDAO = session.getMapper(ICommitDAO.class);
			commit = commitOpDAO.findCommitByRevisionNo(revisionNo);
		} finally {
			session.close();
		}
		return commit;
	}

}
