package com.indivisible.quickspace;

import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.Toast;
import android.view.*;
import android.widget.*;


public class MainService extends Service
{

	public IBinder onBind(Intent p1) {
		return null;
	}
	
    private static final String TAG = "com.indivisible.quickspace.main";
	
	private StorageHandler storageHandler;
	
	/** Called when the activity is first created. */
    @Override
    public void onStart(Intent intent, int startId)
	{
        super.onStart(intent, startId);
		
		storageHandler = new StorageHandler(this.getApplicationContext());
		storageHandler.update();

		String msg = storageHandler.disp();
		Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
		TextView tv = (TextView) toast.getView().findViewById(android.R.id.message);
		if (tv != null)
			tv.setGravity(Gravity.RIGHT);
		toast.show();
		
		this.stopSelf();
    }
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
}
