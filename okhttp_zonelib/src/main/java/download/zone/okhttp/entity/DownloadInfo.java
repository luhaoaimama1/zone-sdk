/*
 * Copyright (C) 2015 pengjianbo(pengjianbosoft@gmail.com), Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package download.zone.okhttp.entity;
import android.text.TextUtils;
import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Default;
import com.litesuits.orm.db.annotation.Ignore;
import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.Table;
import java.util.ArrayList;

/**
 * Desction:文件下载数据模型
 * Author:pengjianbo  借鉴此人的~
 * Date:15/8/22 下午5:11
 */
@Table("DownloadInfo")
public class DownloadInfo extends  BaseEntity{
    //==============State=================
    public static final int DOWNLOADING = 1;//下载中
    public static final int PAUSE = 2;//暂停
    public static final int COMPLETE = 3;//完成
    @Column("url")
    private String url;//文件URL
    @Column( "targetName")
    private String targetName;//保存文件地址
    @Column("targetFolder")
    private String targetFolder;//保存文件夹
    @Column( "isRange")
    private boolean isRange;//是否支持 range
    @Column("totalLength")
    private long totalLength;//总大小
    @Mapping(Mapping.Relation.OneToMany)
    private ArrayList<ThreadInfo> threadInfo;

    @Default("0")
    @Column( "progress")
    private float progress;//下载进度
    @Default("0")
    @Column( "downloadLength")
    private long downloadLength;//已下载大小
    @Ignore
    private long networkSpeed;//下载速度  k/s
    @Ignore
    private boolean isDone = false;


    public synchronized String getUrl() {
        return url;
    }

    public synchronized void setUrl(String url) {
        this.url = url;
    }

    public synchronized String getTargetName() {
        return targetName;
    }

    public synchronized void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public synchronized String getTargetFolder() {
        return targetFolder;
    }

    public synchronized void setTargetFolder(String targetFolder) {
        this.targetFolder = targetFolder;
    }

    public synchronized float getProgress() {
        return progress;
    }

    public synchronized void setProgress(float progress) {
        this.progress = progress;
    }

    public synchronized long getTotalLength() {
        return totalLength;
    }

    public synchronized void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }

    public synchronized long getDownloadLength() {
        return downloadLength;
    }

    public synchronized void setDownloadLength(long downloadLength) {
        this.downloadLength = downloadLength;
    }

    public synchronized long getNetworkSpeed() {
        return networkSpeed;
    }

    public synchronized void setNetworkSpeed(long networkSpeed) {
        this.networkSpeed = networkSpeed;
    }

    public synchronized boolean isDone() {
        return isDone;
    }

    public synchronized void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public synchronized ArrayList<ThreadInfo> getThreadInfo() {
        if(threadInfo==null)
            threadInfo=new ArrayList<ThreadInfo>();
        return threadInfo;
    }

    public synchronized void setThreadInfo(ArrayList<ThreadInfo> threadInfo) {
        this.threadInfo = threadInfo;
    }

    public synchronized boolean isRange() {
        return isRange;
    }

    public synchronized void setRange(boolean range) {
        this.isRange = range;
    }


}
