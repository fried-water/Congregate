package com.mhack.congregate.util;

import java.util.Comparator;

import com.mhack.congregate.dto.ContactDTO;

public class ContactComparator implements Comparator<ContactDTO>{

	@Override
	public int compare(ContactDTO arg0, ContactDTO arg1) {
		return (arg0.name.compareTo(arg1.name));
	}

}
