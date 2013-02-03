package com.mhack.congregate.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mhack.congregate.R;

public class EventList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.event_list);
		
	}
	
	public void onResume() { 
		super.onResume();
		
		LinearLayout mainLayout = (LinearLayout)findViewById(R.id.layoutParent);
		
		View view;
		
		view = getLayoutInflater().inflate(R.layout.event_label, mainLayout, false);
		((TextView)view.findViewById(R.id.eventHeader)).setText("Upcoming Events");
		mainLayout.addView(view);
		
		for(int j = 0; j < 1; j++)
		{
			view = getLayoutInflater().inflate(R.layout.event_cell_invitation, mainLayout, false);
			
			view.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
				}
			});
			
			mainLayout.addView(view);
		}
		
		for(int j = 0; j < 2; j++)
		{
			view = getLayoutInflater().inflate(R.layout.event_cell_event, mainLayout, false);
			mainLayout.addView(view);
		}
		
		view = getLayoutInflater().inflate(R.layout.event_label, mainLayout, false);
		((TextView)view.findViewById(R.id.eventHeader)).setText("Manage Events");
		mainLayout.addView(view);
		
		for(int j = 0; j < 2; j++)
		{
			view = getLayoutInflater().inflate(R.layout.event_cell_manage_event, mainLayout, false);
			mainLayout.addView(view);
		}
		
		Button btnCreateEvent = (Button) findViewById(R.id.btnCreateEvent);
		Button btnCreateGroup = (Button) findViewById(R.id.btnCreateGroup);
		
		btnCreateEvent.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent().setClass(EventList.this, CreateEvent.class);
				startActivity(intent);
			}
		});
		
		btnCreateGroup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
	}
	
	public void chooseDate() { 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_list, menu);
		return true;
	}

}
