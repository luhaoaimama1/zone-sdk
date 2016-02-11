package and.http.okhttp.utils;

import java.io.File;

public class MediaTypeUtils {
	/**
	 * 根据后缀名判断是否是图片文件
	 * 
	 * @param 文件
	 * @return 是否是图片结果true or false
	 */
	public static boolean isImage(File file) {
		String fileName = file.getName();
		int typeIndex = fileName.lastIndexOf(".");
		if (typeIndex != -1) {
			String fileType = fileName.substring(typeIndex + 1).toLowerCase();
			if (fileType != null
					&& (fileType.equals("jpg") || fileType.equals("gif")
							|| fileType.equals("png")
							|| fileType.equals("jpeg")
							|| fileType.equals("bmp")
							|| fileType.equals("wbmp")
							|| fileType.equals("ico") || fileType.equals("jpe"))) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 获取文件类型
	 * @param file  文件
	 * @return   文件后缀名
	 */
	public static String getFileType(File file){
		String fileName = file.getName();
		int typeIndex = fileName.lastIndexOf(".");
		if(typeIndex != -1){
			return fileName.substring(typeIndex + 1).toLowerCase();
		}else{
			return "";
		}
	}
	/**
	 * 获取文件的上传类型
	 * 
	 * @param file
	 * @return 图片格式为image/png,image/jpg等。非图片为application/octet-stream
	 */
	public static  String getContentType(File file) {
		if (MediaTypeUtils.isImage(file)) {
			return "image/" + getFileType(file).toLowerCase();// 将FormatName返回的值转换成小写，默认为大写r
		} 
			return "application/octet-stream";
	}
}
