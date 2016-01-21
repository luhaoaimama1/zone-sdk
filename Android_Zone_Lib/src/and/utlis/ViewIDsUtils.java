package and.utlis;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewIDsUtils {
	public static List<Integer> getIDsByView(View convertView) {
		List<Integer> idList = new ArrayList<Integer>();
		if (!ViewGroup.class.isInstance(convertView)) {
			if (convertView.getId() != -1) {
				idList.add(convertView.getId());
			}
		} else {
			//是 viewGroup 开始迭代
			if (convertView.getId() != -1) {
				idList.add(convertView.getId());
			}
			isVgComeOn(convertView, idList);
		}
		return idList;
	}
	public static List<Integer> getIDsByView(Context context,int layoutId) {
		View view=LayoutInflater.from(context).inflate(layoutId, null);
		return getIDsByView(view);
	}

	private static void isVgComeOn(View convertView, List<Integer> idList) {
		if (ViewGroup.class.isInstance(convertView)) {
			ViewGroup vg = (ViewGroup) convertView;
			for (int i = 0; i < vg.getChildCount(); i++) {
				View child = vg.getChildAt(i);
				int cId = child.getId();
				if (cId != -1) {
					// 看了源码发现-1 是没有id的说
					idList.add(cId);
				}
				// 如果这个view是 viewGroup继续找
				isVgComeOn(child, idList);
			}

		}
	}

}
