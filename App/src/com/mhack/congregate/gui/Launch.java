package com.mhack.congregate.gui;

import com.mhack.congregate.R;
import com.mhack.congregate.sysHide.SystemUiHider;
import com.mhack.congregate.util.Const;
import com.mhack.congregate.util.Globals;
import com.mhack.congregate.util.Utility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class Launch extends Activity {

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.launch);

        final View contentView = findViewById(R.id.fullscreen_content);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider.hide();
        
        Utility.init(Launch.this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        
        new CountDownTimer(3200, 1000) {

			public void onTick(long millisUntilFinished) {
			}

			public void onFinish() {

				if (Globals.prefs.contains(Const.phoneNumber) && !"".equalsIgnoreCase(Globals.prefs.getString(Const.phoneNumber, ""))) { 
					Intent intent = new Intent().setClass(Launch.this, EventList.class);
					startActivity(intent);
				} else { 
					Intent intent = new Intent().setClass(Launch.this, Registration.class);
					startActivity(intent);
				}

				
				finish();
			}

		}.start();
    }

}
