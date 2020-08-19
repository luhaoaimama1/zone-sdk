package com.example.mylib_test.activity.controller.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.mylib_test.LogApp;
import com.example.mylib_test.R;
import com.zone.lib.ZLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import permissions.dispatcher.PermissionUtils;

public class PermissnActivity extends Activity {

    private static HashMap<String, String> hashMap = new HashMap();

    static {
        hashMap.put(Manifest.permission.READ_EXTERNAL_STORAGE, "存储空间");
        hashMap.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储空间");
        hashMap.put(Manifest.permission.READ_PHONE_STATE, "读取电话");
        hashMap.put(Manifest.permission.RECORD_AUDIO, "录音");
        ZLogger.Companion.getLogLevelList().clear();
        ZLogger.Companion.getMayLoggerList().clear();
        ZLogger.Companion.getMayLoggerList().add(LogApp.INSTANCE);
    }


    public static String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
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
        }
        checkPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (PermissionUtils.verifyPermissions(grantResults)) {
                LogApp.INSTANCE.d("Permissions onResult--> 权限ok!");
                permit();
            } else {
                PermissionEntity permissionEntity = caculatePermissionEntity(permissions, grantResults);
                LogApp.INSTANCE.d("Permissions onResult  not ok--> permissionEntity " + permissionEntity);
                if (!permissionEntity.rejectShouldShowRationaleList.isEmpty()) {
                    //分析出 剩下的权限进行系统申请弹窗
                    String[] rejectShouldShowRationales = new String[permissionEntity.rejectShouldShowRationaleList.size()];
                    permissionEntity.rejectShouldShowRationaleList.toArray(rejectShouldShowRationales);
                    ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
                } else {
                    //没有权限能用系统申请弹窗的话 还没当前权限的话 .弹出引导窗口
                    showGuideDialog(permissionEntity);
                }
            }
        }
    }

    private void checkPermission() {
        if (PermissionUtils.hasSelfPermissions(this, permissions)) {
            LogApp.INSTANCE.d("checkPermission 请求权限ok!");
            permit();
        } else {
            LogApp.INSTANCE.d("checkPermission 不够去请求权限了");
            //处理弹窗逻辑直接在 onRequestPermissionsResult去处理
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
        }
    }


    public static boolean verifyPermissions(int... grantResults) {
        if (grantResults.length == 0) {
            return false;
        }
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private PermissionEntity caculatePermissionEntity(@NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionEntity permissionEntity = new PermissionEntity();

        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                permissionEntity.permitList.add(permissions[i]);
            } else {
                if (PermissionUtils.shouldShowRequestPermissionRationale(this, permissions[i])) {
                    permissionEntity.rejectShouldShowRationaleList.add(permissions[i]);
                } else {
                    permissionEntity.rejectNeverRationalList.add(permissions[i]);
                }
            }
        }
        return permissionEntity;
    }

    public class PermissionEntity {
        List<String> permitList = new ArrayList<String>();
        /*
            没申请过也就是没权限属于此rejectShouldShowRationaleList
            所有没有权限的等于rejectNeverRationalList+rejectShouldShowRationaleList
         */
        List<String> rejectShouldShowRationaleList = new ArrayList<String>();
        List<String> rejectNeverRationalList = new ArrayList<String>();

        @Override
        public String toString() {
            return "PermissionEntity{" +
                    "permitList=" + permitList +
                    ", rejectShouldShowRationaleList=" + rejectShouldShowRationaleList +
                    ", rejectNeverRationalList=" + rejectNeverRationalList +
                    '}';
        }
    }

    public void showGuideDialog(PermissionEntity permissionEntity) {
        List<String> permissionNames = permissionEntity.rejectNeverRationalList;

        StringBuilder showNeedPermissionNames = new StringBuilder();
        for (int i = 0; i < permissionNames.size(); i++) {
            if (i != 0) {
                showNeedPermissionNames.append(",");
            }
            showNeedPermissionNames.append(hashMap.get(permissionNames.get(i)));
        }

        setContentView(R.layout.a_permiss);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvExplain = findViewById(R.id.tvExplain);

        tvTitle.setText(String.format("打开%s权限", showNeedPermissionNames));
        tvExplain.setText(String.format("此功能需要打开%s权限，请前往设置打开此权限", showNeedPermissionNames));

        findViewById(R.id.tvCancel).setOnClickListener(v -> {
            reject();
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


    //todo
    private void permit() {
        finish();
    }

    private void reject() {
        finish();
    }
}
