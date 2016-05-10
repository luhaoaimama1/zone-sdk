package com.zone.http2rflist.base;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.zone.http2rflist.utils.ExceptionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

//一个listView即封装一个T
public abstract class BasePullView<T,K,M,E,A> {
	protected final Context context;
	public  T pullView;
	public  M adapter;
	public  List<E> data;
	public  Class<A> clazz;
	//这里是gson转换过来的
	private  A entity;
	public K listView;
	public BaseNetworkEngine baseNetworkQuest;
//	public  OnRefresh2LoadMoreListener listener;
	public  BasePullView(Context context,T pullView,K listView,M adapter,List<E> data) {
		this.context=context;
		this.pullView=pullView;
		this.listView=listView;
		this.adapter=adapter;
		this.data=data;
		setType();
	}
	
	public  void relateBaseNetworkQuest(BaseNetworkEngine baseNetworkQuest){
		this.baseNetworkQuest=baseNetworkQuest;
	}
	//通过泛型得到类
	@SuppressWarnings("unchecked")
	private void setType(){
		Type superClass = getClass().getGenericSuperclass();
		Type[] types = ((ParameterizedType)superClass).getActualTypeArguments();
		this.clazz = (Class<A>)types[types.length-1]; 
	}
	public  boolean gsonParse(String msg){
		boolean resultIsRight= MsgErrorCheck.errorChecked(msg);
		if(!resultIsRight)
			return false;
		Gson g=new Gson();
		try {
			entity=g.fromJson(msg, clazz);
		} catch (JsonSyntaxException e) {
			ExceptionUtils.quiet(e);
			return false;
		}
		return true;
	};
	public void clearData(){
		if (entity != null&& getAdapterData(entity).size()!= 0)
				data.clear();
	}
	public void addAllData2Notify(){
		if (entity!=null) {
			if(getAdapterData(entity).size()==0){
				//todo 又问题
//				baseNetworkQuest.relateReturnEmptyData();
				baseNetworkQuest.isLastPage =true;
				lastPageRemoveOnLoadListener();
			}else {
				baseNetworkQuest.isLastPage =false;
				if(getAdapterData(entity).size()<baseNetworkQuest.getLimit()){
					//不能上拉操作
					baseNetworkQuest.isLastPage =true;
					lastPageRemoveOnLoadListener();
				}

				data.addAll(getAdapterData(entity));
				notifyDataSetChanged();
			}
		}
	}
	

	public abstract void onRefreshComplete();
	public abstract void onLoadMoreComplete();
	public abstract void lastPageRemoveOnLoadListener();
	public abstract void onLoadMoreFail();
	public abstract void notifyDataSetChanged();
	public abstract List<E> getAdapterData(A entity);

//	public  void setOnRefresh2LoadMoreListener(OnRefresh2LoadMoreListener listener){
//		this.listener=listener;
//	};
//	public interface OnRefresh2LoadMoreListener{
//		//if pageNumber==-1 说明你内嵌的框架 不支持此功能
//		 void loadMore(int pageNumber) ;
//		 void onRefresh() ;
//	}
}
