package com.mhack.congregate.gui;

import com.mhack.congregate.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class ViewEvent extends Activity {
	
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.view_event);
	}
	
	public void onResume() { 
		super.onResume();
		
		
	}

}
