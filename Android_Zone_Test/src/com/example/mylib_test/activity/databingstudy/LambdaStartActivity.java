package com.example.mylib_test.activity.databingstudy;

import android.view.View;

import com.android.internal.util.Predicate;
import com.example.mylib_test.R;
import java.util.Arrays;
import java.util.List;
import and.base.activity.BaseActivityZ;


/**
 * Created by Administrator on 2016/4/16.
 */
public class LambdaStartActivity extends BaseActivityZ {

    @Override
    public void setContentView() {
        setContentView(R.layout.a_lambda);


    }

    @Override
    public void findIDs() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bt_run:
                run();
                break;
            case R.id.bt_sort:
                bt_sort();
                break;
            case R.id.bt_finalTest:
              finaltest();
                break;
            case R.id.bt_quoteTest:
                bt_quoteTest();
                break;
            case R.id.bt_for:
                bt_for();
                break;
        }
    }

    private void bt_for() {
        String[] players = {"Rafael Nadal", "Novak Djokovic",
                "Stanislas Wawrinka", "David Ferrer",
                "Roger Federer", "Andy Murray",
                "Tomas Berdych", "Juan Martin Del Potro",
                "Richard Gasquet", "John Isner"};
        List<String> playersList = Arrays.asList(players);
    }

    private void bt_quoteTest() {
        int m=6;
        //Integer既是泛型 不写 就会报错
        Predicate<Integer> xBoolean=x->x>5;//这个函数是比较大小的匿名函数
        boolean ks = xBoolean.apply(m);
        Test a=(boolean x, int y)-> System.out.println(x);
    }

    private void finaltest() {
        String name="abc";
//        name="kbc";
        //name已经是final了 即是 事实上的final常量 如果这个事实上的final类被修改编译过不了
        handler.post(()-> System.out.println(name));

    }

    private void bt_sort() {

        String[] players = {"Rafael Nadal", "Novak Djokovic",
                "Stanislas Wawrinka", "David Ferrer",
                "Roger Federer", "Andy Murray",
                "Tomas Berdych", "Juan Martin Del Potro",
                "Richard Gasquet", "John Isner"};
        Arrays.sort(players,(String s1,String s2)->s1.compareTo(s2));
        System.out.println("参数类型String");
        for (String player : players)
            System.out.println(player);
        Arrays.sort(players,(s1,s2)->s1.compareTo(s2));
        System.out.println("参数无类型");
        for (String player : players)
            System.out.println(player);
    }

    private void run() {
        handler.post(() -> {
            int index = 0;
            while (index < 10) {
                System.out.println(index++);
            }
        });

    }
    public interface  Test{
       void gaga(boolean x, int y);
    }
}
