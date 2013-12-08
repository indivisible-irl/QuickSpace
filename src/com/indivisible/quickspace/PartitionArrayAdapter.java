package com.indivisible.quickspace;

import java.util.List;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/** Custom ArrayAdapter for displaying stats on partitions **/
public class PartitionArrayAdapter extends ArrayAdapter<Storage>
{
	////	data
	
	private static String TAG = "com.indivisible.quickspace.partitionarrayadapter";
	private static String gbFormatText = "%.2f Gb";
	private static String percentFormatText = "%.1f%%";
	
	private Context context;
	private List<Storage> partitions;
	
	
	////	constructor
	
	/** Custom ArrayAdapter for displaying stats on partitions **/
	public PartitionArrayAdapter(Context ctx, int layout, List<Storage> stores)
	{
		super(ctx, layout, stores);
		partitions = stores;
		context = ctx;
	}
	
	
	//// views
	
	public View getView(int position, View convertView, ViewGroup parent)
	{

		View v = convertView;
		if (v == null)
		{
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.row_partition, null);
		}

		Storage partition = partitions.get(position);
		if (partition != null)
		{
			//Canvas canvas = (Canvas) findViewById(...);
			TextView title       = (TextView) v.findViewById(R.id.row_title);
			TextView spaceTotal  = (TextView) v.findViewById(R.id.row_total_space);
			TextView spaceFree   = (TextView) v.findViewById(R.id.row_freeGigs);
			TextView percentFree = (TextView) v.findViewById(R.id.row_freePercent);
			
			LinearLayout pieChartLayout = (LinearLayout) v.findViewById(R.id.row_piechart); 

			if (title != null)
			{
				title.setText(partition.getTitle());
			}
			if (spaceTotal != null)
			{
				String printableSpaceTotal = printGigs(Storage.convBytesToGigs(partition.getSpaceTotal()));
				spaceTotal.setText(printableSpaceTotal);
			}
			if (spaceFree != null)
			{
				String printableSpaceFree = printGigs(Storage.convBytesToGigs(partition.getSpaceFree()));
				spaceFree.setText(printableSpaceFree);
			}
			if (percentFree != null)
			{
				String printablePercentageFree = printPercentage(partition.getPercentageFree());
				percentFree.setText(printablePercentageFree);
			}
			
			//FIXME where a lot of the magic needs to happen
			if (pieChartLayout != null)
			{
				Log.d(TAG, "new pie: " +partition.getTitle());
				
				Log.w(TAG, "testing dimensions:");
				int height = pieChartLayout.getHeight();
				int width = pieChartLayout.getWidth();
				int measHeight = pieChartLayout.getMeasuredHeight();
				int measWidth = pieChartLayout.getMeasuredWidth();
				Log.i(TAG, "h: " +height);
				Log.i(TAG, "w: " +width);
				Log.i(TAG, "mh: " +measHeight);
				Log.i(TAG, "mw: " +measWidth);
				
				
				//FIXME need to get the layout's dimensions programatically or from the layout xml.
				int size = dpToPx(75);
				Log.d(TAG, "Pie Chart height/width in px: " +size);
				
				PieChartView pie = new PieChartView(context, size, partition.getSpaceTotal(), partition.getSpaceFree());
				pie.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//				pie.setScaleType(ScaleType.CENTER_INSIDE);
				pieChartLayout.addView(pie);
			}
		}

		return v;
	}
	
	
	//// util methods
	
	/** util method to convert gigabyte floats to printable text **/
	private static String printGigs(float gigs)
	{
		return String.format(gbFormatText, gigs);
	}
	
	/** util method to convert a float representation of a percentage to printable text **/
	private static String printPercentage(float percentage)
	{
		return String.format(percentFormatText, percentage);
	}
	
	
	public int dpToPx(int dp) {
	    DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
	    int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));       
	    return px;
	}
	
	
	

}
