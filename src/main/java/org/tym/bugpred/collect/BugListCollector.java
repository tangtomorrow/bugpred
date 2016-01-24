package org.tym.bugpred.collect;

import java.util.ArrayList;
import java.util.List;

import org.tym.bugpred.collect.entity.Bug;

/**
 * 收集某个系统对应的bug
 * 
 * @author tangtomorrow
 *
 */
public abstract class BugListCollector {
	protected String projectName;
	protected boolean logEnable;
	protected List<Bug> bugList;

	public abstract void collect();

	public BugListCollector(String projectName, boolean logEnable) {
		this.projectName = projectName;
		this.logEnable = logEnable;
		this.bugList = new ArrayList<Bug>();
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<Bug> getBugList() {
		return bugList;
	}

	public void setBugList(List<Bug> bugList) {
		this.bugList = bugList;
	}
}
