package com.indivisible.quickspace;

import android.content.*;
import android.os.*;
import android.util.*;
import java.io.*;
import java.util.*;

public class StorageHandler
{
    private static final String TAG = "com.indivisible.quickspace.storagehandler";
	
    static final String TYPE_INTERNAL = "internal";
	static final String TYPE_EXTERNAL = "external";
	static final String TYPE_TERTIARY = "tertiary";
	
    ArrayList<Storage> allStores;
	
	
	/* class to contain and parse all storage partitions */
	public StorageHandler(Context context) {
		allStores = new ArrayList<Storage>();
		addNativeStorage(context);
		addTertiaryStorage();
	}
	
	/* append native storage to the List */
	private void addNativeStorage(Context context) {
		// internal storage
		File internal = Environment.getRootDirectory();
		allStores.add(new Storage(internal, TYPE_INTERNAL));
		Log.d(TAG, "internal path: " +internal.getAbsolutePath());
		Log.d(TAG, "internal size: " +internal.getTotalSpace()/1024/1024);
		Log.d(TAG, "internal free: " +internal.getFreeSpace()/1024/1024);
		
		// default sd card. can be either internal or external
		File sdCardMain = context.getFilesDir();
		allStores.add(new Storage(sdCardMain, TYPE_INTERNAL));
	}
	
	/* append tertiary storage to the List */
	private void addTertiaryStorage() {
		String[] roots = RenzhiStorage.getStorageDirectories();
		for (String root : roots) {
			File rootDir = new File(root);
			if (rootDir.canRead()) {
			    allStores.add(new Storage(rootDir, TYPE_TERTIARY));
			} else {
				Log.e(TAG, "Cannot read: " +rootDir.getAbsolutePath());
			}
		}
	}
	
	/* test standard external storage for readability */
	private boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state) ||
			Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		Log.w(TAG, "External Storage unreadable:");
		Log.w(TAG, state);
		return false;
	}
	
	/** update all store's stats **/
	public void update() {
		for (Storage store : allStores) {
			store.updateStats();
		}
	}
	
	/** display stats for all stores **/
	public String disp() {
		StringBuffer sb = new StringBuffer();
		
		boolean isFirst = true;
		for (Storage store : allStores) {
			if (isFirst) {
				sb.append(store.toString());
				isFirst = false;
			}
			else {
				sb.append("\n").append(store.toString());
			}
		}
		
		return sb.toString();
	}
	
	
}
