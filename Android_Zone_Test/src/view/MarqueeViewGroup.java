//package view;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.util.AttributeSet;
//import android.view.Choreographer;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//
//import androidx.annotation.Nullable;
//
//import com.zone.lib.utils.reflect.Reflect;
//
//import java.lang.ref.WeakReference;
//
//public class MarqueeTextViewGroup extends FrameLayout {
//    Marquee mMarquee;
//
//    public MarqueeTextViewGroup(Context context) {
//        this(context, null, -1);
//    }
//
//    public MarqueeTextViewGroup(Context context, @Nullable AttributeSet attrs) {
//        this(context, attrs, -1);
//    }
//
//    public MarqueeTextViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    @Override
//    protected void onFinishInflate() {
//        super.onFinishInflate();
//        View childAt = getChildAt(0);
//        if (childAt != null) {
//            mMarquee = new Marquee(childAt, this);
//        }
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//        if (changed && getHeight() > 0 && getWidth() > 0) {
//            mMarquee.start(-1);
//        }
//    }
//
//    @Override
//    protected void onDetachedFromWindow() {
//        mMarquee.stop();
//        super.onDetachedFromWindow();
//    }
//
//    @Override
//    public boolean isFocused() {
//        return false;
//    }
//
//    @Override
//    protected void dispatchDraw(Canvas canvas) {
//        super.dispatchDraw(canvas);
//    }
//
//    int count = 1;
//
//    @Override
//    public void draw(Canvas canvas) {
//        super.draw(canvas);
//    }
//
//    private static final class Marquee {
//        // TODO: Add an option to configure this
//        private static final float MARQUEE_DELTA_MAX = 0.07f;
//        private static final int MARQUEE_DELAY = 1200;
//        private static final int MARQUEE_DP_PER_SECOND = 30;
//
//        private static final byte MARQUEE_STOPPED = 0x0;
//        private static final byte MARQUEE_STARTING = 0x1;
//        private static final byte MARQUEE_RUNNING = 0x2;
//
//        private final WeakReference<View> mView;
//        private final WeakReference<View> mViewParent;
//        private final Choreographer mChoreographer;
//
//        private byte mStatus = MARQUEE_STOPPED;
//        private final float mPixelsPerMs;
//        private float mMaxScroll;
//        private float mMaxFadeScroll;
//        private float mGhostStart;
//        private float mGhostOffset;
//        private float mFadeStop;
//        private int mRepeatLimit;
//
//        private float mScroll;
//        private long mLastAnimationMs;
//
//        Marquee(View v, ViewGroup viewGroup) {
//            final float density = v.getContext().getResources().getDisplayMetrics().density;
//            mPixelsPerMs = MARQUEE_DP_PER_SECOND * density / 1000f;
//            mView = new WeakReference<View>(v);
//            mViewParent = new WeakReference<View>(viewGroup);
//            mChoreographer = Choreographer.getInstance();
//        }
//
//        private Choreographer.FrameCallback mTickCallback = new Choreographer.FrameCallback() {
//            @Override
//            public void doFrame(long frameTimeNanos) {
//                tick();
//            }
//        };
//
//        private Choreographer.FrameCallback mStartCallback = new Choreographer.FrameCallback() {
//            @Override
//            public void doFrame(long frameTimeNanos) {
//                mStatus = MARQUEE_RUNNING;
//                mLastAnimationMs = getFrameTime();
//                tick();
//            }
//        };
//
//        public long getFrameTime() {
//            return Reflect.on(mChoreographer).call("getFrameTime").get();
////            getFrameTime
////            return System.nanoTime() / NANOS_PER_MS;
//        }
//
//        public static final long NANOS_PER_MS = 1000000;
//
//        private Choreographer.FrameCallback mRestartCallback = new Choreographer.FrameCallback() {
//            @Override
//            public void doFrame(long frameTimeNanos) {
//                if (mStatus == MARQUEE_RUNNING) {
//                    if (mRepeatLimit >= 0) {
//                        mRepeatLimit--;
//                    }
//                    start(mRepeatLimit);
//                }
//            }
//        };
//
//        void tick() {
//            if (mStatus != MARQUEE_RUNNING) {
//                return;
//            }
//
//            mChoreographer.removeFrameCallback(mTickCallback);
//
//            final View textView = mView.get();
//            if (textView != null) {
//                long currentMs = getFrameTime();
//                long deltaMs = currentMs - mLastAnimationMs;
//                mLastAnimationMs = currentMs;
//                float deltaPx = deltaMs * mPixelsPerMs;
//                mScroll += deltaPx;
//                if (mScroll > mMaxScroll) {
//                    mScroll = mMaxScroll;
//                    mChoreographer.postFrameCallbackDelayed(mRestartCallback, MARQUEE_DELAY);
//                } else {
//                    mChoreographer.postFrameCallback(mTickCallback);
//                }
//                textView.invalidate();
//            }
//        }
//
//        void stop() {
//            mStatus = MARQUEE_STOPPED;
//            mChoreographer.removeFrameCallback(mStartCallback);
//            mChoreographer.removeFrameCallback(mRestartCallback);
//            mChoreographer.removeFrameCallback(mTickCallback);
//            resetScroll();
//        }
//
//        private void resetScroll() {
//            mScroll = 0.0f;
//            final View viewGroup = mViewParent.get();
//            if (viewGroup != null) {
//                viewGroup.invalidate();
//            }
//        }
//
//        void start(int repeatLimit) {
//            if (repeatLimit == 0) {
//                stop();
//                return;
//            }
//            mRepeatLimit = repeatLimit;
//            final View view = mView.get();
//            if (view != null) {
//                mStatus = MARQUEE_STARTING;
//                mScroll = 0.0f;
//                final int viewWidth = view.getWidth() - view.getPaddingLeft() - view.getPaddingRight();
//                final float lineWidth = view.getWidth();
//                final float gap = viewWidth / 3.0f;
//                mGhostStart = lineWidth - viewWidth + gap;
//                mMaxScroll = mGhostStart + viewWidth;
//                mGhostOffset = lineWidth + gap;
//                mFadeStop = lineWidth + viewWidth / 6.0f;
//                mMaxFadeScroll = mGhostStart + lineWidth + lineWidth;
//
//                view.invalidate();
//                mChoreographer.postFrameCallback(mStartCallback);
//            }
//        }
//
//        float getGhostOffset() {
//            return mGhostOffset;
//        }
//
//        float getScroll() {
//            return mScroll;
//        }
//
//        float getMaxFadeScroll() {
//            return mMaxFadeScroll;
//        }
//
//        boolean shouldDrawLeftFade() {
//            return mScroll <= mFadeStop;
//        }
//
//        boolean shouldDrawGhost() {
//            return true;
//        }
//
//        boolean isRunning() {
//            return mStatus == MARQUEE_RUNNING;
//        }
//
//        boolean isStopped() {
//            return mStatus == MARQUEE_STOPPED;
//        }
//
//        public WeakReference<View> getView() {
//            return mView;
//        }
//    }
//
//}
