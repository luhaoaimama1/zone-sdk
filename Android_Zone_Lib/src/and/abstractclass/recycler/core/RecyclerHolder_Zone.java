package and.abstractclass.recycler.core;
import android.util.SparseArray;
import android.view.View;
/**
 * Created by Zone on 2015/12/16.
 */
public class RecyclerHolder_Zone extends android.support.v7.widget.RecyclerView.ViewHolder{
    public  SparseArray<View> map=new SparseArray<View>();
    private View convertView;
    public RecyclerHolder_Zone(View convertView) {
        super(convertView);
        this.convertView=convertView;
    }
    public View findViewById(int id){
    	View view = map.get(id);
	    if(view == null){
	    	view=convertView.findViewById(id);
	    	map.put(id, view);
	    }		
    	return view;
    }
}