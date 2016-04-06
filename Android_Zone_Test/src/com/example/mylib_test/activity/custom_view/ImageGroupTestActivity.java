package com.example.mylib_test.activity.custom_view;

import view.FlowLayout_Zone;
import view.ImageGroup_Zone;
import view.ImageGroup_Zone.ShowStatue;

import com.example.mylib_test.R;

import and.utils.ToastUtils;
import and.utils.measure.MeasureUtils;
import and.utils.measure.MeasureUtils.ListenerState;
import and.utils.measure.MeasureUtils.OnMeasureListener;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class ImageGroupTestActivity extends Activity implements OnClickListener {
	private ImageGroup_Zone imageGroup_zone;
	private FlowLayout_Zone flowLayoutZone1;
	private EditText num;
	private int[] colorArray = { Color.BLACK, Color.RED, Color.BLUE, Color.YELLOW };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_imagegrouptest);
		initData();
	}

	private void initData() {
		imageGroup_zone = (ImageGroup_Zone) findViewById(R.id.imageGroup_zone);
		flowLayoutZone1 = (FlowLayout_Zone) findViewById(R.id.flowLayoutZone1);
		num = (EditText) findViewById(R.id.num);
		imageGroup_zone.updateProperty(20, 1, 4, ShowStatue.ALL_VISIBLE);
		MeasureUtils.measure(imageGroup_zone,
				ListenerState.MEASURE_REMOVE, new OnMeasureListener() {

					@Override
					public void measureResult(View v, int viewWidth,
											  int viewHeight) {
						imageGroup_zone.setBackgroundColor(Color.WHITE);
						for (int i = 0; i < imageGroup_zone.getChildCount(); i++) {
							imageGroup_zone.getChild_ChildPro(i).iv.setBackgroundColor(colorArray[i % 4]);
						}
					}
				});

	}

	@Override
	public void onClick(View v) {
		String numStr=num.getText().toString();
		int number=0;
		try {
			number = Integer.parseInt(numStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			ToastUtils.showLong(this, "请按 hint输入");
		}
		switch (v.getId()) {
		case R.id.show:
			imageGroup_zone.getChild_ChildPro(number).iv.setVisibility(View.VISIBLE);
			break;
		case R.id.hide:
			imageGroup_zone.getChild_ChildPro(number).iv.setVisibility(View.INVISIBLE);
			break;
		case R.id.gone:
			imageGroup_zone.getChild_ChildPro(number).iv.setVisibility(View.GONE);
			break;
		case R.id.invalidate:
			System.out.println("invalidate onClick");
			imageGroup_zone.postInvalidate();
			break;
		case R.id.requestLayout:
			System.out.println("requestLayout onClick");
			imageGroup_zone.requestLayout();
			break;

		default:
			break;
		}
	}
}
