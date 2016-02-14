package download.zone.okhttp;

import java.io.File;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String urlPath="http://down.360safe.com/360/inst.exe";
//		String urlPath="http://img4.freemerce.com/ci49h5p.jpg";
//		Exce b=new Exce();
		DownLoader b=DownLoader.INSTANCE;
		b.startTask(urlPath, new File("D:/"));
	}

}
