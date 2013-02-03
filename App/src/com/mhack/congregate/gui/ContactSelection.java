package com.mhack.congregate.gui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.mhack.congregate.R;
import com.mhack.congregate.adapter.ContactsAdapter;
import com.mhack.congregate.dto.ContactDTO;
import com.mhack.congregate.util.Utility;

public class ContactSelection extends Activity {

	private ContentResolver cr;
	private ArrayList<ContactDTO> contacts;
	private ContactsAdapter adapter;
	private ListView listView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.select_contacts);
		contacts = new ArrayList<ContactDTO>();
	}

	@Override
	public void onResume() {
		super.onResume();

		contacts.clear();
		cr = getContentResolver();

		listView = (ListView) findViewById(R.id.listContacts);

		fillContactsList();
		setupAdapter();

		Button btnDone = (Button) findViewById(R.id.btnFinishedContactSelection);
		btnDone.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	/**
	 * Fill contact DTO with information for adapter!
	 */
	public void fillContactsList() {
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);

		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String id = cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

				if (Integer
						.parseInt(cur.getString(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					Cursor pCur = cr.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = ?", new String[] { id }, null);
					while (pCur.moveToNext()) {

						ContactDTO c = new ContactDTO();
						c.phoneNumber = Utility.stripExtraCharsFromPhone(pCur
								.getString(26));
						c.name = name;

						if (c.phoneNumber.length() == 10)
							contacts.add(c);
					}
					pCur.close();
				}
			}
		}
	}

	public void setupAdapter() {
		adapter = new ContactsAdapter(ContactSelection.this,
				R.layout.contact_cell, contacts);
		listView.setAdapter(adapter);

		Log.d("", "== num contents in contacts list: " + contacts.size());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_list, menu);
		return true;
	}

}
