package com.example.mylib_test.activity.http;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import and.abstractclass.BaseActvity;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ImageView;
import android.widget.ListView;
import com.example.mylib_test.R;
import com.example.mylib_test.activity.http.entity.Data;
import com.example.mylib_test.app.Constant;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zone.adapter.adapter.Adapter_Zone;
import com.zone.adapter.adapter.core.ViewHolder_Zone;
import com.zone.http2rflist.RequestParamsNet;
import com.zone.http2rflist.RequestUtils;
import com.zone.http2rflist.impl.enigne.ZhttpEngine;
import com.zone.http2rflist.impl.rflist.GooglePullView;


public class NetworkPull_TestActivity extends BaseActvity  {
	final	String UrlPath = Constant.ADDRESS;
	private SwipeRefreshLayout swipe_container;
	private ListView rv;
	private ZhttpEngine engineGet;
	private static final int GET_TAG=1;
	Map<String,String> params=new HashMap<String,String>();
	private List<String> dataImg=new ArrayList<String>();
	private Adapter_Zone<String> adapter;
	private GooglePullView<String, Data> googlePullView;

	@Override
	public void setContentView() {
		params.put("name", "xoxoxxoo");
		setContentView(R.layout.a_network_pull);
		engineGet=new ZhttpEngine(this, handler);
//		engineGet.newCall(UrlPath,new RequestParamsNet().setParamsMap(params), GET_TAG);
		engineGet.newCall(RequestUtils.post(UrlPath, new RequestParamsNet().setParamsMap(params)).handlerTag(GET_TAG).build());

	}

	@Override
	public void findIDs() {
		swipe_container=(SwipeRefreshLayout)findViewById(R.id.swipe_container);
		rv=(ListView)findViewById(R.id.rv);
	}

	@Override
	public void initData() {

		adapter=new Adapter_Zone<String>(this, dataImg) {

			@Override
			public int setLayoutID() {
				return  R.layout.item_network_pull;
			}

			@Override
			public void setData(ViewHolder_Zone holder, String data,
					int position) {
				ImageView id_num=(ImageView) holder.findViewById(R.id.id_num);
				ImageLoader.getInstance().displayImage(data, id_num);
//				id_num.setText(data);
				
			}

		};
		rv.setAdapter(adapter);
		
		googlePullView=new GooglePullView<String, Data>(swipe_container, rv, adapter, dataImg) {
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
			System.err.println("size:"+dataImg.size());
//			adapter.notifyDataSetChanged();
			break;

		default:
			break;
		}
		return super.handleMessage(msg);
	}

}
