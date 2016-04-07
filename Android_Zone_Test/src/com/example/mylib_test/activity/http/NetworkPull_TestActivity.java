package com.example.mylib_test.activity.http;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import and.base.activity.BaseActvity;
import in.srain.cube.views.ptr.PtrFrameLayout;

import android.os.Message;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.example.mylib_test.R;
import com.example.mylib_test.activity.http.entity.Data;
import com.example.mylib_test.app.Constant;
import com.zone.adapter.QuickAdapter;
import com.zone.adapter.callback.Helper;
import com.zone.http2rflist.NetworkParams;
import com.zone.http2rflist.RequestParamsUtils;
import com.zone.http2rflist.impl.enigne.ZhttpEngine;
import com.zone.http2rflist.impl.rflist.UltraPullView;


public class NetworkPull_TestActivity extends BaseActvity  {
	final	String UrlPath = Constant.ADDRESS;
	private PtrFrameLayout swipe_container;
	private ListView rv;
	private ZhttpEngine engineGet;
	private static final int GET_TAG=1;
	Map<String,String> params=new HashMap<String,String>();
	private List<String> dataImg=new ArrayList<String>();
	private QuickAdapter<String> adapter;
	private UltraPullView<String, Data> googlePullView;

	@Override
	public void setContentView() {
		params.put("name", "xoxoxxoo");
		setContentView(R.layout.a_network_pull);
		engineGet=new ZhttpEngine(this, handler);
//		engineGet.newCall(UrlPath,new RequestParamsNet().setParamsMap(params), GET_TAG);
		engineGet.newCall(RequestParamsUtils.post(UrlPath, new NetworkParams().setParamsMap(params))
				.handlerTag(GET_TAG)
				.build());

	}

	@Override
	public void findIDs() {
		swipe_container=(PtrFrameLayout)findViewById(R.id.swipe_container);
		rv=(ListView)findViewById(R.id.rv);
	}

	@Override
	public void initData() {

		adapter=new QuickAdapter<String>(this, dataImg) {
			@Override
			public void fillData(Helper helper, String item, boolean itemChanged, int layoutId) {
				ImageView id_num=(ImageView) helper.getView(R.id.id_num);
//				ImageLoader.getInstance().displayImage(item, id_num);
				Glide.with(NetworkPull_TestActivity.this).load(item)
						.placeholder(R.drawable.ic_stub).dontAnimate()
						.error(R.drawable.ic_error)
						.into(id_num);
			}

			@Override
			public int getItemLayoutId(String s, int position) {
				return  R.layout.item_network_pull;
			}
		};
		adapter.relatedList(rv);
		googlePullView=new UltraPullView<String, Data>(this,swipe_container, rv, adapter, dataImg) {
			@Override
			public List<String> getAdapterData(Data entity) {
				return entity.getImgEntity().getImg();
			}
		};
		engineGet.relateList(googlePullView);
	}

	@Override
	public void setListener() {
		
	}
	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case GET_TAG:
//			System.out.println("GET_TAG:"+msg.obj);
			System.err.println("handleMessage size:"+dataImg.size());
//			adapter.notifyDataSetChanged();
			break;

		default:
			break;
		}
		return super.handleMessage(msg);
	}

}
