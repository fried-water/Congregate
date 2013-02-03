package com.mhack.congregate.gui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mhack.congregate.R;
import com.mhack.congregate.dto.ContactDTO;
import com.mhack.congregate.util.Const;
import com.mhack.congregate.util.DataTransfer;
import com.mhack.congregate.util.Globals;
import com.mhack.congregate.util.Utility;

public class CreateEvent extends Activity {

	private int year, month, day, hour, min;
	private final int DATE_DIALOGUE = 34, TIME_DIALOGUE = 35;
	private TextView txtDateView, txtTimeView;
	private EditText name, location, description; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_event);

		txtDateView = (TextView) findViewById(R.id.txtDateChosen);
		txtTimeView = (TextView) findViewById(R.id.txtTimeChosen);
		
		name = (EditText) findViewById(R.id.txtEventName);
		location = (EditText) findViewById(R.id.txtEvenLocation);
		description = (EditText) findViewById(R.id.txtEventDesc);
	}

	@Override
	public void onResume() {
		super.onResume();

		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		Button btnSetDate = (Button) findViewById(R.id.btnChooseDate);
		btnSetDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOGUE);
			}
		});
		
		Button btnSetTime = (Button) findViewById(R.id.btnChooseTime);
		btnSetTime.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				showDialog(TIME_DIALOGUE);
			}
		});
		
		Button btnInviteContacts = (Button) findViewById(R.id.btnChooseContacts);
		btnInviteContacts.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent().setClass(CreateEvent.this, ContactSelection.class);
				startActivity(intent);
			}
		});
		
		Button btnSendInvites = (Button) findViewById(R.id.btnSubmitEvent);
		btnSendInvites.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final ProgressDialog progress = new ProgressDialog(CreateEvent.this);
				progress.setIndeterminate(true);
				progress.setMessage("Retrieving Information...");
				
				progress.show();
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						String eventName = name.getText().toString();
						String location = CreateEvent.this.location.getText().toString();
						String dateString;
						
						
						ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
						
						
						
						Calendar date = Calendar.getInstance();
						
						date.set(year, month, day, hour, min);
						
						dateString = Utility.convertDate(date.getTime(), Const.serverDateFormat);
						
						JSONObject eventJSON = new JSONObject();
						
						try {
							eventJSON.put("name", eventName);
							eventJSON.put("location", location);
							eventJSON.put("description", description.getText().toString());
							eventJSON.put("owner", Globals.prefs.getString(Const.phoneNumber, ""));
							eventJSON.put("date", dateString);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Log.d("CREATE EVENT JSON", eventJSON.toString());
						
						params.add(new BasicNameValuePair("json", eventJSON.toString()));
						
						JSONObject response = DataTransfer.postJSONResult(getApplicationContext(), Const.url+"event", params);
						
						if(!DataTransfer.verifyEMPStatus(response))
						{
							Toast.makeText(getApplicationContext(), "Error Creating Event", Toast.LENGTH_LONG).show();
							runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									progress.dismiss();
								}
							});
							
							return;
						}
						
						for(final ContactDTO c : Globals.contactsForEvent) {
							JSONObject inviteJSON = new JSONObject();
							params = new ArrayList<NameValuePair>();
							
							try {
								inviteJSON.put("event_name", eventName);
								inviteJSON.put("guest", c.phoneNumber);
								inviteJSON.put("owner", Globals.prefs.getString(Const.phoneNumber, ""));	
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							Log.d("CREATE INVITE JSON", inviteJSON.toString());
							
							params.add(new BasicNameValuePair("json", inviteJSON.toString()));
							
							response = DataTransfer.postJSONResult(getApplicationContext(), Const.url+"invite", params);
							
							if(!DataTransfer.verifyEMPStatus(response))
							{
								runOnUiThread(new Runnable() {
									
									@Override
									public void run() {
										Toast.makeText(getApplicationContext(), "Error Inviting " + c.name, Toast.LENGTH_LONG).show();
									}
								});
							}
							
							response = DataTransfer.getJSONResult(getApplicationContext(), Const.url+"register?number="+c.phoneNumber);
							
							if(!DataTransfer.verifyEMPStatus(response))
							{
								runOnUiThread(new Runnable() {
									
									@Override
									public void run() {
										Toast.makeText(getApplicationContext(), "Error Checking " + c.name, Toast.LENGTH_LONG).show();
									}
								});
							}
							
							try {
								if(response.getString("is_registered").equals("false")) {
									Utility.sendSMS(c.phoneNumber, "Hey " + c.name + ", wanna come to " + eventName + " on " + 
								Utility.convertDate(date.getTime(), Const.appDateFormat) + " at " + location + "?");
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								progress.dismiss();
							}
						});
						
						finish();
						
					}
				}).start();
				
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOGUE:
			// set date picker as current date
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);

		case TIME_DIALOGUE:
			return new TimePickerDialog(this, timePickerListener, hour, min,
					false);
		}
		return null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.create_event, menu);
		return true;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			
			txtDateView.setText("Date chosen: " + month + "/" + day + "/" + year);
		}
	};

	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			hour = hourOfDay;
			min = minute;
			
			String amPm = "AM";
			
			if (hour>= 0 && hour <= 11) { 
				amPm = "AM";
			} else { 
				amPm = "PM";
			}
			
			if (hour == 0) { 
				hour = 12;
			}
			
			if (hour > 12) { 
				hour = hour % 12;
			}
			
			txtTimeView.setText("Time chosen: " + hour + ":" + (min < 10 ? "0" + min : min) + " " + amPm);
		}
	};

	public void onDestroy() { 
		super.onDestroy();
		
		Globals.contactsForEvent.clear();
	}
}
