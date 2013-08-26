package com.indivisible.quickspace;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;


/** Custom view to display a pie chart **/
public class PieChartView extends View
{
	private static int[] COLORS = { Color.RED, Color.GREEN };
	private static int SIZE = 100;
	private Paint paint;
	private float[] values_degrees;
	private RectF rectf;
	int temp;
	
	/** Custom view to display a pie chart **/
	public PieChartView(Context context, long[] values)
	{
		super(context);
		
		values_degrees = calculateData(values);
		paint  = new Paint(Paint.ANTI_ALIAS_FLAG);
		rectf = new RectF (0, 0, SIZE, SIZE);
		temp=0;
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		for (int i = 0; i < values_degrees.length; i++)
		{
			if (i == 0) {
                paint.setColor(COLORS[i]);
                canvas.drawArc(rectf, 0, values_degrees[i], true, paint);
            } 
            else
            {
                    temp += (int) values_degrees[i - 1];
                    paint.setColor(COLORS[i]);
                    canvas.drawArc(rectf, temp, values_degrees[i], true, paint);
            }
		}
	}
	
	/** Calculate degrees from supplied data **/
	private float[] calculateData(long[] data)
	{
        float total=0;
        float[] degrees = new float[data.length];
        for(int i=0;i<data.length;i++)
        {
            total+=data[i];
        }
        for(int i=0;i<data.length;i++)
        {
        degrees[i]=360*(data[i]/total);            
        }
        return degrees;
    }
	

}
