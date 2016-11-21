
遵守约定：就是google写了一套约定规范 这样大家都照规定写就可以兼容了；

dispatchNestedPreScroll (child消耗前传递给 parent)
dispatchNestedScroll (child消耗后传递给 parent)
fling和 scorll 类似 就不说了


>方法对应：子view会 触发 父view方法。一般是子view发起调用，父view接受回调。

| 子view | 父view  |
| :------------: |:---------------:| 
| startNestedScroll | onStartNestedScroll、onNestedScrollAccepted | 
| dispatchNestedPreScroll | onNestedPreScroll(子view消耗前) | 
| dispatchNestedScroll | onNestedScroll(子view消耗后) | 
| stopNestedScroll   | onStopNestedScroll | 


[child流程接入](http://fromwiz.com/share/s/3Hsjaq1-lQ9Q2SChN02Hkyvk0hpNyB0-1QGL2Fj4sK1tPrPG)

###方法的使用时机:

startNestedScroll,stopNestedScroll；
![](https://github.com/luhaoaimama1/zone-sdk/blob/master/demo/NestedScroll_start.png)

dispatchNestedPreScroll使用时机  此例为RecyclerView的内容；
(因为是子view消耗钱 父消耗 )  所以 用if判断 父亲是否消耗；

![](https://github.com/luhaoaimama1/zone-sdk/blob/master/demo/Nested_PreScroll.png)

dispatchNestedScroll  则和上边不同；是child先消耗了；在吧剩下的给NestedParent；看他消耗不；

![](https://github.com/luhaoaimama1/zone-sdk/blob/master/demo/Nested_dispatchScroll.png)

[parent流程接入](http://fromwiz.com/share/s/3Hsjaq1-lQ9Q2SChN02Hkyvk2vEyiC22IktY2lqqNl1d1jF9)

