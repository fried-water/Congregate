package com.mhack.congregate.gui;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.mhack.congregate.R;
import com.mhack.congregate.util.Const;
import com.mhack.congregate.util.DataTransfer;
import com.mhack.congregate.util.Globals;
import com.mhack.congregate.util.Utility;

public class ViewFriendStatusForEvent extends Activity {

	private String eventName = "";
	private String hostID = "";
	
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.friend_status);
		
		if (getIntent()!= null && getIntent().getExtras() != null && getIntent().getExtras().containsKey(Const.eventName)) { 
			eventName = getIntent().getExtras().getString(Const.eventName);
		}
		
		if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey(Const.hostId)) { 
			hostID = getIntent().getExtras().getString(Const.hostId);
		}
	}
	
	public void onResume() { 
		super.onResume();
		
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
					response = DataTransfer.getJSONResult(getApplicationContext(), Const.url + "invite?host="+Globals.prefs.getString(Const.phoneNumber, "")+
							"&name=" + URLEncoder.encode(Globals.currentEvent.name, "utf-8"));
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(response== null) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {	
							progress.dismiss();
							Toast.makeText(getApplicationContext(), "Error Retreiving Guests.", Toast.LENGTH_LONG).show();
						}
						
					});
					return;
				}
				
				Log.d("JSON GUESTS", response!= null? response.toString(): "null");
				
				try {
					temp = response.getJSONArray("data");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				final JSONArray guests = temp;
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						LinearLayout mainLayout = (LinearLayout)findViewById(R.id.layout_friend_status);
						mainLayout.removeAllViews();
						
						View view = null;
						
						for(int i = 0; i < guests.length();i++) {
							try {
								final JSONObject guest = (JSONObject)guests.get(i);

								Log.d("Message", guest.toString());
								view = getLayoutInflater().inflate(R.layout.friend_status_cell, mainLayout, false);
								
								((TextView)view.findViewById(R.id.txtContactName)).setText(Utility.getNameFromNumber(guest.getString("guest")));
							
								((RadioGroup)view.findViewById(R.id.radioGroup1)).setOnCheckedChangeListener(new OnCheckedChangeListener() {
									
									@Override
									public void onCheckedChanged(RadioGroup group, int checkedId) {
										switch(checkedId) {
										case R.id.radio_going:
											new Thread(new Runnable() {
												
												@Override
												public void run() {
													try {
														Utility.updateStatus(getApplicationContext(), 
																Globals.currentEvent.name,
																Globals.prefs.getString(Const.phoneNumber, ""), 
																guest.getString("guest"), 1);
													} catch (JSONException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
													
												}
											}).start();
											break;
										case R.id.radio_maybe:
												new Thread(new Runnable() {
												
												@Override
												public void run() {
													try {
														Utility.updateStatus(getApplicationContext(), 
																Globals.currentEvent.name,
																Globals.prefs.getString(Const.phoneNumber, ""), 
																guest.getString("guest"), 2);
													} catch (JSONException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
													
												}
											}).start();
											break;
										case R.id.radio_not_going:
												new Thread(new Runnable() {
												
												@Override
												public void run() {
													try {
														Utility.updateStatus(getApplicationContext(), 
																Globals.currentEvent.name,
																Globals.prefs.getString(Const.phoneNumber, ""), 
																guest.getString("guest"), 3);
													} catch (JSONException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
													
												}
											}).start();
											break;
										}
										
									}
								});
								
								switch(guest.getInt("status"))
								{
								case 1:
									((RadioButton)view.findViewById(R.id.radio_going)).setChecked(true);
									Log.d("RADIO", "SET 1");
									break;
								case 2:
									((RadioButton)view.findViewById(R.id.radio_maybe)).setChecked(true);
									Log.d("RADIO", "SET 2");
									break;
								case 3:
									((RadioButton)view.findViewById(R.id.radio_not_going)).setChecked(true);
									Log.d("RADIO", "SET 3");
									break;
								}
								
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
