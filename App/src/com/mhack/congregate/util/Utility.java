package com.mhack.congregate.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;

import com.mhack.congregate.dto.ContactDTO;

public class Utility {

	public static void init(Activity act) {
		Globals.prefs = act.getSharedPreferences("MyPrefs", act.MODE_PRIVATE);
		Globals.prefEdit = Globals.prefs.edit();
	}

	public static boolean isNetworkAvailable(Context ctx) {
		ConnectivityManager cm = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
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

	public static String convertDate(String dateString, String srcFormat,
			String destFormat) {
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

		if (phoneNum == null || phoneNum.length() == 0) {
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
	
	

	public static void fillContactsList2(Activity act) {

		/**
		 * Fill contact DTO with information for adapter!
		 */
		ContentResolver cr = act.getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);

		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String id = cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

				if (Integer
						.parseInt(cur.getString(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					Cursor pCur = cr.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = ?", new String[] { id }, null);
					while (pCur.moveToNext()) {

						ContactDTO c = new ContactDTO();
						c.phoneNumber = Utility.stripExtraCharsFromPhone(pCur.getString(26));
						c.name = name;

						if (c.phoneNumber.length() == 10)
							Globals.allContactsInAddressBook.add(c);
					}
					pCur.close();
				}
			}
		}
		
		sortListAndRemoveDups();
	}

	public static void fillContactsList(Activity act) {

		Globals.allContactsInAddressBook.clear();

		String[] projection = new String[] { People.NAME, People.NUMBER };

		Cursor c = act.getContentResolver().query(People.CONTENT_URI,
				projection, null, null, People.NAME + " ASC");
		c.moveToFirst();
		int nameCol = c.getColumnIndex(People.NAME);
		int numCol = c.getColumnIndex(People.NUMBER);

		int nContacts = c.getCount();
		Log.d("", "== n contacts is: " + nContacts);

		do {
			ContactDTO contact = new ContactDTO();
			contact.name = c.getString(nameCol);
			contact.phoneNumber = Utility.stripExtraCharsFromPhone(c
					.getString(numCol));

			if (contact.phoneNumber.length() == 10) {
				Log.d("", "== number accepted is: " + contact.phoneNumber);
			} else {
				Log.d("", "== number rejected was: " + contact.phoneNumber);
			}

			if (contact.phoneNumber.length() == 10)
				Globals.allContactsInAddressBook.add(contact);

		} while (c.moveToNext());

		Log.d("",
				"== number contacts: "
						+ Globals.allContactsInAddressBook.size());

		sortListAndRemoveDups();
	}

	public static void sortListAndRemoveDups() {

		ArrayList<ContactDTO> dupsRemoved = new ArrayList<ContactDTO>();

		for (int i = 0; i < Globals.allContactsInAddressBook.size(); i++) {

			boolean foundDup = false;
			for (int j = 0; j < dupsRemoved.size(); j++) {
				if (Globals.allContactsInAddressBook.get(i).phoneNumber
						.equals(dupsRemoved.get(j).phoneNumber)) {
					foundDup = true;
				}
			}

			if (!foundDup)
				dupsRemoved.add(Globals.allContactsInAddressBook.get(i));
		}

		Globals.allContactsInAddressBook = dupsRemoved;

		ContactDTO leslie = new ContactDTO();
		leslie.name = "Lorrie Cheng";
		leslie.phoneNumber = "6479937121";
		leslie.selected = false;

		Globals.allContactsInAddressBook.add(leslie);

		Collections.sort(Globals.allContactsInAddressBook,
				new ContactComparator());
	}

	public static String getNameFromNumber(String number) {

		for (ContactDTO contact : Globals.allContactsInAddressBook) {
			String num = contact.phoneNumber;
			num = Utility.stripExtraCharsFromPhone(num);

			if (num.equalsIgnoreCase(number))
				return contact.name;
		}

		return "";
	}

	public static String numPeopleComing() {

		int count = 0;
		for (ContactDTO contact : Globals.allContactsInAddressBook) {
			if (contact.selected)
				count++;
		}

		return count + "";

	}

	public static boolean isRegistered(Context ctx, String phoneNumber) {
		JSONObject response = DataTransfer.getJSONResult(ctx, Const.url
				+ "register?number=" + phoneNumber);

		try {
			return response.getString("is_registered").equals("true");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	public static JSONObject updateStatus(Context ctx, String eventName,
			String eventHost, String guest, int status) {
		JSONObject statusJSON = new JSONObject(), response;
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		try {
			statusJSON.put("event_name", eventName);
			statusJSON.put("owner", eventHost);
			statusJSON.put("guest", guest);
			statusJSON.put("status", status);

			params.add(new BasicNameValuePair("json", statusJSON.toString()));

			response = DataTransfer.postJSONResult(ctx, Const.url + "invite",
					params);
			Log.d("UPDATE STATUS", response.toString());

			return response;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static void clearContactsInParty() {

		for (ContactDTO c : Globals.allContactsInAddressBook) {
			c.selected = false;
		}
	}

}
