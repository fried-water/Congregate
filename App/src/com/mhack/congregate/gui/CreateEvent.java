package com.mhack.congregate.gui;

import com.mhack.congregate.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CreateEvent extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_event);
	}

	@Override
	public void onResume() { 
		super.onResume();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_event, menu);
		return true;
	}
	
}