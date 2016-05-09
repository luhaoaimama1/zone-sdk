package com.example.mylib_test.activity.frag_viewpager_expand;

import android.view.View;
import android.widget.TextView;

import com.example.mylib_test.R;

import and.base.Fragment_Lazy;
import butterknife.Bind;
import butterknife.ButterKnife;

public class Tab2 extends Fragment_Lazy {


    private Mode mode;

    @Bind(R.id.tab2)
    TextView tab2;

    @Override
    protected void onVisible(int visibleInt) {
        System.err.println("onVisible:"+visibleInt);
        tab2.setText("Perfect!");
    }

    @Override
    protected void onInvisible(int inVisibleInt) {
        System.err.println("onInvisible:"+inVisibleInt);
    }

    @Override
    public void setContentView() {
        if (getArguments()!=null) {
            String mode=getArguments().getString("mode","");
            if("normal".equals(mode)){
                ButterKnife.bind(this, setContentView(R.layout.tab2,Mode.Normal));
            }else{
                ButterKnife.bind(this, setContentView(R.layout.tab2,Mode.ViewPager));
            }
        }else{
            ButterKnife.bind(this, setContentView(R.layout.tab2,Mode.ViewPager));
        }
//        tab2= (TextView) findViewById(R.id.tab2);
    }

    @Override
    public void findIDs() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
