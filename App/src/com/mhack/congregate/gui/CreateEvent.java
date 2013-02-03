package com.mhack.congregate.gui;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mhack.congregate.R;
import com.mhack.congregate.util.Globals;

public class CreateEvent extends Activity {

	private int year, month, day, hour, min;
	private final int DATE_DIALOGUE = 34, TIME_DIALOGUE = 35;
	private TextView txtDateView, txtTimeView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_event);

		txtDateView = (TextView) findViewById(R.id.txtDateChosen);
		txtTimeView = (TextView) findViewById(R.id.txtTimeChosen);
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
				//create and send invites here!!! 
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
