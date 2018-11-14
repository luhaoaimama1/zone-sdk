package com.example.mylib_test.activity.touch;

import com.example.mylib_test.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;

public class TouchMainActivity extends Activity implements OnClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_touch_main);
		int a = ViewConfiguration.get(this).getScaledTouchSlop();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.veclocityTracker:
			startActivity(new Intent(this,VeclocityTrackerActivity.class));
			break;
		case R.id.viewDrag:
			startActivity(new Intent(this,ViewDragStudyActivity.class));
			break;
		case R.id.viewDrag2:
			startActivity(new Intent(this,ViewDragStudyActivity2.class));
			break;
		case R.id.gestureDetectorActivity:
			startActivity(new Intent(this,GestureDetectorActivity.class));
			break;
		case R.id.scaleGestureDetector:
			startActivity(new Intent(this,MyScaleActivity.class));
			break;
		case R.id.scrollerView:
			startActivity(new Intent(this,ScrollerActivity.class));
			break;
		case R.id.outConflict1:
			startActivity(new Intent(this,Conflict1Activity.class).putExtra("type", "out"));
			break;
		case R.id.innerConflict1:
			startActivity(new Intent(this,Conflict1Activity.class).putExtra("type", "inner"));
			break;
		case R.id.onTouchConflict1:
			startActivity(new Intent(this,Conflict1Activity.class).putExtra("type", "innerOntouch"));
			break;
		case R.id.frameConflict1:
			startActivity(new Intent(this,Conflict1Activity.class).putExtra("type", "frame"));
			break;
		case R.id.NestedScrolling:
			startActivity(new Intent(this,NestedScrollingActivity_hongParent.class));
			break;
		case R.id.NestedScrollingChild:
			startActivity(new Intent(this,NestedScrollingActivity_Child.class));
			break;
		case R.id.eventPass:
			startActivity(new Intent(this,EventPassLogActivity.class));
			break;
		default:
			break;
		}
		
	}

}
