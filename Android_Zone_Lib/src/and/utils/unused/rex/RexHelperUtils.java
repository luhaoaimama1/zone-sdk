package and.utils.unused.rex;

public class RexHelperUtils{
	private  StringBuffer sb=new StringBuffer();
	private boolean isFirst=true;
	/**
	 * 只是中间添加了|或  为了防止太长而看的迷糊
	 * @param rex
	 * @return
	 */
	public RexHelperUtils addRule(String rex){
		if (isFirst) {
			sb.append(rex);
			isFirst=false;
		}else{
			sb.append("|" +rex);
		}
		return this;
	}
	public String build() {
		return sb.toString();
	}
}
