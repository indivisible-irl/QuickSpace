package com.indivisible.quickspace;

import java.util.List;

import android.app.*;
import android.os.*;
import android.util.Log;
import android.widget.*;

public class MainActivity extends ListActivity
{
	private static final String TAG = "com.indivisible.quickspace.main";
	
	private StorageHandler storageHandler;
	private List<Storage> partitions;
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_dialog);
		
		Log.i(TAG, "Gather partitions...");
		storageHandler = new StorageHandler(this.getApplicationContext());
		storageHandler.update();
		
		partitions = storageHandler.getPartitions();
		Log.d(TAG, "Num of partitions found: " +partitions.size());
		
		Log.i(TAG, "Fill ArrayAdapter...");
		ArrayAdapter<Storage> adapter = new PartitionArrayAdapter(
				this.getApplicationContext(),
				R.layout.row_partition,
				partitions
				);
		Log.d(TAG, "Set ListAdapter with num elements: " +adapter.getCount());
		setListAdapter(adapter);
		
		// wait for a time then dismiss the activity
		//TODO implement a proper AsyncTask (Thread.sleep(millis) / Timer
		//IDEA or maybe just close on any click (that isn't a scroll)??
//		finish();
	}
}
