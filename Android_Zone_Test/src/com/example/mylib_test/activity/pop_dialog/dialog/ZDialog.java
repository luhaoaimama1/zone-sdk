package com.example.mylib_test.activity.pop_dialog.dialog;

import android.app.Activity;
import android.view.View;
import android.view.Window;

import com.example.mylib_test.R;
import com.zone.lib.base.BaseDialog;
import com.zone.lib.utils.activity_fragment_ui.ToastUtils;

/**
 * [2017] by Zone
 */

public class ZDialog extends BaseDialog implements View.OnClickListener {

    private View dialog_cancel, dialog_sure;

    public ZDialog(Activity activity) {
        super(activity);
        setBgVisibility(false);
        setDialogContentView(R.layout.dlg_z, R.id.ll_main);
    }

    @Override
    protected void findView(View mMenuView) {
        dialog_cancel = mMenuView.findViewById(R.id.dialog_cancel);
        dialog_sure = mMenuView.findViewById(R.id.dialog_sure);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        dialog_sure.setOnClickListener(this);
        dialog_cancel.setOnClickListener(this);
    }

    @Override
    protected void setLocation(Window window) {
//        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.bottom_menu_animation); // 添加动画
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_cancel:
                ToastUtils.showShort(activity, "Cancel");
                System.out.println("Dialog->Cancel");
                break;
            case R.id.dialog_sure:
                ToastUtils.showShort(activity, "Sure");
                System.out.println("Dialog->Sure");
                break;
        }

    }

    @Override
    public void dismiss() {
        ToastUtils.showShort(activity, "dismiss");
        System.out.println("Dialog->dismiss");
        super.dismiss();
    }
}
