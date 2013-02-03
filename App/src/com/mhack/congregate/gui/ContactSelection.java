package com.mhack.congregate.gui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.mhack.congregate.R;
import com.mhack.congregate.adapter.ContactsAdapter;
import com.mhack.congregate.util.Globals;

public class ContactSelection extends Activity {

	private ListView listView;
	private ContactsAdapter adapter;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.select_contacts);
	}

	@Override
	public void onResume() {
		super.onResume();

		listView = (ListView) findViewById(R.id.listContacts);

		setupAdapter();

		Button btnDone = (Button) findViewById(R.id.btnFinishedContactSelection);
		btnDone.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	public void setupAdapter() {
		adapter = new ContactsAdapter(ContactSelection.this,
				R.layout.contact_cell, Globals.allContactsInAddressBook);
		listView.setAdapter(adapter);

		Log.d("", "== num contents in contacts list: " + Globals.allContactsInAddressBook.size());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_list, menu);
		return true;
	}
}
