package org.tym.bugpred.mybatis.dao;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.tym.bugpred.mybatis.bean.Commit;

public class ICommitDAOTest {

	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;

	public static SqlSessionFactory getSession() {
		return sqlSessionFactory;
	}

	static {
		try {
			/*
			 * test读取src/main/resources中的配置文件一直读不到，需要复制一份到test文件夹下
			 */
			reader = Resources.getResourceAsReader("Configuration.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testFindCommitByRevisionNo() {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			ICommitDAO commitOp = session.getMapper(ICommitDAO.class);
			Commit commit = commitOp.findCommitByRevisionNo("4f8d75eeb7cad6684b5cda7b800d37fcd93a418f");
			Assert.assertNotNull(commit);
			System.out.println(commit.getId() + "\t" + commit.getAuthor() + "\t" + commit.getTimeStamp() + "\t"
					+ commit.getMessage());
		} finally {
			session.close();
		}
	}

	@Test
	public void testinsertCommit() {
		Commit commit = new Commit();
		commit.setAuthor("Andinet Enquobahrie");
		commit.setRevisionNo("4f8d75eeb7cad6684b5cda7b800d37fcd93a418f");
		commit.setTimeStamp("1362506487");
		commit.setMessage("Merge topic 'UpdateVersionNumber'");

		SqlSession session = sqlSessionFactory.openSession();
		try {
			ICommitDAO commitOp = session.getMapper(ICommitDAO.class);
			commitOp.insertCommit(commit);
			session.commit();
			System.out.println("新增用户ID：" + commit.getId());
		} finally {
			session.close();
		}
	}
}
