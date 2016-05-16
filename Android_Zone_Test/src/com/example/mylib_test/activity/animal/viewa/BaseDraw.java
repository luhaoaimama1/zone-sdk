package com.example.mylib_test.activity.animal.viewa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.example.mylib_test.R;

/**
 * Created by Administrator on 2016/5/13.
 */
public class BaseDraw extends View {
    public BaseDraw(Context context) {
        this(context,null);
    }

    public BaseDraw(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public BaseDraw(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawOther(canvas);
//        drawMe(canvas);

    }

    private void drawMe(Canvas canvas) {
        int size=50;
        String dot="dot:";
        String line="line:";
        String lines="lines:";
        Paint p = new Paint();
        p.setColor(Color.BLACK);// 设置红色
        p.setStyle(Paint.Style.FILL);

        p.setTextSize(size);
        canvas.drawText(dot,0,size,p);
        canvas.drawText(line,0,size*2,p);
        canvas.drawText(lines,0,size*3,p);

        p.setStrokeWidth(10);
        //两个字母是一个汉子  记 size
        canvas.drawPoint(size*(float)Math.ceil(dot.toCharArray().length/2),size,p);
        //starX1,startY2,enxX1,endY1  两个字母是一个汉子  记 size
        canvas.drawLine(size*(float)Math.ceil(line.toCharArray().length/2),size*2,size*(float)Math.ceil(line.toCharArray().length/2)+100,size*2,p);
        //starX1,startY2,enxX1,endY1......starXn,startYn,enxXn,endYn
        float[] pts=new float[]{
                size*(float)Math.ceil(lines.toCharArray().length/2),size*3,size*(float)Math.ceil(lines.toCharArray().length/2)+100,size*3,
                size*(float)Math.ceil(lines.toCharArray().length/2)*2,size*3,size*(float)Math.ceil(lines.toCharArray().length/2)*2+100,size*3,
        };
        canvas.drawLines(pts,p);


        int offset=100;
        canvas.drawRect(offset,size*4,size+offset,size*5,p);
        int radius=50;
        canvas.drawCircle(size+offset+radius,size*4,radius,p);

    }

    private void drawOther(Canvas canvas) {
        //concat 必须放在前面
        canvas.translate(-100,0);//好使 会和下面的操作合并 而不是被代替！！！
        Matrix matrix=new Matrix();
        matrix.postScale(3,3,0,0);
        matrix.postTranslate(100,0);
        canvas.concat(matrix);//缩放必须放在前面
         /*
         * 方法 说明 drawRect 绘制矩形 drawCircle 绘制圆形 drawOval 绘制椭圆 drawPath 绘制任意多边形
         * drawLine 绘制直线 drawPoin 绘制点
         */
        // 创建画笔
        Paint p = new Paint();
        p.setColor(Color.RED);// 设置红色

        canvas.drawText("画圆：", 10, 20, p);// 画文本
        canvas.drawCircle(60, 20, 10, p);// 小圆
        p.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
        canvas.drawCircle(120, 20, 20, p);// 大圆

        p.setColor(Color.BLACK);// 设置绿色
        canvas.drawLine(60, 40, 100, 40, p);// 画线
        canvas.drawLine(110, 40, 190, 80, p);// 斜线
        //画笑脸弧线
        p.setStyle(Paint.Style.STROKE);//设置空心
        RectF oval1=new RectF(150,20,180,40);
        canvas.drawArc(oval1, 180, 180, false, p);//小弧形
        oval1.set(190, 20, 220, 40);
        canvas.drawArc(oval1, 180, 180, false, p);//小弧形
        oval1.set(160, 30, 210, 60);
        canvas.drawArc(oval1, 0, 180, false, p);//小弧形

        canvas.drawText("画矩形：", 10, 80, p);
        p.setColor(Color.GRAY);// 设置灰色
        p.setStyle(Paint.Style.FILL);//设置填满
        canvas.drawRect(60, 60, 80, 80, p);// 正方形
        canvas.drawRect(60, 90, 160, 100, p);// 长方形

        canvas.drawText("画扇形和椭圆:", 10, 120, p);
        /* 设置渐变色 这个正方形的颜色是改变的 */
        Shader mShader = new LinearGradient(0, 0, 100, 100,
                new int[] { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
                        Color.LTGRAY }, null, Shader.TileMode.REPEAT); // 一个材质,打造出一个线性梯度沿著一条线。
        p.setShader(mShader);
        // p.setColor(Color.BLUE);
        RectF oval2 = new RectF(60, 100, 200, 240);// 设置个新的长方形，扫描测量
        canvas.drawArc(oval2, 200, 130, true, p);
        // 画弧，第一个参数是RectF：该类是第二个参数是角度的开始，第三个参数是多少度，第四个参数是真的时候画扇形，是假的时候画弧线
        //画椭圆，把oval改一下
        oval2.set(210,100,250,130);
        canvas.drawOval(oval2, p);

        canvas.drawText("画三角形：", 10, 200, p);
        // 绘制这个三角形,你可以绘制任意多边形
        Path path = new Path();
        path.moveTo(80, 200);// 此点为多边形的起点
        path.lineTo(120, 250);
        path.lineTo(80, 250);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, p);

        // 你可以绘制很多任意多边形，比如下面画六连形
        p.reset();//重置
        p.setColor(Color.LTGRAY);
        p.setStyle(Paint.Style.STROKE);//设置空心
        Path path1=new Path();
        path1.moveTo(180, 200);
        path1.lineTo(200, 200);
        path1.lineTo(210, 210);
        path1.lineTo(200, 220);
        path1.lineTo(180, 220);
        path1.lineTo(170, 210);
        path1.close();//封闭
        canvas.drawPath(path1, p);
        /*
         * Path类封装复合(多轮廓几何图形的路径
         * 由直线段*、二次曲线,和三次方曲线，也可画以油画。drawPath(路径、油漆),要么已填充的或抚摸
         * (基于油漆的风格),或者可以用于剪断或画画的文本在路径。
         */

        //画圆角矩形
        p.setStyle(Paint.Style.FILL);//充满
        p.setColor(Color.LTGRAY);
        p.setAntiAlias(true);// 设置画笔的锯齿效果
        canvas.drawText("画圆角矩形:", 10, 260, p);
        RectF oval3 = new RectF(80, 260, 200, 300);// 设置个新的长方形
        canvas.drawRoundRect(oval3, 20, 15, p);//第二个参数是x半径，第三个参数是y半径

        //画贝塞尔曲线
        canvas.drawText("画贝塞尔曲线:", 10, 310, p);
        p.reset();
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.BLACK);
        Path path2=new Path();
        path2.moveTo(100, 320);//设置Path的起点
        path2.quadTo(150, 310, 170, 400); //设置贝塞尔曲线的控制点坐标和终点坐标
        canvas.drawPath(path2, p);//画出贝塞尔曲线

        //画点
        p.setStyle(Paint.Style.FILL);
        canvas.drawText("画点：", 10, 390, p);
        canvas.drawPoint(60, 390, p);//画一个点
        canvas.drawPoints(new float[]{60,400,65,400,70,400}, p);//画多个点

        //画图片，就是贴图
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        canvas.drawBitmap(bitmap, 200,360, p);

    }
}
