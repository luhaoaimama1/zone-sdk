//package com.example.mylib_test.activity.custom_view;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import view.ImageRecycler_Zone;
//import view.ImageRecycler_Zone.AddType;
//import view.ImageRecycler_Zone.OnImageGroupClickListener;
//import and.base.activity.BaseActvity;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.example.mylib_test.R;
//import com.example.mylib_test.activity.three_place.Images;
//import com.nostra13.universalimageloader.core.ImageLoader;
//
//
//public class ImageGroupRecyclerTestActvitity extends BaseActvity {
//
//	private ImageRecycler_Zone iz;
//	private List<String> data = new ArrayList<String>();
//
//	@Override
//	public void setContentView() {
//		setContentView(R.layout.a_imagegroup_recycler_test);
//	}
//
//	@Override
//	public void findIDs() {
//		iz = (ImageRecycler_Zone) findViewById(R.id.iz);
//	}
//
//	@Override
//	public void initData() {
//		add();
//	}
//
//	private void noAdd() {
//		data.clear();
//		iz.setNumColumns(3);
//		iz.setMaxCount(9);
//		iz.setType(AddType.Hide2Show);
//		iz.initAdapter(data);
//		for (int i = 0; i < Images.imageThumbUrls.length; i++) {
//			data.add(Images.imageThumbUrls[i]);
//		}
//		iz.notifyChanged();
//		iz.setOnImageGroupClickListener(new OnImageGroupClickListener() {
//			int i = 0;
//
//			@Override
//			public void imageClick(int position) {
//				System.out.println("imageClick  position:" + position);
//			}
//
//			@Override
//			public void addClick() {
//				// data.add(Images.imageThumbUrls[i]);
//				// i++;
//				// System.out.println("addClick");
//				// iz.notifyChanged();
//			}
//
//			@Override
//			public void showImage(ImageView iv, int position) {
//				ImageLoader.getInstance().displayImage(data.get(position), iv);
//			}
//		});
//	}
//
//	private void add() {
//		data.clear();
//		iz.setNumColumns(3);//每行几个
//		iz.setHorizontalSpacing(100);
//		iz.setVerticalSpacing(100);
//
//		iz.setMaxCount(9);//最多多少
//		iz.setType(AddType.Add);//添加
//		iz.setAddBackgroud(R.drawable.ic_launcher);
//
//		iz.initAdapter(data);
//		iz.setOnImageGroupClickListener(new OnImageGroupClickListener() {
//			int i = 0;
//			@Override
//			public void imageClick(int position) {
//				System.out.println("imageClick  position:" + position);
//			}
//
//			@Override
//			public void addClick() {
//				data.add(Images.imageThumbUrls[i]);
//				i++;
//				System.out.println("addClick");
//				iz.notifyChanged();
//			}
//
//			@Override
//			public void showImage(ImageView iv, int position) {
//				ImageLoader.getInstance().displayImage(data.get(position), iv);
//			}
//		});
//	}
//	private void oneline() {
//		data.clear();
////		iz.setNumColumns(0);
////		iz.setColumnWidthZone(100);
//		iz.setMaxCount(9);
//		iz.setType(AddType.Add);
//		iz.setAddBackgroud(R.drawable.ic_launcher);
//		iz.initAdapter(data);
//		iz.setOnImageGroupClickListener(new OnImageGroupClickListener() {
//			int i = 0;
//
//			@Override
//			public void imageClick(int position) {
//				System.out.println("imageClick  position:" + position);
//			}
//
//			@Override
//			public void addClick() {
//				data.add(Images.imageThumbUrls[i]);
//				i++;
//				System.out.println("addClick");
//				iz.notifyChanged();
//			}
//
//			@Override
//			public void showImage(ImageView iv, int position) {
//				ImageLoader.getInstance().displayImage(data.get(position), iv);
//			}
//		});
//	}
//	@Override
//	public void setListener() {
//
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.noAdd:
//			noAdd();
//			break;
//		case R.id.add:
//			 add();
//			break;
//		case R.id.online:
//			oneline();
//			break;
//
//		default:
//			break;
//		}
//		super.onClick(v);
//	}
//
//
//
//}
