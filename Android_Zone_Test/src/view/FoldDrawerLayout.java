package view;

import android.content.Context;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author zhy http://blog.csdn.net/lmj623565791/
 */
public class FoldDrawerLayout extends DrawerLayout
{
	private static final String TAG = "DrawerFoldLayout";

	public FoldDrawerLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	protected void onAttachedToWindow()
	{
		super.onAttachedToWindow();

		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++)
		{
			final View child = getChildAt(i);
			if (isDrawerView2(child))
			{
				Log.e(TAG, "at" + i);
				FlodLayoutGroup foldlayout = new FlodLayoutGroup(getContext());
				removeView(child);
				foldlayout.addView(child);
				ViewGroup.LayoutParams layPar = child.getLayoutParams();
				addView(foldlayout, i, layPar);
			}

		}
		setDrawerListener(new DrawerListener()
		{

			@Override
			public void onDrawerStateChanged(int arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset)
			{

				if (drawerView instanceof FlodLayoutGroup)
				{
					FlodLayoutGroup foldLayout = ((FlodLayoutGroup) drawerView);
					Log.e(TAG, "slideOffset = " + slideOffset);
					foldLayout.setProgress((int) (slideOffset*100));
				}

			}

			@Override
			public void onDrawerOpened(View arg0)
			{

			}

			@Override
			public void onDrawerClosed(View arg0)
			{

			}
		});

	}

	boolean isDrawerView2(View child)
	{
		final int gravity = ((LayoutParams) child.getLayoutParams()).gravity;
		final int absGravity = GravityCompat.getAbsoluteGravity(gravity,
				ViewCompat.getLayoutDirection(child));
		return (absGravity & (Gravity.LEFT | Gravity.RIGHT)) != 0;
	}

}
