package and.utils.view.special_view.editview;

import java.util.List;

import and.utils.unused.rex.RexUtils;
import and.utils.unused.rex.Rex_Phone.PhoneEntity;
import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

public class SpannableUtils {
	public interface onClickSpannableListener{
		public abstract void onClick(View widget, String phone);
	}
	/**
	 * 把内容是电话的突出出来,然后添加点击事件
	 * @param tv
	 * @param context
	 * @param contentStr
	 * @param listener
	 */
	public static void contentToPhone(TextView tv,Context context,String contentStr,int color,final onClickSpannableListener listener){
		List<PhoneEntity> phones = RexUtils.byContextGetPhone(contentStr);
		SpannableString ss = new SpannableString(contentStr);
		for (PhoneEntity phoneEntity : phones) {
			
			final String phone = phoneEntity.str;
			ss.setSpan(new ClickableSpan(){
		         //在onClick方法中可以编写单击链接时要执行的动作
		         @Override
		         public void onClick(View widget){
		        	 listener.onClick(widget,phone);
		          }
		      },phoneEntity.start, phoneEntity.end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
			ss.setSpan(new UnderlineSpan(), phoneEntity.start, phoneEntity.end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
			//因为　ClickableSpan　会变成蓝色的　即必须在后边设置颜色
			ss.setSpan(new ForegroundColorSpan(color), phoneEntity.start, 
					phoneEntity.end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		}
		
		tv.setText(ss);
		//在单击链接时凡是有要执行的动作，都必须设置MovementMethod对象
		tv.setMovementMethod(LinkMovementMethod.getInstance());
	}
}
