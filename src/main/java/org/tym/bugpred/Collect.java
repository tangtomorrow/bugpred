package org.tym.bugpred;

import java.io.IOException;
import java.util.List;

import org.tym.bugpred.collect.BugListCollector;
import org.tym.bugpred.collect.BugListWriter;
import org.tym.bugpred.collect.ChangeLogCollector;
import org.tym.bugpred.collect.entity.Bug;
import org.tym.bugpred.collect.igstk.IGSTKBugListCollector;
import org.tym.bugpred.collect.igstk.IGSTKChangeLogCollector;
import org.tym.bugpred.collect.quartz.QUARTZBugListCollector;
import org.tym.bugpred.collect.quartz.QUARTZChangeLogCollector;

/**
 * 从网站和git/svn库导出的日志里获取数据
 * 
 * @author tangtomorrow
 *
 */
public class Collect {
	/*
	 * IGSTK
	 */
	private final static String IGSTK = "IGSTK";
	private final static String IGSTKBugDetailUrl = "http://public.kitware.com/Bug/view.php?id="; // bug详情页前缀
	private final static String IGSTKBugCsvPath = "D:/Graduate/IGSTK/2016.01.15/BugDataCSV.csv"; // 网站导出的全部bug简单信息，用来提供生成网址的bugId
	private final static String IGSTKBugDataPath = "D:/Graduate/IGSTK/2016.01.15/bugData.txt";
	private final static String IGSTKBugCommentDataPath = "D:/Graduate/IGSTK/2016.01.15/bugCommentData.txt";
	private final static String IGSTKGitCommitsPath = "D:/Graduate/IGSTK/gitCommits.log";
	private final static String IGSTKChangeLogPath = "D:/Graduate/IGSTK/changeLogs.txt";

	/*
	 * QUARTZ
	 */
	private final static String QUARTZ = "QUARTZ";
	private final static String QUARTZBugTotalIDs = "QTZ-403,QTZ-401,QTZ-395,QTZ-389,QTZ-387,QTZ-386,QTZ-385,QTZ-382,QTZ-378,QTZ-372,QTZ-350,QTZ-348,QTZ-346,QTZ-342,QTZ-337,QTZ-336,QTZ-333,QTZ-331,QTZ-330,QTZ-329,QTZ-327,QTZ-325,QTZ-323,QTZ-321,QTZ-317,QTZ-316,QTZ-314,QTZ-313,QTZ-310,QTZ-305,QTZ-300,QTZ-296,QTZ-292,QTZ-288,QTZ-283,QTZ-278,QTZ-276,QTZ-273,QTZ-271,QTZ-269,QTZ-263,QTZ-262,QTZ-261,QTZ-259,QTZ-241,QTZ-240,QTZ-239,QTZ-238,QTZ-234,QTZ-233,QTZ-231,QTZ-230,QTZ-229,QTZ-228,QTZ-226,QTZ-219,QTZ-218,QTZ-217,QTZ-216,QTZ-215,QTZ-214,QTZ-213,QTZ-211,QTZ-209,QTZ-205,QTZ-204,QTZ-202,QTZ-201,QTZ-200,QTZ-199,QTZ-195,QTZ-194,QTZ-192,QTZ-191,QTZ-187,QTZ-185,QTZ-184,QTZ-182,QTZ-181,QTZ-180,QTZ-179,QTZ-176,QTZ-174,QTZ-173,QTZ-172,QTZ-167,QTZ-165,QTZ-164,QTZ-163,QTZ-162,QTZ-161,QTZ-160,QTZ-157,QTZ-155,QTZ-153,QTZ-152,QTZ-148,QTZ-146,QTZ-145,QTZ-142,QTZ-141,QTZ-140,QTZ-139,QTZ-138,QTZ-137,QTZ-135,QTZ-134,QTZ-133,QTZ-132,QTZ-131,QTZ-130,QTZ-129,QTZ-128,QTZ-127,QTZ-126,QTZ-125,QTZ-124,QTZ-118,QTZ-116,QTZ-115,QTZ-113,QTZ-111,QTZ-110,QTZ-109,QTZ-106,QTZ-100,QTZ-99,QTZ-96,QTZ-87,QTZ-86,QTZ-85,QTZ-81,QTZ-64,QTZ-61,QTZ-59,QTZ-57,QTZ-54,QTZ-53,QTZ-52,QTZ-51,QTZ-50,QTZ-48,QTZ-45,QTZ-42,QTZ-40,QTZ-39,QTZ-38,QTZ-36,QTZ-34,QTZ-33,QTZ-32,QTZ-31,QTZ-28,QTZ-27,QTZ-24,QTZ-23,QTZ-15,QTZ-12,QTZ-7,QTZ-6,QTZ-5";
	private final static String QUARTZBugDetailUrl = "https://jira.terracotta.org/jira/browse/";
	private final static String QUARTZBugDataPath = "D:/Graduate/QUARTZ/2016.01.14/bugData.txt";
	private final static String QUARTZBugCommentDataPath = "D:/Graduate/QUARTZ/2016.01.14/bugCommentData.txt";
	private final static String QUARTZSVNCommitsPath = "D:/Graduate/QUARTZ/originData/svnCommits.txt";
	private final static String QUARTZChangeLogPath = "D:/Graduate/QUARTZ/changeLogs.txt";

	public static void main(String[] args) throws IOException, InterruptedException {
		/*
		 * IGSTK 从bug网站上挖掘bug信息，存入bugData.txt以及bugCommentData.txt
		 */
		BugListCollector igstkCollector = new IGSTKBugListCollector(IGSTK, true, IGSTKBugCsvPath, IGSTKBugDetailUrl);
		igstkCollector.collect();
		List<Bug> igstkBugList = igstkCollector.getBugList();
		BugListWriter igstkBugListWriter = new BugListWriter(IGSTKBugDataPath, IGSTKBugCommentDataPath, igstkBugList);
		igstkBugListWriter.write();
		// 整理gitCommits
		ChangeLogCollector igstkClc = new IGSTKChangeLogCollector(IGSTKGitCommitsPath, IGSTKChangeLogPath);
		igstkClc.collect();
		
		/*
		 * QUARTZ 从bug网站上挖掘bug信息，存入bugData.txt以及bugCommentData.txt
		 */
		BugListCollector quartzCollector = new QUARTZBugListCollector(QUARTZ, true, QUARTZBugTotalIDs,
				QUARTZBugDetailUrl);
		quartzCollector.collect();
		List<Bug> quartzBugList = quartzCollector.getBugList();
		BugListWriter quartzBugListWriter = new BugListWriter(QUARTZBugDataPath, QUARTZBugCommentDataPath,
				quartzBugList);
		quartzBugListWriter.write();
		// 整理svnCommits
		ChangeLogCollector quartzClc = new QUARTZChangeLogCollector(QUARTZSVNCommitsPath, QUARTZChangeLogPath);
		quartzClc.collect();
	}
}
