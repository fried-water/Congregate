package com.mhack.congregate.datepicker;

import com.mhack.congregate.datepicker.CalendarAdapter.DayCell;

/**
 * listener.
 * @author Sazonov-adm
 *
 */
public interface CalendarDatePick {
	/**
	 * @param dayCell cell.
	 */
	void onDatePicked(com.mhack.congregate.datepicker.CalendarAdapter.DayCell dayCell);
}
