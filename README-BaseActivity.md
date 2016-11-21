
观察者模式：
>考虑兼容(简称BaseActivity):BaseActivity,BaseAppCompatActivity,BaseFragmentActivity

目的：解耦，各司其职。拍照等涉及onActivityResult的封装

>ActivityKinds(观察者的基类): 在使用的BaseActivity注册ActivityKinds或者移除不必要的ActivityKinds
>专注：`onCreate`，`onResume`，`onActivityResult`，`onPause`，`onDestroy`
| 名字 | 功能  |
| :------------: |:---------------:| 
| CollectionActivityKind  | 收集所有Activity用的 |  
| FeaturesKind  |  [专门处理关于onActivityResult的,点此出介绍](./README-FeatureKind.md) | 
| ScreenSettingKind  | setNoTitle,setFullScreen的 | 
| SwipeBackKind  | 左滑退出Activity用的 集成别的项目~ |

>BaseActivity:

| 名字 | 功能  |
| :------------: |:---------------:|
| updateKinds  | 在这里 移除与注册 ActivityKinds | 
 

ActivityKinds范例：
```
 public class StatKind extends ActivityKinds {
     @Override
     public void onResume() {
         super.onResume();
         //百度统计
         StatService.onResume(activity);
         //jpush
         JpushUtils.Activity.resumePush(activity.getApplicationContext());
     }
 
     @Override
     public void onPause() {
         super.onPause();
         StatService.onPause(activity);
         JpushUtils.Activity.stopPush(activity.getApplicationContext());
     }
 }
```

BaseActivity移除不必要的SwipeBackKind 功能,注册StatKind;
```
public abstract class DBaseAppCompatActivity extends BaseAppCompatActivity {

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    @Override
    public void updateKinds() {
        mKindControl.remove(SwipeBackKind.class);
        mKindControl.put(new StatKind());
    }
}
```
