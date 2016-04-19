package com.example.mylib_test.activity.databingstudy;

import android.view.View;

import com.example.mylib_test.R;

import java.util.concurrent.TimeUnit;

import and.base.activity.BaseActvity;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/4/17.
 */
public class RxjavaActivity extends BaseActvity {
    @Override
    public void setContentView() {
        setContentView(R.layout.a_rxjava);
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
            case R.id.bt_hello:
                bt_hello();
                break;
        }
    }

    public static void main(String[] args) {
//        bt_hello();
//        liftExample();
//        skipTest();
//        rangeTest();
        repeatTest();
//        deferTest();
//        scan2ReduceTest();
//        ditinctTest();
//        mergeTest();
//        retryTest();
//        catchTest();
//        timestampTest();
//         async();
    }

    private static void async() {

    }

    private static void timestampTest() {
        Observable.just("i","for","you").all(s->s instanceof String).timestamp().subscribe(s-> System.out.println(s));
    }

    private static void catchTest() {
        //onErrorReturn
//        Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("3");
//                subscriber.onNext("4");
//                subscriber.onError(new NullPointerException());
//                subscriber.onNext("5");
//            }
//        }).onErrorReturn(new Func1<Throwable, String>() {
//            @Override
//            public String call(Throwable throwable) {
//                return "1";
//            }
//        }).subscribe(new Subscriber<String>() {
//            @Override
//            public void onCompleted() {
//                System.out.println("onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("onError");
//            }
//
//            @Override
//            public void onNext(String o) {
//                System.out.println("onNext:"+o);
//            }
//        });
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("3");
                subscriber.onNext("4");
                subscriber.onError(new NullPointerException());
                subscriber.onNext("5");
            }
        }).onErrorResumeNext(new Func1<Throwable, Observable<? extends String>>() {
            @Override
            public Observable<? extends String> call(Throwable throwable) {
                return Observable.just("7","8","9") ;
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onNext(String o) {
                System.out.println("onNext:"+o);
            }
        });

    }

    private static void retryTest() {
        Observable.create((Subscriber<? super String> s) -> {
            System.out.println("subscribing");
            s.onError(new RuntimeException("always fails"));
        }).retryWhen(attempts ->
                attempts.zipWith(Observable.range(1, 3), (n, i) -> i)
                        .flatMap(i -> {
                            System.out.println("delay retry by " + i + " second(s)");
                            return Observable.timer(i, TimeUnit.SECONDS);
                        })
        ).toBlocking().forEach(System.out::println);
    }

    private static void mergeTest() {
        Observable<Integer> odds = Observable.just(1, 3, 5);
        Observable<Integer> evens = Observable.just(2, 4, 6);
        Observable.merge(odds, evens).subscribe(s-> System.out.print("merge"+s+"\t"));
    }

    private static void ditinctTest() {
        Observable.just(1, 2, 1, 1, 2, 3)
                .distinct()
                .subscribe(s-> System.out.println(s));

    }

    private static void scan2ReduceTest() {
        //scan
        Observable.just(1, 2, 3, 4, 5)
                .scan((sum, item) -> {
                    System.out.println("sum:" + sum+"\t item:" + item);
                    return sum + item;
                }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onNext(Integer item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Sequence complete.");
            }
        });
        //reduce
//        Observable.just(1, 2, 3, 4, 5)
//                .reduce((sum, item) -> {
//                    System.out.println("sum:" + sum+"\t item:" + item);
//                    return sum + item;
//                }).subscribe(new Subscriber<Integer>() {
//            @Override
//            public void onNext(Integer item) {
//                System.out.println("Next: " + item);
//            }
//
//            @Override
//            public void onError(Throwable error) {
//                System.err.println("Error: " + error.getMessage());
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("Sequence complete.");
//            }
//        });
    }

    private static void deferTest() {
        Observable.defer(() -> Observable.range(0,10)).subscribe(s-> System.out.print("defer(0,10):"+s));
    }

    private static void repeatTest() {
        //0-10无线循环
//        Observable.range(0,10).repeat().subscribe(s-> System.out.print("repeat(0,10):"+s));
        //0-10 循环一次
        Observable.range(0,10).repeat(1).subscribe(s-> System.out.print("repeat(0,10):"+s));
    }

    private static void rangeTest() {
        Observable.range(0,10).subscribe(s-> System.out.print("range(0,10):"+s));
        System.out.println();
//        Observable.range(0,-1).subscribe(s-> System.out.print("range(0,-1):"+s));
//        System.out.println();
        Observable.range(10,0).subscribe(s-> System.out.print("range(10,0):"+s));
    }

    private static void skipTest() {
        Observable.just("hello", "I", "am", "Ri", "Tian")
                .skip(1)
                .take(2).map(s -> s + ":Zone")
                .subscribe(s -> System.out.println(s));
    }

    private static void liftExample() {
        Observable.just("123").lift((Observable.Operator<Integer, String>) subscriber -> new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted: ");
//                        subscriber.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError: " + e.getMessage());
//                        subscriber.onError(e);
            }

            @Override
            public void onNext(String s) {
                System.out.println( "onNext: s:" + s);
                int value = Integer.valueOf(s) * 2; //转化为Integer类型的值并乘以2
//                        subscriber.onNext(value);
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("subscribe: onCompleted:" );
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("subscribe: onError:");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("subscribe: onNext:" + integer);
            }
        });
    }

    private static void bt_hello() {
        Observable<String> observable = Observable.just("hello", "rxjava");
//        observable.map(t -> t + "Zone").subscribe(o -> System.out.println(o));
//        Observable.just("Hello, world!")
//                .map(s -> s.hashCode())
//                .map(s -> String.valueOf(s))
//                .subscribe(i -> System.out.println(i));
//        String[] ulrs = new String[]{"url1", "url2", "url3"};
//        Observable.just("Hello","world!")
//                .filter(s->s=="Hello")
//                .flatMap(s-> Observable.from(ulrs).map(a->a+" "+s))
//                .take(2)
//                .subscribe(a-> System.out.println(a)).isUnsubscribed();



    }
}
