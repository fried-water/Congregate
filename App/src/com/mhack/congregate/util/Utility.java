package com.mhack.congregate.util;

import android.app.Activity;

public class Utility {

	
	public static void init(Activity act) { 
    	Globals.prefs = act.getSharedPreferences("MyPrefs", act.MODE_PRIVATE); 
    	Globals.prefEdit = Globals.prefs.edit();
	}
}
