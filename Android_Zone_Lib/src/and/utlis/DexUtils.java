package and.utlis;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;

public class DexUtils {
	public static DexClassLoader getDex(Context apps) {
		ApplicationInfo info = apps.getApplicationInfo();
		String dexPath=info.sourceDir;
		String dexOutputDir=info.dataDir;
		String libPath=info.nativeLibraryDir;
		DexClassLoader dl= new DexClassLoader(dexPath, dexOutputDir, 
				libPath, apps.getClass().getClassLoader());
		try {
			Enumeration<URL> gaga = dl.getResources("com.example.mylib_test");
			System.out.println(1);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			DexFile df = new DexFile(dexOutputDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dl;
	}
	public static List<Class> getClassByPackage(String packageName){
		try {
			DexFile df = new DexFile(packageName);
			Enumeration<String> ite = df.entries();
			while (ite.hasMoreElements()) {
				String str = (String) ite.nextElement();
				System.out.println("잚츰："+str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
