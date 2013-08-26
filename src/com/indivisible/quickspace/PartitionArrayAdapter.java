package com.indivisible.quickspace;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.LinearLayout.LayoutParams;
import android.widget.ArrayAdapter;
//import android.widget.LinearLayout;
import android.widget.TextView;

/** Custom ArrayAdapter for displaying stats on partitions **/
public class PartitionArrayAdapter extends ArrayAdapter<Storage>
{
	////	data
	
	private static String gbFormatText = "%.2f Gb";
	private static String percentFormatText = "%.1f%%";
	
//	private Context context;
	private List<Storage> partitions;
	
	
	////	constructor
	
	/** Custom ArrayAdapter for displaying stats on partitions **/
	public PartitionArrayAdapter(Context ctx, int layout, List<Storage> stores)
	{
		super(ctx, layout, stores);
		partitions = stores;
//		context = ctx;
	}
	
	
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
//			LinearLayout pieChart = (LinearLayout) v.findViewById(R.id.row_piechart);

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
//			if (pieChart != null)
//			{
//				long[] sizes = new long[] { partition.getSpaceUsed(), partition.getSpaceFree() };
//				PieChartView pie = new PieChartView(context, sizes);
//				//pie.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//				pieChart.addView(pie);
//			}
		}

		return v;

	}
	
	
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
	
	
	

}
