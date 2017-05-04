package com.example.mylib_test;

import java.util.ArrayList;
import java.util.List;

import com.example.mylib_test.activity.animal.Animal_MainActivity;
import com.example.mylib_test.activity.custom_view.CustomView_MainActivity;
import com.example.mylib_test.activity.databingstudy.DataBingMainActivity;
import com.example.mylib_test.activity.db.Db_MainActivity;
import com.example.mylib_test.activity.db.entity.MenuEntity;
import com.example.mylib_test.activity.file.FileTestActivity;
import com.example.mylib_test.activity.http.Http_MainActivity;
import com.example.mylib_test.activity.http.ZBusActivity;
import com.example.mylib_test.activity.photo_shot.Photo_Shot_MainActivity;
import com.example.mylib_test.activity.pop_dialog.Dialog_Pop_Adapter_MainActivity;
import com.example.mylib_test.activity.ress.ResMainActvitity;
import com.example.mylib_test.activity.system.SystemMainActivity;
import com.example.mylib_test.activity.three_place.ThirdParty_MainActivity;
import com.example.mylib_test.activity.touch.TouchMainActivity;
import com.example.mylib_test.activity.utils.Utils_MainActivity;
import com.example.mylib_test.activity.wifi.Wifi3g_MainActivity;

public class MainMenu {
	public static List<MenuEntity> menu=new ArrayList<MenuEntity>();
	
	static{
		menu.add(new MenuEntity("http测试Thread测试(和handle的测试)", Http_MainActivity.class)) ;
		menu.add(new MenuEntity("ZBusActivity", ZBusActivity.class)) ;
		menu.add(new MenuEntity("pop dialog测试(还有 文字高亮 链接等效果)", Dialog_Pop_Adapter_MainActivity.class)) ;
		menu.add(new MenuEntity("动画、surfaceView、绘图方面的研究", Animal_MainActivity.class)) ;
		menu.add(new MenuEntity("res shape selector等的研究", ResMainActvitity.class)) ;
		menu.add(new MenuEntity("onTouch事件传递与其辅助类的研究", TouchMainActivity.class)) ;
		menu.add(new MenuEntity("自定义控件", CustomView_MainActivity.class)) ;
		menu.add(new MenuEntity("databing、lambda、rxjava的学习", DataBingMainActivity.class)) ;
		menu.add(new MenuEntity("第三方 （ImageLoader等）", ThirdParty_MainActivity.class)) ;
		menu.add(new MenuEntity("工具箱Utils的测试", Utils_MainActivity.class)) ;
//		menu.add(new MenuEntity("db测试", Db_MainActivity.class)) ;
		menu.add(new MenuEntity("手机 文件/IO 测试", FileTestActivity.class)) ;
		menu.add(new MenuEntity("wifi 3g 监听网络情况测试", Wifi3g_MainActivity.class)) ;
		menu.add(new MenuEntity("照片和拍摄(图片裁剪等辅助测试)", Photo_Shot_MainActivity.class)) ;
		menu.add(new MenuEntity("系统控件等的研究", SystemMainActivity.class)) ;
	}

}
