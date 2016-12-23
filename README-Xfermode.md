
#Xfermode
>看之前请看Canvas的模型理解Layer,Canvas与Bitmap的关系[**Canvas介绍**](./README-Canvas.md)

![](./demo/xfermode.png)

>如正确姿势的图，首先要明白的

| 名字 | 解释2  | 名字 | 解释2  | 
| :------------: |:---------------:| :---------------:| :---------------:| 
| 黄色 | 先画,下层(dst) | in | 交集 |
| 蓝色 | 后画,上层(src) | out | 不相交的 | 
>举例：PorterDuff.Mode.SRC_IN参数，表示的显示的是 SRC层 IN交集的部分(注意 `这其实是DST层` 下面有解释)；
 
![](./demo/xfermode2.png)

| 名字 | 解释2  | 
| :------------: |:---------------:| 
| `图2` |  `创建两个Bitmap` `手机宽高` `bitmap上绘制圆` `如果看不懂看下面的代码`| 
| `图1` | `绘两个圆` `如果看不懂看下面的代码` | 

#核心原理(边界)：
>Xfermode效果:作用在 `两个边界之内`；`边界之外` `没有Xfermode效果`；

| 图 |边界 | SRC_IN  | SRC_OUT  |  
| :--: | :-------: |:---------------:|:---------------:| 
| `图2` `正确姿势` | 每个都是手机的宽高  |  正确姿势图中的SRC_IN   | 如正确姿势图中的 SRC_OUT   |
| `图1` `平时的误解` | 每个都是绘的圆那么大 | 如 `正确姿势图中的SRC_IN` `多出黄色的部分` | 如 `正确姿势图中的SRC_OUT` `多出黄色的部分` |
>正确姿势图 是官方给的图；

>Tips:为什么多出黄色的部分？ 因为这个部分是 边界未相交的部分,那么不会有Xfermode的效果 所以剩下； 

>`那么大家会很疑惑，为什么DST剩下了,SRC边界之外为何不剩下？` 

>因为DST先绘制的 就是底图.SRC是为了给DST添加叠加模式的效果的.

>`最终显示的都是DST只是变成有叠加效果的DST；`


`demo效果：动画、surfaceView、绘图方面的研究->Xfermode;然后选择模式；`
[xfermode代码](https://github.com/luhaoaimama1/zone-sdk/blob/master/Android_Zone_Test/src/com/example/mylib_test/activity/animal/viewa/XfermodeView.java)

图1的代码：
```
    canvas.saveLayerAlpha(0, 0, getWidth(), getHeight(), 255,
                Canvas.ALL_SAVE_FLAG);

        paint.setColor(Color.YELLOW);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 200, paint);
        paint.setXfermode(new PorterDuffXfermode(mode));
        paint.setColor(Color.BLUE);
        canvas.drawCircle(getWidth() / 2 + 200, getHeight() / 2 + 200, 200, paint);
```

图2的代码：
```
    //画黄色的圆 满屏幕那种 bitmap
    Bitmap bt = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444);
    canvas2.setBitmap(bt);
    paint.setColor(Color.YELLOW);
    canvas2.drawCircle(getWidth() / 2, getHeight() / 2, 200, paint);
    //画蓝色的圆 满屏幕那种 bitmap
    Bitmap bt2 = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444);
    canvas2.setBitmap(bt2);
    paint.setColor(Color.BLUE);
    canvas2.drawCircle(getWidth() / 2 + 200, getHeight() / 2 + 200, 200, paint);
```

##问题延伸-为什么 不用saveLayerAlpha有时候就不好使 
[harvic博客解释了为什么不用saveLayerAlpha有时候就不好使?](http://blog.csdn.net/harvic880925/article/details/51317746)

```
int layerID = canvas.saveLayer(0, 0, width * 2, height * 2, mPaint, Canvas.ALL_SAVE_FLAG);  
canvas.drawBitmap(dstBmp, 0, 0, mPaint);  
mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));  
canvas.drawBitmap(srcBmp, width / 2, height / 2, mPaint);  
```
![](./demo/xfermode_question1.png)
####**有saveLayer的绘图流程**
这是因为在调用saveLayer时，会生成了一个全新的bitmap，这个bitmap的大小就是我们指定的保存区域的大小，新生成的bitmap是全透明的，在调用saveLayer后所有的绘图操作都是在这个bitmap上进行的。
![](./demo/xfermode_question2.png)
####**没有saveLayer的绘图流程**
由于我们先把整个画布给染成了绿色，然后再画上了一个圆形，所以在应用xfermode来画源图像的时候，目标图像当前Bitmap上的所有图像了，也就是整个绿色的屏幕和一个圆形了。所以这时候源图像的相交区域是没有透明像素的，透明度全是100%，这也就不难解释结果是这样的原因了。
![](./demo/xfermode_question3.png)
>总结就是  saveLayer为了区分，哪一步的图形,应该与合成模式的bitmap去合成 运算；

