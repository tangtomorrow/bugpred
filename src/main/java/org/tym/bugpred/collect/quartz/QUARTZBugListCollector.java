package org.tym.bugpred.collect.quartz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.tym.bugpred.collect.BugListCollector;
import org.tym.bugpred.collect.entity.Bug;
import org.tym.bugpred.collect.entity.BugComment;
import org.tym.bugpred.util.DateUtils;
import org.tym.bugpred.util.NetUtils;

public class QUARTZBugListCollector extends BugListCollector {
	private String bugIds;
	private String urlDetailPre;

	public QUARTZBugListCollector(String projectName, boolean logEnable, String bugIds, String urlDetailPre) {
		super(projectName, logEnable);
		this.bugIds = bugIds;
		this.urlDetailPre = urlDetailPre;
	}

	@Override
	public void collect() {
		String[] keys = bugIds.split(",");
		int count = 0;
		for (String key : keys) {
			Document doc = null;
			try {
				doc = Jsoup.connect(urlDetailPre + key).timeout(5000).userAgent(NetUtils.randomUserAgent()).get();
				if (logEnable) {
					count++;
					System.out.println(count + "\t" + urlDetailPre + key);
					System.out.println("********\n" + doc.title().length() + "\n");
				}
				this.bugList.add(parseQUARTZBug(doc));
				int time = NetUtils.randomSleepTime(3000, 8000);
				// System.out.println("time:\t" + time);
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据单个bug详情页的地址，解析bug内容
	 * 
	 * @param url
	 * @return
	 */
	private Bug parseQUARTZBug(Document doc) {
		Bug bug = new Bug();
		bug.setBugId(doc.select("#key-val").text());
		bug.setStatus(doc.select("#status-val > span").text());
		bug.setOwner(doc.select("#assignee-val > span").text());
		bug.setReporter(doc.select("#reporter-val > span").text());
		bug.setReportDate(DateUtils.formatQUARTZDateToTimestamp(doc.select("#create-date > time").attr("datetime")));
		bug.setModifiedDate(
				DateUtils.formatQUARTZDateToTimestamp(doc.select("#resolved-date > time").attr("datetime")));
		bug.setLastDate(DateUtils.formatQUARTZDateToTimestamp(doc.select("#updated-date > time").attr("datetime")));
		bug.setSummary(doc.select("#summary-val").text());
		bug.setDescription(doc.select("#description-val > .user-content-block").text());
		bug.setComments(parseQUARTZBugComments(doc));

		// System.out.println(bug.getBugId() + "\t" + bug.getComments().size());

		return bug;
	}

	/**
	 * 根据单个bug详情页的地址，解析bug comments
	 * 
	 * @param url
	 * @return
	 */
	private List<BugComment> parseQUARTZBugComments(Document doc) {
		List<BugComment> list = new ArrayList<BugComment>();

		Elements elements = doc.select(
				"#issue_actions_container > .issue-data-block.activity-comment.twixi-block.expanded > div.twixi-wrap.verbose.actionContainer");
		for (Element element : elements) {
			BugComment bugComment = new BugComment();
			bugComment.setDeveloper(element.select("div.action-head > div.action-details > a").text());
			bugComment.setDate(DateUtils
					.formatQUARTZDateToTimestamp(doc.select("div > div > span > span > time").attr("datetime")));
			bugComment.setComment(element.select("div.action-body.flooded").text());
			list.add(bugComment);
		}

		return list;
	}

	public String getUrlDetailPre() {
		return urlDetailPre;
	}

	public void setUrlDetailPre(String urlDetailPre) {
		this.urlDetailPre = urlDetailPre;
	}
}
