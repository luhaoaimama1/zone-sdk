package com.zone.view.base.entity;
import com.zone.view.base.ViewProperty;

public class ViewProperty_FlowLayout extends ViewProperty {
    //是否在布局里绘制 发现不好使 只能用invsibility  为了避免和别人已经invisbility重复 isLayout=false的时候我才恢复成visibility
    public boolean isLayout = true;
    //记录 第几行 第几行的第几个。可以用可以不用 从0,0开始
    public int verticalNum = 0, horizontalNum = 0;
}