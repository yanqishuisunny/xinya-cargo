package com.commom.utils;

import com.google.common.base.Strings;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static String FORMATE_SINGLE_YEAR = "yyyy";
	public static String FORMATE_SINGLE_MONTH = "MM";
	public static String FORMATE_DATE = "yyyy-MM-dd";
	public static String FORMATE_DATE_MONTH = "yyyy-MM";
	public static String FORMATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static String FORMATE_TIME_FORMAT  = "yyyy年MM月dd日 HH:mm:ss";

	public static int getMin(Date startTime,Date endTime){
		Long t = startTime.getTime() - endTime.getTime();
		return t.intValue()/1000;
	}

	/**
	 * 获取 2个日期之间相差多少分钟
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long getSecondOf2Date(Date startTime,Date endTime){
		long between=(startTime.getTime()-endTime.getTime())/1000;//除以1000是为了转换成秒
		return between;
	}

	public static Date getDate(Date date, Integer day, Integer month, Integer year){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if(day != null){
			cal.add(Calendar.DATE, day);
		}
		if(month != null){
			cal.add(Calendar.MONTH, month);
		}
		if(year != null){
			cal.add(Calendar.YEAR, year);
		}
		SimpleDateFormat format = new SimpleDateFormat(FORMATE_TIME);
		return cal.getTime(); // 结果
	}


	public static Date getLastDayOfMonth(final Date date) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		final int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, last);
		return cal.getTime();
	}

	/**
	 * 获取传入日期所在年的最后一天
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfYear(final Date date) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		final int last = cal.getActualMaximum(Calendar.DAY_OF_YEAR);
		cal.set(Calendar.DAY_OF_YEAR, last);
		return cal.getTime();
	}

	/**
	*
	* @Title:
	* @Description:获取提前或延后的日期，格式为yyyy-MM-dd HH:mm
	* @return String
	* @Version:1.1.0
	*/
	public static String getDate(String dateStr, int hour) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(FORMATE_TIME);
			Date date = format.parse(dateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.HOUR_OF_DAY, hour);
			return format.format(cal.getTime());
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 *
	 * @Title:
	 * @Description:获取提前或延后的日期，格式为yyyy-MM-dd HH:mm
	 * @return String
	 * @Version:1.1.0
	 */
	public static Date getDate(Date date, int day) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, day);
			return cal.getTime();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 日期转换成字符串
	 *
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date,String dateFormate) {
		String str = null;
		if (date != null) {
			SimpleDateFormat text = new SimpleDateFormat(dateFormate);
			str = text.format(date);
		} else {
			return null;
		}
		return str;
	}

	/**
	 * 字符串转换成日期
	 *
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str, String dateFormate) {
		Date date = null;
		if (!Strings.isNullOrEmpty(str)) {
			SimpleDateFormat format = new SimpleDateFormat(dateFormate);
			try {
				date = format.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			return null;
		}
		return date;
	}

	/**
	 * 字符串转换成长整形
	 *
	 * @param str
	 * @return
	 */
	public static long StrToLong(String str, String dateFormate) {
		long longTime = 0L;
		if (!Strings.isNullOrEmpty(str)) {
			Date date = StrToDate(str, dateFormate);
			longTime = date.getTime();
		} else {
			return 0L;
		}
		return longTime;
	}

	/**
	 * 验证 当前时间 是否在 时间段内
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
    public static boolean checkDateIn(String startDateStr, String endDateStr) {
		Date currentDate = new Date();
		if(Strings.isNullOrEmpty(startDateStr) && Strings.isNullOrEmpty(endDateStr)){
			return true;
		}
    	if(Strings.isNullOrEmpty(endDateStr)){
			Date startDate = DateUtil.StrToDate(startDateStr + " 00:00:00", DateUtil.FORMATE_TIME);
			if(currentDate.after(startDate)){
				return true;
			}
		}else if(Strings.isNullOrEmpty(startDateStr)){
			Date endDate = DateUtil.StrToDate(endDateStr + " 23:59:59", DateUtil.FORMATE_TIME);
			if(currentDate.before(endDate)){
				return true;
			}
		}else{
			Date startDate = DateUtil.StrToDate(startDateStr + " 00:00:00", DateUtil.FORMATE_TIME);
			Date endDate = DateUtil.StrToDate(endDateStr + " 23:59:59", DateUtil.FORMATE_TIME);
			if(currentDate.after(startDate) && currentDate.before(endDate)){
				return true;
			}
		}
		return false;
    }

	/**
	 * @param mss 要转换的毫秒数
	 * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
	 * @author fy.zhang
	 */
	public static String formatDuring(long mss) {
		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		String returnStr = "";
		if(days > 0){
			returnStr += days + " 天 ";
		}
		if(hours > 0){
			returnStr += hours + " 时 ";
		}
		if(minutes > 0){
			returnStr += minutes + " 分 ";
		}
		if(seconds > 0){
			returnStr += seconds + " 秒 ";
		}
		return  returnStr;
	}
	/**
	 *
	 * @param begin 时间段的开始
	 * @param end	时间段的结束
	 * @return	输入的两个Date类型数据之间的时间间格用* days * hours * minutes * seconds的格式展示
	 * @author fy.zhang
	 */
	public static String formatDuring(Date begin, Date end) {
		return formatDuring(end.getTime() - begin.getTime());
	}

	/**
	 * 两个时间相差
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return
	 */
	public static long diffTime(LocalDateTime start, LocalDateTime end) {
		Long startTime = Timestamp.valueOf(start).getTime();
		Long endTime = Timestamp.valueOf(end).getTime();

		return endTime - startTime;
	}


	public static Long getBeforeTime(LocalDateTime time,int hour){
		LocalDateTime beforeTime = time.minusHours(hour);
		return Timestamp.valueOf(beforeTime).getTime();
	}

	/**
	 * YYYYMMDD --> YYYY-MM-DD
	 * @param date
	 * @return
	 */
	public static String toStringDate(String date){
		if(date==null||date.length()<8){
			System.out.println("wrong date format.");
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		int yaer = Integer.parseInt(date.substring(0, 4));
		buffer.append(yaer).append("-");
		int month = Integer.parseInt(date.substring(4, 6));
		buffer.append(month).append("-");
		int day = Integer.parseInt(date.substring(6, 8));
		buffer.append(day);
		return buffer.toString();
	}

	public static void main(String[] args) {
//		System.out.println(toStringDate("201512071434"));
//		String idCardEndTime = "2018-02-15";
//		Date newDate = DateUtil.StrToDate(idCardEndTime,"yyyy-MM-dd");
//		if(newDate.before(new Date(System.currentTimeMillis()))){
//			System.out.println(true);
//		}else {
//			System.out.println(false);
//		}

        LocalDateTime localDateTime = LocalDateTime.now();
        String string = formatLocalDateToString(localDateTime);
        System.out.println(string);
    }

	/**
	 * 交通局返回的数据 固定位YYYYMMDD
	 * @param str
	 * @return
	 */
	public static String toDateString (String str){
        String yaer = str.substring(0,4);
        String month = str.substring(4,6);
        String day = str.substring(6,8);
		return yaer+"-"+month+"-"+day;
	}

	// LocalDateTime转为"yyyy-MM-dd HH:mm:ss"
	public static String formatLocalDateTimeString(LocalDateTime date) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return date.format(dtf);
	}

	// "yyyy-MM-dd HH:mm:ss" 转为 LocalDateTime
	public static LocalDateTime formatStringLocalDateTime(String date) {
		DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.parse(date, timeDtf);
		return localDateTime;
	}
	// "yyyy-MM-dd HH:mm:ss" 转为 LocalDateTime
	public static LocalDateTime hoursToLocalDateTime(String date) {
		DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyyMMddHH");
		LocalDateTime localDateTime = LocalDateTime.parse(date, timeDtf);
		return localDateTime;
	}
	public static String hoursToLocalDateTime(LocalDateTime date) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHH");
		return date.format(dtf);
	}

	public static LocalDate dayToLocalDateTime(String date) {
		DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate  localDate = LocalDate.parse(date, timeDtf);
		return localDate;
	}
	public static String dayToLocalDateTime(LocalDate date) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		return date.format(dtf);
	}

	public static LocalDate mouthToLocalDateTime(String date) {
		DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate  localDate = LocalDate.parse(date, timeDtf);
		return localDate;
	}
	public static String mouthToLocalDateTime(LocalDate date) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		return date.format(dtf);
	}




	public static String formatLocalDateString(LocalDateTime date) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return date.format(dtf);
	}


    /**
     * LocalDateTime 转成
     * @param date
     * @return
     */
    public static String formatLocalDateToString(LocalDateTime date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        return date.format(dtf);
    }


    /**
     * LocalDateTime转换为Date
     * @param localDateTime
     */
    public static Date localDateTimeToDate( LocalDateTime localDateTime){
        ZoneId zoneId = ZoneId.systemDefault();
        //Combines this date-time with a time-zone to create a  ZonedDateTime.
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    /**
     * LocalDateTime转换为Date
     * @param localDate
     */
    public static Date localDateToDate( LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }
}
