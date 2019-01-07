package com.zone.lib;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;

import androidx.annotation.NonNull;

public class LoadingAnimView extends View {
    public LoadingAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LoadingAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingAnimView(Context context) {
        super(context);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingAnimView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private Paint paint;
    private NodeList nodeList;

    private void init() {
        paint = new Paint();
        paint.setStyle(Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(0xff0e9afe);
    }

    private Animation animation;
    private int circleCount = 0;

    private void start() {
        if (isStart) {
            return;
        }

        circleCount = 0;
        int w = getWidth();
        int h = getHeight();
        if (null == nodeList) {
            if (w > 0 && h > 0) {
                nodeList = new NodeList(w, h, num);
                nodeList.setPaint(paint);
            } else {
                nodeList = new NodeList(num);
                nodeList.setPaint(paint);
                return;
            }
        } else if (!nodeList.hasSize()) {
            if (w > 0 && h > 0) {
                nodeList.setSize(w, h);
            } else {
                return;
            }
        }

        if (null != animation) {
            animation.reset();
            startAnimation(animation);
            isStart = true;
            return;
        }

        animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                nodeList.visit(interpolatedTime);
                invalidate();
            }
        };
        
        animation.setDuration(2400);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                circleCount++;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }
        });
        startAnimation(animation);
        isStart = true;
    }

    public int getCircleCount() {
        return circleCount;
    }

    private boolean isStart = false;

    public void stop() {
        if (null != animation) {
            animation.cancel();
        }
        isStart = false;
    }

    private int num = 8;

    public void setNum(int num) {
        int w = getWidth();
        int h = getHeight();
        this.num = num;
        if (null == nodeList && w > 0 && h > 0) {
            nodeList = new NodeList(w, h, num);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (null != nodeList && nodeList.hasSize()) {
            nodeList.draw(canvas);
        } else {
            start();
        }
        super.draw(canvas);
    }

    class NodeList {
        private Paint paint;
        private int hostW, hostH;
        private final Node[] nodes;
        private final int deltaAlpha;
        private final float deltaFactor;

        public NodeList(int w, int h, int num) {
            this(num);
            setSize(w, h);
        }

        public void setPaint(Paint paint) {
            this.paint = paint;
        }

        public void setSize(int w, int h) {
            hostW = w;
            hostH = h;
            // caculate cx cy radius
            float cx = hostW / 2;
            float cy = hostH / 2;
            float radius = hostW / 3;
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = new Node(cx, cy, radius);
            }
        }

        public boolean hasSize() {
            return hostW > 0 && hostW > 0;
        }

        public NodeList(int num) {
            super();
            nodes = new Node[num];
            deltaAlpha = 220 / num;
            deltaFactor = 0.5f / num;
        }

        /**
         * @param factor 0.0f ~ 1.0f表示一周
         */
        public void visit(float factor) {
            float tmpFactor = factor;
            for (Node node:nodes) {
                if (tmpFactor > 0) {
                    node.visit(tmpFactor * 2);
                } else {
                    node.isShow = false;
                    node.factor = 0.0f;
                }
                tmpFactor = tmpFactor - deltaFactor;
            }
        }

        public void draw(Canvas canvas) {
            if (null != nodes) {
                int alpha;
                for (int i = 1; i < nodes.length; i++) {
                    alpha = 255 - i * deltaAlpha;
                    paint.setAlpha(alpha);
                    if (nodes[i].isShow) {
                        nodes[i].draw(canvas, paint);
                    } else if (nodes[i].factor == 0.0f) {
                        break;
                    }
                }
                paint.setAlpha(255);
                nodes[0].draw(canvas, paint);
            }
        }
    }

    class Node {
        final float hostCX;
        final float hostCY;
        final float hostRadius;
        float cx;
        float cy;
        final float radius;
        float factor;
        boolean isShow;

        public Node(float hostCX, float hostCY, float hostRadius) {
            this.hostCX = hostCX;
            this.hostCY = hostCY;
            this.hostRadius = hostRadius;
            this.radius = hostRadius / 5;
        }

        public void visit(float ft) {
            factor = ft;
            if (factor >= 1.0f) {
                this.factor = 1.0f;
                isShow = false;
                return;
            } else {
                isShow = true;
            }
            // caculate cx, cy
            double angle = -1 * (0.5f + 2 * factor) * Math.PI;
            cx = (float) Math.cos(angle) * hostRadius + hostCX;
            cy = (float) Math.sin(angle) * hostRadius + hostCY;
        }

        public void draw(Canvas canvas, Paint paint) {
            canvas.drawCircle(cx, cy, radius, paint);
        }
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == GONE || visibility == INVISIBLE) {
            stop();
        } else {
            start();
        }
    }
}
