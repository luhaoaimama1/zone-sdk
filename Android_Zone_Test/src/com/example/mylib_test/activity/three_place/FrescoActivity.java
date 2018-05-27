package com.example.mylib_test.activity.three_place;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.mylib_test.R;
import com.example.mylib_test.delegates.FrescoDeletates;
import com.zone.adapter3.QuickRcvAdapter;
import com.zone.adapter3.base.IAdapter;
import com.zone.lib.base.activity.BaseActivity;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/4.
 */
public class FrescoActivity extends BaseActivity {
    @Bind(R.id.rv)
    RecyclerView rv;

    private List<Entity> mDatas = new ArrayList<>();
    private IAdapter<Entity> muliAdapter;

    public class Entity {
        public String introduce;
        public String uri;

        public Entity(String introduce, String uri) {
            this.introduce = introduce;
            this.uri = uri;
        }
    }


    @Override
    public void setContentView() {
        setContentView(R.layout.a_fresco);
        ButterKnife.bind(this);
    }

    @Override
    public void findIDs() {

    }

    @Override
    public void initData() {
        //todo   HTTP,File,Resource,Uri,Gif,Mp4,Error;
        mDatas.add(new Entity("http","https://raw.githubusercontent.com/facebook/fresco/gh-pages/static/logo.png"));
        mDatas.add(new Entity("File","https://raw.githubusercontent.com/facebook/fresco/gh-pages/static/logo.png"));
        mDatas.add(new Entity("Resource","https://raw.githubusercontent.com/facebook/fresco/gh-pages/static/logo.png"));
        mDatas.add(new Entity("Uri","https://raw.githubusercontent.com/facebook/fresco/gh-pages/static/logo.png"));
        mDatas.add(new Entity("Gif","https://raw.githubusercontent.com/facebook/fresco/gh-pages/static/logo.png"));
        mDatas.add(new Entity("Mp4","https://raw.githubusercontent.com/facebook/fresco/gh-pages/static/logo.png"));
        mDatas.add(new Entity("Error","https://raw.githubusercontent.com/facebook/fresco/gh-pages/static/logo.png"));
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());
        muliAdapter = new QuickRcvAdapter(this, mDatas) {
//            @Override
//            protected int getItemViewType2(int dataPosition) {
//                return dataPosition % 3 == 0 ? 1 : 0;
//            }
        };
        muliAdapter
                .addViewHolder(new FrescoDeletates())//默认
                .relatedList(rv)
                .addItemDecoration(10);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    private static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }
}
