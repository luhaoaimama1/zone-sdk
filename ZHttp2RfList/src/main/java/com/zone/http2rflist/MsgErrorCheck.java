package com.zone.http2rflist;

public  class MsgErrorCheck {
	private static MsgCheckCallBack msgCheckCallBack;
	//检测 网络上回来的数据是否 是正确的格式  如果不  既不gson解析
	public static  boolean  errorChecked(String msg){
		if(msgCheckCallBack!=null)
			return msgCheckCallBack.errorChecked(msg);
		return true;
	};
	public static void setMsgCheckCallBack(MsgCheckCallBack msgCheckCallBack){
		MsgErrorCheck.msgCheckCallBack=msgCheckCallBack;
	};
	public interface MsgCheckCallBack{
		//这里可以在错误的时候发送信息
		boolean errorChecked(String msg);
	}
}
