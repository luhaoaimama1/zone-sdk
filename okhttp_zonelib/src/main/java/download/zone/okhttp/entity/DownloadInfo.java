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
import java.util.List;

/**
 * Desction:文件下载数据模型
 * Author:pengjianbo  借鉴此人的~
 * Date:15/8/22 下午5:11
 */
@Table("DownloadInfo")
public class DownloadInfo extends  BaseEntity implements Comparable<DownloadInfo>{
    //==============State=================
    public static final int WAIT = 0;//等待
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
    private List<ThreadInfo> threadInfo;

    @Default("0")
    @Column( "progress")
    private int progress;//下载进度
    @Default("0")
    @Column( "downloadLength")
    private long downloadLength;//已下载大小

    @Ignore
    private long networkSpeed;//下载速度
    @Ignore
    private int state = PAUSE;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetFolder() {
        return targetFolder;
    }

    public void setTargetFolder(String targetFolder) {
        this.targetFolder = targetFolder;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public long getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }

    public long getDownloadLength() {
        return downloadLength;
    }

    public void setDownloadLength(long downloadLength) {
        this.downloadLength = downloadLength;
    }

    public long getNetworkSpeed() {
        return networkSpeed;
    }

    public void setNetworkSpeed(long networkSpeed) {
        this.networkSpeed = networkSpeed;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<ThreadInfo> getThreadInfo() {
        if(threadInfo==null)
            threadInfo=new ArrayList<ThreadInfo>();
        return threadInfo;
    }

    public void setThreadInfo(List<ThreadInfo> threadInfo) {
        this.threadInfo = threadInfo;
    }

    public boolean isRange() {
        return isRange;
    }

    public void setRange(boolean range) {
        this.isRange = range;
    }

    @Override
    public boolean equals(Object o) {
        if ( o instanceof DownloadInfo ) {
            DownloadInfo info = (DownloadInfo) o;
            if ( info != null && TextUtils.equals(info.getUrl(), url)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(DownloadInfo another) {
        if ( another == null ) {
            return 0;
        }

        int lhs = getId();
        int rhs = another.getId();
        return lhs < rhs ? -1 : (lhs == rhs ? 0 : 1);
    }
}
