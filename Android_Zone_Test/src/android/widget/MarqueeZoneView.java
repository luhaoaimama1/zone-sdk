package android.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.Gravity;

import androidx.annotation.Nullable;

import com.zone.lib.utils.reflect.Reflect;

import java.lang.ref.WeakReference;

public class MarqueeZoneView extends androidx.appcompat.widget.AppCompatTextView {
    Marquee mMarquee = new Marquee(this);

    public MarqueeZoneView(Context context) {
        this(context, null, -1);
    }

    public MarqueeZoneView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MarqueeZoneView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setMarqueeRepeatLimit(-1);
        setSingleLine();
//        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed&&getLayout()!=null) {
            mMarquee.start(-1);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mMarquee.stop();
        super.onDetachedFromWindow();
    }

    @Override
    public boolean isFocused() {
        return false;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        int voffsetText = 0;
        int voffsetCursor = 0;

        // translate in by our padding
        /* shortcircuit calling getVerticaOffset() */
        if ((getGravity() & Gravity.VERTICAL_GRAVITY_MASK) != Gravity.TOP) {
            voffsetText = Reflect.on(this).call("getVerticalOffset", false).get();
            voffsetCursor = Reflect.on(this).call("getVerticalOffset", true).get();
//            voffsetText =getVerticalOffset(false);
//            voffsetCursor = getVerticalOffset(true);
        }
        final int cursorOffsetVertical = voffsetCursor - voffsetText;

        Layout layout = getLayout();
        if (mMarquee != null && mMarquee.shouldDrawGhost()) {
            if ( mMarquee.isRunning()) {
                final float dx = -mMarquee.getScroll();
                canvas.translate(layout.getParagraphDirection(0) * dx, 0.0f);
            }


            final float dx = mMarquee.getGhostOffset();
            canvas.translate(layout.getParagraphDirection(0) * dx, 0.0f);
//            layout.draw(canvas, highlight, mHighlightPaint, cursorOffsetVertical);
            layout.draw(canvas, Reflect.on(this).call("getUpdatedHighlightPath").get(),
                    Reflect.on(this).get("mHighlightPaint"), cursorOffsetVertical);
        }

        canvas.restore();
    }

    int count=1;
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    private static final class Marquee {
        // TODO: Add an option to configure this
        private static final float MARQUEE_DELTA_MAX = 0.07f;
        private static final int MARQUEE_DELAY = 1200;
        private static final int MARQUEE_DP_PER_SECOND = 30;

        private static final byte MARQUEE_STOPPED = 0x0;
        private static final byte MARQUEE_STARTING = 0x1;
        private static final byte MARQUEE_RUNNING = 0x2;

        private final WeakReference<TextView> mView;
        private final Choreographer mChoreographer;

        private byte mStatus = MARQUEE_STOPPED;
        private final float mPixelsPerMs;
        private float mMaxScroll;
        private float mMaxFadeScroll;
        private float mGhostStart;
        private float mGhostOffset;
        private float mFadeStop;
        private int mRepeatLimit;

        private float mScroll;
        private long mLastAnimationMs;

        Marquee(TextView v) {
            final float density = v.getContext().getResources().getDisplayMetrics().density;
            mPixelsPerMs = MARQUEE_DP_PER_SECOND * density / 1000f;
            mView = new WeakReference<TextView>(v);
            mChoreographer = Choreographer.getInstance();
        }

        private Choreographer.FrameCallback mTickCallback = new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                tick();
            }
        };

        private Choreographer.FrameCallback mStartCallback = new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                mStatus = MARQUEE_RUNNING;
                mLastAnimationMs = getFrameTime();
                tick();
            }
        };

        public long getFrameTime() {
           return  Reflect.on(mChoreographer).call("getFrameTime").get();
//            getFrameTime
//            return System.nanoTime() / NANOS_PER_MS;
        }

        public static final long NANOS_PER_MS = 1000000;

        private Choreographer.FrameCallback mRestartCallback = new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                if (mStatus == MARQUEE_RUNNING) {
                    if (mRepeatLimit >= 0) {
                        mRepeatLimit--;
                    }
                    start(mRepeatLimit);
                }
            }
        };

        void tick() {
            if (mStatus != MARQUEE_RUNNING) {
                return;
            }

            mChoreographer.removeFrameCallback(mTickCallback);

            final TextView textView = mView.get();
            if (textView != null) {
                long currentMs = getFrameTime();
                long deltaMs = currentMs - mLastAnimationMs;
                mLastAnimationMs = currentMs;
                float deltaPx = deltaMs * mPixelsPerMs;
                mScroll += deltaPx;
                if (mScroll > mMaxScroll) {
                    mScroll = mMaxScroll;
                    mChoreographer.postFrameCallbackDelayed(mRestartCallback, MARQUEE_DELAY);
                } else {
                    mChoreographer.postFrameCallback(mTickCallback);
                }
                textView.invalidate();
            }
        }

        void stop() {
            mStatus = MARQUEE_STOPPED;
            mChoreographer.removeFrameCallback(mStartCallback);
            mChoreographer.removeFrameCallback(mRestartCallback);
            mChoreographer.removeFrameCallback(mTickCallback);
            resetScroll();
        }

        private void resetScroll() {
            mScroll = 0.0f;
            final TextView textView = mView.get();
            if (textView != null) textView.invalidate();
        }

        void start(int repeatLimit) {
            if (repeatLimit == 0) {
                stop();
                return;
            }
            mRepeatLimit = repeatLimit;
            final TextView textView = mView.get();
            if (textView != null && textView.getLayout() != null) {
                mStatus = MARQUEE_STARTING;
                mScroll = 0.0f;
                final int textWidth = textView.getWidth() - textView.getCompoundPaddingLeft()
                        - textView.getCompoundPaddingRight();
                final float lineWidth = textView.getLayout().getLineWidth(0);
                final float gap = textWidth / 3.0f;
                mGhostStart = lineWidth - textWidth + gap;
                mMaxScroll = mGhostStart + textWidth;
                mGhostOffset = lineWidth + gap;
                mFadeStop = lineWidth + textWidth / 6.0f;
                mMaxFadeScroll = mGhostStart + lineWidth + lineWidth;

                textView.invalidate();
                mChoreographer.postFrameCallback(mStartCallback);
            }
        }

        float getGhostOffset() {
            return mGhostOffset;
        }

        float getScroll() {
            return mScroll;
        }

        float getMaxFadeScroll() {
            return mMaxFadeScroll;
        }

        boolean shouldDrawLeftFade() {
            return mScroll <= mFadeStop;
        }

        boolean shouldDrawGhost() {
            return true;
        }

        boolean isRunning() {
            return mStatus == MARQUEE_RUNNING;
        }

        boolean isStopped() {
            return mStatus == MARQUEE_STOPPED;
        }
    }

}
