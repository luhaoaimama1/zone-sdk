//package com.zone.view;
//
//import android.animation.Animator;
//import android.animation.ValueAnimator;
//import android.annotation.TargetApi;
//import android.graphics.Path;
//import android.graphics.PointF;
//import android.os.Build;
//import android.view.animation.AccelerateDecelerateInterpolator;
//
///**
// * 弹性球
// * @author linzewu
// * @date 2016/6/1
// */
//public class ElasticBall extends Ball {
//    /**
//     * 向上运动
//     */
//    private static final int DIRECTION_UP = 1;
//    /**
//     * 向下运动
//     */
//    private static final int DIRECTION_DOWN = 2;
//    /**
//     * 向左运动
//     */
//    private static final int DIRECTION_LEFT = 3;
//    /**
//     * 向右运动
//     */
//    private static final int DIRECTION_RIGHT = 4;
//    /**
//     * 运动方向
//     */
//    private int mDirection;
//    /**
//     * 动画完成百分比（0~1）
//     */
//    private float mAnimPercent;
//    /**
//     * 弹性距离
//     */
//    private float mElasticDistance;
//    /**
//     * 弹性比例
//     */
//    private float mElasticPercent = 0.8f;
//    /**
//     * 位移距离
//     */
//    private float mMoveDistance;
//    /**
//     * 动画消费时间
//     */
//    private long mDuration = 1500;
//
//    /**
//     * 偏移值
//     */
//    private float offsetTop, offsetBottom, offsetLeft, offsetRight;
//    /**
//     * 圆形偏移比例
//     */
//    private float c = 0.551915024494f;
//
//    private float c2 = 0.65f;
//    /**
//     * 动画开始点
//     */
//    private Ball mStartPoint;
//
//    /**
//     * 动画结束点
//     */
//    private Ball mEndPoint;
//
//    /**
//     * 构造方法
//     *
//     * @param x 圆心横坐标
//     * @param y 圆心纵坐标
//     * @param radius 圆半径
//     */
//    public ElasticBall(float x, float y, float radius) {
//        super(x, y, radius);
//        init();
//    }
//
//
//    private void init() {
//        mElasticDistance = mElasticPercent * radius;
//        offsetTop = c * radius;
//        offsetBottom = c * radius;
//        offsetLeft = c * radius;
//        offsetRight = c * radius;
//    }
//
//    public interface ElasticBallInterface{
//        void onChange(Path path);
//        void onFinish();
//    }
//
//    private ElasticBallInterface mElasticBallInterface;
//
//    /**
//     * 对外公布方法，设置弹性比例 （0~1）
//     * @param elasticPercent
//     */
//    public void setElasticPercent(float elasticPercent) {
//
//    }
//    /**
//     * 对外公布方法，设置动画时间
//     * @param duration
//     */
//    public void setDuration(long duration) {
//        this.mDuration = duration;
//    }
//
//    /**
//     * 对外公布方法， 开启动画
//     * @param endPoint
//     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    public void startElasticAnim(PointF endPoint, ElasticBallInterface elasticBallInterface) {
//        this.mEndPoint = new ElasticBall(endPoint.x, endPoint.y, radius);
//        this.mStartPoint = new ElasticBall(x, y, radius);
//        this.mStatusPoint1 = new ElasticBall(x, y, radius);
//        this.mStatusPoint2 = new ElasticBall(x, y, radius);
//        this.mStatusPoint3 = new ElasticBall(x, y, radius);
//        this.mStatusPoint4 = new ElasticBall(x, y, radius);
//        this.mStatusPoint5 = new ElasticBall(x, y, radius);
//        this.mElasticBallInterface = elasticBallInterface;
//        judgeDirection();
//        mMoveDistance = getDistance(mStartPoint.x, mStatusPoint1.y, endPoint.x, endPoint.y);
//        animStatus0();
//        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
//        valueAnimator.setDuration(mDuration);
//        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        valueAnimator.start();
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                mAnimPercent = (float) animation.getAnimatedValue();
//                if(mAnimPercent>=0 && mAnimPercent <= 0.2){
//                    animStatus1();
//                }
//                else if(mAnimPercent > 0.2 && mAnimPercent <= 0.5){
//                    animStatus2();
//                }
//                else if(mAnimPercent > 0.5 && mAnimPercent <= 0.8){
//                    animStatus3();
//                }
//                else if(mAnimPercent > 0.8 && mAnimPercent <= 0.9){
//                    animStatus4();
//                }
//                else if(mAnimPercent > 0.9&&mAnimPercent <= 1){
//                    animStatus5();
//                }
//                if (mElasticBallInterface != null) {
//                    mElasticBallInterface.onChange(drawElasticCircle(topX, topY, offsetTop, offsetTop,
//                            bottomX, bottomY, offsetBottom, offsetBottom,
//                            leftX, leftY, offsetLeft, offsetLeft,
//                            rightX, rightY, offsetRight, offsetRight));
//                }
//            }
//        });
//        valueAnimator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                if (mElasticBallInterface != null) {
//                    mElasticBallInterface.onFinish();
//                }
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
//    }
//
//    private void judgeDirection() {
//        if (mEndPoint.x - mStartPoint.x > 0) {
//            mDirection = DIRECTION_RIGHT;
//        }else if (mEndPoint.x - mStartPoint.x < 0) {
//            mDirection = DIRECTION_LEFT;
//        }else if (mEndPoint.y - mStartPoint.x > 0) {
//            mDirection = DIRECTION_DOWN;
//        }else if (mEndPoint.y - mStartPoint.y < 0){
//            mDirection = DIRECTION_UP;
//        }
//    }
//
//    /**
//     * 动画状态0 （初始状态：圆形）
//     */
//    private void animStatus0() {
//        offsetTop = c * radius;
//        offsetBottom = c * radius;
//        offsetLeft = c * radius;
//        offsetRight = c * radius;
//    }
//
//    private Ball mStatusPoint1;
//
//    /**
//     * 动画状态1 （0~0.2）
//     */
//    private void animStatus1() {
//        float percent = mAnimPercent * 5f;
//        if (mDirection == DIRECTION_LEFT) {
//            leftX = mStartPoint.leftX - percent * mElasticDistance;
//        } else if (mDirection == DIRECTION_RIGHT) {
//            rightX = mStartPoint.rightX + percent * mElasticDistance;
//        } else if (mDirection == DIRECTION_UP) {
//            topY = mStartPoint.topY - percent * mElasticDistance;
//        } else if (mDirection == DIRECTION_DOWN) {
//            bottomY = mStartPoint.bottomY + percent * mElasticDistance;
//        }
//        mStatusPoint1.refresh(x, y, topX, topY, bottomX, bottomY,
//                leftX, leftY, rightX, rightY);
//    }
//
//    private Ball mStatusPoint2;
//
//    /**
//     * 动画状态2 （0.2~0.5）
//     */
//    private void animStatus2() {
//        float percent = (float) ((mAnimPercent - 0.2) * (10f / 3));
//        if (mDirection == DIRECTION_LEFT) {
//            leftX = mStatusPoint1.leftX - percent * (mMoveDistance / 2 - mElasticDistance / 2 );
//            x = mStatusPoint1.x - percent * (mMoveDistance / 2);
//            rightX = mStatusPoint1.rightX - percent * (mMoveDistance / 2 - mElasticDistance / 2 );
//            topX = x;
//            bottomX = x;
//            //偏移值稍作变化
//            offsetTop = radius * c + radius * ( c2 - c ) * percent;
//            offsetBottom = radius * c + radius * ( c2 - c ) * percent;
//        } else if (mDirection == DIRECTION_RIGHT) {
//            rightX = mStatusPoint1.rightX + percent * (mMoveDistance / 2 - mElasticDistance / 2 );
//            x = mStatusPoint1.x + percent * (mMoveDistance / 2);
//            leftX = mStatusPoint1.leftX + percent * (mMoveDistance / 2 - mElasticDistance / 2 );
//            topX = x;
//            bottomX = x;
//            //偏移值稍作变化
//            offsetTop = radius * c + radius * ( c2 - c ) * percent;
//            offsetBottom = radius * c + radius * ( c2 - c ) * percent;
//        } else if (mDirection == DIRECTION_UP) {
//            topY = mStatusPoint1.topY - percent * (mMoveDistance / 2 - mElasticDistance / 2 );
//            y = mStatusPoint1.y - percent * (mMoveDistance / 2);
//            bottomY = mStatusPoint1.bottomY - percent * (mMoveDistance / 2 - mElasticDistance / 2 );
//            leftY = y;
//            rightY = y;
//            //偏移值稍作变化
//            offsetLeft = radius * c + radius * ( c2 - c ) * percent;
//            offsetRight = radius * c + radius * ( c2 - c ) * percent;
//        } else if (mDirection == DIRECTION_DOWN) {
//            bottomY = mStatusPoint1.bottomY + percent * (mMoveDistance / 2 - mElasticDistance / 2 );
//            y = mStatusPoint1.y + percent * (mMoveDistance / 2);
//            topY = mStatusPoint1.topY + percent * (mMoveDistance / 2 - mElasticDistance / 2 );
//            leftY = y;
//            rightY = y;
//            //偏移值稍作变化
//            offsetLeft = radius * c + radius * ( c2 - c ) * percent;
//            offsetRight = radius * c + radius * ( c2 - c ) * percent;
//        }
//        mStatusPoint2.refresh(x, y, topX, topY, bottomX, bottomY,
//                leftX, leftY, rightX, rightY);
//    }
//
//    private Ball mStatusPoint3;
//
//    /**
//     * 动画状态3 （0.5~0.8）
//     */
//    private void animStatus3() {
//        float percent = (mAnimPercent - 0.5f) * (10f / 3f);
//        if (mDirection == DIRECTION_LEFT) {
//            leftX = mStatusPoint2.leftX - Math.abs(percent * (mEndPoint.rightX - mStatusPoint2
//                    .rightX));
//            x = mStatusPoint2.x - Math.abs(percent * (mEndPoint.x - mStatusPoint2.x));
//            rightX = mStatusPoint2.rightX - Math.abs(percent * (mEndPoint.x - mStatusPoint2.x));
//            topX = x;
//            bottomX = x;
//            //偏移值稍作变化
//            offsetTop = radius * c2 - radius * ( c2 - c ) * percent;
//            offsetBottom = radius * c2 - radius * ( c2 - c ) * percent;
//        } else if (mDirection == DIRECTION_RIGHT) {
//            rightX = mStatusPoint2.rightX + percent * (mEndPoint.rightX - mStatusPoint2.rightX);
//            x = mStatusPoint2.x + percent * (mEndPoint.x - mStatusPoint2.x);
//            leftX = mStatusPoint2.leftX + percent * (mEndPoint.x - mStatusPoint2.x);
//            topX = x;
//            bottomX = x;
//            //偏移值稍作变化
//            offsetTop = radius * c2 - radius * ( c2 - c ) * percent;
//            offsetBottom = radius * c2 - radius * ( c2 - c ) * percent;
//        } else if (mDirection == DIRECTION_UP) {
//            topY = mStatusPoint2.topY - Math.abs(percent * (mEndPoint.topY - mStatusPoint2
//                    .topY));
//            y = mStatusPoint2.y - Math.abs(percent * (mEndPoint.y - mStatusPoint2.y));
//            bottomY = mStatusPoint2.bottomY - Math.abs(percent * (mEndPoint.y - mStatusPoint2.y));
//            leftY = y;
//            rightY = y;
//            //偏移值稍作变化
//            offsetLeft = radius * c2 - radius * ( c2 - c ) * percent;
//            offsetRight = radius * c2 - radius * ( c2 - c ) * percent;
//        } else if (mDirection == DIRECTION_DOWN) {
//            bottomY = mStatusPoint2.bottomY + percent * (mEndPoint.bottomY - mStatusPoint2
//                    .bottomY);
//            y = mStatusPoint2.y + percent * (mEndPoint.y - mStatusPoint2.y);
//            topY = mStatusPoint2.topY + percent * (mEndPoint.y - mStatusPoint2.y);
//            leftY = y;
//            rightY = y;
//            //偏移值稍作变化
//            offsetLeft = radius * c2 - radius * ( c2 - c ) * percent;
//            offsetRight = radius * c2 - radius * ( c2 - c ) * percent;
//        }
//        mStatusPoint3.refresh(x, y, topX, topY, bottomX, bottomY,
//                leftX, leftY, rightX, rightY);
//    }
//
//    private Ball mStatusPoint4;
//
//    /**
//     * 动画状态4 （0.8~0.9）
//     */
//    private void animStatus4() {
//        float percent = (float) (mAnimPercent - 0.8) * 10;
//        if (mDirection == DIRECTION_LEFT) {
//            rightX = mStatusPoint3.rightX - percent * (Math.abs(mEndPoint.rightX - mStatusPoint3
//                    .rightX) + mElasticDistance/2);
//            //再做一次赋值，防止和终点不重合
//            leftX = mEndPoint.leftX;
//            x = mEndPoint.x;
//            bottomX = mEndPoint.bottomX;
//            topX = mEndPoint.topX;
//        } else if (mDirection == DIRECTION_RIGHT) {
//            leftX = mStatusPoint3.leftX + percent * (mEndPoint.leftX - mStatusPoint3.leftX +
//                    mElasticDistance/2);
//            //再做一次赋值，防止和终点不重合
//            rightX = mEndPoint.rightX;
//            x = mEndPoint.x;
//            bottomX = mEndPoint.bottomX;
//            topX = mEndPoint.topX;
//        } else if (mDirection == DIRECTION_UP) {
//            bottomY = mStatusPoint3.bottomY - percent * (Math.abs(mEndPoint.bottomY - mStatusPoint3
//                    .bottomY) + mElasticDistance/2);
//            //再做一次赋值，防止和终点不重合
//            topY = mEndPoint.topY;
//            y = mEndPoint.y;
//            leftY = mEndPoint.leftY;
//            rightY = mEndPoint.rightY;
//        } else if (mDirection == DIRECTION_DOWN) {
//            topY = mStatusPoint3.topY + percent * (mEndPoint.topY - mStatusPoint3
//                    .topY + mElasticDistance/2);
//            //再做一次赋值，防止和终点不重合
//            bottomY = mEndPoint.bottomY;
//            y = mEndPoint.y;
//            leftY = mEndPoint.leftY;
//            rightY = mEndPoint.rightY;
//        }
//        mStatusPoint4.refresh(x, y, topX, topY, bottomX, bottomY,
//                leftX, leftY, rightX, rightY);
//    }
//
//    private Ball mStatusPoint5;
//
//    /**
//     * 动画状态5 （0.9~1）回弹
//     */
//    private void animStatus5() {
//        float percent = (float) (mAnimPercent - 0.9) * 10;
//        if (mDirection == DIRECTION_LEFT) {
//            rightX = mStatusPoint4.rightX + percent * (mEndPoint.rightX - mStatusPoint4.rightX);
//        } else if (mDirection == DIRECTION_RIGHT) {
//            leftX = mStatusPoint4.leftX + percent * (mEndPoint.leftX - mStatusPoint4.leftX);
//        } else if (mDirection == DIRECTION_UP) {
//            bottomY = mStatusPoint4.bottomY + percent * (mEndPoint.bottomY - mStatusPoint4.bottomY);
//        } else if (mDirection == DIRECTION_DOWN) {
//            topY = mStatusPoint4.topY + percent * (mEndPoint.topY - mStatusPoint4.topY);
//        }
//        mStatusPoint5.refresh(x, y, topX, topY, bottomX, bottomY,
//                leftX, leftY, rightX, rightY);
//    }
//
//    /**
//     * 绘制弹性圆
//     * 通过绘制四段三阶贝塞尔曲线，来实现有弹性变化的圆
//     * @param topX
//     * @param topY
//     * @param offsetTop1
//     * @param offsetTop2
//     * @param bottomX
//     * @param bottomY
//     * @param offsetBottom1
//     * @param offsetBottom2
//     * @param leftX
//     * @param leftY
//     * @param offsetLeft1
//     * @param offsetLeft2
//     * @param rightX
//     * @param rightY
//     * @param offsetRight1
//     * @param offsetRight2
//     * @return
//     */
//    private Path drawElasticCircle(
//            float topX, float topY, float offsetTop1, float offsetTop2,
//            float bottomX, float bottomY, float offsetBottom1, float offsetBottom2,
//            float leftX, float leftY, float offsetLeft1, float offsetLeft2,
//            float rightX, float rightY, float offsetRight1, float offsetRight2
//    ) {
//        /**
//         * 绘制每一段三阶贝塞尔曲线需要两个控制点
//         */
//        PointF controlTop1, controlTop2, controlBottom1, controlBottom2,
//                controlLeft1, controlLeft2, controlRight1, controlRight2;
//        controlTop1 = new PointF();
//        controlTop1.x = topX - offsetTop1;
//        controlTop1.y = topY;
//        controlTop2 = new PointF();
//        controlTop2.x = topX + offsetTop2;
//        controlTop2.y = topY;
//        controlBottom1 = new PointF();
//        controlBottom1.x = bottomX - offsetBottom1;
//        controlBottom1.y = bottomY;
//        controlBottom2 = new PointF();
//        controlBottom2.x = bottomX + offsetBottom2;
//        controlBottom2.y = bottomY;
//        controlLeft1 = new PointF();
//        controlLeft1.x = leftX;
//        controlLeft1.y = leftY - offsetLeft1;
//        controlLeft2 = new PointF();
//        controlLeft2.x = leftX;
//        controlLeft2.y = leftY + offsetLeft2;
//        controlRight1 = new PointF();
//        controlRight1.x = rightX;
//        controlRight1.y = rightY - offsetRight1;
//        controlRight2 = new PointF();
//        controlRight2.x = rightX;
//        controlRight2.y = rightY + offsetRight2;
//
//        Path path = new Path();
//        /**
//         * 绘制top到left的圆弧
//         */
//        path.moveTo(topX, topY);
//        path.cubicTo(controlTop1.x, controlTop1.y, controlLeft1.x, controlLeft1.y, leftX, leftY);
//        /**
//         * 绘制left到bottom的圆弧
//         */
//        path.cubicTo(controlLeft2.x ,controlLeft2.y, controlBottom1.x, controlBottom1.y, bottomX,
//                bottomY);
//        /**
//         * 绘制bottom到right的圆弧
//         */
//        path.cubicTo(controlBottom2.x, controlBottom2.y, controlRight2.x, controlRight2.y,
//                rightX, rightY);
//        /**
//         * 绘制right到top的圆弧
//         */
//        path.cubicTo(controlRight1.x, controlRight1.y, controlTop2.x, controlTop2.y, topX, topY);
//        return path;
//    }
//
//    /**
//     * 求两点之间的距离
//     * @param x1 第一个点的横坐标
//     * @param y1 第一个点的纵坐标
//     * @param x2 第二个点的横坐标
//     * @param y2 第二个点的纵坐标
//     * @return 两点距离
//     */
//    private float getDistance(float x1, float y1, float x2, float y2) {
//        return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
//    }
//
//}