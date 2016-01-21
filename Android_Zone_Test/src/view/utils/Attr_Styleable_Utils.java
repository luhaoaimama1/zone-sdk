package view.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class Attr_Styleable_Utils {
	private Context context;
	private AttributeSet attrs;
	private int defStyle;
	private int[] styleable_declare_Id;
	private View v;
	private TypedArray ta;
	public Attr_Styleable_Utils(Context context,AttributeSet attrs, int defStyle,int[] styleable_declare_Id,View v) {
		this.context=context;
		this.attrs=attrs;
		this.defStyle=defStyle;
		this.styleable_declare_Id=styleable_declare_Id;
		this.v=v;
		ta = context.getTheme().obtainStyledAttributes(attrs,
				styleable_declare_Id, defStyle, 0);
	}
	public int get_attr_enum_to_int(int styleable_attr_id,int int_defValue) {
		return ta.getInt(styleable_attr_id, int_defValue);
	}
	public int get_attr_int(int styleable_attr_id,int int_defValue) {
		return ta.getInt(styleable_attr_id, int_defValue);
	}
	public Float get_attr_Float(int styleable_attr_id,Float int_defValue) {
		return ta.getFloat(styleable_attr_id, int_defValue);
	}

	public int get_attr_demen_toPix(int styleable_attr_id,int dp_defValue) {
		int pix=ta.getDimensionPixelSize(styleable_attr_id, (int) TypedValue
				.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ta.getDimension(styleable_attr_id, dp_defValue),
						v.getResources().getDisplayMetrics()));
		return pix;
	}

	public void recycle() {
		if (ta != null) {
			ta.recycle();
		}
	}
	

}
