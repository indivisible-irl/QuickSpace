package com.indivisible.quickspace;

import java.util.ArrayList;
import java.util.List;

import android.app.*;
import android.os.*;
import android.util.Log;
import android.widget.ListView;
import android.widget.*;

public class MainActivity extends ListActivity
{
	private static final String TAG = "com.indivisible.quickspace.main";
	
	private static final long DISPLAY_TIME = 6000;
	
	private StorageHandler storageHandler;
	private List<Storage> partitions;
	private ListView lvPartitions;
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_dialog);
//		lvPartitions = (ListView) findViewById(R.)
		
		storageHandler = new StorageHandler(this.getApplicationContext());
		storageHandler.update();
		
		partitions = storageHandler.getPartitions();
		Log.d(TAG, "Num of partitions found: " +partitions.size());
		
		
		ArrayAdapter<Storage> adapter = new PartitionArrayAdapter(
				this.getApplicationContext(),
				R.layout.row_partition,
				partitions
				);
		setListAdapter(adapter);
		
		// wait for a time then dismiss the activity
		//TODO implement a proper AsyncTask (Thread.sleep(millis) / Timer
//		long timeNow = System.currentTimeMillis();
//		long timeEnd = timeNow + DISPLAY_TIME;
//		while (System.currentTimeMillis() < timeEnd) {
//			// nada
//		}
		
		//finish();
		
		
		// depreciated background Toast service
//		startService(new Intent(this, MainService.class));
		
	}
}
