package com.example.mylib_test.activity.frag_viewpager_expand;

import android.widget.TextView;

import com.example.mylib_test.R;

import com.zone.lib.base.Fragment_Lazy;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Tab2 extends Fragment_Lazy {


    private Mode mode;

    @BindView(R.id.tab2)
    TextView tab2;
    private Unbinder bk;

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
                bk=ButterKnife.bind(this, setContentView(R.layout.tab2,Mode.Normal));
            }else{
                bk=ButterKnife.bind(this, setContentView(R.layout.tab2,Mode.ViewPager));
            }
        }else{
            bk= ButterKnife.bind(this, setContentView(R.layout.tab2,Mode.ViewPager));
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
        bk.unbind();
    }


}
