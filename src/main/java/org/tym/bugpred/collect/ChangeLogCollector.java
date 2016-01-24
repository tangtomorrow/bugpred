package org.tym.bugpred.collect;

/**
 * 将版本库导出的logs整理成relink需要的格式
 * 
 * @author tangtomorrow
 *
 */
public abstract class ChangeLogCollector {
	protected String commitLogPath; // inputFile
	protected String changeLogPath; // outputFile
	
	public ChangeLogCollector(String commitLogPath, String changeLogPath) {
		setCommitLogPath(commitLogPath);
		setChangeLogPath(changeLogPath);
	}
	
	public abstract void collect();
	
	public String getCommitLogPath() {
		return commitLogPath;
	}

	public void setCommitLogPath(String commitLogPath) {
		this.commitLogPath = commitLogPath;
	}

	public String getChangeLogPath() {
		return changeLogPath;
	}

	public void setChangeLogPath(String changeLogPath) {
		this.changeLogPath = changeLogPath;
	}
}
