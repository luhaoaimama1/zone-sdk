package com.zone.http2rflist;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;

import com.zone.http2rflist.callback.NetworkListener;
import com.google.gson.Gson;
import com.zone.http2rflist.entity.HttpTypeNet;

import java.util.ArrayList;
import java.util.List;

//这个是处理    网络请求 dialog  与handler返回信息
public abstract class BaseNetworkQuest {
	private static  String limitColumn ="limit", offsetColumn ="offset";
	private Handler handler;
	private boolean showDialog=false;
	private int limit=10, pageNumber=0;
	private Dialog dialog;
	private BasePullView listView;
	private RequestParamsNet params;
	private NetworkListener listener;
	private  String urlString;
	private int tag;
	//不暴漏的 外部的类还用到的
	protected Context context;
	protected boolean  lastPage=false;
	//防止当前页面正处理的时候  又翻页了 这时候翻页参数会错乱
	private List<Integer> pageNumberhistory=new ArrayList<>();

	public BaseNetworkQuest(Context context,Handler handler) {
		this(context,handler,false);
	}
	public BaseNetworkQuest(Context context,Handler handler,boolean showDialog) {
		this.context=context;
		this.handler= handler;
		this.showDialog= showDialog;
	}
	
	//这个是已经请求过  就用firstPage即可
	public  void firstPage(){
		turnPageExceptionChecked();
		pageNumber=0;
		startTask();
	};
	public  void nextPage(){
		if (!lastPage) {
			turnPageExceptionChecked();
			pageNumber++;
			startTask();
		}else{

		}
	};
	public  void turnPage(int number){
		turnPageExceptionChecked();
		pageNumber=number;
		startTask();
	};
	private void turnPageExceptionChecked(){
		if(listView==null)
			throw new IllegalStateException("please must be use method:relateList!");
	}

	//开始任务
	public void startTask(){
		sendFileFinal(true);
	}
	//map type  post 默认 get
	//-----------------------------get------------------------
	public  void send(String urlString,int tag){
		send(urlString, null, tag, null);
	};
	public  void send(String urlString,RequestParamsNet params,int tag){
		send(urlString, params, tag, null);
	};
	public  void send(String urlString,RequestParamsNet params,int tag,NetworkListener listener){
		sendFile(urlString, params, tag, listener);
	};
	//-----------------------------head-------------------------
	public void sendHead(String urlString,int tag){
		sendHead(urlString, null, tag, null);
	};
	public  void sendHead(String urlString,RequestParamsNet params,int tag){
		sendHead(urlString, params, tag, null);
	};
	public  void sendHead(String urlString,RequestParamsNet params,int tag,NetworkListener listener){
		params.setHttpTypeNet(HttpTypeNet.HEAD);
		sendFile(urlString, params, tag, listener);
	};
	//-----------------------------delete-------------------------
	public void sendDelete(String urlString,int tag){
		sendDelete(urlString, null, tag, null);
	};
	public  void sendDelete(String urlString,RequestParamsNet params,int tag){
		sendDelete(urlString, params, tag, null);
	};
	public  void sendDelete(String urlString,RequestParamsNet params,int tag,NetworkListener listener){
		params.setHttpTypeNet(HttpTypeNet.DELETE);
		sendFile(urlString, params, tag, listener);
	};
	//-----------------------------post-------------------------
	public  void sendPost(String urlString,RequestParamsNet params,int tag){
		sendPost(urlString, params, tag, listener);
	};
	public  void sendPost(String urlString,RequestParamsNet params,int tag,NetworkListener listener){
		params.setHttpTypeNet(HttpTypeNet.POST);
		sendFile(urlString, params, tag, listener);
	};
	//-----------------------------jsonStr-------------------------
	public  void sendPostJson(String urlString, RequestParamsNet params,int tag){
		sendPostJson(urlString, params, tag, listener);
	};
	public  void sendPostJson(String urlString,RequestParamsNet params,int tag,NetworkListener listener){
		params.setHttpTypeNet(HttpTypeNet.POST.postJson());
		sendFile(urlString, params, tag, listener);
	};
	//-----------------------------put-------------------------
	public  void sendPut(String urlString,RequestParamsNet params,int tag){
		sendPut(urlString, params, tag, listener);
	};
	public  void sendPut(String urlString,RequestParamsNet params,int tag,NetworkListener listener){
		params.setHttpTypeNet(HttpTypeNet.PUT);
		sendFile(urlString, params, tag, listener);
	};
	//-----------------------------patch-------------------------
	public  void sendPatch(String urlString,RequestParamsNet params,int tag){
		sendPatch(urlString, params, tag, listener);
	};
	public  void sendPatch(String urlString,RequestParamsNet params,int tag,NetworkListener listener){
		params.setHttpTypeNet(HttpTypeNet.PATCH);
		sendFile(urlString, params, tag, listener);
	};

	public  void sendFile(String urlString,RequestParamsNet params,int tag){
		sendFile(urlString, params, tag, null);
	}
	public  void sendFile(String urlString,RequestParamsNet params,int tag,NetworkListener listener){
		this.params=params;
		this.urlString=urlString;
		this.tag=tag;
		this.listener=listener;
		sendFileFinal(false);
	}
	private  void sendFileFinal(boolean run){
		if (run&&params!=null) {
			showDialog();
			relateAddTurnPage();
			//TODO 仅仅get的时候有缓存好了  内存缓存   本地缓存 然后http
			ab_Send(urlString, params, tag, listener);
			}
	}
	// error  与  success都需要 发送消息  但是记住必须只有一个发出来
	public void sendhandlerMsg(final String msg,final int tag){
		//把dialog弄掉
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				hideDialog();
			}
		});
		//联动list 动画弄掉   数据处理
		if(listView!=null&&pageNumberhistory.size()>0){
			//handler就是串行的
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					int number=pageNumberhistory.get(0);
					//数据处理
					boolean parseOK=listView.gsonParse(msg);
					if (parseOK) {
						if(number==0){
							listView.clearData();
							//当第一页 数据处理成功  可以翻页
							lastPage=false;
						}
						listView.addAllData2Notify();
					}
					//动画弄掉
					removeListAnimal(number, listView);
					//把nubmerHistory处理过的 移除
					pageNumberhistory.remove(0);
					//arg1  是页数  ar2不知道 那就-1被~
					handler.obtainMessage(tag,number,-1,msg).sendToTarget();
				}
				//动画弄掉
				private void removeListAnimal(int number, BasePullView listView) {
					if(number==0)
						listView.onRefreshComplete();
					else
						listView.onloadMoreComplete();
				}
			});
		}else{
			//未联动 发送信息 到handler
			handler.obtainMessage(tag,msg).sendToTarget();
		}
	}
	//建立list联动后 会添加翻页功能
	public void relateList(BasePullView  listView ){
		this.listView=listView;
		listView.relateBaseNetworkQuest(this);
	}
	public void relateAddTurnPage(){
		if(listView!=null){
			params.put(limitColumn, limit + "");
			pageNumberhistory.add(pageNumber);
	        int offest = limit* pageNumber;
			params.put(offsetColumn, offest + "");
		}
	}
	protected abstract void ab_Send(String urlString, RequestParamsNet params ,int tag, NetworkListener listener);
	protected  abstract void cancelAllRequest();
	//设置 默认的dialog
	protected  abstract Dialog createDefaultDialog(Context context);
	//设置dialog
	public  void setDialog(Dialog dialog){
		this.dialog=dialog;
	};

	//listView请求的时候 不展示Dialog
	protected void showDialog() {
		if (listView==null) {
			if (dialog == null)
                dialog = createDefaultDialog(context);
			//这样没有dialog也不会爆空了
			if (dialog != null)
                dialog.show();
		}
	}

	protected void hideDialog() {
		if (dialog != null&&dialog.isShowing())
			dialog.dismiss();
	}
	
	public boolean isShowDialog() {
		return showDialog;
	}
	public void setShowDialog(boolean showDialog) {
		this.showDialog = showDialog;
	}
	
	public  <A> A gsonParseNoRelateList(String msg, Class<A> clazz){
		boolean resultIsRight= MsgErrorCheck.errorChecked(msg);
		if(!resultIsRight)
			return null;
		Gson g=new Gson();
		return 	g.fromJson(msg, clazz);
	};
	//如果得到的数据是0既刚刚翻得那一页 是错误的 所以需要减回来
	void relateReturnEmptyData(){
		if(pageNumber>0)
			pageNumber--;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public static String getLimitColumn() {
		return limitColumn;
	}

	public static void setLimitColumn(String limitColumn) {
		BaseNetworkQuest.limitColumn = limitColumn;
	}

	public static String getOffsetColumn() {
		return offsetColumn;
	}

	public static void setOffsetColumn(String offsetColumn) {
		BaseNetworkQuest.offsetColumn = offsetColumn;
	}
}
