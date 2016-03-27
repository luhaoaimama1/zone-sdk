package com.example.mylib_test.activity.frag_viewpager_expand;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.mylib_test.R;
import and.base.Fragment_Lazy;

/**
 * Created by Zone on 2016/1/27.
 */
public class ImageFragment extends Fragment_Lazy {
    private ImageView iv;
    private Integer path;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_image_common,null,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iv=(ImageView)view.findViewById(R.id.iv);
        path=getArguments().getInt("path");
        iv.setImageResource(path);
    }

    @Override
    protected void onVisible() {

    }

    @Override
    protected void onInvisible() {

    }
}
