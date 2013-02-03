package com.mhack.congregate.datepicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mhack.congregate.R;

/**
 * one month view.
 * @author Sazonov-adm
 * 
 */
public class CalendarMonthView extends LinearLayout {

	/**
	 * expand change observer.
	 */
	private CalendarDatePick mObserver;

	/**
	 * date with which view was init.
	 */
	private Calendar mInitialMonth;
	/**
	 * adapter.
	 */
	private CalendarAdapter mDaysAdapter;

	/**
	 * context.
	 */
	private Context mContext;

	/**
	 * ÐšÐ¾Ð½Ñ�Ñ‚Ñ€ÑƒÐºÑ‚Ð¾Ñ€.
	 * 
	 * @param context ÐºÐ¾Ð½Ñ‚ÐµÐºÑ�Ñ‚
	 */
	public CalendarMonthView(final Context context) {
		this(context, null);
	}

	/**
	 * ÐšÐ¾Ð½Ñ�Ñ‚Ñ€ÑƒÐºÑ‚Ð¾Ñ€.
	 * 
	 * @param context ÐºÐ¾Ð½Ñ‚ÐµÐºÑ�Ñ‚
	 * @param attrs Ð°Ñ‚Ñ€Ð¸Ð±ÑƒÑ‚Ñ‹
	 */
	public CalendarMonthView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.calendar, this, true);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mInitialMonth = Calendar.getInstance();

		mDaysAdapter = new CalendarAdapter(mContext, mInitialMonth);

		GridView gridview = (GridView) findViewById(R.id.calendar_days_gridview);
		gridview.setAdapter(mDaysAdapter);

		initDayCaptions(mContext);
		initMonthCaption();
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				if (v.getTag() != null) {
					if (mObserver != null) {
						mObserver.onDatePicked((CalendarAdapter.DayCell) v.getTag());
					}
				}
			}
		});
	}
	
	/**
	 * set current.
	 * @param month month.
	 */
	public final void setCurrentDay(Calendar currentDay) {
		//mInitialMonth = month;
		mDaysAdapter.setCurrentDay(currentDay);
		refreshCalendar();
	}
	
	/**
	 * set current.
	 * @param month month.
	 */
	public final void setMonth(Calendar month) {
		mInitialMonth = month;
		mDaysAdapter.setMonth(month);
		refreshCalendar();
	}

	/**
	 * @return current month.
	 */
	public final Calendar getMonth() {
		return mInitialMonth;
	}

	/**
	 * init.
	 */
	private void initMonthCaption() {

		TextView title = (TextView) findViewById(R.id.title);
		String month;
		if (LocaleHelper.isRussianLocale(mContext)) {
			String[] months = mContext.getResources().getStringArray(R.array.months_long);
			month = months[mInitialMonth.get(Calendar.MONTH)];
		} else {
			month = android.text.format.DateFormat.format("MMMM", mInitialMonth).toString();
		}
		if (month.length() > 1) {
			// make first letter in upper case
			month = month.substring(0, 1).toUpperCase() + month.substring(1);
		}
		title.setText(String.format("%s %s", month, android.text.format.DateFormat.format("yyyy", mInitialMonth)));
	}

	/**
	 * @param context context.
	 */
	private void initDayCaptions(final Context context) {
		final int week = 7;
		String[] dayCaptions = new String[week];
		SimpleDateFormat weekDateFormat = new SimpleDateFormat("EE");
		Calendar weekDay = DateHelper.createCurrentBeginDayCalendar();

		if (DateHelper.isMondayFirst()) {
			weekDay.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		} else {
			weekDay.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		}

		for (int i = 0; i < week; i++) {
			dayCaptions[i] = weekDateFormat.format(weekDay.getTime());
			DateHelper.increment(weekDay);
		}

		GridView captionsGridView = (GridView) findViewById(R.id.calendar_captions_gridview);
		captionsGridView.setAdapter(new ArrayAdapter<String>(context, R.layout.calendar_caption_item, R.id.calendar_caption_date, dayCaptions));
	}

	/**
	 * refresh view.
	 */
	public final void refreshCalendar() {
		mDaysAdapter.refreshDays();
		mDaysAdapter.notifyDataSetChanged();
		initMonthCaption();
	}

	/**
	 * Ð—Ð°Ñ€ÐµÐ³Ð¸Ñ�Ñ‚Ñ€Ð¸Ñ€Ð¾Ð²Ð°Ñ‚ÑŒ Ð½Ð¾Ð²Ñ‹Ð¹ Ð½Ð°Ð±Ð»ÑŽÐ´Ð°Ñ‚ÐµÐ»ÑŒ.
	 * 
	 * @param observer Ð�Ð°Ð±Ð»ÑŽÐ´Ð°Ñ‚ÐµÐ»ÑŒ.
	 */
	public final void registerCalendarDatePickObserver(final CalendarDatePick observer) {
		this.mObserver = observer;
	}

	/**
	 * Ð Ð°Ð·Ñ€ÐµÐ³Ð¸Ñ�Ñ‚Ñ€Ð¸Ñ€Ð¾Ð²Ð°Ñ‚ÑŒ Ð½Ð¾Ð²Ñ‹Ð¹ Ð½Ð°Ð±Ð»ÑŽÐ´Ð°Ñ‚ÐµÐ»ÑŒ.
	 * 
	 * @param observer Ð�Ð°Ð±Ð»ÑŽÐ´Ð°Ñ‚ÐµÐ»ÑŒ.
	 */
	public final void unregisterCalendarDatePickObserver() {
		this.mObserver = null;
	}
}
