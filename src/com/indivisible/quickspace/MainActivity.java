package com.indivisible.quickspace;

import android.app.*;
import android.os.*;
import android.content.*;

public class MainActivity extends Activity
{
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// dialog activity
//		setContentView(android.R.drawable.btn_plus);
		
		// background Toast service
		startService(new Intent(this, MainService.class));
		
		finish();
	}
}
