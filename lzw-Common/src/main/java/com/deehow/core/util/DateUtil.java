package com.deehow.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期操作辅助类
 * 
 * @author ShenHuaJie
 * @version $Id: DateUtil.java, v 0.1 2014年3月28日 上午8:58:11 ShenHuaJie Exp $
 */
public final class DateUtil {
	private DateUtil() {
	}
	/** 日期格式 **/
    public interface DATE_PATTERN {
        String HHMMSS = "HHmmss";
        String HH_MM_SS = "HH:mm:ss";
        String YYYYMMDD = "yyyyMMdd";
        String YYYY_MM_DD = "yyyy-MM-dd";
        String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
        String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
        String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    }

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static final String format(Object date) {
		return format(date, DATE_PATTERN.YYYY_MM_DD);
	}
	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static final String formatHMS(Object date) {
		return format(date, DATE_PATTERN.HH_MM_SS);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static final String format(Object date, String pattern) {
		if (date == null) {
			return null;
		}
		if (pattern == null) {
			return format(date);
		}
		return new SimpleDateFormat(pattern).format(date);
	}
	public static final Date parse(Object date, String pattern){
		if (date == null) {
			return null;
		}
		if (pattern == null) {
			return null;
		}
		try {
			return new SimpleDateFormat(pattern).parse(date.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取日期
	 * 
	 * @return
	 */
	public static final String getDate() {
		return format(new Date());
	}

	/**
	 * 获取日期时间
	 * 
	 * @return
	 */
	public static final String getDateTime() {
		return format(new Date(), DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 获取日期
	 * 
	 * @param pattern
	 * @return
	 */
	public static final String getDateTime(String pattern) {
		return format(new Date(), pattern);
	}

	/**
	 * 日期计算
	 * 
	 * @param date
	 * @param field
	 * @param amount
	 * @return
	 */
	public static final Date addDate(Date date, int field, int amount) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}

	/**
	 * 字符串转换为日期:不支持yyM[M]d[d]格式
	 * 
	 * @param date
	 * @return
	 */
	public static final Date stringToDate(String date) {
		if (date == null) {
			return null;
		}
		String separator = String.valueOf(date.charAt(4));
		String pattern = "yyyyMMdd";
		if (!separator.matches("\\d*")) {
			pattern = "yyyy" + separator + "MM" + separator + "dd";
			if (date.length() < 10) {
				pattern = "yyyy" + separator + "M" + separator + "d";
			}
		} else if (date.length() < 8) {
			pattern = "yyyyMd";
		}
		pattern += " HH:mm:ss.SSS";
		pattern = pattern.substring(0, Math.min(pattern.length(), date.length()));
		try {
			return new SimpleDateFormat(pattern).parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 间隔天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static final Integer getDayBetween(Date startDate, Date endDate) {
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MILLISECOND, 0);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		end.set(Calendar.HOUR_OF_DAY, 0);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		end.set(Calendar.MILLISECOND, 0);

		long n = end.getTimeInMillis() - start.getTimeInMillis();
		return (int) (n / (60 * 60 * 24 * 1000l));
	}

	/**
	 * 间隔月
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static final Integer getMonthBetween(Date startDate, Date endDate) {
		if (startDate == null || endDate == null || !startDate.before(endDate)) {
			return null;
		}
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		int year1 = start.get(Calendar.YEAR);
		int year2 = end.get(Calendar.YEAR);
		int month1 = start.get(Calendar.MONTH);
		int month2 = end.get(Calendar.MONTH);
		int n = (year2 - year1) * 12;
		n = n + month2 - month1;
		return n;
	}

	/**
	 * 间隔月，多一天就多算一个月
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static final Integer getMonthBetweenWithDay(Date startDate, Date endDate) {
		if (startDate == null || endDate == null || !startDate.before(endDate)) {
			return null;
		}
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		int year1 = start.get(Calendar.YEAR);
		int year2 = end.get(Calendar.YEAR);
		int month1 = start.get(Calendar.MONTH);
		int month2 = end.get(Calendar.MONTH);
		int n = (year2 - year1) * 12;
		n = n + month2 - month1;
		int day1 = start.get(Calendar.DAY_OF_MONTH);
		int day2 = end.get(Calendar.DAY_OF_MONTH);
		if (day1 <= day2) {
			n++;
		}
		return n;
	}
	/**
	 * 当月第一天
	 */
	public static final String getFristDay(String date){
		  //获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date, "yyyy-MM-dd"));
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,本月第一天 
        String first = format(c.getTime());
        return first;  
	}
	public static final String getLastDay(String date){
      Calendar c = Calendar.getInstance();
      c.setTime(parse(date, "yyyy-MM-dd"));
      c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
      String last = format(c.getTime());
      return last;  
	}
	public static final String getNextDay(String date){
      Calendar c = Calendar.getInstance();
      c.setTime(parse(date, "yyyy-MM-dd"));
      c.add(Calendar.DAY_OF_MONTH,1);//设置为1号,本月第一天 
      String next = format(c.getTime());
      return next;  
	}
	public static void main(String[] args) {
		System.out.println(getNextDay("2017-07-07"));
	}
	
	/**
	 * java格式化CST日期时间
	 * @param cstDateStr
	 * @return
	 */
	public static String formatCSTDate(String cstDateStr) {
		Date date = null;
		SimpleDateFormat sdf1 = null;
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
			date = sdf1.parse(cstDateStr);
		} catch (ParseException e) {
			return "";
		}
		return sdf2.format(date);
	}
}
