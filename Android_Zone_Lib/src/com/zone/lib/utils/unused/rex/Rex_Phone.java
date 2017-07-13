package com.zone.lib.utils.unused.rex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Rex_Phone {
	public static class PhoneEntity{
		public String str;
		public int start;
		public int end;
		public PhoneEntity(String str,int start,int end) {
			this.str=str;
			this.start=start;
			this.end=end;
		}
	}
	// 11位 18510640011 185-1064-0011
	// 10位 4002342222 400-234-2222
	// 8位 22223334 2222-3334
	//调用打电话  带-也是可以识别的
	//	Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phoneno));
	//	startActivity(intent);
	public static List<PhoneEntity>  byContextGetPhone(String str){
		RexHelperUtils ru=new RexHelperUtils();
		ru.addRule("\\D(\\d{11})\\D");
		ru.addRule("\\D(\\d{3}-\\d{4}-\\d{4})\\D");
		ru.addRule("\\D(\\d{10})\\D");
		ru.addRule("\\D(\\d{3}-\\d{3}-\\d{4})\\D");
		ru.addRule("\\D(\\d{8})\\D");
		ru.addRule("\\D(\\d{4}-\\d{4})\\D");
		return getValuePhone(str,ru.build());
	}
	/**
	 * 得一正则表达对应的内容
	 * 
	 * @param con
	 * @param reg
	 * @return
	 */
	private static List<PhoneEntity> getValuePhone(String con, String reg) {
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(" "+con+" ");//因为这里为了让在开头的、结尾的也匹配上 所以得到的顺序-1
		String res = "";
		List<PhoneEntity>  list=new ArrayList<PhoneEntity>();
		while (m.find()) {
			for (int i = 0; i <= m.groupCount(); i++) {
				res = m.group(i);
				if(res == null||i==0){
					continue;
				}
				//所以这里顺序-1
				list.add(new PhoneEntity(res, m.start(i)-1, m.end(i)-1));
//				System.out.println(res+"-----------\t ks:"+m.start(i)+"   \tend:"+m.end(i)+"\t i："+i);
			}
		}
		return list;
	}
}
