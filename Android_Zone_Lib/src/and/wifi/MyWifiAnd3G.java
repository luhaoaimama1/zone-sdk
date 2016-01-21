package and.wifi;

import java.lang.reflect.Method;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;

/**
 * 所有有关wifi与3G的类已经加载完毕  
 * <br>为完成  wifi SSID重复问题  当遇到问题在修改
 * <br> private 没有 get 用到再说吧  mWifiConfiguration mNetWorkInfo OK了
 * @author Zone
 *
 */
public class MyWifiAnd3G {
	
	// 管理连接
	private ConnectivityManager mConnectivityManager;
	private WifiManager mWifiManager;
	
	// 定义NetworkInfo、WifiInfo对象
	private NetworkInfo mNetWorkInfo;
	private WifiInfo mWifiInfo;

	// 扫描出的网络连接列表、网络连接列表
	private List<ScanResult> mWifiList;
	private List<WifiConfiguration> mWifiConfiguration;
	
	// 定义一个WifiLock
	private WifiLock mWifiLock;

	
	//	<!-- WIFI权限 -->
	//    <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
	//    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
	//    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
	//    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
	
	/**
	 * 添加wifi权限  进入lib里 可看到<br> 
	 * 初始化 配置 <br>
	 * @param context
	 */
	public MyWifiAnd3G(Context context) {
		//初始化 Manager
		mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		//初始化 当前的wifiInfo、netWorkInfo
		mWifiInfo = mWifiManager.getConnectionInfo();
		mNetWorkInfo=mConnectivityManager.getActiveNetworkInfo();
//		Log.e("查看的东西", "WifiManager信息是:"+mWifiManager.toString()+"\n"+"ConnectivityManager信息是:"+mConnectivityManager.toString()+"\n"+"WifiInfo信息是:"+mWifiInfo.toString()+"\n");
		
		
	}
	
	/**
	 * 得到配置好的网络
	 * 
	 * @return
	 */
	public List<WifiConfiguration> getConfiguration() {
		return mWifiManager.getConfiguredNetworks();
	}
	/**
	 * @return 当前的WifiInfo
	 */
	public WifiInfo getWifiInfo() {
		return mWifiManager.getConnectionInfo();
	}

	
	/**
	 * 打开或关闭当前3G网络
	 * 
	 * @param value
	 *            true 打开 false 关闭
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object openOrClose3GNet(boolean value) {
		Class ownerClass = mConnectivityManager.getClass();
		Class[] argsClass = new Class[1];
		argsClass[0] = boolean.class;
		Method method;
		Object obj = null;
		try {
			method = ownerClass.getMethod("setMobileDataEnabled", argsClass);
			obj = method.invoke(mConnectivityManager, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	/**
	 * 
	 * @return  返回3gNet的状态
	 * @throws Exception
	 */
	public boolean state3GNet()  {
		Class<? extends ConnectivityManager> ownerClass = mConnectivityManager
				.getClass();
		boolean state = false;
		try {
			Method method = ownerClass.getMethod("getMobileDataEnabled");
			state = (Boolean) method.invoke(mConnectivityManager);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return state;
	}

	/**
	 * 判断当前WIFI是否处于打开状态
	 * 
	 * @return
	 */
	public boolean isWiFiActive() {
		return mWifiManager.isWifiEnabled();
	}
	/**
	 * 返回当前Wifi状态
	 * 
	 * @return
	 */
	public int checkState() {
		// WIFI_STATE_DISABLED WIFI关闭
		// WIFI_STATE_DISABLING WIFI正在关闭
		// WIFI_STATE_ENABLED WIFI已打开
		// WIFI_STATE_ENABLING WIFI正在打开
		// WIFI_STATE_UNKNOWN WIFI状态未知
		return mWifiManager.getWifiState();
	}
	/**
	 * 打开Wifi
	 */
	public void openWifi() {
		if (!mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(true);//false是关闭wifi true是打开wifi
			mWifiManager.isWifiEnabled();//wifi是开着的吗？
		}
	}

	/**
	 * 关闭Wifi
	 */
	public void closeWifi() {
		if (mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(false);
		}
	}


	/**
	 * 判断手机当前是否联网
	 * 
	 * @return
	 */
	public boolean isNetwork() {
		if (mNetWorkInfo == null) {
			return false;
		}
			return mNetWorkInfo.isAvailable();
	}
	/**
	 * 判断手机当前网络类型  WifiOr3G
	 * 
	 * @return  0:无网络 1:3g 2:WIFI
	 */
	public int getNowConnectType() {
		if (mNetWorkInfo == null) {
			return 0;
		}else
		{
			if("WIFI".equals(mNetWorkInfo.getTypeName()))
			{
				return 2;
			}
		}
		return 1;
		
	}

	
	/**
	 *  连接指定的配置好的网络进行 
	 * 
	 * @param wcfg     WifiConfiguration(能在此类中get到)
	 * @param mostCount     最多连多少次
	 * @param sleepMs     每次睡多少毫秒
	 */
	public boolean connectConfiguration(WifiConfiguration wcfg,int mostCount,int sleepMs) {
		// 索引大于配置好的网络索引返回
		if (wcfg ==null) {
			System.err.println("WifiConfiguration wcfg为null！！！");
			return  false;
		}
		mWifiInfo=mWifiManager.getConnectionInfo();
		if (mWifiInfo != null && mWifiInfo.getNetworkId() == wcfg.networkId&&mWifiInfo.getLinkSpeed()>0) {
			// 不连接  有wifi并且当前wifi  是这个即将要连接的wifi
			System.out.println("当前连接的wifi 已经连接上 不必重复连接~~~");
			return  true;

		} else {
			// 连接配置好的指定ID的网络
			System.out.println("要连接的netid:\t" + wcfg.networkId);
			boolean connStatue= mWifiManager.enableNetwork(wcfg.networkId, true);
			boolean  resultStatue=false;
			if (connStatue) {
				// 当连接 wifi 成功的时候
				int i = 1;
				while (i <= mostCount) {
					try {
						if (getWifiInfo().getNetworkId() == wcfg.networkId
								&& WifiManager.WIFI_STATE_ENABLED == mWifiManager.getWifiState()) {
							resultStatue = true;
							System.out.println("wifi开启成功success!!!");
							break;
						}
						Thread.sleep(sleepMs);
						if (i == 1) {
							System.out.println("每次等待时间为:\t" + sleepMs + "\tms");
						}
						System.out.println("等待wifi完全开启  第" + i + "次");
						i++;
					} catch (InterruptedException e) {
						e.printStackTrace();
						System.err.println("Thread sleep发生异常");
					}
				}
				if (!resultStatue) {
					System.err.println("连接fail");
				}
			}
		
			return resultStatue;
		}
		
	}
	/**
	 * 断开当前网络
	 */
	public boolean disconnectWifi() {

		// 断开指定的 wifi连接
		// mWifiManager.disableNetwork(wcfg.networkId);
		// 断开当前wifi的连接 一个意思
		mWifiInfo=mWifiManager.getConnectionInfo();
		if (mWifiInfo == null) {
			System.out.println("当前没有网络连接 故不用断开~~~");
			return true;
		}
		return mWifiManager.disconnect();
	}
	/**
	 * 搜索网络
	 */
	public void startScan() {
		mWifiManager.startScan();
		// 得到扫描结果
		mWifiList = mWifiManager.getScanResults();
		// 得到配置好的网络连接
		mWifiConfiguration = mWifiManager.getConfiguredNetworks();
	}

	/**
	 * 查看扫描结果
	 * 
	 * @return
	 */
	@SuppressLint("UseValueOf")
	public StringBuilder lookUpScan() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < mWifiList.size(); i++) {
			stringBuilder
					.append("Index_" + new Integer(i + 1).toString() + ":");
			// 将ScanResult信息转换成一个字符串包
			stringBuilder.append((mWifiList.get(i)).toString());
			stringBuilder.append("/n");
		}
		return stringBuilder;
	}


	/**
	 * 
	 * @return  也是例如：ip解析后地址：192.168.60.104
	 */
	public String getIPAddress() {
		if (mWifiInfo == null) {
			return null;
		} else {
			//例如：得到IP地址 ip :1748805824
			int ip = mWifiInfo.getIpAddress();
			return ((ip & 0xff) + "." + (ip >> 8 & 0xff) + "."+ (ip >> 16 & 0xff) + "." + (ip >> 24 & 0xff));
		}
	}


	
	/**
	 * 锁定WifiLock
	 */
	public void acquireWifiLock() {
		mWifiLock.acquire();
	}

	/**
	 * 解锁WifiLock
	 */
	public void releaseWifiLock() {
		// 判断时候锁定
		if (mWifiLock.isHeld()) {
			mWifiLock.acquire();
		}
	}

	/**
	 * 创建一个WifiLock
	 */
	public void creatWifiLock() {
		mWifiLock = mWifiManager.createWifiLock("Test");
	}

	
	

}
