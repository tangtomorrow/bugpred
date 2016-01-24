package org.tym.bugpred.collect;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.tym.bugpred.collect.entity.Bug;
import org.tym.bugpred.collect.entity.BugComment;

public class BugListWriter {

	private final static String Sperator = "\t";
	private final static String Type = "Defect";

	private String bugDataPath;
	private String bugCommentDataPath;
	private List<Bug> bugList;

	public BugListWriter(String bugDataPath, String bugCommentDataPath, List<Bug> bugList) {
		this.bugDataPath = bugDataPath;
		this.bugCommentDataPath = bugCommentDataPath;
		this.bugList = bugList;
	}

	public void write() {
		FileWriter fwBugData = null;
		FileWriter fwBugCommentData = null;
		try {
			fwBugData = new FileWriter(this.bugDataPath, true);
			fwBugCommentData = new FileWriter(this.bugCommentDataPath, true);
			for (Bug bug : this.bugList) {
				if (bug.getStatus().equalsIgnoreCase("fixed") || bug.getStatus().equalsIgnoreCase("closed")
						|| bug.getStatus().equalsIgnoreCase("Resolved")) {
					/*
					 * collect bugData and write to bugDataPath file
					 */
					StringBuffer sbBugData = new StringBuffer();
					sbBugData.append(bug.getBugId()).append(Sperator).append(Type).append(Sperator)
							.append(bug.getStatus()).append(Sperator).append(bug.getOwner()).append(Sperator)
							.append(bug.getReporter()).append(Sperator).append(bug.getReportDate()).append(Sperator)
							.append(bug.getModifiedDate()).append(Sperator).append(bug.getLastDate()).append(Sperator)
							.append(bug.getSummary()).append(Sperator);

					if (bug.getComments().size() < 1) {
						sbBugData.append("[no]").append("\n");
					} else {
						StringBuffer sbTmpBugCommentDeveloper = new StringBuffer();
						for (BugComment bugComment : bug.getComments()) {
							sbBugData.append(bugComment.getComment()).append(" ");
							sbTmpBugCommentDeveloper.append(bugComment.getDeveloper()).append(Sperator);
						}
						sbBugData.append(Sperator).append(sbTmpBugCommentDeveloper).append("\n");
					}
					// write to bugDataPath
					String str = sbBugData.toString().replace("\\\n", " ");
					//fwBugData.write(sbBugData.toString());
					fwBugData.write(str);

					/*
					 * collect bugCommentData and write to bugCommentDataPath
					 * file
					 */
					StringBuffer sbBugCommentData = new StringBuffer();
					for (BugComment bugComment : bug.getComments()) {
						sbBugCommentData.append(bug.getBugId()).append(Sperator).append(bugComment.getDeveloper())
								.append(Sperator).append(bugComment.getDate()).append(Sperator)
								.append(bugComment.getComment()).append("\n");						
					}
					// write to bugCommentDataPath
					String str1 = sbBugCommentData.toString().replace("\\\n", " ");
					fwBugCommentData.write(str1);
					//fwBugCommentData.write(sbBugCommentData.toString());
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException!");
			e.printStackTrace();
		} finally {
			if (fwBugData != null) {
				try {
					fwBugData.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fwBugCommentData != null) {
				try {
					fwBugCommentData.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getBugCommentDataPath() {
		return bugCommentDataPath;
	}

	public void setBugCommentDataPath(String bugCommentDataPath) {
		this.bugCommentDataPath = bugCommentDataPath;
	}

	public String getBugDataPath() {
		return bugDataPath;
	}

	public void setBugDataPath(String bugDataPath) {
		this.bugDataPath = bugDataPath;
	}

	public List<Bug> getBugList() {
		return bugList;
	}

	public void setBugList(List<Bug> bugList) {
		this.bugList = bugList;
	}
}
