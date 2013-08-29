package com.indivisible.quickspace;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
//import android.widget.ImageView;


/** Custom view to display a pie chart **/
public class PieChartView extends View
{
	private static String TAG = "com.indivisible.quickspace.piechartview";
	private static int COLOR_USED = Color.RED;
	private static int COLOR_FREE = Color.GREEN;
	private Paint paint;

	private float freeSpaceDegrees;
	private RectF rectf;
	int temp;
	
	/** Custom view to display a pie chart **/
	public PieChartView(Context context, int size, long spaceTotal, long spaceFree)
	{
		super(context);
		
		freeSpaceDegrees = calculateDegrees(spaceTotal, spaceFree);
		paint  = new Paint(Paint.ANTI_ALIAS_FLAG);
		rectf = new RectF (0, 0, (float) size, (float) size);
		temp=0;
	}
	
	/** Just to get rid of the lint warning
	 * TODO check how AttributeSet works
	 * @param context
	 */
	public PieChartView(Context context) {
		super(context);
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
	
	/** calculate the degrees in a circle that the free space should be represented by **/
	private float calculateDegrees(long total, long free)
	{
		float percentage = ((float) free / (float) total);
		Log.i(TAG, "freeSpace percentage: " +percentage);
		return 360.0f * percentage;
	}
	

}
