package and.base.activity;
import com.nostra13.universalimageloader.core.ImageLoader;
import and.base.activity.decorater.ZFinalDecorater;
import android.os.Bundle;

public abstract class BaseActvity extends ZFinalDecorater {

	protected ImageLoader imageLoader;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		imageLoader = ImageLoader.getInstance();
	}
}
