package com.mhack.congregate.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mhack.congregate.R;
import com.mhack.congregate.dto.ContactDTO;
import com.mhack.congregate.util.Globals;

public class ContactsAdapter extends ArrayAdapter<ContactDTO> {
	
	private int resource;
	private Activity act;
	
	public ContactsAdapter(Activity act, int resource) {
		super(act, resource, Globals.allContactsInAddressBook);
		
		this.resource = resource;
		this.act = act;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		
		if (v == null) { 
			LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(resource, null);
		}
		
		final ContactDTO contact = Globals.allContactsInAddressBook.get(position);
		
		if (contact != null) {
			final TextView textName = (TextView) v.findViewById(R.id.txtContactName);
			final TextView textNumber = (TextView) v.findViewById(R.id.txtContactNumber);
			final CheckBox chk = (CheckBox) v.findViewById(R.id.chkBox);
			
			Log.d("", "== text name: " + textName);
			
			if (textName != null) {
				textName.setText(contact.name);
			}
			
			if (textNumber != null) { 
				textNumber.setText(contact.phoneNumber);
			}
			
			Log.d("", "== name: " + contact.name + " #: " + contact.phoneNumber);
			
			chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) { 
						Log.d("", "=== MAY DAY MAY DAY JUST CHECKED OFF A CONTACT FOR THE PARTY!!!");
						contact.selected = true;
					} else { 
						contact.selected = false;
					}
				}
			});
		} else { 
			Log.d("", "== CONTACT IS NULL LIKE A NOOB");
		}
		
		return v;
	}
}
