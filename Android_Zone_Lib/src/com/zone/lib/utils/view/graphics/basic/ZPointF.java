package com.zone.lib.utils.view.graphics.basic;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fuzhipeng on 16/8/4.
 * 有父子关系;注意:但是构造器的x,y不是 相对的;
 * 在offset的时候对  children进行offset ;
 */
public class ZPointF {
    public static final String TAG="ZPointF";
    public List<ZPointF> childs;
    public ZPointF parent;
    private Map<String, PointF> offsetReplaceMap = new HashMap<>();
    public float x;
    public float y;
    public float rx;
    public float ry;


    public ZPointF() {
    }

    public ZPointF(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public ZPointF(ZPointF point) {
        this(point.x, point.y);
    }

    public ZPointF(float rx, float ry, ZPointF parent) {
        this.rx = rx;
        this.ry = ry;
        setParent(parent);
        calculatePositionByParent();
    }

    public ZPointF(ZPointF point, ZPointF parent) {
        this(point.x, point.y, parent);
    }

    public void bindParent(ZPointF parent) {
        unBindParent();
        setParent(parent);
        calculateRposition();
    }

    public void unBindParent() {
        if (parent == null)
            return;
        parent.removeChild(this);
        parent = null;
        rx = ry = 0F;
    }


    /**
     * Set the point's x and y coordinates
     */
    public final void set(float x, float y) {
        if (this.x == x && this.y == y)
            return;
        this.x = x;
        this.y = y;
        calculateRposition();
        calculateChildsPosition();
    }

    public final void set(ZPointF point) {
        set(point.x, point.y);
    }

    public final void rSet(float rx, float ry) {
        if (this.rx == rx && this.ry == ry)
            return;
        this.rx = rx;
        this.ry = ry;
        calculatePositionByParent();
        calculateChildsPosition();
    }

    /**
     * Set the point's x and y coordinates to the coordinates of p
     */
    public final void rSet(ZPointF point) {
        rSet(point.x, point.y);
    }

    //offsetReplace 曾经移动的值 提交 ,既下次用的时候不用减回去了
    public void offsetReplaceCommit(String tag) {
        PointF lastOffset = offsetReplaceMap.get(tag);
        if (lastOffset != null)
            lastOffset.set(0, 0);
    }

    public void offsetReplaceCommitUpdateChilds(String tag) {
        offsetReplaceCommit(tag);
        if (childs != null && childs.size() > 0)
            for (ZPointF child : childs)
                child.offsetReplaceCommitUpdateChilds(tag);
    }

    public void offsetReplaceCommitAll() {
        for (Map.Entry<String, PointF> stringPointFEntry : offsetReplaceMap.entrySet()) {
            stringPointFEntry.getValue().set(0, 0);
        }
    }

    public void offsetReplaceCommitAllUpdateChilds() {
        offsetReplaceCommitAll();
        if (childs != null && childs.size() > 0)
            for (ZPointF child : childs)
                child.offsetReplaceCommitAllUpdateChilds();
    }
    public final void offsetReplace(float dx, float dy){
        offsetReplace(dx,dy,TAG);
    }
    public final void offsetReplace(ZPointF pointF){
        offsetReplace(pointF.x,pointF.y,TAG);
    }

    /**
     * parent==null  x,y move
     * parent!=null rx,ry,x,y  move
     * @param dx
     * @param dy
     * @param tag
     */
    public final void offsetReplace(float dx, float dy, String tag) {
        if (dx == 0F && dy == 0F)
            return;
        PointF lastOffset = offsetReplaceMap.get(tag);
        if (lastOffset == null)
            lastOffset = new PointF();

        x = x - lastOffset.x + dx;
        y = y - lastOffset.y + dy;

        lastOffset.set(dx,dy);
        offsetReplaceMap.put(tag, lastOffset);

        calculateRposition();
        calculateChildsPosition();
    }

    public final void offsetReplace(ZPointF pointF, String tag) {
        offsetReplace(pointF.x, pointF.y, tag);
    }

    /**
     * parent==null  x,y move
     * parent!=null rx,ry,x,y  move
     * @param dx
     * @param dy
     */
    public final void offset(float dx, float dy) {
        if (dx == 0F && dy == 0F)
            return;
        x += dx;
        y += dy;
        calculateRposition();
        calculateChildsPosition();
    }


    //todo
    public final void offset(ZPointF pointF) {
        offset(pointF.x, pointF.y);
    }


    /**
     * Returns true if the point's coordinates equal (x,y)
     */
    public final boolean equals(float x, float y) {
        return this.x == x && this.y == y;
    }


    @Override
    public String toString() {
        return "ZPointF(x:" + x + ", y:" + y + ",parent:" + (parent == null ? "null" : parent) + ",rx:" + rx + ",ry" + ry + "child:不叙述了)";
    }

    private void addChild(ZPointF child) {
        if (this.childs == null)
            this.childs = new ArrayList<>();
        this.childs.add(child);
    }

    private void removeChild(ZPointF child) {
        if (this.childs == null)
            this.childs = new ArrayList<>();
        this.childs.remove(child);
    }

    private void calculatePositionByParent() {
        if (parent == null)
            throw new NullPointerException("parent is null!");
        this.x = parent.x + rx;
        this.y = parent.y + ry;
    }

    public void calculateRposition() {
        if (parent == null)
            return;
        rx = this.x - parent.x;
        ry = this.y - parent.y;
    }

    private void calculateChildsPosition() {
        if (childs != null && childs.size() > 0)
            for (ZPointF child : childs)
                child.calculatePositionByParent();
    }

    private void setParent(ZPointF parent) {
        //防止绑定多次
        if (this.parent == parent)
            return;
        this.parent = parent;
        this.parent.addChild(this);
    }

}
