package other_project.pinyin_sidebar;

import java.util.Comparator;

public class PinYinComparator implements Comparator<SortModel>{

	/**
	 * 排序比较
	 */
	@Override
	public int compare(SortModel lhs, SortModel rhs) {
		return lhs.getSortLetter().compareTo(rhs.getSortLetter());
	}

}
