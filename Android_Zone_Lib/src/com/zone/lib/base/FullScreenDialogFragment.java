package com.zone.lib.base;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Window;
import android.view.WindowManager;
/**
 * http://blog.csdn.net/javaandroid730/article/details/69526202
 */
public class FullScreenDialogFragment extends DialogFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
//    <!--关键点1-->
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
//    <!--关键点2-->
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
    }

}