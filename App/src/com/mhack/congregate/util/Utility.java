package com.mhack.congregate.util;

import android.app.Activity;

public class Utility {

	
	public static void init(Activity act) { 
    	Globals.prefs = act.getSharedPreferences("MyPrefs", act.MODE_PRIVATE); 
    	Globals.prefEdit = Globals.prefs.edit();
	}
	
	public static String stripExtraCharsFromPhone(String phoneNum) { 
		
		phoneNum = phoneNum.replace("-", "");
		phoneNum = phoneNum.replace("(", "");
		phoneNum = phoneNum.replace(")", "");
		phoneNum = phoneNum.replace("+", "");
		phoneNum = phoneNum.replace(" ", "");
		phoneNum = phoneNum.replaceAll("[a-zA-Z]*", "");
		
		if (phoneNum.charAt(0) == '1')
			phoneNum = phoneNum.substring(1);
		
		return phoneNum;
	}
}
