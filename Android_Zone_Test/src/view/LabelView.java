package view;
import java.util.ArrayList;
import java.util.List;

import view.utils.DragZoomRorateUtils;

import com.example.mylib_test.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
/**
 * 我最终完美的控件
 * @author Zone
 *
 */
public class LabelView extends View {
	/**
	 * 这个是对于屏幕的属性 而不是真正VIEW中的宽高
	 */
	private List<MoveAttri> moveAttri_list = new ArrayList<MoveAttri>();
	private Mode mode=Mode.NONE;
	private int width,height;
	private boolean rimVisibility;
	private enum Mode{
		NONE,ZOOM,DRAG 
	}

	/**
	 * 真是位置为  quitRect 偏移  重中心点 到实际位置的点    
	 * @author Zone
	 *
	 */
	public static  Bitmap quitBm=null;
	public static  Bitmap scaleBm=null;
	/** 
	 * 0为00
	 * 0---1---2 
	 * |       | 
	 * 7   8   3 
	 * |       | 
	 * 6---5---4  
	 */ 
	private class MoveAttri {
		//矩形永远不变  每次都计算  自己的位移 旋转 放大  最后的矩形范围
		public float downX = 0, downY = 0, offsetX = 0, offsetY = 0,
				offsetX_history = 0, offsetY_history = 0,scaleRadio=1F,scaleRadio_history=1,
				oriDis=0,ratote=0,ratoteHistory=0,oriRatote=0;
		public Matrix oriMatrix=new Matrix(),invert_Matrix=new Matrix(),scaleMatrix=new Matrix(),
				quitMatrix=new Matrix();
		
		/**---------------------dst则是变化的 不变的属性 start------------------ */
		public float centerLeft_first=0,centerTop_first=0;
		public Bitmap bitmap=null;
		public float [] srcPs , dstPs ;  
		public RectF rect_img_widget=new RectF(),dst_rect_img_widget=new RectF();
		public RectF quitRect=new RectF(), dst_quitRect=new RectF();
		public RectF scaleRect=new RectF(),dst_scaleRect=new RectF();
		/**---------------------不变的属性    end------------------ */
		@Override
		public String toString() {
			System.out.println("{downX:"+downX+",downY"+downY+",offsetX"+offsetX+",offsetY"+offsetY+",offsetX_history"+offsetX_history
					+",offsetY_history"+offsetY_history+",centerLeft_first"+centerLeft_first+",centerTop_first"+centerTop_first+"}");
			return super.toString();
		}
		
	}
	/** 
	 * 0为00
	 * 0---1---2 
	 * |       | 
	 * 7   8   3 
	 * |       | 
	 * 6---5---4  
	 */ 
	private RectF getScaleRectF(MoveAttri item){
		getMainRect_WithRorate(item);
		 float zoomX = item.dstPs[4] ;
		 float zoomY = item.dstPs[5] ;
		 item.scaleMatrix.reset();
		 item.scaleMatrix.postTranslate(zoomX-item.scaleRect.width()/2,zoomY-item.scaleRect.height()/2);
		 item.scaleMatrix.postRotate(item.ratote+item.ratoteHistory,zoomX,zoomY);
		 item.scaleMatrix.mapRect(item.dst_scaleRect, item.scaleRect);
		return item.dst_scaleRect;
	}
	private Matrix getScaleMatrix(MoveAttri item){
		getScaleRectF(item);
		return 	item.scaleMatrix;
	}
	/** 
	 * 0为00
	 * 0---1---2 
	 * |       | 
	 * 7   8   3 
	 * |       | 
	 * 6---5---4  
	 */ 
	private RectF getQuitRectF(MoveAttri item){
		getMainRect_WithRorate(item);
		float quitX = item.dstPs[12] ;
		float quitY = item.dstPs[13] ;
		
		item.quitMatrix.reset();
		item.quitMatrix.postTranslate(quitX-item.quitRect.width()/2,quitY-item.quitRect.height()/2);
		item.quitMatrix.postRotate(item.ratote+item.ratoteHistory,quitX,quitY);
		item.quitMatrix.mapRect(item.dst_quitRect, item.quitRect);
		return item.dst_quitRect;
	}
	private Matrix getQuitMatrix(MoveAttri item){
		getQuitRectF(item);
		return 	item.quitMatrix;
	}
	private RectF getMainRect_WithRorate(MoveAttri item){
	
		float scaleRadioTotal=item.scaleRadio*item.scaleRadio_history;
		float rorateTotal=item.ratote+item.ratoteHistory;
		item.oriMatrix.reset();
		item.oriMatrix.postScale(scaleRadioTotal, scaleRadioTotal, item.rect_img_widget.centerX(), item.rect_img_widget.centerY());
		item.oriMatrix.postRotate(rorateTotal,item.rect_img_widget.centerX(), item.rect_img_widget.centerY());
		item.oriMatrix.postTranslate(item.offsetX+item.offsetX_history+1F*width/2-item.rect_img_widget.width()/2, 
				item.offsetY+item.offsetY_history+1F*height/2-item.rect_img_widget.height()/2);
		
		item.oriMatrix.mapRect(item.dst_rect_img_widget, item.rect_img_widget);
		item.oriMatrix.mapPoints(item.dstPs, item.srcPs);
		return item.dst_rect_img_widget;
	}
	private Matrix getMainMaritx_withRorate(MoveAttri item){
		getMainRect_WithRorate(item);
		return item.oriMatrix;
	}
	
	private void removeLabel_Bitmap(MoveAttri moveAttri){
		if (moveAttri.bitmap!=null) {
			boolean deleteIsOk = true;
			for (MoveAttri item : moveAttri_list) {
				if (item == moveAttri) {
					continue;
				}
				if (item.bitmap == moveAttri.bitmap) {
					deleteIsOk = false;
				}
			}
			if (deleteIsOk) {
				moveAttri.bitmap.recycle();
				moveAttri.bitmap=null;
			}
			moveAttri_list.remove(moveAttri);
		}
	}
	public void removeAllLabels(){
		for (MoveAttri item : moveAttri_list) {
			removeLabel_Bitmap(item);
		}
	}
	
	public LabelView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		 scaleBm=BitmapFactory.decodeResource(context.getResources(), R.drawable.fangda);
		 quitBm=BitmapFactory.decodeResource(context.getResources(), R.drawable.guanbi);
		
		 
	}

	public LabelView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public LabelView(Context context) {
		this(context, null);
	}
	private MoveAttri tempTouch=null ;
	private MoveAttri showHelper=null ;
	private boolean quitDown=false;//触发事件  判断消耗用的

	//通过逆向矩阵  找到图形最开始 不变点的位置
	private float[] getInvertEventPoint(MoveAttri moveAttri,MotionEvent event,Type type){
		float[] src=new float[]{event.getX(),event.getY()};
		float[] dst=new float[src.length];
		switch (type) {
		case MAIN:
			getMainMaritx_withRorate(moveAttri).invert(moveAttri.invert_Matrix);
			moveAttri.invert_Matrix.mapPoints(dst, src);
			break;
		case SCALE:
			getScaleMatrix(moveAttri).invert(moveAttri.invert_Matrix);
			moveAttri.invert_Matrix.mapPoints(dst, src);
			break;
		case QUIT:
			getQuitMatrix(moveAttri).invert(moveAttri.invert_Matrix);
			moveAttri.invert_Matrix.mapPoints(dst, src);
			break;
		default:
			break;
		}
	
//		System.out.println("down :x"+event.getX()+"\ty"+event.getY());
//		System.out.println("逆向后 x:"+dst[0]+"\ty:"+dst[1]);
		return dst;
	}
	private enum Type{
		MAIN,SCALE,QUIT
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:// 手指压下屏幕
		
			//先判断 是碰到那个图片
			for (int i = moveAttri_list.size() - 1; i >= 0; i--) {
				MoveAttri temp = moveAttri_list.get(i);
				float[] main_invertEventPoint = getInvertEventPoint(temp, event,Type.MAIN);
				if(temp.rect_img_widget.contains(main_invertEventPoint[0], main_invertEventPoint[1])){
					System.out.println("碰到标签了 index:"+i);
					mode = Mode.DRAG;
					//记录一些属性 与初始一些值
					temp.downX=event.getX();
					temp.downY=event.getY();
					tempTouch=temp;
					showHelper=temp;
					
					break;
				}
				if(showHelper!=null&&showHelper==temp){
					//当我知道摁下他的时候  是否是嗯的位置是右上角还是左下角
					float[] scale_invertEventPoint = getInvertEventPoint(temp, event,Type.SCALE);
					float[] quit_invertEventPoint = getInvertEventPoint(temp, event,Type.QUIT);
					 if(temp.scaleRect.contains(scale_invertEventPoint[0],scale_invertEventPoint[1])){
						 mode = Mode.ZOOM;
						 tempTouch=showHelper;
						 tempTouch.oriDis = getHalf_Length_RectfDiagonal(getMainRect_WithRorate(showHelper), event);
						 tempTouch.oriRatote=getRotateDegrees_centerToRight(getMainRect_WithRorate(showHelper), event);
						 System.out.println("碰到you角的点了");
					 }
					 if(temp.quitRect.contains(quit_invertEventPoint[0],quit_invertEventPoint[1])){
						 quitDown=true;
						 System.out.println("碰到左下角的点了");
						 mode=Mode.NONE;
						 removeLabel_Bitmap(showHelper);
						 tempTouch=null;
						 showHelper=null;
					 }
					 break;
				}
			}
			
			
			if(tempTouch==null)
				showHelper=null;
			invalidate();
			break;
			
//		case MotionEvent.ACTION_POINTER_DOWN:// 当屏幕上还有触点（手指），再有一个手指压下屏幕
//			mode = Mode.ZOOM;
//			break;

		case MotionEvent.ACTION_MOVE:// 手指在屏幕移动，该 事件会不断地触发
			if (tempTouch != null) {
				if (mode == Mode.DRAG) {
					tempTouch.offsetX = event.getX() - tempTouch.downX;
					tempTouch.offsetY = event.getY() - tempTouch.downY;

				} else if (mode == Mode.ZOOM) {// 缩放与旋转
					//缩放
					float tempDis = getHalf_Length_RectfDiagonal(getMainRect_WithRorate(showHelper), event);
					tempTouch.scaleRadio=tempDis/tempTouch.oriDis;
					//旋转
					float tempRatote=getRotateDegrees_centerToRight(getMainRect_WithRorate(showHelper), event);
					tempTouch.ratote=tempRatote-tempTouch.oriRatote;
//					System.out.println("比率是多少："+	tempTouch.scaleRadio);
//					System.out.println("角度是多少："+tempTouch.ratote);
				}
			}
			invalidate();
			break;

		case MotionEvent.ACTION_UP:// 手指离开屏
			mode = Mode.NONE;
			if(tempTouch!=null){
				//记录一些值
				tempTouch.offsetX_history += tempTouch.offsetX;
				tempTouch.offsetX=0;
				tempTouch.offsetY_history += tempTouch.offsetY;
				tempTouch.offsetY=0;
				tempTouch.scaleRadio_history*=tempTouch.scaleRadio;
				tempTouch.scaleRadio=1F;
				tempTouch.ratoteHistory+=tempTouch.ratote;
				tempTouch.ratote=0;
				tempTouch=null;
			}
			break;
//		case MotionEvent.ACTION_POINTER_UP:// 有手指离开屏幕,但屏幕还有触点（手指）
//			mode = Mode.NONE;
//			break;
		default:
			break;
		}
		if (!quitDown) {
			if (tempTouch != null) {
				//点到了就消费  不传递了
				return true;
			} else {
				return false;
			}
		}else{
			//点击的时候消耗掉  消耗的时候还原了
			quitDown=false;
			return true;
		}
	}
	private static float getHalf_Length_RectfDiagonal(RectF rectf,MotionEvent event){
		PointF start=new PointF(rectf.centerX(),rectf.centerY());
		PointF end=new PointF(event.getX(), event.getY());
		return DragZoomRorateUtils.distance(start, end);
	}
	private static float getRotateDegrees_centerToRight(RectF rectf,MotionEvent event){
		PointF start=new PointF(rectf.centerX(),rectf.centerY());
		PointF end=new PointF(event.getX(), event.getY());
		return 	(float)(Math.toDegrees(Math.atan2(end.y-start.y, end.x-start.x)));
	}
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		width=getMeasuredWidth();
		height=getMeasuredHeight();
	}
	@Override
	protected void onDraw(Canvas canvas) {
		for (MoveAttri item : moveAttri_list) {
//			item.toString();
//			System.out.println("位置："+moveAttri_list.indexOf(item)+"\tx:"+item.getReallyLocation_X()+"\eventType y:"+ item.getReallyLocation_Y());
			//原图的操作
			canvas.drawBitmap(item.bitmap,getMainMaritx_withRorate(item), null);
			if(showHelper!=null&&item==showHelper){
				Paint paint=new Paint();
				paint.setColor(Color.WHITE);
				paint.setStrokeWidth(3);
				paint.setStyle(Style.STROKE);
				if (rimVisibility) {
					//TODO 白色框框
					RectF tempRectF = getMainRect_WithRorate(item);
					canvas.drawRect(tempRectF, paint);
				}
				/** 
				 * 0为00
				 * 0---1---2 
				 * |       | 
				 * 7   8   3 
				 * |       | 
				 * 6---5---4  
				 */ 
				getMainRect_WithRorate(item);
				float[] dstPs = item.dstPs;
				canvas.drawLine(dstPs[0], dstPs[1], dstPs[4], dstPs[5], paint);
				canvas.drawLine(dstPs[4], dstPs[5], dstPs[8], dstPs[9], paint);
				canvas.drawLine(dstPs[8], dstPs[9], dstPs[12], dstPs[13], paint);
				canvas.drawLine(dstPs[12], dstPs[13], dstPs[0], dstPs[1], paint);
				//放大按钮
				canvas.drawBitmap(scaleBm, getScaleMatrix(item), null);
				//删除按钮
				canvas.drawBitmap(quitBm,getQuitMatrix(item), null);	
			}
		}
		super.onDraw(canvas);
	}
	public void hideHelper(){
		showHelper=null;
		invalidate();
	}
	public Bitmap save(){
		Bitmap save = Bitmap.createBitmap( width, height, Config.ARGB_8888);
		Canvas canvas=new Canvas(save);
		for (MoveAttri item : moveAttri_list) {
			canvas.drawBitmap(item.bitmap,getMainMaritx_withRorate(item), null);
		}
		return save;
	}
	public int  getSize(){
		return moveAttri_list.size();
	}
	/** 
	 * 0为00
	 * 0---1---2 
	 * |       | 
	 * 7   8   3 
	 * |       | 
	 * 6---5---4  
	 */ 
	public void addBitmap(Bitmap bt){
		//当给map的时候布局已经好了
		MoveAttri moveAttri=new MoveAttri();
		//位图
		moveAttri.bitmap=bt;
		float mainBmpWidth = 1F*bt.getWidth();
		float mainBmpHeight = 1F*bt.getHeight();
		
		//最初的位置
		moveAttri.centerLeft_first=1F*width/2-bt.getWidth()/2;
		moveAttri.centerTop_first=1F*height/2-bt.getHeight()/2;
		
		moveAttri.srcPs = new float[]{  
				 0,0,   
                 mainBmpWidth/2,0,   
                 mainBmpWidth,0,   
                 mainBmpWidth,mainBmpHeight/2,  
                 mainBmpWidth,mainBmpHeight,   
                 mainBmpWidth/2,mainBmpHeight,   
                 0,mainBmpHeight,   
                 0,mainBmpHeight/2,   
                 mainBmpWidth/2,mainBmpHeight/2  
	        }; 
		moveAttri.dstPs=new float[moveAttri.srcPs.length];
		//记录 Rect 
		moveAttri.scaleRect.set(0, 0, 
				scaleBm.getWidth(),1F* scaleBm.getHeight());
		moveAttri.quitRect.set(0,0,
				quitBm.getWidth(), quitBm.getHeight());
		moveAttri.rect_img_widget.set(0,0,
				 bt.getWidth(), bt.getHeight());
		
		moveAttri_list.add(moveAttri);
		invalidate();
	}
	//rim边框
	public void setRimVisibility(boolean rim){
		rimVisibility=rim;
	}

}
