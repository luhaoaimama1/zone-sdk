
观察者模式：

>ExtraFeature(观察者的基类):专注：`init（onResume中使用）`,`onActivityResult`,`destory`

>RequestCodeConfig(所有ExtraFeature的Code声明):其中的START_CODE(默认1000开始)可以修改 主要是怕碰到别的类似开源项目code重复导致不必要的问题;

| 名字 | 功能  |
| :------------: |:---------------:|
| Feature_Pic  | 拍照功能与相册选择功能(如果写建议参考这个) | 
| Feature_SystemClip  | 系统裁剪(demo 让我删除了.) | 
| Featrue_CustomCamera  | 自定义相机(demo 没有. 很久以前的了就不给demo了~) | 
> Tips:`返回值的方法要抽象`,这样就不会忘记写了

> 剩下的方法就可以public不抽象了~

Activity中拍照的使用范例：
>声明后 注意 把拍照的功能添加到FeaturesKind里面 `mKindControl.get(FeaturesKind.class).addFeature(feature_Pic);`

```
@Override
public void updateKinds() {
    super.updateKinds();

    feature_Pic = new Feature_Pic(this) {
        @Override
        protected void getReturnedPicPath(String path) {
            System.out.println(path);
            Intent intent = new Intent(Photo_Shot_MainActivity.this,ShowPicActivity.class);
            Uri uri = Uri.parse(path);
            intent.setData(uri);
            startActivity(intent);
        }
    };
    mKindControl.get(FeaturesKind.class).addFeature(feature_Pic);
}
```
