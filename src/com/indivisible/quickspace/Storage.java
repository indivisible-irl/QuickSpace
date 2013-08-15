package com.indivisible.quickspace;

import java.io.*;
import android.util.*;


public class Storage
{
    private static final String TAG = "com.indivisible.quickspace.storage";
	
    public String storeType;	// care??
    private File storeRoot;
    private long spaceTotal;
	private long spaceFree;
	private long spaceUsed;
	
	
	/** class to hold stats on a single storage partition/type **/
	public Storage(File file, String type) {
		storeRoot = file;
		storeType = type;
		updateStats();
	}
	
	/** update all storage stats. use when reloading **/
	public void updateStats() {
		spaceTotal = storeRoot.getTotalSpace();
		spaceFree  = storeRoot.getFreeSpace();
		spaceUsed  = spaceTotal - spaceFree;
	}

	/** convert bytes to gigabytes (long to float) **/
	private static float convBytesToGigs(long bytes) {
		return bytes / 1024f / 1024f / 1024f;
	}
	
	/** calculate the percentage of free space remaining **/
	private float calcPercentageFree() {
		return ( (float)spaceFree / (float)spaceTotal ) * 100f;
	}
	
	/** format long bytes into readable gigs **/
	private static String printableGigs(long bytes) {
		return String.format(
				"%.2f Gb",
				convBytesToGigs(bytes));
	}
	
	/** return storage folder title **/
	public String getTitle() {
		return storeRoot.getName();
	}
	
	/** format the % space free in readable form **/
	public String printablePercentage() {
		return String.format(
				"%.1f%%",
				calcPercentageFree());
	}
	
	/** return formatted total space in gigs **/
	public String printableSpaceTotal() {
		return printableGigs(spaceTotal);
	}
	
	/** return formatted free space in gigs **/
	public String printableSpaceFree() {
		return printableGigs(spaceFree);
	}
	
	
	/** used in initial simple one-line listview **/
	@Deprecated
	@Override
	public String toString() {
	    return String.format(
		    "  %.1f%% of %.2f Gb  |  %.2f Gb  ",
		    calcPercentageFree(),
			convBytesToGigs(spaceTotal),
			convBytesToGigs(spaceFree));
	}

}
