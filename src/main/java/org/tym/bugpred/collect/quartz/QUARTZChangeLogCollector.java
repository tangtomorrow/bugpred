package org.tym.bugpred.collect.quartz;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import org.tym.bugpred.collect.ChangeLogCollector;
import org.tym.bugpred.util.DateUtils;

public class QUARTZChangeLogCollector extends ChangeLogCollector {

	public QUARTZChangeLogCollector(String commitLogPath, String changeLogPath) {
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
			String str = br.readLine();
			while (str != null) {

				String revisionNo = str.split("\\s")[1];
				if (line == 55) {
					System.out.println(revisionNo);
				}
				String author = br.readLine().trim();
				author = author.substring(8, author.length());
				String date = br.readLine().trim();
				date = DateUtils.formatQUARTZSVNDateToTimestamp(date.substring(6, date.length()));
				br.readLine();

				// get message
				StringBuffer message = new StringBuffer("");
				for (String s = br.readLine(); !s.equals("----"); s = br.readLine()) {
					message.append(s).append(" ");
				}
				if (message.equals("")) {
					message.append("[no log message]");
				}

				StringBuffer fileList = new StringBuffer("");
				for (String s = br.readLine(); s != null && !s.equals(""); s = br.readLine()) {
					fileList.append(s).append(" ");
				}
				if (fileList.equals("")) {
					fileList.append("[no file list]");
				}

				StringBuffer totalLine = new StringBuffer();
				totalLine.append(line).append("\t").append(date).append("\t").append(author).append("\t")
						.append(message).append(" ").append(fileList).append("\t").append(message).append("\n");
				fw.write(totalLine.toString());

				str = br.readLine();
				line++;
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
