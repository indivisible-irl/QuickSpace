package com.indivisible.quickspace;

import java.io.*;

import android.content.Context;
import android.util.Log;


public class Storage
{
    private static final String TAG = "com.indivisible.quickspace.storage";
	
    public String storeType;
    private File storeRoot;
    private String title;
    private long spaceTotal;
	private long spaceFree;
	private long spaceUsed;
	
	
	/** class to hold stats on a single storage partition/type **/
	public Storage(File file, String type, Context context)
	{
		storeRoot = file;
		storeType = type;
		initTitle(context);
		updateStats();
	}
	
	/** set the partition's title depending on storage type and location **/
	private void initTitle(Context context)
	{
		if (storeType == StorageHandler.TYPE_INTERNAL_STORE)
		{
			title = context.getString(R.string.storage_internal);
		}
		else if (storeType == StorageHandler.TYPE_INTERNAL_SDCARD)
		{
			title = context.getString(R.string.storage_internal_sdcard);
		}
		else if (storeType == StorageHandler.TYPE_EXTERNAL_SDCARD)
		{
			title = context.getString(R.string.storage_external_sdcard);
		}
		else if (storeType == StorageHandler.TYPE_TERTIARY)
		{
			title = '"' + storeRoot.getName() +'"';
		}
		else
		{
			Log.w(TAG, "Uncaught StorageHandler.TYPE_XXX: " +storeType);
			Log.w(TAG, "Path: " +storeRoot);
			title = '"' + storeRoot.getName() +'"';
		}
	}
	
	/** update all storage stats. use when reloading **/
	public void updateStats()
	{
		spaceTotal = storeRoot.getTotalSpace();
		spaceFree  = storeRoot.getFreeSpace();
		spaceUsed  = spaceTotal - spaceFree;
	}
	
	
	//// get stats
	
	/** return storage folder title **/
	public String getTitle()
	{
		return title;
	}
	
	/** get the total space allocated to this partition in bytes **/
	public long getSpaceTotal()
	{
		return spaceTotal;
	}
	
	/** get the amount of space available on the partition in bytes **/
	public long getSpaceFree()
	{
		return spaceFree;
	}
	
	/** get the amount of used space on the partition in bytes **/
	public long getSpaceUsed()
	{
		return spaceUsed;
	}

	/** calculate and return the percentage of free space remaining **/
	public float getPercentageFree()
	{
		return ( (float)spaceFree / (float)spaceTotal ) * 100f;
	}
	
	
	
	////	Byte long conversions
	
	/** convert bytes to gigabytes (long to float) **/
	public static float convBytesToGigs(long bytes)
	{
		return bytes / 1024f / 1024f / 1024f;
	}
	
	
	////	Old String methods for depreciated/testing simple Toast notification display (MainService.java)
	
	/** format long bytes into readable gigs **/
	@Deprecated
	private static String printableGigs(long bytes)
	{
		return String.format(
				"%.2f Gb",
				convBytesToGigs(bytes)
				);
	}
	
	/** format the % space free in readable form **/
	@Deprecated
	public String printablePercentage()
	{
		return String.format(
				"%.1f%%",
				getPercentageFree()
				);
	}
	
	/** return formatted total space in gigs **/
	@Deprecated
	public String printableSpaceTotal()
	{
		return printableGigs(spaceTotal);
	}
	
	/** return formatted free space in gigs **/
	@Deprecated
	public String printableSpaceFree()
	{
		return printableGigs(spaceFree);
	}
	
	/** used in initial simple one-line listview **/
	////@Deprecated
	@Override
	public String toString()
	{
	    return title + String.format(
		    "  %.1f%% of %.2f Gb  |  %.2f Gb  ",
		    getPercentageFree(),
			convBytesToGigs(spaceTotal),
			convBytesToGigs(spaceFree)
			);
	}

}
