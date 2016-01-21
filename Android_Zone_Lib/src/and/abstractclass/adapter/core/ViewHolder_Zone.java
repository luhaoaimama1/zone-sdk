package and.abstractclass.adapter.core;

import android.util.SparseArray;
import android.view.View;
public class ViewHolder_Zone {
	public  SparseArray<View> map=new SparseArray<View>();
    private View convertView;
    public ViewHolder_Zone(View convertView) {
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
