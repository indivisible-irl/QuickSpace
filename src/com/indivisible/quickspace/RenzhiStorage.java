package com.indivisible.quickspace;

import java.io.*;
import java.util.ArrayList;
import java.util.*;
import android.os.*;


public class RenzhiStorage
{
    public static String[] getStorageDirectories() {
		String[] dirs = null;
		BufferedReader bufReader = null;

		try {
			bufReader = new BufferedReader(new FileReader("/proc/mounts"));
			ArrayList<String> storageList = new ArrayList<String>();
			String line;

			while ((line = bufReader.readLine()) != null) {
				if (line.contains("vfat") || line.contains("/mnt")) {
					StringTokenizer tokens = new StringTokenizer(line, " ");
					String s = tokens.nextToken();
					s = tokens.nextToken(); // Take the second token, i.e. mount point

					if (s.equals(Environment.getExternalStorageDirectory().getPath())) {
						storageList.add(s);
					}
					else if (line.contains("/dev/block/vold")) {
						if (!line.contains("/mnt/secure") &&
						    !line.contains("/mnt/asec") &&
							!line.contains("/mnt/obb") &&
							!line.contains("/dev/mapper") &&
							!line.contains("tmpfs")) {
						    	storageList.add(s);
						}
					}
				}
			}
			dirs = new String[storageList.size()];
			for (int i = 0; i < storageList.size(); i++) {
				dirs[i] = storageList.get(i);
			}
		}

		catch (FileNotFoundException e) {}
		catch (IOException e) {}

		finally {
			if (bufReader != null) {
				try {
					bufReader.close();
				}
				catch (IOException e) {}
			}
			
		}
		
		return dirs;
	}
	
}
