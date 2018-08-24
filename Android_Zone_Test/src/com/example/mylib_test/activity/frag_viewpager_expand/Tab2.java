package com.example.mylib_test.activity.frag_viewpager_expand;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.mylib_test.R;
import com.zone.lib.base.LazyFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Tab2 extends LazyFragment {


    @BindView(R.id.tab2)
    TextView tab2;
    private Unbinder bk;

    @Override
    public void setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            String mode = getArguments().getString("mode", "");
            if ("normal".equals(mode)) {
                bk = ButterKnife.bind(this, setContentView(R.layout.tab2));
            } else {
                bk = ButterKnife.bind(this, setContentView(R.layout.tab2));
            }
        } else {
            bk = ButterKnife.bind(this, setContentView(R.layout.tab2));
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
    protected void onFragmentShown() {
        tab2.setText("Perfect!");
    }

    @Override
    protected void onFragmentDismiss() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bk.unbind();
    }


}
