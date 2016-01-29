package org.tym.bugpred;

import java.util.ArrayList;
import java.util.List;

import org.tym.bugpred.mybatis.bean.Commit;
import org.tym.bugpred.mybatis.bean.FileOp;
import org.tym.bugpred.service.ICommitService;
import org.tym.bugpred.service.impl.CommitServiceImpl;

public class Test {

	public static void main(String[] args) throws Exception {

		// testCommitServiceInsertCommit();
		testCommitServiceFindCommit();

	}

	public static void testCommitServiceInsertCommit() {
		ICommitService commitService = new CommitServiceImpl();
		Commit commit = new Commit();
		commit.setAuthor("Andinet");
		commit.setRevisionNo("afdkldsfjdklafkdjasflkajwoeflakslf2412ll");
		commit.setTimeStamp("1334343566");
		commit.setMessage("Test add commit");

		FileOp fp = null;
		List<FileOp> files = new ArrayList<FileOp>();
		for (int i = 0; i < 5; i++) {
			fp = new FileOp();
			fp.setPath("Source/hello\t" + i + "\t.world");
			fp.setType("Added\t" + i);
			files.add(fp);
		}
		commit.setFiles(files);

		boolean flag = commitService.addCommit(commit);
		if (flag) {
			System.out.println("Commit ID:\t" + commit.getId());
		} else {
			System.out.println("success!");
		}
	}

	public static void testCommitServiceFindCommit() {
		ICommitService commitService = new CommitServiceImpl();
		Commit commit = commitService.findCommitByRevisionNo("afdkldsfjdklafkdjasflkajwoeflakslf2412ll");
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
	}

}
