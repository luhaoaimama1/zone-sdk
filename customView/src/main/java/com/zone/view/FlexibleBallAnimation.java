package com.zone.view;

import android.graphics.Matrix;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by fuzhipeng on 16/8/4.
 */
public class FlexibleBallAnimation {
    /**
     * 位移距离
     */
    private float mMoveDistance;
    /**
     * 弹性距离
     */
    private float mElasticDistance;
    /**
     * 弹性比例
     */
    private float mElasticPercent = 0.8f;

    /**
     * 动画消费时间
     */
    private long mDuration = 1500;
    /**
     * 圆形偏移比例
     */
    private float c = (float) (4 * Math.tan(Math.PI / 8) / 3);//

    //最大变胖的值
    private float c2 = 0.65f;
    public float mAnimPercent;
    public Listener mListener;
    public MathUtils.Circle circle ;
    private float controlLength;
    private MathUtils.Circle endCircle;


    public void start(final MathUtils.Circle circle, ZPointF end, Listener listener) {
        this.mListener=listener;
        this.circle=circle;

        //初始化一些值
        controlLength = circle.r * c;
        mElasticDistance=circle.r*mElasticPercent;
        mMoveDistance=MathUtils.getLength(circle.center,end);
        ZPointF tempPonit = new ZPointF(mMoveDistance, 0);
        tempPonit.offset(circle.center);
        endCircle=new MathUtils.Circle(tempPonit,circle.r);

        //仅仅考虑右边既可以了嘎嘎!先把end点 按照 start点旋转成右边 --->最后按照start点把画布反旋转过来  来适应所有角度
        final float startAngel = MathUtils.getP2AngleByX(end,circle.center);
        Matrix m = new Matrix();
        float[] src = new float[]{end.x, end.y};
        float[] dst = new float[2];//代替了c2.center的点
        m.postRotate(-startAngel, circle.center.x, circle.center.y); //现在都在右边了
        m.mapPoints(dst, src);
        ZPointF endReplace = new ZPointF(dst[0], dst[1]);


        //计算 8个控制点    动画状态0 （初始状态：圆形）
        initContrlPoint(circle);

        ValueAnimator mValueAnimator = ValueAnimator.ofFloat(0F, 1F);
        mValueAnimator.setDuration(mDuration);
        mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mValueAnimator.start();
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimPercent = (float) animation.getAnimatedValue();
                if(mAnimPercent<0.2){ //动画状态1 （0~0.2）
                    animStatus1();
                }else if(mAnimPercent<0.5){//动画状态2 （0.2~0.5）
                    animStatus2();
                }else if(mAnimPercent<0.8){ //动画状态3 （0.5~0.8）
                    animStatus3();
                } else if(mAnimPercent<0.9){//动画状态4 （0.8~0.9）
                    animStatus4();
                }else{
                    animStatus5();  //动画状态5 （0.9~1）回弹
                }
                ZPath circlePath=buildPath(startAngel);
                if(mListener!=null)
                    mListener.update(circlePath);
            }
        });
    }

    //把控制点  和 都绘制起来;
    private ZPath buildPath(float startAngel) {
        //编辑Path
        ZPath circlePath = new ZPath();
        circlePath.moveTo(circle.top);
        circlePath.cubicTo(topLeftContrl, leftTopContrl, circle.left);
        circlePath.cubicTo(leftBottomContrl, bottomLeftContrl, circle.bottom);
        circlePath.cubicTo(bottomRightContrl, rightBottomContrl, circle.right);
        circlePath.cubicTo(rightTopContrl, topRightContrl, circle.top);
        //在反向 旋转回去
        Matrix m2 = new Matrix();
        m2.postRotate(startAngel, circle.center.x, circle.center.y);
        circlePath.transform(m2);
        return circlePath;
    }

    boolean status1Commit,status2Commit,status3Commit,status4Commit;
    //动画状态1 （0~0.2）开始启动，此时右边点位移，其他点不动
    private void animStatus1() {
        float percent = mAnimPercent * 5F;//为了变成1
        circle.right.offsetReplace(percent*mElasticDistance,0,"parent");

    }
    //动画状态2 （0.2~0.5） 开始加速
    private void animStatus2() {
        if(!status1Commit){
            status1Commit=true;
            //动画状态1 把这个位置真正的记录下来;
            commit();
        }

        float percent = (float) ((mAnimPercent - 0.2) * (10f / 3));

        circle.right.offsetReplace((mMoveDistance-mElasticDistance)*percent/2,0,"parent");
        circle.left.offsetReplace((mMoveDistance-mElasticDistance)*percent/2,0,"parent");

        circle.top.offsetReplace(mMoveDistance*percent/2,0,"parent");
        circle.bottom.offsetReplace(mMoveDistance*percent/2,0,"parent");

        //控制点 左边的往左边一点  右边的往右边有一点  lachen的感觉
        topLeftContrl.offsetReplace(-(c2-c)*percent,0,"child");
        topRightContrl.offsetReplace((c2-c)*percent,0,"child");
        bottomLeftContrl.offsetReplace(-(c2-c)*percent,0,"child");
        bottomRightContrl.offsetReplace((c2-c)*percent,0,"child");

    }
    private ZPointF rightStart3,centerStart3;
    //动画状态3 （0.5~0.8） 减速
    private void animStatus3() {
        if(!status2Commit){
            status2Commit=true;
            //动画状态1 把这个位置真正的记录下来;
            commit();
            rightStart3=new ZPointF(circle.right);//必须新建对象来 记录值 不能用等于
            centerStart3=new ZPointF(circle.top);
        }
        float percent = (float) ((mAnimPercent - 0.5) * (10f / 3));
        circle.right.offsetReplace((endCircle.right.x-rightStart3.x)*percent,0,"parent");
        circle.left.offsetReplace((endCircle.top.x-centerStart3.x)*percent,0,"parent");
        circle.top.offsetReplace((endCircle.top.x-centerStart3.x)*percent,0,"parent");
        circle.bottom.offsetReplace((endCircle.top.x-centerStart3.x)*percent,0,"parent");

        //控制点 在反向回去
        topLeftContrl.offsetReplace((c2-c)*percent,0,"child");
        topRightContrl.offsetReplace(-(c2-c)*percent,0,"child");
        bottomLeftContrl.offsetReplace((c2-c)*percent,0,"child");
        bottomRightContrl.offsetReplace(-(c2-c)*percent,0,"child");

    }

    private void commit() {
        circle.right.offsetReplaceCommitAll();
        circle.left.offsetReplaceCommitAll();
        circle.top.offsetReplaceCommitAll();
        circle.bottom.offsetReplaceCommitAll();

        topLeftContrl.offsetReplaceCommitAll();
        topRightContrl.offsetReplaceCommitAll();

        bottomLeftContrl.offsetReplaceCommitAll();
        bottomRightContrl.offsetReplaceCommitAll();

        rightBottomContrl.offsetReplaceCommitAll();
        rightTopContrl.offsetReplaceCommitAll();

        leftBottomContrl.offsetReplaceCommitAll();
        leftTopContrl.offsetReplaceCommitAll();
    }

    private ZPointF leftStart4;

    //动画状态4 （0.8~0.9） 到达终点后的  挤压
    private void animStatus4() {
        if(!status3Commit) {
            status3Commit = true;
            commit();
            leftStart4=new ZPointF(circle.left);
            topRightBottomFix();
        }
        float percent = (float) (mAnimPercent - 0.8) * 10;
        circle.left.offsetReplace((endCircle.left.x-leftStart4.x+mElasticDistance/2)*percent,0,"parent");

    }

    private void topRightBottomFix() {
        circle.right.set(endCircle.right);
        circle.bottom.set(endCircle.bottom);
        circle.top.set(endCircle.top);

        initContrlPointNoLeft(endCircle);
    }

    private ZPointF leftStart5;
    //动画状态5 （0.9~1）回弹
    private void animStatus5() {
        System.err.println("mAnimPercent:"+mAnimPercent);
        float percent = (float) (mAnimPercent - 0.9) * 10;
        if(!status4Commit) {
            status4Commit = true;
            commit();
            leftStart5=new ZPointF(circle.left);
        }
        circle.left.offsetReplace((endCircle.left.x-leftStart5.x)*percent,0,"parent");
    }


    private ZPointF topLeftContrl, topRightContrl, bottomLeftContrl, bottomRightContrl,
            leftTopContrl, leftBottomContrl, rightTopContrl, rightBottomContrl;

    //修正位置用的   float计算 稍微有点不精确
    private void initContrlPointNoLeft(MathUtils.Circle circle1) {
        topLeftContrl = new ZPointF();
        topLeftContrl.set(-controlLength, 0);
        topLeftContrl.offset(circle1.top.x, circle1.top.y);//跟随top移动

        topRightContrl = new ZPointF();
        topRightContrl.set(controlLength, 0);
        topRightContrl.offset(circle1.top.x, circle1.top.y);

        bottomLeftContrl = new ZPointF();
        bottomLeftContrl.set(-controlLength, 0);
        bottomLeftContrl.offset(circle1.bottom.x, circle1.bottom.y);

        bottomRightContrl = new ZPointF();
        bottomRightContrl.set(controlLength, 0);
        bottomRightContrl.offset(circle1.bottom.x, circle1.bottom.y);

        rightTopContrl = new ZPointF();
        rightTopContrl.set(0, controlLength);
        rightTopContrl.offset(circle1.right.x, circle1.right.y);

        rightBottomContrl = new ZPointF();
        rightBottomContrl.set(0, -controlLength);
        rightBottomContrl.offset(circle1.right.x, circle1.right.y);
    }
    private void initContrlPoint(MathUtils.Circle circle1) {
        topLeftContrl = new ZPointF();
        topLeftContrl.set(-controlLength, 0);
        topLeftContrl.offset(circle1.top.x, circle1.top.y);//跟随top移动

        topRightContrl = new ZPointF();
        topRightContrl.set(controlLength, 0);
        topRightContrl.offset(circle1.top.x, circle1.top.y);

        bottomLeftContrl = new ZPointF();
        bottomLeftContrl.set(-controlLength, 0);
        bottomLeftContrl.offset(circle1.bottom.x, circle1.bottom.y);

        bottomRightContrl = new ZPointF();
        bottomRightContrl.set(controlLength, 0);
        bottomRightContrl.offset(circle1.bottom.x, circle1.bottom.y);


        leftTopContrl = new ZPointF();
        leftTopContrl.set(0, controlLength);
        leftTopContrl.offset(circle1.left.x, circle1.left.y);


        leftBottomContrl = new ZPointF();
        leftBottomContrl.set(0, -controlLength);
        leftBottomContrl.offset(circle1.left.x, circle1.left.y);


        rightTopContrl = new ZPointF();
        rightTopContrl.set(0, controlLength);
        rightTopContrl.offset(circle1.right.x, circle1.right.y);

        rightBottomContrl = new ZPointF();
        rightBottomContrl.set(0, -controlLength);
        rightBottomContrl.offset(circle1.right.x, circle1.right.y);

        //绑定父子关系   父亲移动了孩子就跟着移动
        circle1.top.addChildren(topLeftContrl,topRightContrl);
        circle1.left.addChildren(leftBottomContrl,leftTopContrl);
        circle1.bottom.addChildren(bottomLeftContrl,bottomRightContrl);
        circle1.right.addChildren(rightBottomContrl,rightTopContrl);
    }


    public interface Listener{
        void update(ZPath path);
    }
}
