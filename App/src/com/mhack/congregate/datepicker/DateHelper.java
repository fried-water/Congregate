package com.mhack.congregate.datepicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import android.text.format.DateUtils;

final class DateHelper {
	public static final String UTC_TIME_ZONE = "UTC";

	public static final int YEAR1900 = 1900;

	/**
	 * Ð Ñ™Ð Ñ•Ð Ð…Ð¡ÐƒÐ¡â€šÐ¡Ð‚Ð¡Ñ“Ð Ñ”Ð¡â€šÐ Ñ•Ð¡Ð‚.
	 */
	private DateHelper() {
	}

	public static Date createDate(final int year, final int month, final int day, final int hour, final int minute) {

		Calendar calendar = new GregorianCalendar(year, month, day, hour, minute);
		return calendar.getTime();
	}

	public static Date createDate(final int year, final int month, final int day) {
		return createDate(year, month, day, 0, 0);
	}

	public static Date createDateGMT(final int year, final int month, final int day) {
		return convertToUTC(DateHelper.createDate(year, month, day, 0, 0));
	}

	public static boolean dateMoreOrEqual(final Date date1, final Date date2) {
		return date1.compareTo(date2) >= 0;
	}

	/**
	 * Ð ÐŽÐ¡Ð‚Ð Â°Ð Ð†Ð Ð…Ð ÂµÐ Ð…Ð Ñ‘Ð Âµ Ð Ò‘Ð Ð†Ð¡Ñ“Ð¡â€¦ Ð Ò‘Ð Â°Ð¡â€š Ð¡Ðƒ Ð¡Ñ“Ð¡â€¡Ð¡â€˜Ð¡â€šÐ Ñ•Ð Ñ˜ Ð Ð†Ð¡Ð‚Ð ÂµÐ Ñ˜Ð ÂµÐ Ð…Ð Ñ‘.
	 * @param date1 Ð Ñ—Ð ÂµÐ¡Ð‚Ð Ð†Ð Â°Ð¡Ð� Ð Ò‘Ð Â°Ð¡â€šÐ Â°.
	 * @param date2 Ð Ð†Ð¡â€šÐ Ñ•Ð¡Ð‚Ð Â°Ð¡Ð� Ð Ò‘Ð Â°Ð¡â€šÐ Â°.
	 * @return true, Ð ÂµÐ¡ÐƒÐ Â»Ð Ñ‘ Ð Ñ—Ð ÂµÐ¡Ð‚Ð Ð†Ð Â°Ð¡Ð� Ð Ò‘Ð Â°Ð¡â€šÐ Â° Ð Ñ˜Ð ÂµÐ Ð…Ð¡ÐŠÐ¡â‚¬Ð Âµ Ð Ð†Ð¡â€šÐ Ñ•Ð¡Ð‚Ð Ñ•Ð â„–.
	 */
	public static boolean dateLess(final Date date1, final Date date2) {
		return date1.compareTo(date2) < 0;
	}

	/**
	 * Ð ï¿½Ð Â·Ð Ñ˜Ð ÂµÐ Ð…Ð¡Ð�Ð ÂµÐ¡â€š Ð Â·Ð Ð…Ð Â°Ð¡â€¡Ð ÂµÐ Ð…Ð Ñ‘Ð Âµ Ð Ò‘Ð Â°Ð¡â€šÐ¡â€¹.
	 * @param date Ð Ñ‘Ð¡ÐƒÐ¡â€¦Ð Ñ•Ð Ò‘Ð Ð…Ð Ñ•Ð Âµ Ð Â·Ð Ð…Ð Â°Ð¡â€¡Ð ÂµÐ Ð…Ð Ñ‘Ð Âµ.
	 * @param field Ð Ñ‘Ð Â·Ð Ñ˜Ð ÂµÐ Ð…Ð¡Ð�Ð ÂµÐ Ñ˜Ð Ñ•Ð Âµ Ð Ñ—Ð Ñ•Ð Â»Ð Âµ (Ð¡ÐƒÐ Ñ˜. Ð Ñ”Ð Ñ•Ð Ð…Ð¡ÐƒÐ¡â€šÐ Â°Ð Ð…Ð¡â€šÐ¡â€¹ Ð Ñ”Ð Â»Ð Â°Ð¡ÐƒÐ¡ÐƒÐ Â° Calendar).
	 * @param value Ð Ð…Ð Â° Ð¡ÐƒÐ Ñ”Ð Ñ•Ð Â»Ð¡ÐŠÐ Ñ”Ð Ñ• Ð Ñ‘Ð Â·Ð Ñ˜Ð ÂµÐ Ð…Ð Ñ‘Ð¡â€šÐ¡ÐŠ Ð Â·Ð Ð…Ð Â°Ð¡â€¡Ð ÂµÐ Ð…Ð Ñ‘Ð Âµ.
	 * @return Ð Ð…Ð Ñ•Ð Ð†Ð Ñ•Ð Âµ Ð Â·Ð Ð…Ð Â°Ð¡â€¡Ð ÂµÐ Ð…Ð Ñ‘Ð Âµ Ð Ò‘Ð Â°Ð¡â€šÐ¡â€¹.
	 */
	public static Date add(final Date date, final int field, final int value) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(field, value);
		return calendar.getTime();
	}

	/**
	 * Ð ÑŸÐ¡Ð‚Ð Ñ‘Ð Ð†Ð Ñ•Ð Ò‘Ð Ñ‘Ð¡â€š Ð Ò‘Ð Â°Ð¡â€šÐ¡Ñ“ Ð¡Ðƒ Ð Ð†Ð¡Ð‚Ð ÂµÐ Ñ˜Ð ÂµÐ Ð…Ð ÂµÐ Ñ˜ Ð Ñ” Ð Ò‘Ð Â°Ð¡â€šÐ Âµ Ð¡Ðƒ Ð Ð†Ð¡Ð‚Ð ÂµÐ Ñ˜Ð ÂµÐ Ð…Ð ÂµÐ Ñ˜, Ð¡ÐƒÐ Ñ•Ð Ñ•Ð¡â€šÐ Ð†Ð ÂµÐ¡â€šÐ¡ÐƒÐ¡â€šÐ Ð†Ð¡Ñ“Ð¡Ð‹Ð¡â€°Ð Ñ‘Ð Ñ˜ Ð Ð…Ð Â°Ð¡â€¡Ð Â°Ð Â»Ð¡Ñ“ Ð¡ÐƒÐ¡Ñ“Ð¡â€šÐ Ñ•Ð Ñ”.
	 * @param date Ð â€�Ð Â°Ð¡â€šÐ Â° Ð¡Ðƒ Ð Ð†Ð¡Ð‚Ð ÂµÐ Ñ˜Ð ÂµÐ Ð…Ð ÂµÐ Ñ˜.
	 * @return Ð Ò‘Ð Â°Ð¡â€šÐ Â° Ð¡Ðƒ Ð Ð†Ð¡Ð‚Ð ÂµÐ Ñ˜Ð ÂµÐ Ð…Ð ÂµÐ Ñ˜ Ð Ð…Ð Â° Ð Ð…Ð Â°Ð¡â€¡Ð Â°Ð Â»Ð Ñ• Ð¡ÐƒÐ¡Ñ“Ð¡â€šÐ Ñ•Ð Ñ”.
	 */
	public static Date clearTime(final Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(GregorianCalendar.HOUR_OF_DAY, 0);
		calendar.set(GregorianCalendar.MINUTE, 0);
		calendar.set(GregorianCalendar.SECOND, 0);
		calendar.set(GregorianCalendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * Ð ÐŽÐ¡Ð‚Ð Â°Ð Ð†Ð Ð…Ð ÂµÐ Ð…Ð Ñ‘Ð Âµ Ð Ð…Ð Â° Ð¡Ð‚Ð Â°Ð Ð†Ð ÂµÐ Ð…Ð¡ÐƒÐ¡â€šÐ Ð†Ð Ñ• Ð Ò‘Ð Ð†Ð¡Ñ“Ð¡â€¦ Ð Ò‘Ð Â°Ð¡â€š, Ð Â±Ð ÂµÐ Â· Ð¡Ñ“Ð¡â€¡Ð¡â€˜Ð¡â€šÐ Â° Ð¡ÐƒÐ Ñ•Ð¡ÐƒÐ¡â€šÐ Â°Ð Ð†Ð Â»Ð¡Ð�Ð¡Ð‹Ð¡â€°Ð Ñ‘Ð¡â€¦ Ð Ð†Ð¡Ð‚Ð ÂµÐ Ñ˜Ð ÂµÐ Ð…Ð Ñ‘.
	 * @param date1 Ð Ñ—Ð ÂµÐ¡Ð‚Ð Ð†Ð Â°Ð¡Ð� Ð Ò‘Ð Â°Ð¡â€šÐ Â°.
	 * @param date2 Ð Ð†Ð¡â€šÐ Ñ•Ð¡Ð‚Ð Â°Ð¡Ð� Ð Ò‘Ð Â°Ð¡â€šÐ Â°.
	 * @return true, Ð ÂµÐ¡ÐƒÐ Â»Ð Ñ‘ Ð Ò‘Ð Â°Ð¡â€šÐ¡â€¹ Ð¡Ð‚Ð Â°Ð Ð†Ð Ð…Ð¡â€¹.
	 */
	public static boolean equalsIgnoreTime(final Date date1, final Date date2) {
		Date clearedDate1 = DateHelper.clearTime(date1);
		Date clearedDate2 = DateHelper.clearTime(date2);
		return clearedDate1.equals(clearedDate2);
	}

	/**
	 * Ð â€”Ð Â°Ð Ñ˜Ð ÂµÐ Ð…Ð Ñ‘Ð¡â€šÐ¡ÐŠ Ð Ò‘Ð Â°Ð¡â€šÐ¡Ñ“.
	 * @param sourceDate Ð ï¿½Ð¡ÐƒÐ¡â€¦Ð Ñ•Ð Ò‘Ð Ð…Ð Â°Ð¡Ð� Ð Ò‘Ð Â°Ð¡â€šÐ Â°/Ð Ð†Ð¡Ð‚Ð ÂµÐ Ñ˜Ð¡Ð�.
	 * @param year Ð â€œÐ Ñ•Ð Ò‘.
	 * @param monthOfYear Ð ÑšÐ ÂµÐ¡ÐƒÐ¡Ð�Ð¡â€ .
	 * @param dayOfMonth Ð â€�Ð ÂµÐ Ð…Ð¡ÐŠ Ð Ñ˜Ð ÂµÐ¡ÐƒÐ¡Ð�Ð¡â€ Ð Â°.
	 * @return Ð ÑœÐ Ñ•Ð Ð†Ð Â°Ð¡Ð� Ð Ò‘Ð Â°Ð¡â€šÐ Â°.
	 */
	public static Date replaceDate(final Date sourceDate, final int year, final int monthOfYear, final int dayOfMonth) {
		java.util.Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(sourceDate);

		calendar.set(GregorianCalendar.YEAR, year);
		calendar.set(GregorianCalendar.MONTH, monthOfYear);
		calendar.set(GregorianCalendar.DAY_OF_MONTH, dayOfMonth);

		return calendar.getTime();
	}

	/**
	 * Ð â€”Ð Â°Ð Ñ˜Ð ÂµÐ Ð…Ð Ñ‘Ð¡â€šÐ¡ÐŠ Ð Ò‘Ð Â°Ð¡â€šÐ¡Ñ“ Ð Ð† Ð Ñ”Ð Â°Ð Â»Ð ÂµÐ Ð…Ð Ò‘Ð Â°Ð¡Ð‚Ð Âµ.
	 * @param calendar Ð Ñ™Ð Â°Ð Â»Ð ÂµÐ Ð…Ð Ò‘Ð Â°Ð¡Ð‚Ð¡ÐŠ.
	 * @param year Ð â€œÐ Ñ•Ð Ò‘.
	 * @param monthOfYear Ð ÑšÐ ÂµÐ¡ÐƒÐ¡Ð�Ð¡â€ .
	 * @param dayOfMonth Ð â€�Ð ÂµÐ Ð…Ð¡ÐŠ Ð Ñ˜Ð ÂµÐ¡ÐƒÐ¡Ð�Ð¡â€ Ð Â°.
	 */
	public static void changeDate(final java.util.Calendar calendar, final int year, final int monthOfYear, final int dayOfMonth) {
		calendar.set(GregorianCalendar.YEAR, year);
		calendar.set(GregorianCalendar.MONTH, monthOfYear);
		calendar.set(GregorianCalendar.DAY_OF_MONTH, dayOfMonth);
	}

	/**
	 * Ð â€”Ð Â°Ð Ñ˜Ð ÂµÐ Ð…Ð Ñ‘Ð¡â€šÐ¡ÐŠ Ð Ð†Ð¡Ð‚Ð ÂµÐ Ñ˜Ð¡Ð�.
	 * @param sourceDate Ð ï¿½Ð¡ÐƒÐ¡â€¦Ð Ñ•Ð Ò‘Ð Ð…Ð Â°Ð¡Ð� Ð Ò‘Ð Â°Ð¡â€šÐ Â°/Ð Ð†Ð¡Ð‚Ð ÂµÐ Ñ˜Ð¡Ð�.
	 * @param hourOfDay Ð Â§Ð Â°Ð¡Ðƒ.
	 * @param minute Ð ÑšÐ Ñ‘Ð Ð…Ð¡Ñ“Ð¡â€šÐ Â°.
	 * @param second Ð ÐŽÐ ÂµÐ Ñ”Ð¡Ñ“Ð Ð…Ð Ò‘Ð Â°.
	 * @return Ð ÑœÐ Ñ•Ð Ð†Ð Â°Ð¡Ð� Ð Ò‘Ð Â°Ð¡â€šÐ Â°.
	 */
	public static Date replaceTime(final Date sourceDate, final int hourOfDay, final int minute, final int second) {
		java.util.Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(sourceDate);

		calendar.set(GregorianCalendar.HOUR_OF_DAY, hourOfDay);
		calendar.set(GregorianCalendar.MINUTE, minute);
		calendar.set(GregorianCalendar.SECOND, second);

		return calendar.getTime();
	}

	/**
	 * Ð â€”Ð Â°Ð Ñ˜Ð ÂµÐ Ð…Ð Ñ‘Ð¡â€šÐ¡ÐŠ Ð Ð†Ð¡Ð‚Ð ÂµÐ Ñ˜Ð¡Ð�.
	 * @param calendar Ð Ñ™Ð Â°Ð Â»Ð ÂµÐ Ð…Ð Ò‘Ð Â°Ð¡Ð‚Ð¡ÐŠ.
	 * @param hourOfDay Ð Â§Ð Â°Ð¡Ðƒ.
	 * @param minute Ð ÑšÐ Ñ‘Ð Ð…Ð¡Ñ“Ð¡â€šÐ Â°.
	 * @param second Ð ÐŽÐ ÂµÐ Ñ”Ð¡Ñ“Ð Ð…Ð Ò‘Ð Â°.
	 */
	public static void changeTime(final java.util.Calendar calendar, final int hourOfDay, final int minute, final int second) {
		calendar.set(GregorianCalendar.HOUR_OF_DAY, hourOfDay);
		calendar.set(GregorianCalendar.MINUTE, minute);
		calendar.set(GregorianCalendar.SECOND, second);
	}

	/**
	 * Ð ÑŸÐ¡Ð‚Ð ÂµÐ Ñ•Ð Â±Ð¡Ð‚Ð Â°Ð Â·Ð Ñ•Ð Ð†Ð Â°Ð¡â€šÐ¡ÐŠ Ð Ñ‘Ð Â· Ð Ñ–Ð¡Ñ“Ð Ñ–Ð Â»Ð Ñ•Ð Ð†Ð¡ÐƒÐ Ñ”Ð Ñ•Ð Ñ–Ð Ñ• Ð¡â€šÐ Ñ‘Ð Ñ—Ð Â° Ð Ò‘Ð Â°Ð¡â€šÐ¡â€¹ Ð Ð† Ð¡Ð�Ð Ð†Ð Ñ•Ð Ð†Ð¡ÐƒÐ Ñ”Ð Ñ‘Ð â„–.
	 * @param date Ð Ò‘Ð Â°Ð¡â€šÐ Â°
	 * @return Ð Ò‘Ð Â°Ð¡â€šÐ Â° Ð Ð† Ð¡Ð�Ð Ð†Ð Â° Ð¡â€žÐ Ñ•Ð¡Ð‚Ð Ñ˜Ð Â°Ð¡â€šÐ Âµ
	 */
	public static GregorianCalendar fromDateToCalendar(final Date date) {
		GregorianCalendar calendar = new GregorianCalendar(0, 0, 0, 0, 0);
		calendar.setTimeInMillis(date.getTime());
		return calendar;
	}

	/**
	 * Ð Â¤Ð Ñ•Ð¡Ð‚Ð Ñ˜Ð Â°Ð¡â€šÐ Ñ‘Ð¡Ð‚Ð Ñ•Ð Ð†Ð Â°Ð¡â€šÐ¡ÐŠ Ð Ò‘Ð Â°Ð¡â€šÐ¡Ñ“.
	 * @param format Ð Â¤Ð Ñ•Ð¡Ð‚Ð Ñ˜Ð Â°Ð¡â€š Ð Ò‘Ð Â°Ð¡â€šÐ¡â€¹ (Ð¡ÐƒÐ¡â€šÐ Â°Ð Ð…Ð Ò‘Ð Â°Ð¡Ð‚Ð¡â€šÐ Ð…Ð¡â€¹Ð â„–, Ð Ñ•Ð¡â€š SimpleDateFormat).
	 * @param date Ð Ò‘Ð Â°Ð¡â€šÐ Â°.
	 * @return Ð Â Ð ÂµÐ Â·Ð¡Ñ“Ð Â»Ð¡ÐŠÐ¡â€šÐ Â°Ð¡â€š.
	 */
	public static String dateFormat(final String format, final Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	/**
	 * Ð ÑŸÐ¡Ð‚Ð ÂµÐ Ñ•Ð Â±Ð¡Ð‚Ð Â°Ð Â·Ð Ñ•Ð Ð†Ð Â°Ð¡â€šÐ¡ÐŠ Ð Ò‘Ð Â°Ð¡â€šÐ¡Ñ“ Ð Ð† Ð Ò‘Ð Â°Ð¡â€šÐ¡Ñ“ Ð Ñ—Ð Ñ• Ð Ñ–Ð¡Ð‚Ð Ñ‘Ð Ð…Ð Ð†Ð Ñ‘Ð¡â€¡Ð¡Ñ“. Ð ÑœÐ Â°Ð Ñ—Ð¡Ð‚Ð Ñ‘Ð Ñ˜Ð ÂµÐ¡Ð‚, 01.04.2012 00:00:00
	 * GMT+04:00 -> 01.04.2012 00:00:00 GMT.
	 * @param date Ð â€�Ð Â°Ð¡â€šÐ Â°, Ð Ñ”Ð Ñ•Ð¡â€šÐ Ñ•Ð¡Ð‚Ð¡Ñ“Ð¡Ð‹ Ð Ð…Ð ÂµÐ Ñ•Ð Â±Ð¡â€¦Ð Ñ•Ð Ò‘Ð Ñ‘Ð Ñ˜Ð Ñ• Ð Ñ—Ð¡Ð‚Ð ÂµÐ Ñ•Ð Â±Ð¡Ð‚Ð Â°Ð Â·Ð Ñ•Ð Ð†Ð Â°Ð¡â€šÐ¡ÐŠ.
	 * @return Ð â€�Ð Â°Ð¡â€šÐ Â° Ð Ñ—Ð Ñ• Ð Ñ–Ð¡Ð‚Ð Ñ‘Ð Ð…Ð Ð†Ð Ñ‘Ð¡â€¡Ð ÂµÐ¡ÐƒÐ Ñ”Ð Ñ•Ð Ñ˜Ð¡Ñ“ Ð Ð†Ð¡Ð‚Ð ÂµÐ Ñ˜Ð ÂµÐ Ð…Ð Ñ‘.
	 */
	public static Date convertToUTC(final Date date) {
		GregorianCalendar calendar = new GregorianCalendar(date.getYear() + YEAR1900, date.getMonth(), date.getDate(), date.getHours(), date.getMinutes(),
				date.getSeconds());
		calendar.setTimeZone(TimeZone.getTimeZone(UTC_TIME_ZONE));

		return calendar.getTime();
	}

	/**
	 * Ð ï¿½Ð Â·Ð Ñ˜Ð ÂµÐ Ð…Ð Ñ‘Ð¡â€šÐ¡ÐŠ Ð Â·Ð Ð…Ð Â°Ð¡â€¡Ð ÂµÐ Ð…Ð Ñ‘Ð Âµ Ð Ñ”Ð Â°Ð Â»Ð ÂµÐ Ð…Ð Ò‘Ð Â°Ð¡Ð‚Ð¡Ð� Ð Ð…Ð Â° Ð Ð…Ð Â°Ð¡â€¡Ð Â°Ð Â»Ð Ñ• Ð¡ÐƒÐ¡Ñ“Ð¡â€šÐ Ñ•Ð Ñ” Ð Ñ‘ Ð Ñ‘Ð Â·Ð Ñ˜Ð ÂµÐ Ð…Ð Ñ‘Ð¡â€šÐ¡ÐŠ Ð Ð†Ð¡Ð‚Ð ÂµÐ Ñ˜Ð ÂµÐ Ð…Ð Ð…Ð¡Ñ“Ð¡Ð‹ Ð Â·Ð Ñ•Ð Ð…Ð¡Ñ“
	 * UTC.
	 * @param calendar Ð Ñ™Ð Â°Ð Â»Ð ÂµÐ Ð…Ð Ò‘Ð Â°Ð¡Ð‚Ð¡ÐŠ.
	 */
	public static void changeToBeginDayUTC(final java.util.Calendar calendar) {
		int year = calendar.get(GregorianCalendar.YEAR);
		int month = calendar.get(GregorianCalendar.MONTH);
		int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);

		calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
		calendar.set(GregorianCalendar.YEAR, year);
		calendar.set(GregorianCalendar.MONTH, month);
		calendar.set(GregorianCalendar.DAY_OF_MONTH, day);
		calendar.set(GregorianCalendar.HOUR_OF_DAY, 0);
		calendar.set(GregorianCalendar.MINUTE, 0);
		calendar.set(GregorianCalendar.SECOND, 0);
		calendar.set(GregorianCalendar.MILLISECOND, 0);
	}

	/**
	 * Ð ÐˆÐ¡ÐƒÐ¡â€šÐ Â°Ð Ð…Ð Ñ•Ð Ð†Ð Ñ‘Ð¡â€šÐ¡ÐŠ Ð Ð† Ð Ñ”Ð Â°Ð Â»Ð ÂµÐ Ð…Ð Ò‘Ð Â°Ð¡Ð‚Ð Âµ Ð¡Ñ“Ð Ñ”Ð Â°Ð Â·Ð Â°Ð Ð…Ð Ð…Ð Ñ•Ð Âµ Ð Ð†Ð¡Ð‚Ð ÂµÐ Ñ˜Ð¡Ð� Ð Ñ‘ Ð¡â€¡Ð Â°Ð¡ÐƒÐ Ñ•Ð Ð†Ð Ñ•Ð â„– Ð Ñ—Ð Ñ•Ð¡Ð�Ð¡Ðƒ.
	 * @param calendar Ð Ñ™Ð Â°Ð Â»Ð ÂµÐ Ò‘Ð Â°Ð¡Ð‚Ð¡ÐŠ.
	 * @param hours Ð Â§Ð Â°Ð¡ÐƒÐ¡â€¹.
	 * @param minutes Ð ÑšÐ Ñ‘Ð Ð…Ð¡Ñ“Ð¡â€šÐ¡â€¹.
	 * @param timeZone Ð â€™Ð¡Ð‚Ð ÂµÐ Ñ˜Ð ÂµÐ Ð…Ð Ð…Ð Â°Ð¡Ð� Ð Â·Ð Ñ•Ð Ð…Ð Â°.
	 */
	public static void changeTimeAndTimeZone(final java.util.Calendar calendar, final int hours, final int minutes, final String timeZone) {
		int year = calendar.get(GregorianCalendar.YEAR);
		int month = calendar.get(GregorianCalendar.MONTH);
		int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);

		calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
		calendar.set(GregorianCalendar.YEAR, year);
		calendar.set(GregorianCalendar.MONTH, month);
		calendar.set(GregorianCalendar.DAY_OF_MONTH, day);
		calendar.set(GregorianCalendar.HOUR_OF_DAY, hours);
		calendar.set(GregorianCalendar.MINUTE, minutes);
	}

	/**
	 * Ð ÑŸÐ¡Ð‚Ð ÂµÐ Ñ•Ð Â±Ð¡Ð‚Ð Â°Ð Â·Ð Ñ•Ð Ð†Ð Â°Ð Ð…Ð Ñ‘Ð Âµ Ð Ñ‘Ð Â· Ð Ñ–Ð¡Ð‚Ð Ñ‘Ð Ð…Ð Ð†Ð Ñ‘Ð¡â€¡Ð Â° Ð Ð† Ð¡â€šÐ ÂµÐ Ñ”Ð¡Ñ“Ð¡â€°Ð ÂµÐ Âµ Ð Ð†Ð¡Ð‚Ð ÂµÐ Ñ˜Ð¡Ð�.
	 * @param dateGMT Ð â€�Ð Â°Ð¡â€šÐ Â° Ð Ð† GMT.
	 * @return Ð Ò‘Ð Â°Ð¡â€šÐ Â° Ð Ð† Ð Â»Ð Ñ•Ð Ñ”Ð Â°Ð Â»Ð¡ÐŠÐ Ð…Ð Ñ•Ð â„– Ð Â·Ð Ñ•Ð Ð…Ð Âµ.
	 */
	public static Date convertToCurrentTimeZone(final Date dateGMT) {
		long date = dateGMT.getTime() + dateGMT.getTimezoneOffset() * DateUtils.MINUTE_IN_MILLIS;
		return new Date(date);
	}

	/**
	 * @param date date.
	 * @return sql date format string.
	 */
	public static String dateToSqlFormat(Date date) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder now = new StringBuilder(dateformat.format(date));
		return now.toString();
	}

	/**
	 * Ð ÐŽÐ Ñ•Ð Â·Ð Ò‘Ð Â°Ð¡â€šÐ¡ÐŠ Ð Ñ”Ð Â°Ð Â»Ð ÂµÐ Ð…Ð Ò‘Ð Â°Ð¡Ð‚Ð¡ÐŠ.
	 * @param year Ð â€œÐ Ñ•Ð Ò‘.
	 * @param month Ð ÑšÐ ÂµÐ¡ÐƒÐ¡Ð�Ð¡â€ .
	 * @param day Ð â€�Ð ÂµÐ Ð…Ð¡ÐŠ.
	 * @param timezone Ð â€™Ð¡Ð‚Ð ÂµÐ Ñ˜Ð ÂµÐ Ð…Ð Ð…Ð Â°Ð¡Ð� Ð Â·Ð Ñ•Ð Ð…Ð Â°.
	 * @return Ð Ñ”Ð Â°Ð Â»Ð ÂµÐ Ð…Ð Ò‘Ð Â°Ð¡Ð‚Ð¡ÐŠ.
	 */
	public static Calendar createCalendar(final int year, final int month, final int day, final String timezone) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timezone));

		calendar.set(GregorianCalendar.YEAR, year);
		calendar.set(GregorianCalendar.MONTH, month);
		calendar.set(GregorianCalendar.DAY_OF_MONTH, day);
		calendar.set(GregorianCalendar.HOUR_OF_DAY, 0);
		calendar.set(GregorianCalendar.MINUTE, 0);
		calendar.set(GregorianCalendar.SECOND, 0);
		calendar.set(GregorianCalendar.MILLISECOND, 0);

		return calendar;
	}

	/**
	 * Ð ÐŽÐ Ñ•Ð Â·Ð Ò‘Ð Â°Ð¡â€šÐ¡ÐŠ Ð Ñ”Ð Â°Ð Â»Ð ÂµÐ Ð…Ð Ò‘Ð Â°Ð¡Ð‚Ð¡ÐŠ.
	 * @return Ð Ñ”Ð Â°Ð Â»Ð ÂµÐ Ð…Ð Ò‘Ð Â°Ð¡Ð‚Ð¡ÐŠ.
	 */
	public static Calendar createCurrentBeginDayCalendar() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar;
	}

	/**
	 * Ð ÐŽÐ Ñ•Ð Â·Ð Ò‘Ð Â°Ð¡â€šÐ¡ÐŠ Ð Ñ”Ð Â°Ð Â»Ð ÂµÐ Ð…Ð Ò‘Ð Â°Ð¡Ð‚Ð¡ÐŠ.
	 * @param year Ð â€œÐ Ñ•Ð Ò‘.
	 * @param month Ð ÑšÐ ÂµÐ¡ÐƒÐ¡Ð�Ð¡â€ .
	 * @param day Ð â€�Ð ÂµÐ Ð…Ð¡ÐŠ.
	 * @param hour Ð¡â€¡Ð Â°Ð¡Ðƒ.
	 * @param minute Ð Ñ˜Ð Ñ‘Ð Ð…Ð¡Ñ“Ð¡â€šÐ Â°.
	 * @param timezone Ð Â·Ð Ñ•Ð Ð…Ð Â°.
	 * @return Ð Ñ”Ð Â°Ð Â»Ð ÂµÐ Ð…Ð Ò‘Ð Â°Ð¡Ð‚Ð¡ÐŠ.
	 */
	public static Calendar createCalendar(final int year, final int month, final int day, final int hour, final int minute, final String timezone) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timezone));

		calendar.set(GregorianCalendar.YEAR, year);
		calendar.set(GregorianCalendar.MONTH, month);
		calendar.set(GregorianCalendar.DAY_OF_MONTH, day);
		calendar.set(GregorianCalendar.HOUR_OF_DAY, hour);
		calendar.set(GregorianCalendar.MINUTE, minute);
		calendar.set(GregorianCalendar.SECOND, 0);
		calendar.set(GregorianCalendar.MILLISECOND, 0);

		return calendar;
	}

	/**
	 * increment by 1 day.
	 * @param currentDate date.
	 */
	public static void increment(Calendar currentDate) {
		currentDate.add(Calendar.DATE, 1);
	}

	/**
	 * @param day day.
	 * @return is day weekend.
	 */
	public static boolean isWeekendDay(Calendar day) {
		if (isMondayFirst()) {
			return (day.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (day.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
		} else {
			return (day.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
		}
	}

	/**
	 * @param day day.
	 * @return is last day of week.
	 */
	public static boolean isLastDayOfWeek(Calendar day) {
		if (isMondayFirst()) {
			return (day.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
		} else {
			return (day.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY);
		}
	}

	/**
	 * @return is week starts from monday.
	 */
	public static boolean isMondayFirst() {
		return Calendar.getInstance().getFirstDayOfWeek() == Calendar.MONDAY;
	}

	/**
	 * Ð ÐŽÐ Ñ•Ð Â·Ð Ò‘Ð Â°Ð¡â€šÐ¡ÐŠ Ð¡â€šÐ ÂµÐ Ñ”Ð¡Ñ“Ð¡â€°Ð Ñ‘Ð Âµ Ð¡ÐƒÐ¡Ñ“Ð¡â€šÐ Ñ”Ð Ñ‘ Ð Ð† UTC timezone. Ð â€˜Ð ÂµÐ¡Ð‚Ð¡â€˜Ð¡â€šÐ¡ÐƒÐ¡Ð� Ð¡â€šÐ ÂµÐ Ñ”Ð¡Ñ“Ð¡â€°Ð Â°Ð¡Ð� Ð Ò‘Ð Â°Ð¡â€šÐ Â° Ð Ñ‘Ð Â· Ð¡â€šÐ Â°Ð â„–Ð Ñ˜Ð Â·Ð Ñ•Ð Ð…Ð¡â€¹ Ð Ñ—Ð Ñ•
	 * Ð¡Ñ“Ð Ñ˜Ð Ñ•Ð Â»Ð¡â€¡Ð Â°Ð Ð…Ð Ñ‘Ð¡Ð‹, Ð Ñ‘ Ð Ò‘Ð ÂµÐ Â»Ð Â°Ð ÂµÐ¡â€šÐ¡ÐƒÐ¡Ð� Ð¡â€šÐ Â°Ð Ñ”Ð Â°Ð¡Ð� Ð Â¶Ð Âµ Ð Ò‘Ð Â°Ð¡â€šÐ Â°, Ð Ð…Ð Ñ• Ð Ð† UTC.
	 * @return Ð¡â€šÐ ÂµÐ Ñ”Ð¡Ñ“Ð¡â€°Ð Ñ‘Ð Âµ Ð¡ÐƒÐ¡Ñ“Ð¡â€šÐ Ñ”Ð Ñ‘ Ð Ð† UTC timezone.
	 */
	public static Calendar createCurrentBeginDayInUTC() {
		Calendar now = new GregorianCalendar();

		Calendar result = Calendar.getInstance(TimeZone.getTimeZone(UTC_TIME_ZONE));
		result.set(Calendar.YEAR, now.get(Calendar.YEAR));
		result.set(Calendar.MONTH, now.get(Calendar.MONTH));
		result.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));
		result.set(Calendar.HOUR_OF_DAY, 0);
		result.set(Calendar.MINUTE, 0);
		result.set(Calendar.SECOND, 0);
		result.set(Calendar.MILLISECOND, 0);

		return result;
	}
}
