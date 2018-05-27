package com.example.mylib_test.activity.touch;

import java.util.Arrays;
import java.util.List;

import com.example.mylib_test.R;
import com.example.mylib_test.Images;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Conflict1Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String type = getIntent().getStringExtra("type");
        if ("out".equals(type)) {
            setContentView(R.layout.a_touch_confict1);
        } else if ("frame".equals(type)) {
            setContentView(R.layout.a_touch_confict1_frame);
        } else if ("innerOntouch".equals(type)) {
            setContentView(R.layout.a_touch_confict1_ontouch);
        } else {
            setContentView(R.layout.a_touch_confict1_inner);
        }

        ListView lv = (ListView) findViewById(R.id.lv);
        List<String> temp = Arrays.asList(Images.imageThumbUrls);
        lv.setAdapter(new Adapter2(this, temp));

    }

    public class Adapter2 extends BaseAdapter {

        private List<String> stuList;
        private LayoutInflater inflater;

        public Adapter2(Context context, List<String> data) {
            stuList = data;
            inflater = LayoutInflater.from(context);
        }

//		@Override
//		public void fillData(Helper helper, String item, boolean itemChanged, int layoutId) {
//			helper.setText(R.id.tv,item);
//		}
//
//		@Override
//		public int getItemLayoutId(String s, int position) {
//			return R.layout.item_textview;
//		}

        @Override
        public int getCount() {
            return stuList.size();
        }

        @Override
        public Object getItem(int position) {
            return stuList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = inflater.inflate(R.layout.item_textview, null);
            TextView tv = (TextView) view.findViewById(R.id.tv);
            tv.setText(stuList.get(position));
            return view;
        }
    }
}
