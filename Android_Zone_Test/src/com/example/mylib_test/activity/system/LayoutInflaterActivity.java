package com.example.mylib_test.activity.system;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mylib_test.R;

/**
 * Created by fuzhipeng on 2016/10/26.
 */

public class LayoutInflaterActivity extends AppCompatActivity {
    public static final String Tag = "LayoutInflater";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

                //替换 view;
                if (name.equals("TextView")) {
                    Button button = new Button(context, attrs);
                    return button;
                }

                //兼容 问题 tint等 系统已经替换好的view
                AppCompatDelegate delegate = getDelegate();
                View view = delegate.createView(parent, name, context, attrs);

                //统一更改字体；
                if (view != null && (view instanceof TextView)) {
                    ((TextView) view).setTypeface(null);
                }

                return null;
            }
        });
        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                sout("name=" + name + "----open");
                int n = attrs.getAttributeCount();
                for (int i = 0; i < n; i++) {
                    sout("AttributeName:" + attrs.getAttributeName(i));
                    sout("AttributeValue:" + attrs.getAttributeValue(i));
                }
                if (name.equals("TextView")) {
                    Button button = new Button(context, attrs);
                    return button;
                }
                sout("-------------end");
                return null;
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_system_test);
    }

    public void sout(String str) {
        System.out.println(Tag + "->" + str);
    }
}
