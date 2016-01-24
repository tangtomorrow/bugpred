package org.tym.bugpred.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtils {
	/**
	 * 将
	 * 2013-11-12 14:02
	 * 型的时间补齐后转换为时间戳
	 * 
	 * @param date
	 * @return
	 */
	public static String formatIGSTKDateToTimestamp(String date) {
		//System.out.println("*****\t" + date + "\t*****");
		return String.valueOf(Timestamp.valueOf(date + ":00.000").getTime());
	}
	
	/**
	 * 将
	 * 2013-09-04T05:13:36-0700
	 * 型的时间补齐后转换为时间戳
	 * 
	 * @param date
	 * @return
	 */
	public static String formatQUARTZDateToTimestamp(String date) {
		return String.valueOf(Timestamp.valueOf(date.replace("T", " ").substring(0, date.length() - 5) + ".000").getTime());
	}
	
	/**
	 * 将
	 * 2016年1月14日 23:43:19
	 * 型的时间补齐后转换为时间戳
	 * 
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static String formatQUARTZSVNDateToTimestamp(String date) {
		//System.out.println("*****\t" + date + "\t*****");
		//System.out.println(date.length() + "\t" + date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			return String.valueOf(Timestamp.valueOf(sdf2.format(sdf.parse(date)) + ".000").getTime());
		} catch (ParseException e) {			
			e.printStackTrace();
			return "[error time]";
		}
	}
}
