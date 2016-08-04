package com.zone.view;
import android.annotation.TargetApi;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.FloatMath;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fuzhipeng on 16/8/4.
 * 有父子关系;注意:但是构造器的x,y不是 相对的;
 * 在offset的时候对  children进行offset ;
 */
public class ZPointF implements Parcelable {
    public List<ZPointF> children;
    public float x;
    public float y;


    public ZPointF() {}

    public ZPointF(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public ZPointF(ZPointF p) {
        this.x = p.x;
        this.y = p.y;
    }

    /**
     * Set the point's x and y coordinates
     */
    public final void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Set the point's x and y coordinates to the coordinates of p
     */
    public final void set(ZPointF p) {
        this.x = p.x;
        this.y = p.y;
    }

    public final void negate() {
        x = -x;
        y = -y;
    }
    //offsetReplace 曾经移动的值 提交 ,既下次用的时候不用减回去了
    public void offsetReplaceCommit(String tag){
        PointF lastOffset = mapTag.get(tag);
        if(lastOffset!=null)
            lastOffset.set(0,0);
    }
    public void offsetReplaceCommitAll(){
        for (Map.Entry<String, PointF> stringPointFEntry : mapTag.entrySet()) {
            stringPointFEntry.getValue().set(0,0);
        }
    }
//    private float lastDx,  lastDy;//记录上一次的可替换偏移值 NB的地方啊

//    public final void offsetReplace(float dx, float dy){
//        x =x-lastDx+dx;
//        y =y-lastDy+ dy;
//        lastDx=dx;
//        lastDy=dy;
//        if(children!=null&&children.size()>0)
//            for (ZPointF child : children)
//                child.offsetReplace(dx,dy);
//    }
    private Map<String,PointF> mapTag=new HashMap<>();
    public final void offsetReplace(float dx, float dy,String tag){
        PointF lastOffset = mapTag.get(tag);
        if(lastOffset==null)
            lastOffset=new PointF();
        x =x-lastOffset.x+dx;
        y =y-lastOffset.y+ dy;
        lastOffset.set(dx,dy);
        mapTag.put(tag,lastOffset);
        if(children!=null&&children.size()>0)
            for (ZPointF child : children)
                child.offsetReplace(dx,dy,tag);
    }
    public final void offsetReplace(ZPointF pointF,String tag){
        offsetReplace(pointF.x,pointF.y,tag);
    }

    public final void offset(float dx, float dy) {
        x += dx;
        y += dy;
        if(children!=null&&children.size()>0)
            for (ZPointF child : children)
                child.offset(dx,dy);
    }
    public final void offset(ZPointF pointF) {
        offset(pointF.x,pointF.y);
    }

    /**
     * Returns true if the point's coordinates equal (x,y)
     */
    public final boolean equals(float x, float y) {
        return this.x == x && this.y == y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZPointF ZPointF = (ZPointF) o;

        if (Float.compare(ZPointF.x, x) != 0) return false;
        if (Float.compare(ZPointF.y, y) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ZPointF(" + x + ", " + y + ")";
    }

    /**
     * Return the euclidian distance from (0,0) to the point
     */
    public final float length() {
        return length(x, y);
    }

    /**
     * Returns the euclidian distance from (0,0) to (x,y)
     */
    public static float length(float x, float y) {
        return FloatMath.sqrt(x * x + y * y);
    }

    /**
     * Parcelable interface methods
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Write this point to the specified parcel. To restore a point from
     * a parcel, use readFromParcel()
     * @param out The parcel to write the point's coordinates into
     */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeFloat(x);
        out.writeFloat(y);
    }

    public static final Parcelable.Creator<ZPointF> CREATOR = new Parcelable.Creator<ZPointF>() {
        /**
         * Return a new point from the data in the specified parcel.
         */
        @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
        public ZPointF createFromParcel(Parcel in) {
            ZPointF r = new ZPointF();
            r.readFromParcel(in);
            return r;
        }

        /**
         * Return an array of rectangles of the specified size.
         */
        public ZPointF[] newArray(int size) {
            return new ZPointF[size];
        }
    };

        /**
     * Set the point's coordinates from the data stored in the specified
     * parcel. To write a point to a parcel, call writeToParcel().
     *
     * @param in The parcel to read the point's coordinates from
     */
    public void readFromParcel(Parcel in) {
        x = in.readFloat();
        y = in.readFloat();
    }

    public void addChildren(ZPointF... childs){
        if(children==null)
            children=new ArrayList<>();
        for (int i = 0; i < childs.length; i++)
            children.add(childs[i]);
    }
}
