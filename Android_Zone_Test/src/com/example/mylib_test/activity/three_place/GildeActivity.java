package com.example.mylib_test.activity.three_place;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mylib_test.R;

import java.io.File;

import and.base.activity.BaseActvity;
import and.utils.file2io2data.FileUtils;
import and.utils.file2io2data.SDCardUtils;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/4.
 */
public class GildeActivity extends BaseActvity {
    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.bt)
    Button bt;
    Type type=Type.HTTP;
    @Override
    public void setContentView() {
        setContentView(R.layout.a_glide);
        ButterKnife.bind(this);
    }

    @Override
    public void findIDs() {

    }

    @Override
    public void initData() {
        setImageBitmap();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bt:
                if (type==Type.HTTP) {
                    type=Type.File;
                }else if (type==Type.File) {
                    type=Type.Resource;
                }else if (type==Type.Resource) {
                    type=Type.Uri;
                }else if (type==Type.Uri) {
                    type=Type.Gif;
                }else if (type==Type.Gif) {
                    type=Type.Error;
                }else if (type==Type.Error) {
                    type=Type.Mp4;
                }else if (type==Type.Mp4) {
                    type=Type.HTTP;
                }
                setImageBitmap();
                break;
        }
    }

    private void setImageBitmap() {



        switch (type) {
            case HTTP:
                Glide.with(this) .load("http://ww4.sinaimg.cn/mw1024/005PquKVgw1ezrgh5ppeyj30ku0kujvv.jpg")
                        .placeholder(R.drawable.ic_stub).dontAnimate()
                        .into(iv);
                bt.setText("HTTP");
                break;
            case File:
                Glide.with(this).load(new FileUtils().getFile(SDCardUtils.getSDCardDir(),"1.jpg"))
                        .placeholder(R.drawable.ic_stub).dontAnimate()
                        .into(iv);
                bt.setText("File");
                break;
            case Resource:
                Glide.with(this).load(R.drawable.aaaaaaaaaaaab)
                        .placeholder(R.drawable.ic_stub).dontAnimate()
                        .into(iv);
                bt.setText("Resource");
                break;
            case Uri:
                Glide.with(this).load(resourceIdToUri(this, R.drawable.aaaaaaaaaaaab))
                        .placeholder(R.drawable.ic_stub).dontAnimate()
                        .into(iv);
                bt.setText("Uri");
                break;
            case Gif:
//                Glide.load(this).load("http://ww1.sinaimg.cn/mw1024/005PquKVjw1f2jerohsz1g30ku0esb2a.gif").asGif()
                Glide.with(this).load(new FileUtils().getFile(SDCardUtils.getSDCardDir(),"abc.gif")).asGif()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .fitCenter()
                        .placeholder(R.drawable.ic_stub).dontAnimate()
                        .error(R.drawable.ic_error)
                        .into(iv);
                bt.setText("Gif");
                break;
            case Mp4:
                //todo 显示不了。。。 看到这页了 http://mrfu.me/2016/02/27/Glide_Displaying_Gifs_&_Videos/
//                File file = new FileUtils().getFile("面具男鬼步舞教程6个基本动作鬼步舞音乐 高清.mp4");
                File file2 = new FileUtils().getFile(SDCardUtils.getSDCardDir(),"DCIM","Camera","VID_20160306_125251.mp4");
                Glide.with(this).load(Uri.fromFile(file2))
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .fitCenter()
                        .placeholder(R.drawable.ic_stub).dontAnimate()
                        .error(R.drawable.ic_error)
                        .into(iv);
                bt.setText("Mp4");
                break;
            case Error:
                Glide.with(this).load(new FileUtils().getFile(SDCardUtils.getSDCardDir(),"1111.jpg"))
                        .placeholder(R.drawable.ic_stub).dontAnimate()
                        .error(R.drawable.ic_error)
                        .into(iv);
                bt.setText("Error");
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public enum Type{
        HTTP,File,Resource,Uri,Gif,Mp4,Error;
    }
    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";
    private static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }
}
