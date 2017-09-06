package com.zone.lib.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

public class FullScrreenDialog extends Dialog {

    public FullScrreenDialog(Context context) {
        super(context);
//        <!--关键点1-->
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onStart() {
        getWindow().setBackgroundDrawable(new ColorDrawable(0x8899CCFF));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
    }
}