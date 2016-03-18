package download.zone.okhttp;

import java.util.Map;

/**
 * Created by Zone on 2016/3/18.
 */
public class TaskHelper {
    //如何判断 一个任务  多个线程  真正的停止     开始   存在  不存在
    /**
     * 维护一个正在运行的task: 为了一个url只有一个task运行
     */
    private Map<String,Integer> taskRunning;
}
