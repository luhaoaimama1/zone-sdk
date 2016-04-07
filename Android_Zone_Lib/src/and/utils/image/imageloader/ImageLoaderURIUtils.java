package and.utils.image.imageloader;

public class ImageLoaderURIUtils {
	/**
	 * 	 例子： http的话 任何人都会用就不封装里面了
		<br>String imageUri = "http://site.com/image.png"; // 网络图片  
		<br>String imageUri = "file:///mnt/sdcard/image.png"; //SD卡图片  
		<br>String imageUri = "content://media/external/audio/albumart/13"; // 媒体文件夹  
		<br>String imageUri = "assets://image.png"; // assets  
		<br>String imageUri = "drawable://" + R.drawable.image; //  drawable文件  
	 *
	 */
	public enum Type{
		File("file://"),Assets("assets://"),Drawable("drawable://"),Content("content://");
		private String str;
		private Type(String str) {
			this.str=str;
		}
		public String getStr() {
			return str;
		}
	}
	public static String transformURI(String uri,Type type){
		return type.getStr()+uri;
	}
}
