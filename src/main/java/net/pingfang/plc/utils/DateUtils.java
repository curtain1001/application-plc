package net.pingfang.plc.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.tuple.Pair;

/**
 * 时间工具类
 *
 * @author qiuhu
 * @version 1.0
 * @date 2019-12-31 10:23:33
 * @see
 * @since JDK 1.8
 */
public class DateUtils {

	static final ZoneId localZoneId = ZoneId.of("Asia/Shanghai");
	static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 获取当前时间
	 *
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentTime() {
		return format.format(new Date());
	}

	/**
	 * 得到本周周一
	 *
	 * @return yyyy-MM-dd
	 */
	public static Date getMondayOfThisWeek(Date date) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		c.setTime(date);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0) {
			day_of_week = 7;
		}
		c.add(Calendar.DATE, -day_of_week + 1);
		return c.getTime();
	}

	/**
	 * 得到本周周日
	 *
	 * @return yyyy-MM-dd
	 */
	public static Date getSundayOfThisWeek(Date date) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		c.setTime(date);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0) {
			day_of_week = 7;
		}
		c.add(Calendar.DATE, -day_of_week + 7);
		return c.getTime();
	}

	/**
	 * 得到上周周一
	 *
	 * @param date
	 * @return
	 */
	public static Date getMondayOfLastWeek(Date date) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		cal.setTime(getMondayOfThisWeek(date));
		cal.add(Calendar.DATE, -7);
		return cal.getTime();
	}

	public static Date getLastMonthBegin(Date date) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		c.setTime(date);

		c.add(Calendar.MONTH, -1);
		// 设置为1号,当前日期既为本月第一天
		c.set(Calendar.DAY_OF_MONTH, 1);
		// 将小时至0
		c.set(Calendar.HOUR_OF_DAY, 0);
		// 将分钟至0
		c.set(Calendar.MINUTE, 0);
		// 将秒至0
		c.set(Calendar.SECOND, 0);
		// 将毫秒至0
		c.set(Calendar.MILLISECOND, 0);
		// 获取本月第一天的时间戳
		return c.getTime();
	}

	public static Date getMonthBegin(Date date) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date getMonthEnd(Date date) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		c.setTime(date);

		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 0);
		// 获取本月第一天的时间戳
		return c.getTime();
	}

	public static Pair<LocalDateTime, LocalDateTime> getHourRange(LocalDateTime datetime, int hourRange,
			ZoneId zoneId) {
		LocalDateTime beginTime = datetime.minusHours(hourRange);
		LocalDateTime endTime = datetime.plusHours(hourRange);
		Instant beginInstant = beginTime.atZone(zoneId).toInstant();
		Instant endInstant = endTime.atZone(zoneId).toInstant();
		return Pair.of(LocalDateTime.ofInstant(beginInstant, zoneId), LocalDateTime.ofInstant(endInstant, zoneId));
	}

	public static Pair<LocalDateTime, LocalDateTime> getDayRange(LocalDateTime datetime, ZoneId zoneId) {
		datetime = datetime.minusDays(1);
		LocalDate dt = datetime.toLocalDate();
		Instant beginInstant = datetime.toLocalDate().atStartOfDay().atZone(zoneId).toInstant();
		return Pair.of(LocalDateTime.ofInstant(beginInstant, zoneId), LocalDateTime.of(dt, LocalTime.of(23, 59, 59)));
	}

	public static Pair<LocalDateTime, LocalDateTime> getLastWeekRange(LocalDateTime datetime, ZoneId zoneId) {

		int dw = datetime.getDayOfWeek().getValue();

		LocalDate bg = datetime.minusDays(dw + 6).toLocalDate();
		LocalDate ed = datetime.minusDays(dw).toLocalDate();

		return Pair.of(LocalDateTime.ofInstant(bg.atStartOfDay().atZone(zoneId).toInstant(), zoneId),
				LocalDateTime.of(ed, LocalTime.of(23, 59, 59)));
	}

	public static Pair<LocalDateTime, LocalDateTime> getCurrWeekRange(LocalDateTime datetime, ZoneId zoneId) {

		int dw = datetime.getDayOfWeek().getValue();
		LocalDate bg = datetime.minusDays(dw - 1).toLocalDate();
		return Pair.of(LocalDateTime.ofInstant(bg.atStartOfDay().atZone(zoneId).toInstant(), zoneId),
				LocalDateTime.of(datetime.toLocalDate().plusDays(7 - dw), LocalTime.of(23, 59, 59)));
	}

	public static Pair<LocalDateTime, LocalDateTime> getlastMonthRange(LocalDateTime datetime) {
		LocalDateTime ed = datetime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).minusSeconds(1);
		LocalDateTime bg = ed.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
		return Pair.of(bg, ed);
	}

	public static Pair<LocalDateTime, LocalDateTime> getCurrMonthRange(LocalDateTime datetime) {
		LocalDateTime ed = datetime.plusMonths(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0)
				.minusSeconds(1);
		LocalDateTime bg = datetime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
		return Pair.of(bg, ed);
	}

	public static boolean between(Date date, Date from, Date to) {
		return date.after(from) && date.before(to);
	}

}
