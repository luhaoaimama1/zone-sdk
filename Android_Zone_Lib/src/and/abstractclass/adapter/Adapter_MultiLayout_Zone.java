package and.abstractclass.adapter;
import java.util.List;
import java.util.Map;

import and.abstractclass.adapter.core.BaseAdapter_Zone;
import android.content.Context;
import android.view.View;

/**
 * 嗣票擁
 * @author Zone
 */
public abstract class Adapter_MultiLayout_Zone<T> extends  BaseAdapter_Zone<T> {
	private int[] layout_ids;
	/**
	 * @param context
	 * @param data
	 * @param layout_ids
	 */
	public Adapter_MultiLayout_Zone(Context context, List<T> data) {
		super(context,data);
		this.layout_ids=setLayoutIDs();
	}

	public int getItemViewType(int position) {
		int temp=getItemViewType_Zone(position,layout_ids);
		return temp;
	}
	public int getViewTypeCount() {
		return layout_ids.length;
	}
	/**
	 * 
	 * @param position
	 * @param layout_ids 
	 * @return  殿隙腔id岆訧埭id
	 */
	public abstract int getItemViewType_Zone(int position, int[] layout_ids);
	
	@Override
	public int getLayoutID(int position) {
		return getItemViewType(position);
	}
	public abstract  int[] setLayoutIDs();


}
