package com.mhack.congregate.util;

import java.util.ArrayList;

import android.content.SharedPreferences;

import com.mhack.congregate.dto.ContactDTO;

public class Globals {

    static public SharedPreferences prefs;
    static public SharedPreferences.Editor prefEdit;
    
    static public ArrayList<ContactDTO> contactsForEvent;
    
    static { 
    	contactsForEvent = new ArrayList<ContactDTO>();
    }
}
