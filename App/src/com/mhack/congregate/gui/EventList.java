package com.mhack.congregate.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.mhack.congregate.R;
import com.mhack.congregate.dto.EventDTO;
import com.mhack.congregate.util.Const;
import com.mhack.congregate.util.DataTransfer;
import com.mhack.congregate.util.Globals;
import com.mhack.congregate.util.Utility;

public class EventList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.event_list);
		
	}
	
	public void onResume() { 
		super.onResume();
		final ProgressDialog progress = new ProgressDialog(this);
		
		if(Utility.isNetworkAvailable(this))
		{

			progress.setIndeterminate(true);
			progress.setMessage("Retrieving Information...");
			progress.show();
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					JSONObject response;
					JSONArray temp = null;
					
					response = DataTransfer.getJSONResult(getApplicationContext(), Const.url + "event?host="+Globals.prefs.getString(Const.phoneNumber, ""));
					
					if(response== null) {
						runOnUiThread(new Runnable() {

							@Override
							public void run() {	
								progress.dismiss();
								Toast.makeText(getApplicationContext(), "Error Retreiving Events.", Toast.LENGTH_LONG).show();
							}
							
						});
						return;
					}
					
					Log.d("JSON HOSTED EVENTS", response!= null? response.toString(): "null");
					
					try {
						temp = response.getJSONArray("data");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					final JSONArray hostedEvents = temp;
					
					response = DataTransfer.getJSONResult(getApplicationContext(), Const.url + "event?guest="+Globals.prefs.getString(Const.phoneNumber, ""));
					
					Log.d("JSON GUEST EVENTS", response!= null? response.toString(): "null");
					
					try {
						temp = response.getJSONArray("data");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					final JSONArray guestEvents = temp;
					
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							LinearLayout mainLayout = (LinearLayout)findViewById(R.id.layoutParent);
							mainLayout.removeAllViews();
							
							View view;
							
							view = getLayoutInflater().inflate(R.layout.event_label, mainLayout, false);
							((TextView)view.findViewById(R.id.eventHeader)).setText("Upcoming Events");
							mainLayout.addView(view);
							
							if(guestEvents.length() > 0) {
								for(int i = 0; i < guestEvents.length();i++) {
									try {
										final JSONObject event = (JSONObject)guestEvents.get(i);
										
										Log.d("WOO", event.toString());
										view = getLayoutInflater().inflate(R.layout.event_cell_invitation, mainLayout, false);
										
										((TextView)view.findViewById(R.id.txt_event_name)).setText(event.getString("name"));
										((TextView)view.findViewById(R.id.txt_event_details)).setText("On " + Utility.convertDate(event.getString("date"), Const.serverDateFormat, Const.appDateFormat) + " at " + event.getString("location"));
										
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
																		event.getString("name"),
																		event.getString("host"), 
																		Globals.prefs.getString(Const.phoneNumber, ""), 1);
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
																		event.getString("name"),
																		event.getString("host"), 
																		Globals.prefs.getString(Const.phoneNumber, ""), 2);
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
																		event.getString("name"),
																		event.getString("host"), 
																		Globals.prefs.getString(Const.phoneNumber, ""), 3);
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
										
										switch(event.getInt("status"))
										{
										case 0:
											((TextView)view.findViewById(R.id.txt_invitation_from)).setText(event.getString("host") + " has invited you to");
											break;
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
									
										view.setOnClickListener(new View.OnClickListener() {
											
											@Override
											public void onClick(View v) {
												EventDTO eventDTO = new EventDTO();
												try {
													eventDTO.name = event.getString("name");
													eventDTO.description = event.getString("description");
													eventDTO.date = Utility.convertDate(event.getString("date"), Const.serverDateFormat, Const.appDateFormat);
													eventDTO.location = event.getString("location");
													eventDTO.status = event.getInt("status");
													eventDTO.host = event.getString("host");
												} catch (JSONException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												
												Globals.currentEvent = eventDTO;
												Intent intent = new Intent().setClass(EventList.this, ViewEvent.class);
												startActivity(intent);
											}
										});
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									mainLayout.addView(view);
								}
							} else { 
								view = getLayoutInflater().inflate(R.layout.event_none, mainLayout, false);
								mainLayout.addView(view);
								
							}
							
							view = getLayoutInflater().inflate(R.layout.event_label, mainLayout, false);
							((TextView)view.findViewById(R.id.eventHeader)).setText("Manage Events");
							mainLayout.addView(view);
							
							if(hostedEvents.length() > 0) {
								for(int i = 0; i < hostedEvents.length();i++) {
									try {
										final JSONObject event = (JSONObject)hostedEvents.get(i);
										
										Log.d("WEE", event.toString());
										view = getLayoutInflater().inflate(R.layout.event_cell_manage_event, mainLayout, false);
										
										((TextView)view.findViewById(R.id.txt_event_name)).setText(event.getString("name"));
										((TextView)view.findViewById(R.id.txt_event_date)).setText("At " + Utility.convertDate(event.getString("date"), Const.serverDateFormat, Const.appDateFormat));
										((TextView)view.findViewById(R.id.txt_event_confirm_count)).setText(""+event.getInt("confirm_count"));
									
										view.setOnClickListener(new View.OnClickListener() {
											
											@Override
											public void onClick(View v) {
												EventDTO eventDTO = new EventDTO();
												try {
													eventDTO.name = event.getString("name");
													eventDTO.description = event.getString("description");
													eventDTO.date = Utility.convertDate(event.getString("date"), Const.serverDateFormat, Const.appDateFormat);
													eventDTO.location = event.getString("location");
												} catch (JSONException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												
												Globals.currentEvent = eventDTO;
												Intent intent = new Intent().setClass(EventList.this, ManageEvent.class);
												startActivity(intent);
											}
										});
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									
									
									mainLayout.addView(view);
								}
							} else { 
								view = getLayoutInflater().inflate(R.layout.event_none, mainLayout, false);
								mainLayout.addView(view);
							}
							
							progress.dismiss();
						}
					});
					
				}
			}).start();
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
