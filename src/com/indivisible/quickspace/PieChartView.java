package com.indivisible.quickspace;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;


/** Custom view to display a pie chart **/
public class PieChartView extends View
{
	private static String TAG = "com.indivisible.quickspace.piechartview";
	private static int COLOR_USED = Color.RED;
	private static int COLOR_FREE = Color.GREEN;
	
	//TODO private static final int PADDING = 8; // padding in pixels for all sides
	
	private Paint paint;
	private RectF rectf;
	private float freeSpaceDegrees;
	
	
	//// constructors and initialisation
	
	/** Custom view to display a pie chart **/
	public PieChartView(Context context, int size, long spaceTotal, long spaceFree)
	{
		super(context);
		init(size, spaceTotal, spaceFree);
	}
	
	/** Just to get rid of the lint warning
	 * TODO AttributeSet??
	 * @param context
	 */
	public PieChartView(Context context)	//altern: PieChartView(Context context, AttributeSet attributes)
	{
		super(context);
	}
	
	/** Initialisation method to create objects and set values **/
	private void init(int size, long spaceTotal, long spaceFree)
	{
		freeSpaceDegrees = calculateDegrees(spaceTotal, spaceFree);
		paint  = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		//TODO defSize placeholder instead (??) of size and move actual size calc/measurement of parent's bounds to new onMeasure()
		rectf = new RectF (0, 0, (float) size, (float) size);
	}
		
	
	//// override methods
	
	@Override
	protected void onMeasure(int width, int height) {
		//TODO http://developer.android.com/training/custom-views/custom-drawing.htm
	}
	
	@Override
	protected void onSizeChanged(int newWidth, int newHeight, int oldWidth, int oldHeight) {
		//TODO http://developer.android.com/training/custom-views/custom-drawing.htm
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		Log.i(TAG, "freeSpaceDegrees: " +freeSpaceDegrees);
		
		for (int i = 0; i < 2; i++)
		{
			if (i == 0) {
                paint.setColor(COLOR_USED);
                canvas.drawArc(rectf, 270 + freeSpaceDegrees, 360, true, paint);
            } 
            else
            {
                paint.setColor(COLOR_FREE);
                canvas.drawArc(rectf, 270, freeSpaceDegrees, true, paint);
            }
		}
	}
	
	//// util methods
	
	/** calculate the degrees in a circle that the free space should be represented by **/
	private float calculateDegrees(long total, long free)
	{
		float percentage = ((float) free / (float) total);
		Log.i(TAG, "freeSpace percentage: " +percentage);
		return 360.0f * percentage;
	}
	

}
