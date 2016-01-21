package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class Progressbar_Zone extends View {
	private float tatal = 100F;
	private float progress = 50F;
	private Paint paint;
	private float radio=100;
	private int backColor;
	private int forecolor;

	public Progressbar_Zone(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initPaint();
	}
	

	private void initPaint() {
		paint=new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		backColor=Color.RED;
		forecolor=Color.YELLOW;
		
	}

	public Progressbar_Zone(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public Progressbar_Zone(Context context) {
		this(context, null);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.saveLayer(0, 0, getWidth(), getHeight(), paint,Canvas.ALL_SAVE_FLAG);
		RectF r = new RectF(0, 0, getWidth(), getHeight());
		paint.setColor(backColor);
		canvas.drawRoundRect(r, radio, radio, paint);
		paint.setColor(forecolor);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawRect(new RectF(progress*getWidth()/tatal, 0, getWidth(), getHeight()), paint);
	}

	public float getTatal() {
		return tatal;
	}

	public void setTatal(float tatal) {
		this.tatal = tatal;
	}

	public float getProgress() {
		return progress;
	}

	public void setProgress(float progress) {
		this.progress = progress;
		invalidate();
	}

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}

	public float getRadio() {
		return radio;
	}

	public void setRadio(float radio) {
		this.radio = radio;
	}


	public int getBackColor() {
		return backColor;
	}


	public void setBackColor(int backColor) {
		this.backColor = backColor;
	}


	public int getForecolor() {
		return forecolor;
	}


	public void setForecolor(int forecolor) {
		this.forecolor = forecolor;
	}

}
