package com.mhack.congregate.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.SmsManager;

public class Utility {

	
	public static void init(Activity act) { 
    	Globals.prefs = act.getSharedPreferences("MyPrefs", act.MODE_PRIVATE); 
    	Globals.prefEdit = Globals.prefs.edit();
	}
	
	public static boolean isNetworkAvailable(Context ctx) {
	    ConnectivityManager cm = (ConnectivityManager) 
	    		ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
	    // if no network is available networkInfo will be null
	    // otherwise check if we are connected
	    if (networkInfo != null && networkInfo.isConnected()) {
	        return true;
	    }
	    return false;
	}
	
	public static String readStream(InputStream in) {
		StringBuilder contents = new StringBuilder();
		
		BufferedReader reader = null;
		try {
		    reader = new BufferedReader(new InputStreamReader(in));
		    String line = "";
		    while ((line = reader.readLine()) != null) {
		    	contents.append(line);
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    if (reader != null) {
		        try {
		            reader.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}
		
		return contents.toString();
	}
	
	public static String convertDate(Date date, String format) {
		SimpleDateFormat newDt = new SimpleDateFormat(format);
		
		return newDt.format(date);
	}
	
	public static String convertDate(String dateString, String srcFormat, String destFormat) {
		String format = "hh:mma dd/MM/yyyy";
		
		SimpleDateFormat dt = new SimpleDateFormat(format);
		Date date = null;
		
		try {
			date = dt.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		SimpleDateFormat newDt = new SimpleDateFormat("MMM d @ h:mma");
		
		return newDt.format(date);
	}
	
	public static String stripExtraCharsFromPhone(String phoneNum) { 
		
		if(phoneNum == null || phoneNum.length() == 0) {
			return "";
		}
		
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
	
	public static void sendSMS(String phoneNumber, String message) {
	    SmsManager smsManager = SmsManager.getDefault();
	    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
	}
}
