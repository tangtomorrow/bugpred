package org.tym.bugpred.collect.entity;

import java.util.List;

public class Bug {
	// bugID, Type Status, Owner, Reporter, ReportDate, ModifiedDate, LastDate,
	// Summary, Comments
	private String bugId;
	private String Status;
	private String owner;
	private String reporter;
	private String reportDate;
	private String modifiedDate;
	private String lastDate;
	private String summary;
	private String description;
	private List<BugComment> comments;

	public String getBugId() {
		return bugId;
	}

	public void setBugId(String bugId) {
		this.bugId = bugId;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<BugComment> getComments() {
		return comments;
	}

	public void setComments(List<BugComment> comments) {
		this.comments = comments;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}
