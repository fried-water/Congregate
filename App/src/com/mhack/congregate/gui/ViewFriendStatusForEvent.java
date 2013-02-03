package com.mhack.congregate.gui;

import com.mhack.congregate.R;
import com.mhack.congregate.util.Const;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class ViewFriendStatusForEvent extends Activity {

	private String eventName = "";
	private String hostID = "";
	
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.contact_cell);
		
		if (getIntent()!= null && getIntent().getExtras() != null && getIntent().getExtras().containsKey(Const.eventName)) { 
			eventName = getIntent().getExtras().getString(Const.eventName);
		}
		
		if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey(Const.hostId)) { 
			hostID = getIntent().getExtras().getString(Const.hostId);
		}
	}
	
	public void onResume() { 
		super.onResume();
		
		
	}
}
