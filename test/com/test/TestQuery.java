package com.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.mail.internet.InternetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.agloco.mail.MailMessage;
import com.agloco.mail.MailPart;
import com.agloco.mail.Part;
import com.agloco.model.AGMember;
import com.agloco.model.AGQuery;
import com.agloco.report.util.AGReportResultList;
import com.agloco.report.util.ReportUtil;
import com.agloco.service.dao.util.ReportDaoUtil;
import com.agloco.service.util.MailServiceUtil;
import com.agloco.service.util.ReportServiceUtil;
import com.agloco.util.WordUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;

public class TestQuery
{

	private static Log _log = LogFactory.getLog(TestQuery.class);

	private final static int DAY = 1; // 1 day

	private final static int WEEK = 7;// 7 days

	private final static int MONTH = 30;// 30 days, not exact, it only means a month
	
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{

		ContextHelper.addResource("jdbc.properties");
		ContextHelper.addResource("mail.properties");

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		TimeZone timeZone = TimeZone.getDefault();
		df.setTimeZone(timeZone);

		String filePath =  PropsUtil.get("report.dir");
		File fold = new File(filePath);
		if(!fold.exists())
		{
			fold.mkdirs();
		}
		String outFile = filePath + df.format(Calendar.getInstance().getTime()) + ".zip";
		
		try
		{
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					outFile));
			List agQuerys = ReportDaoUtil.getAGQueryByType(30);
			for (int i = 0; i < agQuerys.size(); i++)
			{
				AGReportResultList resultList = null;
				AGQuery aq = (AGQuery) agQuerys.get(i);
				try
				{
					resultList = ReportServiceUtil.getReportResultList(aq);
				}
				catch (Exception e)
				{
					_log.error(aq.getQueryName() + " reportTask failed! ");
				}
				if (resultList == null)
					continue;

				String fileName = resultList.getTitle()+"_"+df.format(resultList.getDate().getTime())+ ".csv";

				StringBuffer sb = resultList.exportToExcel(timeZone);
				
				out.putNextEntry(new ZipEntry(fileName));
				int len = sb.length();
				byte[] b = new byte[len];
				sb.toString().getBytes(0, len, b, 0);
				out.write(b, 0, len);
				
			}
			// Complete the entry
			out.closeEntry();

			// Complete the ZIP file
			out.close();
			ReportUtil.sendReportMail(outFile,"");

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}


}
