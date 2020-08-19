package com.example.mylib_test.activity.controller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.mylib_test.LogApp;
import com.example.mylib_test.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import permissions.dispatcher.PermissionUtils;

public class PermissnActivity extends Activity {

    private static HashMap<String, String> hashMap = new HashMap();

    static {
        hashMap.put(Manifest.permission.READ_EXTERNAL_STORAGE, "存储空间");
        hashMap.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储空间");
        hashMap.put(Manifest.permission.READ_PHONE_STATE, "存储空间");
    }


    public static String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_PHONE_STATE,
    };

    public static final String PERMISSIONS = "permissions";
    public static final int REQUEST_CODE = 1;
    public static final int SETTING_REQUEST_CODE = 2;

    public static void start(Context context, String[] permissions) {
        Intent intent = new Intent(context, PermissnActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArray(PERMISSIONS, permissions);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();    //得到传过来的bundle
        if (bundle != null) {
            permissions = bundle.getStringArray(PERMISSIONS);
        } else {
            finish();
            return;
        }

        if (PermissionUtils.hasSelfPermissions(this, permissions)) {
            LogApp.INSTANCE.d("请求权限ok!");
            permit();
        } else {
//            if (!shouldShowRequestPermissionRationale(this, permissions)) {
//                LogApp.INSTANCE.d("需要引导！");
////                ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
//                showGuideDialog();
//            } else {
//                LogApp.INSTANCE.d("请求权限");
//                ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
//            }
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
        }

    }

    private void permit() {
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (PermissionUtils.verifyPermissions(grantResults)) {
                LogApp.INSTANCE.d("Permissions onResult--> 权限ok!");
                permit();
            } else {
                if (!shouldShowRequestPermissionRationale(this, permissions)) {
                    //NeverAskAgain的逻辑  所有的权限都不需要弹窗 但是还有权限没有申请过
                    showGuideDialog();
                    LogApp.INSTANCE.d("Permissions onResult--> 需要引导 ");
                } else {
                    //手动拒绝的逻辑
                    LogApp.INSTANCE.d("Permissions onResult--> 拒绝 !");
                    finish();
                }
            }
        }
    }

    public void showGuideDialog() {
        List<String> permissionNames = new ArrayList<>();
        for (String permission : permissions) {
            if (!PermissionUtils.hasSelfPermissions(this, permission)) {
                String noPermissionName = hashMap.get(permission);
                if (noPermissionName != null && permissionNames.indexOf(noPermissionName) != -1) {
                    permissionNames.add(noPermissionName);
                }
            }
        }
        StringBuilder showNeedPermissionNames = new StringBuilder();
        for (int i = 0; i < permissionNames.size(); i++) {
            if (i != 0) {
                showNeedPermissionNames.append(",");
            }
            showNeedPermissionNames.append(permissionNames.get(i));
        }

        setContentView(R.layout.a_permiss);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvExplain = findViewById(R.id.tvExplain);

        tvTitle.setText(String.format("打开%s权限", showNeedPermissionNames));
        tvExplain.setText(String.format("此功能需要打开%s权限，请前往设置打开此权限", showNeedPermissionNames));

        findViewById(R.id.tvCancel).setOnClickListener(v -> {
            finish();
        });

        findViewById(R.id.tvConfirm).setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, SETTING_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTING_REQUEST_CODE) {
            if (PermissionUtils.hasSelfPermissions(this, permissions)) {
                permit();
                finish();
            }
        }
    }

    private static boolean shouldShowRequestPermissionRationale(Activity activity, String... permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true;
            }
        }
        return false;
    }
}
