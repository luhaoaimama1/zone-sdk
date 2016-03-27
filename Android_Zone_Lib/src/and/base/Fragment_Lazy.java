package and.base;

import android.support.v4.app.Fragment;  

/** 
* Author: msdx (645079761@qq.com) 
* Time: 14-7-17 下午5:46 
*/  
public abstract class Fragment_Lazy extends Fragment {  
  protected boolean isVisible;  
  /** 
   * 在这里实现Fragment数据的缓加载. 
   * @param isVisibleToUser 
   */  
  @Override  
  public void setUserVisibleHint(boolean isVisibleToUser) {  
      super.setUserVisibleHint(isVisibleToUser);
      //判断是否对用户可见
      if(getUserVisibleHint()) {  
          isVisible = true;  
          onVisible();  
      } else {  
          isVisible = false;  
          onInvisible();  
      }  
  }  

  protected abstract void onVisible();

  protected abstract  void onInvisible();
}  
