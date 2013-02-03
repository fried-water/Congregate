package com.mhack.congregate.gui;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mhack.congregate.R;
import com.mhack.congregate.util.Const;
import com.mhack.congregate.util.DataTransfer;
import com.mhack.congregate.util.Globals;
import com.mhack.congregate.util.Utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ViewEvent extends Activity {
	
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.view_event);
	}
	
	public void onResume() { 
		super.onResume();
		
		((TextView)findViewById(R.id.txtEventTitle)).setText(Globals.currentEvent.name);
		((TextView)findViewById(R.id.txtEventDesc)).setText(Globals.currentEvent.description);
		((TextView)findViewById(R.id.txtEventLocTime)).setText(Globals.currentEvent.date + " - " + Globals.currentEvent.location);
		
		
		LinearLayout view = (LinearLayout)findViewById(R.id.layoutMain);
		
		((RadioGroup)view.findViewById(R.id.radioGroup1)).setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId) {
				case R.id.radio_going:
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							Utility.updateStatus(getApplicationContext(), 
									Globals.currentEvent.name,
									Globals.currentEvent.host, 
									Globals.prefs.getString(Const.phoneNumber, ""), 1);
							
						}
					}).start();
					break;
				case R.id.radio_maybe:
						new Thread(new Runnable() {
						
						@Override
						public void run() {
							Utility.updateStatus(getApplicationContext(), 
									Globals.currentEvent.name,
									Globals.currentEvent.host,
									Globals.prefs.getString(Const.phoneNumber, ""), 2);
							
						}
					}).start();
					break;
				case R.id.radio_not_going:
						new Thread(new Runnable() {
						
						@Override
						public void run() {
							Utility.updateStatus(getApplicationContext(), 
									Globals.currentEvent.name,
									Globals.currentEvent.host,
									Globals.prefs.getString(Const.phoneNumber, ""), 3);
						}
					}).start();
					break;
				}
				
			}
		});
		
		switch(Globals.currentEvent.status)
		{
		case 1:
			((RadioButton)view.findViewById(R.id.radio_going)).setChecked(true);
			break;
		case 2:
			((RadioButton)view.findViewById(R.id.radio_maybe)).setChecked(true);
			break;
		case 3:
			((RadioButton)view.findViewById(R.id.radio_not_going)).setChecked(true);
			break;
		}
		
		final ProgressDialog progress = new ProgressDialog(this);
		
		progress.setIndeterminate(true);
		progress.setMessage("Retrieving Information...");
		progress.show();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				JSONObject response = null;
				JSONArray temp = null;
				
				try {
					response = DataTransfer.getJSONResult(getApplicationContext(), Const.url + "message?host="+Globals.prefs.getString(Const.phoneNumber, "")+
							"&name=" + URLEncoder.encode(Globals.currentEvent.host, "utf-8") + "&recipient="+ URLEncoder.encode(Globals.currentEvent.name, "utf-8"));
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(response== null) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {	
							progress.dismiss();
							Toast.makeText(getApplicationContext(), "Error Retreiving Messages.", Toast.LENGTH_LONG).show();
						}
						
					});
					return;
				}
				
				Log.d("JSON MESSAGES", response!= null? response.toString(): "null");
				
				try {
					temp = response.getJSONArray("data");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				final JSONArray messages = temp;
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						LinearLayout mainLayout = (LinearLayout)findViewById(R.id.layoutWallMessages);
						mainLayout.removeAllViews();
						
						View view = null;
						
						for(int i = 0; i < messages.length();i++) {
							try {
								final JSONObject message = (JSONObject)messages.get(i);
								String dateString = Utility.convertDate(message.getString("time"), Const.serverDateFormat, Const.appDateFormat);
								
								Log.d("Message", message.toString());
								view = getLayoutInflater().inflate(R.layout.message_cell, mainLayout, false);
								
								((TextView)view.findViewById(R.id.txtTimeMessage)).setText(Globals.currentEvent.host + " said on " + dateString);
								((TextView)view.findViewById(R.id.txtMessage)).setText(message.getString("message"));
							
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							mainLayout.addView(view);
						}

						progress.dismiss();
					}
				});
				
			}
		}).start();
	}

}
