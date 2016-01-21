package and.abstractclass.refreshdata;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
/**
 * 添加后 记得在ondestory里摧毁
 * @author Zone
 *
 */
public class DataUpdate_Observable {
	private List<DataUpdate_IObserver> observersList = new ArrayList<DataUpdate_IObserver>();
	
	public void addIObserver (DataUpdate_IObserver observer){
		observersList.add(observer);
	}
	
	public void clear(){
		observersList.clear();
	}
	/**
	 * 这个会删除 所有的关于这个类的所有监听
	 * @param observer
	 */
	public void removeIObserver(DataUpdate_IObserver observer){
		observersList.remove(observer);
	}
	
	public void notify(Object o){
		for (DataUpdate_IObserver item : observersList) {
			item.updateData(o);
		}
	}
}
