package and.http.downfile.entity;
import java.util.ArrayList;
import java.util.List;

import and.http.downfile.DownLoader.ProgressListener;
import android.os.Handler;
import android.os.Looper;

public class ProgressEntity {
	public enum RangeStaue {
		SUPPORTED, UNSUPPORTED
	}

	public static List<ProgressEntity> peList = new ArrayList<ProgressEntity>();


	private RangeStaue rs;
	public String url;
	private int total;
	private boolean initStatue=false;
	/**
	 * 注意因为线程是从一开始的 所以 0 默认不用
	 */
	private List<Integer> countList = null;
	private List<Boolean> completeList = null;

	public ProgressEntity(String url) {
		this.url = url;
		peList.add(this);
	}
	public void init( int total, int threadCount, RangeStaue rs){
		this.total = total;
		this.rs = rs;
		countList = new ArrayList<Integer>();
		completeList = new ArrayList<Boolean>();
		
		for (int i = 0; i <= threadCount; i++) {
			countList.add(0);
			completeList.add(false);
		}
		initStatue=true;
	}

	public void set_updateProgress(int indexThread, int overLengh,
			final ProgressListener pl, Handler handler) {
		if(!initStatue){
			throw new IllegalStateException("may be not use init() method!");
		}
		//监听进度
		if (pl != null) {
			countList.set(indexThread, overLengh);
			int current = 0;
			for (Integer item : countList) {
				current += item;
			}
			final float progress = 1F * current / total;
			final	int  currentFinal=current;
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					pl.onProgressUpdate(currentFinal, total, progress);
				}
			});
		
		}
		
	}
	public void set_complete(int index){
		completeList.set(index, true);
		//判断是否完成
		for (int i = 1; i < completeList.size(); i++) {
			if (!completeList.get(i)) {
				return;
			}
		}
		peList.remove(this);
	}

}
