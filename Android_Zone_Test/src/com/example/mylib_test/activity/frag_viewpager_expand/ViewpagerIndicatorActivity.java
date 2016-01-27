package com.example.mylib_test.activity.frag_viewpager_expand;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;
import com.example.mylib_test.R;
import com.viewpagerindicator.CirclePageIndicator;
import and.abstractclass.BaseActvity;

/**
 * Created by Zone on 2016/1/26.
 */
public class ViewpagerIndicatorActivity extends BaseActvity {
    private CirclePageIndicator indicator;
    private ViewPager pager;

    @Override
    public void setContentView() {
        setContentView(R.layout.a_viewpagerindicator);
    }

    @Override
    public void findIDs() {
        indicator=(CirclePageIndicator)findViewById(R.id.indicator);
        pager = (ViewPager)findViewById(R.id.pager);
    }

    @Override
    public void initData() {
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        indicator.setViewPager(pager);
    }

    @Override
    public void setListener() {
        //如果我们要对ViewPager设置监听，用indicator设置就行了
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                Toast.makeText(ViewpagerIndicatorActivity.this,arg0+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Tab1();
                case 1:
                    return new Tab2();
                case 2:
                    return new Tab3();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
