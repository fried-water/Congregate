package com.mhack.congregate.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mhack.congregate.R;
import com.mhack.congregate.util.Const;

public class ManageEvent extends Activity {

	private LinearLayout messageGroup, inviteFriends, viewFriendStatus;

	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.create_event);

		messageGroup = (LinearLayout) findViewById(R.id.layout_message_group);
		inviteFriends = (LinearLayout) findViewById(R.id.layout_invite_people);
		viewFriendStatus = (LinearLayout) findViewById(R.id.layout_people_coming);
	}

	public void onResume() {
		super.onResume();

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
				intent.putExtra(Const.eventName, "ADD EVENT NAME HERE");
				intent.putExtra(Const.hostId, "ADD HOST ID HERE");
				startActivity(intent);
			}
		});
	}

	public void messageDialogue() {
		LayoutInflater factory = LayoutInflater.from(this);
		final View textEntryView = factory.inflate(R.layout.dialogue_with_text, null);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
		
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
	}

}
