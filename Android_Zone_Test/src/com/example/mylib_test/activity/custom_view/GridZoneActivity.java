package com.example.mylib_test.activity.custom_view;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import com.bumptech.glide.Glide;
import com.example.mylib_test.R;
import com.example.mylib_test.Images;
import com.zone.view.ninegridview.TouchGreyImageView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import and.base.activity.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.zone.view.ninegridview.ZGridViewAdapter;
import com.zone.view.ninegridview.ZGridView;
import com.zone.view.ninegridview.preview.ImageInfo;
import com.zone.view.ninegridview.preview.ImagePreviewAdapter;

/**
 * Created by Administrator on 2016/4/12.
 */
public class GridZoneActivity extends BaseActivity {
    @Bind(R.id.gvz)
    ZGridView gvz;
    @Bind(R.id.bt_change)
    Button btChange;

    @Override
    public void setContentView() {
        setContentView(R.layout.a_gridzone);
        ButterKnife.bind(this);
    }

    @Override
    public void findIDs() {

    }

    @Override
    public void initData() {
        List<String> list = Arrays.asList(Images.imageThumbUrls);
        final List<String> sub = list.subList(0, 5);
        gvz.setColumns(3);
        gvz.setGap(10);
        gvz.setAdapter(new ZGridViewAdapter(sub) {
            @Override
            public void onItemImageClick(Context context, int index, Object data) {
                System.out.println("onItemImageClick index:" + index);
            }

            @Override
            public void onItemImageLongClick(Context context, int index, Object data) {
                System.out.println("onItemImageLongClick index:" + index);
            }

            @Override
            public View getView(Context context, int index) {
                View item = LayoutInflater.from(context).inflate(R.layout.grid_item_pic, null);
                TouchGreyImageView tgiv = (TouchGreyImageView) item.findViewById(R.id.tgiv);
//                tgiv.setClickable(true);
                Glide.with(context).load(sub.get(index)).into(tgiv);
                return item;
            }
        });
    }

    @Override
    public void setListener() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bt_change:
                List<String> list = Arrays.asList(Images.imageThumbUrls);
                final List<String> sub = list.subList(0, 6);
                gvz.setColumns(3);
                gvz.setGap(10);
                List<ImageInfo> listIf=new ArrayList<>();
                for (String s : sub) {
                    listIf.add(new ImageInfo(s,s));
                }
                gvz.setAdapter(new ImagePreviewAdapter(listIf));
                break;
        }
    }
}
