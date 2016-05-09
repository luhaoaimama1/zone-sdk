package com.zone.http2rflist.base;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import com.google.gson.Gson;
import com.zone.http2rflist.RequestParams;
import com.zone.http2rflist.callback.IBaseNetworkEngine;
import com.zone.http2rflist.utils.Pop_Zone;
import java.util.ArrayList;
import java.util.List;

//这个是处理    网络请求 dialog  与handler返回信息
public abstract class BaseNetworkEngine implements IBaseNetworkEngine {

	public enum DialogType{
		pop,dialog;
	}
	//默认级别的
	private DialogType  dialogType=DialogType.pop;

	private static  String limitColumn ="limit", offsetColumn ="offset";
	protected Handler handler;
	private boolean isShowDialog =false;
	protected int limit=10, pageNumber=0, startPage=0;
	private Dialog dialog;
	private Pop_Zone popWindow;

	private BasePullView listView;
	//不暴漏的 外部的类还用到的
	public final Context context;
	protected boolean isLastPage =false;
	//防止当前页面正处理的时候  又翻页了 这时候翻页参数会错乱
	private List<Integer> pageNumberhistory=new ArrayList<>();
	private RequestParams request;

	public BaseNetworkEngine(Context context, Handler handler) {
		this(context,handler,false);
	}
	public BaseNetworkEngine(Context context, Handler handler, boolean isShowDialog) {
		this.context=context;
		this.handler= handler;
		this.isShowDialog = isShowDialog;
	}

	@Override
	public void setStartPage(int startPage) {
		this.startPage=startPage;
		pageNumber+=startPage;
	}

	@Override
	//这个是已经请求过  就用firstPage即可
	public  void firstPage(){
		turnPageExceptionChecked();
		pageNumber=startPage;
		start();
	};
	@Override
	public  void nextPage(){
		if (!isLastPage) {
			turnPageExceptionChecked();
			pageNumber++;
			start();
		}else{
			//是最后一页的时候
			listView.onLoadMoreComplete();
		}
	};
	@Override
	public  void turnPage(int number){
		turnPageExceptionChecked();
		pageNumber=number;
		start();
	};
	private void turnPageExceptionChecked(){
		if(listView==null)
			throw new IllegalStateException("please must be use method:relatePullView!");
	}
	@Override
	//开始任务
	public void start(){
		execute(true);
	}
	@Override
	public  void prepare(RequestParams request){
		this.request=request;
		execute(false);
	}

	@Override
	public void prepare(RequestParams.Builder request) {
		this.request=request.build();
		execute(false);
	}

	private void execute(boolean run){
		if (run&&request!=null) {
			showDialog();
			relateAddTurnPage();
			//TODO 仅仅get的时候有缓存好了  内存缓存   本地缓存 然后http
			ab_Send(request);
		}
	}
	@Override
	// error  与  success都需要 发送消息  但是记住必须只有一个发出来
	public void sendhandlerMsg(final String msg,final int handlerTag){
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
						if(number==startPage){
							listView.clearData();
							//当第一页 数据处理成功  可以翻页
							isLastPage =false;
						}
						listView.addAllData2Notify();
					}
					//动画弄掉
					removeListAnimal(number, listView,parseOK);
					//把nubmerHistory处理过的 移除
					pageNumberhistory.remove(0);
					//arg1  是页数  ar2不知道 那就-1被~
					if (handlerTag !=-1)
						handler.obtainMessage(handlerTag,number,-1,msg).sendToTarget();
				}
				//动画弄掉
				private void removeListAnimal(int number, BasePullView listView,boolean parseOK) {
					if(number==startPage)
						listView.onRefreshComplete();
					else{
						if (parseOK)
							listView.onLoadMoreComplete();
						else
							listView.onLoadMoreFail();
					}
				}
			});
		}else{
			//未联动 发送信息 到handler
			if (handlerTag ==-1)
				throw new IllegalStateException("handlerTag must be set!");
			handler.obtainMessage(handlerTag,msg).sendToTarget();
		}
	}
	@Override
	//建立list联动后 会添加翻页功能
	public void relatePullView(BasePullView  listView ){
		this.listView=listView;
		listView.relateBaseNetworkQuest(this);
	}
	private void relateAddTurnPage(){
		if(listView!=null){
			request.params.put(limitColumn, limit + "");
			pageNumberhistory.add(pageNumber);
	        int offest = limit* pageNumber;
			request.params.put(offsetColumn, offest + "");
		}
	}
	protected abstract void ab_Send(RequestParams request);

	//设置 默认的dialog
	protected  abstract Dialog createDefaultDialog(Context context);
	//设置 默认的popWindow
	protected  abstract Pop_Zone createDefaultPopWindow(Context context);
	@Override
	//设置dialog
	public  void setDialog(Dialog dialog){
		this.dialog=dialog;
	};
	@Override
	//设置popWindow
	public  void setPopWindow(Pop_Zone popWindow){
		this.popWindow=popWindow;
	};

	//listView请求的时候 不展示Dialog
	private void showDialog() {
		if (listView==null) {
			switch (dialogType) {
				case pop:
					if (popWindow == null&& isShowDialog)
						popWindow = createDefaultPopWindow(context);
					//这样没有dialog也不会爆空了
					if (popWindow != null&&!popWindow.isShowing())
						popWindow.show();
					break;
				case dialog:
					if (dialog == null&& isShowDialog)
						dialog = createDefaultDialog(context);
					//这样没有dialog也不会爆空了
					if (dialog != null&&dialog.isShowing())
						dialog.show();
					break;
			}
		}
	}
	private void hideDialog() {
		if (isShowDialog) {
			if (dialog != null&&dialog.isShowing())
				dialog.dismiss();
			if(popWindow!=null&&popWindow.isShowing())
				popWindow.dismiss();
		}
	}
	@Override
	public boolean isShowDialog() {
		return isShowDialog;
	}
	@Override
	public void setShowDialog(boolean showDialog) {
		this.isShowDialog = showDialog;
	}
	@Override
	public  <A> A gsonParseNoRelateList(String msg, Class<A> clazz){
		boolean resultIsRight= MsgErrorCheck.errorChecked(msg);
		if(!resultIsRight)
			return null;
		Gson g=new Gson();
		return 	g.fromJson(msg, clazz);
	};
	@Override
	public int getLimit() {
		return limit;
	}
	@Override
	public void setLimit(int limit) {
		this.limit = limit;
	}

	public static String getLimitColumn() {
		return limitColumn;
	}

	public static void setLimitColumn(String limitColumn) {
		BaseNetworkEngine.limitColumn = limitColumn;
	}

	public static String getOffsetColumn() {
		return offsetColumn;
	}

	public static void setOffsetColumn(String offsetColumn) {
		BaseNetworkEngine.offsetColumn = offsetColumn;
	}
}
