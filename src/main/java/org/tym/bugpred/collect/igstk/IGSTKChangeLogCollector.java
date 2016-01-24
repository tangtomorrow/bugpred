package org.tym.bugpred.collect.igstk;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.tym.bugpred.collect.ChangeLogCollector;
import org.tym.bugpred.util.StringUtils;

public class IGSTKChangeLogCollector extends ChangeLogCollector {

	private static final Pattern P = Pattern.compile(".*BUG:(\\s)+((\\d)+).*"); // 匹配commit
																				// title中的BugId

	public IGSTKChangeLogCollector(String commitLogPath, String changeLogPath) {
		super(commitLogPath, changeLogPath);
	}

	@Override
	public void collect() {
		BufferedReader br = null;
		FileWriter fw = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(this.commitLogPath)));
			fw = new FileWriter(this.changeLogPath);
			int line = 1;
			StringBuffer totalMessage = null;
			br.readLine();
			while (true) {
				String hash = br.readLine();
				if (null == hash) {
					break;
				}
				String author = br.readLine().trim().replaceAll(" ", "-");
				String timestamp = br.readLine().trim() + "000";
				StringBuffer tmpMessage = new StringBuffer();
				String newStr = br.readLine();
				while (newStr != null && !"".equals(newStr)) {
					tmpMessage.append(newStr).append(" ");
					newStr = br.readLine();
				}
				String message = tmpMessage.toString();

				Matcher m = P.matcher(message);
				if (m.find()) {
					String bugID = StringUtils.normalizeBugID(m.group(2));
					message = message.replace(m.group(2), bugID);
				}
				totalMessage = new StringBuffer().append(line).append("\t").append(timestamp).append("\t")
						.append(author).append("\t").append(message).append(" ");

				// totalMessage = new
				// StringBuffer().append(line).append("\t").append(hash).append("\t").append(timestamp).append("\t").append(author).append("\t").append(message).append("
				// ");

				// 跳过多个空格
				String str = br.readLine();
				for (; str == null || str.equals(""); str = br.readLine()) {

				}

				// 如果当前commit存在文件列表
				if (!"**********".equals(str)) {
					for (; str != null && !str.equals("**********"); str = br.readLine()) {
						String[] strs = str.split("(\\s)+");
						totalMessage.append(StringUtils.switchType(strs[0])).append(" : ").append(strs[1]).append(" ");
					}

					// 存在文件列表，则为有效commit，写入新的changeLog
					totalMessage.append("\t").append(message).append("\n");
					fw.write(totalMessage.toString());
					line++;
				}

				totalMessage = null;

			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException!");
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
