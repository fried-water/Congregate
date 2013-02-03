package com.mhack.congregate.gui;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mhack.congregate.R;
import com.mhack.congregate.util.Const;
import com.mhack.congregate.util.DataTransfer;
import com.mhack.congregate.util.Globals;
import com.mhack.congregate.util.Utility;

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

				final String num = txtNumber.getText().toString();
				final String name = txtName.getText().toString();
				
				if ("".equalsIgnoreCase(num)) { 
					launchDialogue("Please enter a valid 10 digit phone number (no hyphens, spaces, or dots).");
				} else if (num.length() < 10) { 
					launchDialogue("Your phone number must be at least 10 digits.");
				} else if ("".equalsIgnoreCase(name)) { 
					launchDialogue("Please enter your name so your friends know who is contacting them about an event.");
				} else { 
					
					final ProgressDialog progress = new ProgressDialog(Registration.this);
					
					
					if(Utility.isNetworkAvailable(getApplicationContext()))
					{
						progress.setIndeterminate(true);
						progress.setMessage("Registering Information...");
						
						progress.show();
						
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								
								ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
								
								JSONObject request = new JSONObject();
								try {
									request.put(Const.name, name);
									request.put("number", Long.parseLong(num.replaceAll("-", "")));
								} catch (JSONException e) {
									runOnUiThread(new Runnable() {
										
										@Override
										public void run() {
											progress.dismiss();
											Toast.makeText(getApplicationContext(), "Error Registering.", Toast.LENGTH_LONG).show();
											//launchDialogue("Error Registering.");
										}
									});
									e.printStackTrace();
									return;
								}
								
								params.add(new BasicNameValuePair("json", request.toString()));
								
								JSONObject res = DataTransfer.postJSONResult(getApplicationContext(), Const.url+"register", params);
								
								if(res != null && DataTransfer.verifyEMPStatus(res)) {
									Globals.prefEdit.putString(Const.phoneNumber, num);
									Globals.prefEdit.putString(Const.name, name);
									Globals.prefEdit.commit();
									
									runOnUiThread(new Runnable() {
										
										@Override
										public void run() {
											progress.dismiss();
										}
									});
									
									
									
									Intent intent = new Intent().setClass(Registration.this,
											EventList.class);
									startActivity(intent);
									
									finish();
								} else {
									runOnUiThread(new Runnable() {
										
										@Override
										public void run() {
											progress.hide();
											Toast.makeText(getApplicationContext(), "Error Registering.", Toast.LENGTH_LONG).show();
											//launchDialogue("Error Registering.");
										}
									});
								}
							}
						}).start();
					}
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
