package com.example.mylib_test.activity.animal;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Layout;
import android.text.ParcelableSpan;
import android.text.Selection;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.text.style.UpdateAppearance;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mylib_test.R;
import com.zone.lib.utils.view.DrawUtils;
import com.zone.okhttp.utils.StringUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import view.ExpandTextView;

//https://blog.csdn.net/wangyingtong/article/details/51693668
public class TextViewLinkActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_textview_span);

        anySpan();
        roateSpan();
        ((ExpandTextView) findViewById(R.id.etv)).setMaxLine(3);
        emoji();

        changeColor();
        deleteEdit();
    }

    private void deleteEdit() {
        EditText textView = (EditText) findViewById(R.id.deleteEdit);
//        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
//        String str = textView.getText().toString();
//        Pattern p = Pattern.compile(escapeExprSpecialWord("[")
//                + "([.&[^" + escapeExprSpecialWord("[") + "]]+?)"
//                + escapeExprSpecialWord("]"));
//        Matcher matcher = p.matcher(str);
//        while (matcher.find()) {
//            System.out.println(str.substring(matcher.start(), matcher.end()));
//            ssb.setSpan(new ForegroundColorSpan(Color.RED), matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }
//        textView.setText(ssb);

        //设置图片 mutate
        textView.addTextChangedListener(new TextWatcher() {

            public CharSequence s;

            boolean skip=false;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                Log.d("TextChanged", "beforeTextChanged 被执行----> s=" + s + "----start=" + start
                        + "----after=" + after + "----count" + count);
                this.s=s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //在这里  把自己键盘添加或者删除的操作  更换掉！
                Log.d("TextChanged", "onTextChanged 被执行---->s=" + s + "----start=" + start
                        + "----before=" + before + "----count" + count);
                if(!skip){//删除前提 这是一整段
                    skip=true;//防止循环  因为setText又会走 beforeTextChanged 又会走onTextChanged
                    textView.setText(this.s);
                    Selection.setSelection(textView.getText(),textView.getText().length()-3,textView.getText().length()-1);
                }else{
                    skip=false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void changeColor() {
        TextView textView = (TextView) findViewById(R.id.changeColor);
        textView.getText();
        SpannableStringBuilder ssb = new SpannableStringBuilder(textView.getText());
        ssb.setSpan(new ForegroundColorSpanAni(textView), 0, textView.getText().length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(ssb);
    }

    public class ForegroundColorSpanAni extends CharacterStyle
            implements UpdateAppearance {

        private final View view;

        int color = Color.RED;

        public ForegroundColorSpanAni(View view) {
            this.view = view;

            ValueAnimator animator = ValueAnimator.ofInt(Color.RED, Color.BLUE);
            animator.setEvaluator(new ArgbEvaluator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    color = (int) animation.getAnimatedValue();
                    view.invalidate();
                }
            });
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.setRepeatCount(-1);
            animator.start();
        }

        @Override
        public void updateDrawState(TextPaint tp) {// view的draw()方法会走他
            tp.setColor(color);
        }
    }

    private Handler handler = new Handler();

    private void emoji() {
        TextView textView = (TextView) findViewById(R.id.emoj);
        String str = textView.getText().toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);

        Pattern p = Pattern.compile(escapeExprSpecialWord("[")
                + "([.&[^" + escapeExprSpecialWord("[") + "]]+?)"
                + escapeExprSpecialWord("]"));
        Matcher matcher = p.matcher(str);
        //设置图片 mutate
        Drawable drawable2 = getResources().getDrawable(R.drawable.emoji_people).mutate();
        drawable2.setBounds(0, 0, textView.getLineHeight(), textView.getLineHeight());

        if (drawable2 instanceof AnimationDrawable) {
            //https://stackoverflow.com/questions/9418530/dynamically-load-an-animationdrawable-in-a-textview
            drawable2.setCallback(new Drawable.Callback() {

                @Override
                public void invalidateDrawable(@NonNull Drawable who) {
                    Log.d("drawable2", "invalidateDrawable");
                    textView.invalidate();
                }

                @Override
                public void scheduleDrawable(Drawable who, Runnable what, long when) {
                    handler.postAtTime(what, when);
                    Log.d("drawable2", "scheduleDrawable");
//                    handler.postDelayed(what, when - SystemClock.uptimeMillis());
                }

                @Override
                public void unscheduleDrawable(Drawable who, Runnable what) {
                    Log.d("drawable2", "unscheduleDrawable");
                    handler.removeCallbacks(what);
                }

            });
            ((AnimationDrawable) drawable2).start();
        }


        while (matcher.find()) {
            System.out.println(str.substring(matcher.start(), matcher.end()));
            ssb.setSpan(new ImageSpan(drawable2), matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setText(ssb);


    }

    /**
     * 转义正则特殊字符 （$()*+.[]?\^{},|）
     *
     * @param keyword
     * @return
     */
    public static String escapeExprSpecialWord(String keyword) {
        if (!TextUtils.isEmpty(keyword)) {
            String[] fbsArr = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }

    private void roateSpan() {
        TextView tv_rotate_img_span = (TextView) findViewById(R.id.tv_rotate_img_span);
        ImageView img_rotate = (ImageView) findViewById(R.id.img_rotate);
        Drawable drawable = getResources().getDrawable(R.drawable.icon);
//        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.setBounds(0, 0, 200, 200);
        img_rotate.setImageDrawable(drawable);

        ViewGroup.LayoutParams lp = img_rotate.getLayoutParams();
        lp.width = 200;
        lp.height = 200;
        img_rotate.setLayoutParams(lp);

        //这个可以在oncreate的时候测量出来
        StaticLayout sl = new StaticLayout("aa", tv_rotate_img_span.getPaint(),
                100, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        int lineHeight = sl.getLineBottom(0) - sl.getLineTop(0);// 比 tv_rotate_img_span.getLineHeight() 这个好 因为每行高刚度可能不一致

        int affectLine = 0;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (i * lineHeight > 200 + 20) {
                affectLine = i - 1;
                break;
            }
        }

        SpannableString msp = new SpannableString("可是现在我已不是在天上翱翔的小鸟，在地上奔跑的动物了，因为我长大了，已经五年级了，大家都在努力地学习，谁都不想输给别人，每个人都在奋发图强，“学习如逆水行舟，不进则退”。这句话在我耳边回荡，难道我们就不应该有一个孩子应该有的童真吗？难道我已经失去了吗？难道现在我们所有的孩子都应该抓住学习这根攀山绳吗？为什么我们不？");
        tv_rotate_img_span.setText(msp);
        tv_rotate_img_span.setGravity(Gravity.LEFT);

        msp.setSpan(new ImageRotate(affectLine), 0, msp.length() - 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv_rotate_img_span.setText(msp);
    }


    public class ImageRotate implements LeadingMarginSpan, LeadingMarginSpan.LeadingMarginSpan2 {
        int lines;

        public ImageRotate(int lines) {
            this.lines = lines;
        }

        @Override
        public int getLeadingMargin(boolean first) {
            if (first) {
                return 200 + 20;
            } else {
                return 0;
            }
        }

        @Override
        public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline, int bottom, CharSequence text, int start, int end, boolean first, Layout layout) {

        }

        @Override
        public int getLeadingMarginLineCount() {
            return lines;
        }
    }


    private void anySpan() {
        TextView mTextView = (TextView) findViewById(R.id.tv_any_span);
        SpannableString msp = null;
        //创建一个 SpannableString对象
        msp = new SpannableString("字体测试字体大小一半两倍前景色背景色正常粗体斜体粗斜体下划线删除线x1x2电话邮件网站短信彩信地图X轴综合—bot我擦g");

        //设置字体(default,default-bold,monospace,serif,sans-serif)
        msp.setSpan(new TypefaceSpan("monospace"), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new TypefaceSpan("serif"), 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置字体大小（绝对值,单位：像素）
        msp.setSpan(new AbsoluteSizeSpan(20), 4, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new AbsoluteSizeSpan(20, true), 6, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //第二个参数boolean dip，如果为true，表示前面的字体大小单位为dip，否则为像素，同上。

        //设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍
        msp.setSpan(new RelativeSizeSpan(0.5f), 8, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半
        msp.setSpan(new RelativeSizeSpan(2.0f), 10, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //2.0f表示默认字体大小的两倍

        //设置字体前景色
//        msp.setSpan(new ForegroundColorSpan(Color.MAGENTA), 12, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置前景色为洋红色
        msp.setSpan(new BgFgCustomSpan(), 12, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置字体背景色
//        msp.setSpan(new BackgroundColorSpan(Color.CYAN), 15, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置背景色为青色

        //设置字体样式正常，粗体，斜体，粗斜体
        msp.setSpan(new StyleSpan(android.graphics.Typeface.NORMAL), 18, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //正常
        msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 20, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //粗体
        msp.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), 22, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //斜体
        msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 24, 27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //粗斜体

        //设置下划线
        msp.setSpan(new UnderlineSpan(), 27, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置删除线
        msp.setSpan(new StrikethroughSpan(), 30, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置上下标
        msp.setSpan(new SubscriptSpan(), 34, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //下标
        msp.setSpan(new SuperscriptSpan(), 36, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);   //上标

        //超级链接（需要添加setMovementMethod方法附加响应）
        msp.setSpan(new URLSpan("tel:4155551212"), 37, 39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //电话
        msp.setSpan(new URLSpan("mailto:webmaster@google.com"), 39, 41, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //邮件
        msp.setSpan(new URLSpan("http://www.baidu.com"), 41, 43, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //网络
        msp.setSpan(new URLSpan("sms:4155551212"), 43, 45, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //短信   使用sms:或者smsto:
        msp.setSpan(new URLSpan("mms:4155551212"), 45, 47, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //彩信   使用mms:或者mmsto:
        msp.setSpan(new URLSpan("geo:38.899533,-77.036476"), 47, 49, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //地图

        //设置字体大小（相对值,单位：像素） 参数表示为默认字体宽度的多少倍
        msp.setSpan(new ScaleXSpan(2.0f), 49, 51, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //2.0f表示默认字体宽度的两倍，即X轴方向放大为默认字体的两倍，而高度不变

        //设置字体（依次包括字体名称，字体大小，字体样式，字体颜色，链接颜色）
//        msp.setSpan(new TextAppearanceSpan("monospace",android.graphics.Typeface.BOLD_ITALIC, 30, csl, csllink), 51, 53, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置项目符号
        msp.setSpan(new BulletSpan(BulletSpan.STANDARD_GAP_WIDTH, Color.GREEN), 0, msp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //第一个参数表示项目符号占用的宽度，第二个参数为项目符号的颜色

        //设置图片
        Drawable drawable = getResources().getDrawable(R.drawable.icon);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.setBounds(0, 0, 120, 120);
//        msp.setSpan(new ImageSpan(drawable), 53, 57, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new ImageCustomSpan(drawable), 53, 57, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new ImageSpan(drawable), 57, 58, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        msp.setSpan(new BgFgCustomSpan(), 57, 59, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

//        除此之外，还有MaskFilterSpan可以实现模糊和浮雕效果，RasterizerSpan可以实现光栅效果，
        mTextView.setText(msp);
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * 我的推测
     * 每个span 都
     */

    public class BgFgCustomSpan extends CharacterStyle {

        @Override
        public void updateDrawState(TextPaint tp) {
            tp.setColor(Color.RED);
            tp.bgColor = Color.CYAN;
            //模糊啥的 完全可以用tp set去设置；
        }

    }

    /**
     * 我的推测
     * 每个span 都
     */

    public class ImageCustomSpan extends ImageSpan {

        public ImageCustomSpan(Drawable d) {
            super(d);
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end,
                         float x, int top, int y, int bottom, Paint paint) {
            //todo top: bottom  表示start与end中的字符  的 行 在textview中的位置；
//            System.out.println("ImageCustomSpan?:"+text);
//            super.draw(canvas, text, start, end, x, top, y, bottom, paint);
            Drawable b = getDrawable();


            int transY = bottom - b.getBounds().bottom;//这样就是地对齐
            if (mVerticalAlignment == ALIGN_BASELINE) {
                transY -= paint.getFontMetricsInt().descent;
            }

            Paint paint2 = DrawUtils.getStrokePaint(Paint.Style.STROKE, 4);
            paint2.setColor(Color.RED);

            canvas.save();
            canvas.translate(x, top);
            canvas.drawLine(0, 0, 100, 0, paint2);
            canvas.restore();


            canvas.save();
            paint2.setColor(Color.GREEN);
            canvas.translate(x, y);
            canvas.drawLine(0, 0, 100, 0, paint2);
            canvas.restore();

            canvas.save();
            paint2.setColor(Color.BLUE);
            canvas.translate(x, bottom);
            canvas.drawLine(0, 0, 100, 0, paint2);
            canvas.restore();
        }

        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end,
                           Paint.FontMetricsInt fm) {
            return super.getSize(paint, text, start, end, fm);
        }
    }
}