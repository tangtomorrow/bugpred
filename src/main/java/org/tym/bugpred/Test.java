package org.tym.bugpred;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.tym.bugpred.mybatis.bean.Commit;
import org.tym.bugpred.mybatis.bean.FileOp;
import org.tym.bugpred.mybatis.dao.ICommitDAO;
import org.tym.bugpred.mybatis.dao.IFileOpDAO;
import org.tym.bugpred.util.DBUtils;

public class Test {

	private final static String name = "igstk";
	private final static String path = "D:/Graduate/Test/data/" + name + "/bugData.txt";

	public static void main(String[] args) throws Exception {
		// System.out.println("c2d8c4632e726b8e48ca23dbc2a0f17bf7f50a99".length());
		/*
		 * BufferedReader br = null; br = new BufferedReader(new
		 * FileReader(path)); String str = br.readLine();
		 * 
		 * while (str != null) { String[] strs = str.split("\t");
		 * System.out.println("********************");
		 * System.out.println(strs.length);
		 * 
		 * for (String s : strs) { System.out.println(s); } str = br.readLine();
		 * }
		 */

		//testInsertCommit();
		testFindCommitByRevisionNo();
		
		//testInsertFileOp();
		//testGetFileOpsByCommitId();
	}

	public static void testFindCommitByRevisionNo() {
		SqlSession session = DBUtils.getSession();
		try {
			ICommitDAO commitOp = session.getMapper(ICommitDAO.class);
			
			Commit commit = commitOp.findCommitByRevisionNo("4f8d75eeb7cad6684b5cda7b800d37fcd93a418f");
			if (commit != null) {
				System.out.println(commit.getId() + "\t" + commit.getAuthor() + "\t" + commit.getTimeStamp() + "\t"
						+ commit.getMessage());
				if (commit.getFiles() != null) {
					System.out.println("the size of list:\t" + commit.getFiles().size());
					for (FileOp fp : commit.getFiles()) {
						System.out.println(fp.getId() + "\t" + fp.getType() + "\t" + fp.getPath());
					}
				}
			}
			
			/*
			List<Commit> commits = commitOp.findCommitByRevisionNo("4f8d75eeb7cad6684b5cda7b800d37fcd93a418f");
			if (commits != null) {
				System.out.println(commits.size());
			}
			*/
		} finally {
			session.close();
		}
	}

	public static void testInsertCommit() {
		Commit commit = new Commit();
		commit.setAuthor("Andinet Enquobahrie");
		commit.setRevisionNo("4f8d75eeb7cad6684b5cda7b800d37fcd93a418f");
		commit.setTimeStamp("1362506487");
		commit.setMessage("Merge topic 'UpdateVersionNumber'");

		SqlSession session = DBUtils.getSession();
		try {
			ICommitDAO commitOp = session.getMapper(ICommitDAO.class);
			commitOp.insertCommit(commit);
			session.commit();
			System.out.println("新增用户ID：" + commit.getId());
		} finally {
			session.close();
		}
	}
	
	public static void testInsertFileOp() {
		FileOp fileOp = new FileOp();
		fileOp.setPath("Source/igstkImageSpatialObject.txx");
		fileOp.setType("Modified");
		fileOp.setCommitId(123);
		
		SqlSession session = DBUtils.getSession();
		try {
			IFileOpDAO fileOpDAO = session.getMapper(IFileOpDAO.class);
			fileOpDAO.insertFileOp(fileOp);
			session.commit();
			System.out.println("新增fileOp的ID：" + fileOp.getId());
		} finally {
			session.close();
		}
	}
	
	public static void testGetFileOpsByCommitId() {
		int commitId = 123;
		List<FileOp> list = null;
		
		SqlSession session = DBUtils.getSession();
		try {
			IFileOpDAO fileOpDAO = session.getMapper(IFileOpDAO.class);
			list = fileOpDAO.getFileOpsByCommitId(commitId);
		} finally {
			session.close();
		}
		
		if (list != null) {
			//查询返回结果为空时，是一个size为0的list，而不是null
			System.out.println("the size of list:\t" + list.size());
			for (FileOp fp : list) {
				System.out.println(fp.getId() + "\t" + fp.getType() + "\t" + fp.getPath() + "\t" + fp.getCommitId());
			}
		} else {
			System.out.println("list is null");
		}
	}
}
