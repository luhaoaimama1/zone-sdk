package and.abstractclass.recycler;

import java.util.List;

import and.abstractclass.recycler.core.BaseRecyclerAdapter;
import and.abstractclass.recycler.core.RecyclerHolder_Zone;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
/**
 * Created by Zone on 2015/12/16.
 */
public abstract class AdapterRecycler_Zone<T> extends BaseRecyclerAdapter<T>{
	private int layout_id;
	public AdapterRecycler_Zone(Context context,List<T> data) {
		super(context, data);
		this.layout_id=setLayoutID();
	}
	@Override
	public RecyclerHolder_Zone onCreateViewHolder(ViewGroup parent,  int viewType) {
		return new RecyclerHolder_Zone(LayoutInflater.from(context).inflate(layout_id, parent, false));
	}
	public abstract  int setLayoutID();
	

}
