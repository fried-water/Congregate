package com.example.congregate;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_list);
		
		LinearLayout mainLayout = (LinearLayout)findViewById(R.id.layoutParent);
		
		View view;
		
		view = getLayoutInflater().inflate(R.layout.event_label, mainLayout, false);
		((TextView)view.findViewById(R.id.eventHeader)).setText("INVITATIONS");
		mainLayout.addView(view);
		
		for(int j = 0; j < 3; j++)
		{
			view = getLayoutInflater().inflate(R.layout.event_cell, mainLayout, false);
			((TextView)view.findViewById(R.id.eventLabel)).setText("Event "+j);
			mainLayout.addView(view);
		}
		
		view = getLayoutInflater().inflate(R.layout.event_label, mainLayout, false);
		((TextView)view.findViewById(R.id.eventHeader)).setText("MY EVENTS");
		mainLayout.addView(view);
		
		for(int j = 0; j < 3; j++)
		{
			view = getLayoutInflater().inflate(R.layout.event_cell, mainLayout, false);
			((TextView)view.findViewById(R.id.eventLabel)).setText("Event "+j);
			mainLayout.addView(view);
		}
		
		view = getLayoutInflater().inflate(R.layout.event_label, mainLayout, false);
		((TextView)view.findViewById(R.id.eventHeader)).setText("MANAGE EVENTS");
		mainLayout.addView(view);
		
		for(int j = 0; j < 3; j++)
		{
			view = getLayoutInflater().inflate(R.layout.event_cell, mainLayout, false);
			((TextView)view.findViewById(R.id.eventLabel)).setText("Event "+j);
			mainLayout.addView(view);
		}
		
		
		view = getLayoutInflater().inflate(R.layout.create_button, mainLayout, false);
		((Button)view.findViewById(R.id.button)).setText("Create Event");
		mainLayout.addView(view);
		
		view = getLayoutInflater().inflate(R.layout.create_button, mainLayout, false);
		((Button)view.findViewById(R.id.button)).setText("Create Group");
		mainLayout.addView(view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_list, menu);
		return true;
	}

}
