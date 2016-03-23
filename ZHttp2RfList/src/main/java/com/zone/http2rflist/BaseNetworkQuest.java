package com.zone.http2rflist;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;

import com.zone.http2rflist.callback.NetworkListener;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//这个是处理    网络请求 dialog  与handler返回信息
//TODO 暂时不支持多种请求因为 那些我没见过
//TODO  缓存暂时没有
//TODO  cancel没有  因为貌似okhttp有多种  了解之后才可以写
//TODO  大文件下载 应该是独立的一部分 不属于这里 所以就不封装了
public abstract class BaseNetworkQuest {
	public Handler handler;
	private NetworkListener listener;
	private Map<String,String> map;
	private Map<String,File> fileMap;
	private int tag;
	private  String urlString;
	
	private boolean showDialog=false;
	private Dialog dialog;

	private BasePullView listView;
	public static final String LIMIT="limit";
	public static final String OFFSET="offset";
	private  int limit=10, pageNumber=0;
	private List<Integer> pageNumberhistory=new ArrayList<Integer>();
	private boolean isGet=true;
	private Context context;
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
		exceptionChecked();
		pageNumber=0;
		startTask();
	};
	public  void nextPage(){
		 exceptionChecked();
		 pageNumber++;
		 startTask();
	};
	public  void trunPage(int number){
		exceptionChecked();
		pageNumber=number;
		startTask();
	};
	private void exceptionChecked(){
		if(listView==null)
			throw new IllegalStateException("please must be use  associationList!");
	}
	
	//开始任务
	public void startTask(){
		 sendFile(urlString, map, fileMap, tag, listener,true);
	}
	//map type  post 默认 get 
	public  void send(String urlString,Map<String,String> map,int tag,NetworkListener listener){
		isGet=true;
		sendFile(urlString, map, null, tag, listener);
	};
	public  void send(String urlString,Map<String,String> map,int tag){
		send(urlString, map, tag, null);
	};
	public  void sendPost(String urlString,Map<String,String> map,int tag,NetworkListener listener){
		isGet=false;
		sendFile(urlString, map, null, tag, listener);
	};
	public  void sendPost(String urlString,Map<String,String> map,int tag){
		sendPost(urlString, map, tag, listener);
	};
	public  void sendFile(String urlString,Map<String,String> map,Map<String,File> fileMap,int tag,NetworkListener listener){
		this.urlString=urlString;
		this.map=map;
		this.fileMap=fileMap;
		this.tag=tag;
		this.listener=listener;
		sendFile(urlString, map, fileMap, tag, listener,false);
	}
	public  void sendFile(String urlString,Map<String,String> map,Map<String,File> fileMap,int tag){
		sendFile(urlString, map, fileMap, tag, null);
	}
	private  void sendFile(String urlString,Map<String,String> map,Map<String,File> fileMap,int tag,NetworkListener listener,boolean run){
		if (run) {
			showDialog();
			relateAddTurnPage(map);
			if (fileMap == null) {
				if (isGet) {
					//TODO 仅仅get的时候有缓存好了  内存缓存   本地缓存 然后http
					ab_Send(urlString, map, tag, listener);
				} else
					ab_SendPost(urlString, map, tag, listener);
			} else
				ab_SendFile(urlString, map, fileMap, tag, listener);
		}
	}
	// TODO  error  与  success都需要 发送消息  但是记住必须只有一个发出来
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
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					int number=pageNumberhistory.get(0);
					//数据处理
					listView.gsonParse(msg);
					if(number==0)
						listView.clearData();
					removeListAnimal(number,listView);
					//动画弄掉
					listView.addAllData2Notify();
					//把nubmerHistory处理过的 移除
					pageNumberhistory.remove(0);
					handler.obtainMessage(tag,msg).sendToTarget();
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
	public void relateAddTurnPage(Map<String, String> map){
		if(listView!=null){
			map.put(LIMIT, limit+ "");
			pageNumberhistory.add(pageNumber);
	        int offest = limit* pageNumber;
	        map.put(OFFSET, offest + "");
		}
	} 
	protected abstract void ab_Send(String urlString, Map<String, String> map, int tag, NetworkListener listener);
	protected abstract void ab_SendPost(String urlString, Map<String, String> map, int tag, NetworkListener listener);
	protected  abstract void ab_SendFile(String urlString, Map<String, String> map,Map<String, File> fileMap, int tag,
			NetworkListener listener);
	//设置 默认的dialog 
	protected  abstract Dialog createDefaultDialog(Context context);
	//设置dialog   
	public  void setDialog(Dialog dialog){
		this.dialog=dialog;
	};
	protected   void showDialog(){
		if(dialog==null)
			dialog=createDefaultDialog(context);
		//这样没有dialog也不会爆空了
		if(dialog!=null)
			dialog.show();
	};
	protected   void hideDialog(){
		if(dialog!=null)
			dialog.dismiss();
	}
	
	public boolean isShowDialog() {
		return showDialog;
	}
	public void setShowDialog(boolean showDialog) {
		this.showDialog = showDialog;
	}
	
	public  <A> A gsonParseNoRelateList(String msg, Class<A> clazz){
		boolean resultIsRight=MsgCheck.errorChecked(msg);
		if(!resultIsRight)
			return null;
		Gson g=new Gson();
		return 	g.fromJson(msg, clazz);
	};
	void relateReturnEmptyData(){
		if(pageNumber>0)
			pageNumber--;
	}

	
}
