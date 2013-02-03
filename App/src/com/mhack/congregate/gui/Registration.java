package com.mhack.congregate.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.mhack.congregate.R;
import com.mhack.congregate.util.Const;
import com.mhack.congregate.util.Globals;

public class Registration extends Activity {

	private EditText txtNumber, txtName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.registration);

		txtNumber = (EditText) findViewById(R.id.txtUserNumber);
		txtName = (EditText) findViewById(R.id.txtUserName);
	}

	public void onResume() {
		super.onResume();

		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 

		Button btnRegister = (Button) findViewById(R.id.btnRegister);
		Log.d("", "== btn register is: " + btnRegister);
		
		btnRegister.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				String num = txtNumber.getText().toString();
				String name = txtName.getText().toString();
				
				if ("".equalsIgnoreCase(num)) { 
					launchDialogue("Please enter a valid 10 digit phone number (no hyphens, spaces, or dots).");
				} else if (num.length() < 10) { 
					launchDialogue("Your phone number must be at least 10 digits.");
				} else if ("".equalsIgnoreCase(name)) { 
					launchDialogue("Please enter your name so your friends know who is contacting them about an event.");
				} else { 
					
					Globals.prefEdit.putString(Const.phoneNumber, num);
					Globals.prefEdit.putString(Const.name, name);
					Globals.prefEdit.commit();
					
					Intent intent = new Intent().setClass(Registration.this,
							EventList.class);
					startActivity(intent);
				}
			}
		});
	}

	public void launchDialogue(String message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getBaseContext());

		// set title
		alertDialogBuilder.setTitle("Error");

		// set dialog message
		alertDialogBuilder.setMessage(message)
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}
}
