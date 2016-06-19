//package view;
//
//import java.util.ArrayList;
//import java.util.List;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.util.AttributeSet;
//import android.view.View;
//import android.widget.AbsListView;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//
//import com.zone.adapter.adapter.core.BaseAdapter_Zone;
//import com.zone.adapter.adapter.core.ViewHolder_Zone;
//import com.zone.adapter.recycler.core.BaseRecyclerAdapter;
//
////TODO 单行无限  未做
////itemMargin的话 继承GirdView你懂的 	iz.setHorizontalSpacing(100);setVerticalSpacing(100);
////android:horizontalSpacing   android:verticalSpacing
////android:numColumns  可以设置列数
//public class ImageRecycler_Zone extends GridView {
//
//	private Context context;
//	private AddType addType=AddType.Hide2Show;
//	private int maxCount=0;
//	private List<String> realUseData=new ArrayList<String>();
//	private List<String> outData=new ArrayList<String>();
//	private BaseAdapter_Zone<String> bAdapter;
//
//	public ImageRecycler_Zone(Context context, AttributeSet attrs) {
//		this(context, attrs, 0);
//	}
//
//	public ImageRecycler_Zone(Context context) {
//		this(context, null);
//	}
//
//	public ImageRecycler_Zone(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//		this.context=context;
//	}
//	private BaseRecyclerAdapter<String> adapter;
//	private int addBackgroudId=-1;
//	private OnImageGroupClickListener onImageGroupClickListener;
//	private Bitmap addBackBt;
//
//	private static final String ADD_STRING="AddType.Add";
//	public void setType(AddType addType){
//		this.addType=addType;
//	}
//	public  void initAdapter(List<String> data){
//		initAdapter(data,null);
//	}
//	private boolean notifyChanged=false;
//	public  void initAdapter(List<String> data,final SetLabelPicListener listener){
//		notifyChanged=true;
//		setData(data);
//		setBackgroundColor(Color.DKGRAY);
//		associatedAdapter(bAdapter=new BaseAdapter_Zone<String>(context, realUseData) {
//			private static final int LL_ID=111;
//			private static final int LABEL_ID=123;
//			private static final int IV_ID=234;
//			//控制最多显示多少   maxCount==-1 不控制
//			@Override
//			public int getCount() {
//				int total=0;
//				if(maxCount==-1)
//					total=super.getCount();
//				else
//					total=maxCount<super.getCount()?maxCount:super.getCount();
//				return total;
//			}
//			@Override
//			public void setData(ViewHolder_Zone holder, final String data,final int position) {
////				if (position==0) {
////					if (notifyChanged) {
////						setDataDeal(holder, data, position);
////					}
////				}else{
////					notifyChanged=false;
//					setDataDeal(holder, data, position);
////				}
//			}
//			private void setDataDeal(ViewHolder_Zone holder, final String data,
//					final int position) {
//				SquareImageView iv = (SquareImageView) holder
//						.findViewById(IV_ID);
//				System.out.println("日狗少年data:" + data + "\eventType position:"
//						+ position);
//				if (!ADD_STRING.equals(data)) {
//					if (onImageGroupClickListener != null) {
//						onImageGroupClickListener.showImage(iv, position);
//					}
//					if (listener != null) {
//						SquareImageView label = (SquareImageView) holder
//								.findViewById(LABEL_ID);
//						listener.setLabelPic(label, position);
//					}
//				} else
//					iv.setImageBitmap(addBackBt);
//				if (onImageGroupClickListener != null) {
//					iv.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View v) {
//							if (!ADD_STRING.equals(data)) {
//								onImageGroupClickListener
//										.imageClick(position);
//							} else
//								onImageGroupClickListener.addClick();
//						}
//					});
//				}
//
//			}
//			@Override
//			public View getConverView(int position) {
//				RelativeLayout ll=new RelativeLayout(context);
//				ll.setLayoutParams(new  AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
////				ll.setBackgroundColor(Color.RED);
//				ll.setId(LL_ID);
//				if (listener!=null) {
//					SquareImageView label = new SquareImageView(context);
//					label.setId(LABEL_ID);
//					label.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
//					ll.addView(label);
//				}
//				SquareImageView iv=new SquareImageView(context);
//				iv.setId(IV_ID);
//				iv.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
//				ll.addView(iv);
//				return ll;
//			}
//			//由于有这个getConverView  所以此无用
//			@Override
//			public int getLayoutID(int position) {
//				return 0;
//			}
//
//
//		});
//	}
//	public void setAddBackgroud(int addBackgroudId){
//		this.addBackgroudId=addBackgroudId;
//		addBackBt=BitmapFactory.decodeResource(getResources(),addBackgroudId);
//	}
//
//	public void notifyChanged(){
//		realUseData();
//		bAdapter.notifyDataSetChanged();
//		notifyChanged=true;
//	}
//
//	//maxCount:-1为 无限大小
//	public void setMaxCount(int maxCount){
//		this.maxCount=maxCount;
//	}
//	public void setOnImageGroupClickListener(OnImageGroupClickListener onImageGroupClickListener){
//		this.onImageGroupClickListener=onImageGroupClickListener;
//	}
//	private void setData(List<String> data){
//		this.outData=data;
//		realUseData();
//	}
//	private void realUseData(){
//		realUseData.clear();
//		for (String string : outData) {
//			realUseData.add(string);
//		}
//		if (addType == AddType.Add)
//			realUseData.add(ADD_STRING);
//	}
//
//	public enum AddType{
//		Add,Hide2Show;
//	}
//	public interface OnImageGroupClickListener{
//		public void showImage(ImageView iv,int position);
//		public void imageClick(int position);
//		public void addClick();
//	}
//	public interface SetLabelPicListener{
//		public void setLabelPic(SquareImageView label, int position);
//	}
//
//}
