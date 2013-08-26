package com.indivisible.quickspace;

import android.content.*;
import android.os.*;
import android.util.*;
import java.io.*;
import java.util.*;

/** class to contain and parse all storage partitions **/
public class StorageHandler
{
    private static final String TAG = "com.indivisible.quickspace.storagehandler";
	
    public static final String TYPE_INTERNAL_STORE = "internal";
    public static final String TYPE_INTERNAL_SDCARD = "int_sdcard";
	public static final String TYPE_EXTERNAL_SDCARD = "ext_sdcard";
	public static final String TYPE_TERTIARY = "tertiary";
	
    private ArrayList<Storage> allStores;
	
    
	////	constructor
    
	/** class to contain and parse all storage partitions **/
	public StorageHandler(Context context)
	{
		allStores = new ArrayList<Storage>();
		addNativeStorage(context);
		addTertiaryStorage(context);
	}
	
	
	////	add storage to ArrayList
	
	/** append native storage to the List **/
	private void addNativeStorage(Context context)
	{
		// internal storage
		File internal = Environment.getRootDirectory();
		allStores.add(new Storage(internal, TYPE_INTERNAL_STORE, context));
		
		// default external storage. can be either internal (emulated) or external (sdCard)
		File externalStorage = Environment.getExternalStorageDirectory();
		if (Environment.isExternalStorageRemovable())
		{
			allStores.add(new Storage(externalStorage, TYPE_EXTERNAL_SDCARD, context));
		}
		else
		{
			allStores.add(new Storage(externalStorage, TYPE_INTERNAL_SDCARD, context));
		}
		
	}
	
	/** append tertiary storage to the List **/
	private void addTertiaryStorage(Context context)
	{
		String[] roots = RenzhiStorage.getStorageDirectories();
		for (String root : roots)
		{
			File rootDir = new File(root);
			if (rootDir.canRead())
			{
			    allStores.add(new Storage(rootDir, TYPE_TERTIARY, context));
			}
			else
			{
				Log.e(TAG, "Cannot read: " +rootDir.getAbsolutePath());
			}
		}
	}
	
	////	methods working with the ArrayList
	
	/** update all store's stats **/
	public void update()
	{
		for (Storage store : allStores)
		{
			store.updateStats();
		}
	}
	
	/** get all storage partitions **/
	public List<Storage> getPartitions()
	{
		return allStores;
	}
	
	
	////	tests on storage partitions
	
	//TODO implement this check somewhere
//	/** test standard external storage for readability **/
//	private boolean isExternalStorageReadable()
//	{
//		String state = Environment.getExternalStorageState();
//		if (Environment.MEDIA_MOUNTED.equals(state) ||
//			Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
//		{
//			return true;
//		}
//		Log.w(TAG, "External Storage unreadable:");
//		Log.w(TAG, state);
//		return false;
//	}
	
	
	////	depreciated methods from Toast display (MainService.java)
	
	/** 
	 * display stats for all stores
	 * Depreciated: part of old Toast notification service (MainService.java)
	 **/
	@Deprecated
	public String disp()
	{
		StringBuffer sb = new StringBuffer();
		
		boolean isFirst = true;
		for (Storage store : allStores)
		{
			if (isFirst)
			{
				sb.append(store.toString());
				isFirst = false;
			}
			else
			{
				sb.append("\n").append(store.toString());
			}
		}
		
		return sb.toString();
	}
	
	
}
