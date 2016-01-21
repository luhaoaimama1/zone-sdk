package memorylarge;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class StorageActivity extends Activity{
	private static Context context;
	private static View sView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this;
		sView=new View(this);
	}

}
