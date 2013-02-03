package com.mhack.congregate.gui;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mhack.congregate.R;
import com.mhack.congregate.dto.EventDTO;
import com.mhack.congregate.util.Const;
import com.mhack.congregate.util.DataTransfer;
import com.mhack.congregate.util.Globals;
import com.mhack.congregate.util.Utility;

public class ManageEvent extends Activity {

	private LinearLayout messageGroup, inviteFriends, viewFriendStatus;

	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.manage_event);

		messageGroup = (LinearLayout) findViewById(R.id.layout_message_group);
		inviteFriends = (LinearLayout) findViewById(R.id.layout_invite_people);
		viewFriendStatus = (LinearLayout) findViewById(R.id.layout_people_coming);
	}

	public void onResume() {
		super.onResume();
		final ProgressDialog progress = new ProgressDialog(this);
		
		((TextView)findViewById(R.id.txtEventTitle)).setText("Manage " + Globals.currentEvent.name);
		((TextView)findViewById(R.id.txtEventDesc)).setText(Globals.currentEvent.description);
		((TextView)findViewById(R.id.txtEventLocTime)).setText(Globals.currentEvent.date + " - " + Globals.currentEvent.location);
		
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
						LinearLayout mainLayout = (LinearLayout)findViewById(R.id.layout_message_wall);
						mainLayout.removeAllViews();
						
						View view = null;
						
						for(int i = 0; i < messages.length();i++) {
							try {
								final JSONObject message = (JSONObject)messages.get(i);
								String dateString = Utility.convertDate(message.getString("time"), Const.serverDateFormat, Const.appDateFormat);
								
								Log.d("Message", message.toString());
								view = getLayoutInflater().inflate(R.layout.message_cell, mainLayout, false);
								
								((TextView)view.findViewById(R.id.txtTimeMessage)).setText(message.getString("You said on " + dateString));
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
		
		messageGroup.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				messageDialogue();
			}
		});

		inviteFriends.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				//TODO: add to globals thing of contacts added the friends already attending
				Intent intent = new Intent().setClass(ManageEvent.this, ContactSelection.class);
				startActivity(intent);
			}
		});

		viewFriendStatus.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent().setClass(ManageEvent.this, ViewFriendStatusForEvent.class);
				intent.putExtra(Const.eventName, Globals.currentEvent.name);
				intent.putExtra(Const.hostId, Globals.prefs.getString(Const.phoneNumber, ""));
				startActivity(intent);
			}
		});
	}

	public void messageDialogue() {
		LayoutInflater factory = LayoutInflater.from(this);
		final View textEntryView = factory.inflate(R.layout.dialogue_with_text, null);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setTitle("Message for group");
		builder.setView(textEntryView);
		builder.setPositiveButton("Send",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
									int whichButton) {
					
					EditText txtMesssage = (EditText)textEntryView.findViewById(R.id.txtMessage);
					if (txtMesssage.getText().toString().length() > 0) { 
						//TODO: send messages to everyone
					} else { 
						Toast.makeText(getApplicationContext(), "You have not entered a message.", Toast.LENGTH_LONG).show();
					}
				}
			});
		builder.setNegativeButton("Cancel",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
									int whichButton) {

					dialog.dismiss();
				}
			});
		
		AlertDialog alert = builder.create();
		alert.show();
	}

}
