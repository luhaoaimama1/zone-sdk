package and.viewpage.simpleadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.List;
import and.viewpage.PagerAdapterCycle;
import and.viewpage.simpleadapter.inter.SimpleAdapterSetImage;

/**
 * Created by Zone on 2016/1/27.
 */
public abstract class PagerAdapterCircle_Image<T> extends PagerAdapterCycle implements SimpleAdapterSetImage {
    public PagerAdapterCircle_Image(Context context,List<T> data) {
        super(context, data);
    }
    @Override
    public View getView(Context context,int position) {
        ImageView iv = new ImageView(context);
        iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        setImage(iv, position);
        return iv;
    }
}
