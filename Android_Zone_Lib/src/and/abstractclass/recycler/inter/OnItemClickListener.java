package and.abstractclass.recycler.inter;

import and.abstractclass.recycler.core.RecyclerHolder_Zone;
import android.view.View;

public interface OnItemClickListener {
	void onItemClick(View convertView, int position, RecyclerHolder_Zone holder);
}
