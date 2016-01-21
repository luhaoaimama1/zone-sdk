package and.log;

import android.util.Log;

/**
 * The class for print log
 * TODO 当多用户的时候　是否要区分用户呢　　反正暂时不需要
 */
public class Logger_Zone {
	
	public enum LogStatue{
		Open,Close,Child_Control
	}
	
	//全局控制  默认关闭
	private  static LogStatue logAllFlag = LogStatue.Close;
	private static boolean infoLocation=false;
	//单个logger的的控制
	private boolean  logflag=true;
	private  String projectName="";
	private String mClassName;
	private String prefix="--------->";
	public Logger_Zone(Class<?> clas,Config config) {
		mClassName = clas.getName();
		projectName=config.projectName;
	}
	//常量中配置的config　　
	public static class Config {
		private String projectName = "";

		public Config(String projectName) {
			this.projectName = projectName;
		}

		public String getAppName() {
			return projectName;
		}

		public void setAppName(String appName) {
			this.projectName = appName;
		}
		
	}
	
	public  Logger_Zone closeLog(){
		 logflag=false;
		 return this;
	};
	public  Logger_Zone openLog(){
		 logflag=true;
		 return this;
	};
	public static void setAllLogStatue(LogStatue logStatue,boolean infoLocation){
		 logAllFlag=logStatue;
		 Logger_Zone.infoLocation=infoLocation;
	};
	public static LogStatue getAllLogStatue(){
		return  logAllFlag;
	};
	public static boolean  getLogIsLoaction(){
		return infoLocation;
	}
	public boolean isPrint(){
		boolean flag=false;
		switch (logAllFlag) {
		case Close:
			break;
		case Child_Control:
			if (logflag) {
				flag=logflag;
			}
			break;
	
		case Open:
			flag=true;
			break;

		default:
			break;
		}
		return flag;
	}


	/**
	 * 不带定位信息的log
	 * @param str
	 */
	public void log(String str) {
		log(str, false,Log.INFO);
	}
	public void v(String str) {
		log(str, false,Log.VERBOSE);
	}
	public void d(String str) {
		log(str, false,Log.DEBUG);
	}
	public void e(String str) {
		log(str, false,Log.ERROR);
	}
	public void w(String str) {
		log(str, false,Log.WARN);
	}
	/**
	 * @param str
	 * @param location 有没有定位信息
	 */
	private void log(String str, boolean location,int logType) {
		
		switch (logAllFlag) {
		case Close:
			break;
		case Open:
			logOut(str,logType);
			break;
		case Child_Control:
			if (logflag) {
				logOut(str,logType);
			}
			break;
		default:
			break;
		}
		logOut(str,logType);
		
	}
	private void logOut(String str, int logType) {
		String name=null;
		if (infoLocation) {
			name = getFunctionName();
		}
		String connect="";
		if (name != null) {
			connect= name + prefix + str;
		} else {
			connect=str;
		}
		switch (logType) {
		case Log.VERBOSE:
			Log.v(projectName, connect);
			break;
		case Log.DEBUG:
			Log.d(projectName, connect);
			break;
		case Log.ERROR:
			Log.e(projectName, connect);
			break;
		case Log.INFO:
			Log.i(projectName, connect);
			break;
		case Log.WARN:
			Log.w(projectName, connect);
			break;
		default:
			break;
		}
	}

	/**
	 * Get The Current Function Name
	 * 
	 * @return
	 */
	private String getFunctionName() {
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();
		if (sts == null) {
			return null;
		}
		for (StackTraceElement st : sts) {
			if (st.isNativeMethod()) {
				continue;
			}
			if (st.getClassName().equals(Thread.class.getName())) {
				continue;
			}
			if (st.getClassName().equals(this.getClass().getName())) {
				continue;
			}
			return  "[ ThreadName:" + Thread.currentThread().getName() + ","
					+ mClassName + ":" + st.getMethodName()+ ",lineName:"+st.getLineNumber()+" ]";
		}
		return null;
	}

}
