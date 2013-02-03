package com.mhack.congregate.datepicker;

import android.content.Context;

/**
 * locale helper.
 * @author Sazonov-adm
 *
 */
class LocaleHelper {
	/**
	 * is current locale - russian.
	 * @param context context.
	 * @return is russian.
	 */
	public static boolean isRussianLocale(Context context) {
		return context.getResources().getConfiguration().locale.getLanguage().equals("ru");
	}
}
