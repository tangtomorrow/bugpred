package org.tym.bugpred.collect.igstk;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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
import org.tym.bugpred.util.StringUtils;

public class IGSTKBugListCollector extends BugListCollector {

	private String filePath;
	private String urlPre;

	public IGSTKBugListCollector(String projectName, boolean logEnable, String filePath, String urlPre) {
		super(projectName, logEnable);
		this.filePath = filePath;
		this.urlPre = urlPre;
	}

	@Override
	public void collect() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
			String str = br.readLine();
			int count = 0;
			while (str != null) {
				String bugId = str.split(",")[0];

				if (logEnable) {
					count++;
					System.out.println(count + "\t" + urlPre + StringUtils.shortBugId(bugId));
				}
				int time = NetUtils.randomSleepTime(3000, 5000);
				// System.out.println("time:\t" + time);
				Thread.sleep(time);
				Document doc = Jsoup.connect(urlPre + StringUtils.shortBugId(bugId)).timeout(5000)
						.userAgent(NetUtils.randomUserAgent()).get();
				this.bugList.add(parseIGSTKBug(doc));

				str = br.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException!");
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 根据单个bug详情页的地址，解析bug内容
	 * 
	 * @param url
	 * @return
	 */
	private Bug parseIGSTKBug(Document doc) {
		Bug bug = new Bug();
		bug.setBugId(doc.select("body > table:nth-child(5) > tbody > tr:nth-child(3) > td:nth-child(1)").text());
		bug.setStatus(doc.select("body > table:nth-child(5) > tbody > tr:nth-child(8) > td:nth-child(4)").text());
		bug.setOwner(doc.select("body > table:nth-child(5) > tbody > tr:nth-child(6) > td:nth-child(2)").text());
		bug.setReporter(doc.select("body > table:nth-child(5) > tbody > tr:nth-child(5) > td:nth-child(2)").text());
		bug.setReportDate(DateUtils.formatIGSTKDateToTimestamp(
				doc.select("body > table:nth-child(5) > tbody > tr:nth-child(3) > td:nth-child(5)").text()));
		bug.setLastDate(DateUtils.formatIGSTKDateToTimestamp(
				doc.select("body > table:nth-child(5) > tbody > tr:nth-child(3) > td:nth-child(6)").text()));
		bug.setModifiedDate(bug.getLastDate());
		bug.setSummary(doc.select("body > table:nth-child(5) > tbody > tr:nth-child(11) > td:nth-child(2)").text());
		bug.setDescription(doc.select("body > table:nth-child(5) > tbody > tr:nth-child(12) > td:nth-child(2)").text());
		bug.setComments(parseIGSTKBugComments(doc));

		return bug;
	}

	/**
	 * 根据单个bug详情页的地址，解析bug comments
	 * 
	 * @param url
	 * @return
	 */
	private List<BugComment> parseIGSTKBugComments(Document doc) {
		List<BugComment> list = new ArrayList<BugComment>();

		Elements elements = doc.select(".bugnote");
		for (Element element : elements) {
			BugComment bugComment = new BugComment();
			bugComment.setDeveloper(element.select("td.bugnote-public > a").text());
			bugComment.setDate(DateUtils
					.formatIGSTKDateToTimestamp(element.select("td.bugnote-public > span:nth-child(6)").text()));
			bugComment.setComment(element.select("td.bugnote-note-public").text());
			list.add(bugComment);
		}

		return list;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getUrlPre() {
		return urlPre;
	}

	public void setUrlPre(String urlPre) {
		this.urlPre = urlPre;
	}
}
