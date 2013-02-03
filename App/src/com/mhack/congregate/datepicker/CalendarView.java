package com.mhack.congregate.datepicker;

import java.util.Calendar;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.mhack.congregate.R;

/**
 * calendar view.
 * @author Sazonov-adm
 *
 */
public class CalendarView extends LinearLayout {
	
	/**
	 * pager.
	 */
	private ViewPager pager;
	
	/**
	 * adapter.
	 */
	private MonthPagerAdapter adapter;
	
	/**
	 * Ð Ñ™Ð Ñ•Ð Ð…Ð¡ÐƒÐ¡â€šÐ¡Ð‚Ð¡Ñ“Ð Ñ”Ð¡â€šÐ Ñ•Ð¡Ð‚.
	 * 
	 * @param context Ð Ñ”Ð Ñ•Ð Ð…Ð¡â€šÐ ÂµÐ Ñ”Ð¡ÐƒÐ¡â€š
	 */
	public CalendarView(final Context context) {
		this(context, null);
	}

	/**
	 * Ð Ñ™Ð Ñ•Ð Ð…Ð¡ÐƒÐ¡â€šÐ¡Ð‚Ð¡Ñ“Ð Ñ”Ð¡â€šÐ Ñ•Ð¡Ð‚.
	 * 
	 * @param context Ð Ñ”Ð Ñ•Ð Ð…Ð¡â€šÐ ÂµÐ Ñ”Ð¡ÐƒÐ¡â€š
	 * @param attrs Ð Â°Ð¡â€šÐ¡Ð‚Ð Ñ‘Ð Â±Ð¡Ñ“Ð¡â€šÐ¡â€¹
	 */
	public CalendarView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.calendar_view, this, true);
		//FIXME Ð’Ð¾Ð¾Ð±Ñ‰Ðµ, Ð¸Ñ�ÐºÐ°Ñ‚ÑŒ Ñ�Ñ€Ð°Ð·Ñƒ Ð½ÐµÐ»ÑŒÐ·Ñ�. ÐœÐ¾Ð¶ÐµÑ‚ Ð±Ñ‹Ñ‚ÑŒ null. Ð�ÑƒÐ¶Ð½Ð¾ Ð¸Ñ�ÐºÐ°Ñ‚ÑŒ Ð² onFinishInflate.
		pager = ((ViewPager) findViewById(R.id.calendar_view_pager));
		adapter = new MonthPagerAdapter(inflater, pager);
		pager.setAdapter(adapter);
		pager.setCurrentItem(MonthPagerAdapter.INFINITE / 2);
	}
	
	/**
	 * Ð â€”Ð Â°Ð¡Ð‚Ð ÂµÐ Ñ–Ð Ñ‘Ð¡ÐƒÐ¡â€šÐ¡Ð‚Ð Ñ‘Ð¡Ð‚Ð Ñ•Ð Ð†Ð Â°Ð¡â€šÐ¡ÐŠ Ð Ð…Ð Ñ•Ð Ð†Ð¡â€¹Ð â„– Ð Ð…Ð Â°Ð Â±Ð Â»Ð¡Ð‹Ð Ò‘Ð Â°Ð¡â€šÐ ÂµÐ Â»Ð¡ÐŠ.
	 * 
	 * @param observer Ð ÑœÐ Â°Ð Â±Ð Â»Ð¡Ð‹Ð Ò‘Ð Â°Ð¡â€šÐ ÂµÐ Â»Ð¡ÐŠ.
	 */
	public final void registerCalendarDatePickObserver(final CalendarDatePick observer) {
		((MonthPagerAdapter) pager.getAdapter()).setPickObserver(observer);
	}
	
	/**
	 * set current day.
	 * @param month month.
	 */
	public final void setCurrentDay(Calendar currentDay) {
		adapter.setCurrentDay(currentDay);
	}
	
	/**
	 * set current month.
	 * @param month month.
	 */
	public final void setMonth(Calendar month) {
		adapter.setMonth(month);
	}

	/**
	 * get current.
	 * @return month month.
	 */
	public final Calendar getMonth() {
		return adapter.getMonth();
	}
}
