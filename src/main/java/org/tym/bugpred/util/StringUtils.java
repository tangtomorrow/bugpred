package org.tym.bugpred.util;

public class StringUtils {
	/**
	 * 将不规则的bugId统一为7位，在前面补0
	 * 
	 * @param bugId
	 *            原来的bugId
	 * @return
	 */
	public static String normalizeBugID(String shortBugId) {
		int len = shortBugId.length();
		if (len == 7) {
			return shortBugId;
		} else {
			int zeroLen = 7 - len;
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < zeroLen; i++) {
				sb.append('0');
			}
			sb.append(shortBugId);
			return sb.toString();
		}
	}

	/**
	 * 将规则的bugId前面的0去除
	 * 
	 * @param normalizeBugID
	 * @return
	 */
	public static String shortBugId(String normalizeBugID) {
		int len = normalizeBugID.length();
		int firstNoZeroIndex = 0;
		for (int i = 0; i < len; i++) {
			if ('0' != normalizeBugID.charAt(i)) {
				firstNoZeroIndex = i;
				break;
			}
		}

		return normalizeBugID.substring(firstNoZeroIndex, len);
	}
	
	
	/**
	 * 文件的操作类型信息补全
	 * @param type 操作类型缩略格式
	 * @return
	 */
	public static String switchType(String type) {
		if (type.equals("M")) {
			return "Modified";
		} else if (type.equals("A")) {
			return "Added";
		} else if (type.equals("D")) {
			return "Deleted";
		} else {
			return "";
		}
	}
}
