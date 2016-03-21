package download.zone.okhttp.entity;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Ignore;
import com.litesuits.orm.db.annotation.Mapping;

/**
 * Created by Zone on 2016/2/14.
 */
public class ThreadInfo extends BaseEntity {
    @Column("threadId")
    private int threadId;//线程id
    @Column("startIndex")
    private long startIndex;
    @Column("endIndex")
    private long endIndex;
    @Column("downloadLength")
    private long downloadLength;//已下载大小
    @Mapping(Mapping.Relation.ManyToOne)
    private DownloadInfo downloadInfo;
    @Column("complete")
    private boolean complete;
    @Column("stoping")
    private boolean stoping;

    public synchronized boolean isStoping() {
        return stoping;
    }

    public synchronized void setStoping(boolean isStop) {
        this.stoping = isStop;
    }

    public synchronized boolean isComplete() {
        return complete;
    }

    public synchronized void setComplete(boolean complete) {
        this.complete = complete;
    }

    public synchronized long getDownloadLength() {
        return downloadLength;
    }

    public synchronized void setDownloadLength(long downloadLength) {
        this.downloadLength = downloadLength;
    }

    public synchronized DownloadInfo getDownloadInfo() {
        return downloadInfo;
    }

    public synchronized void setDownloadInfo(DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;
    }

    public synchronized long getEndIndex() {
        return endIndex;
    }

    public synchronized void setEndIndex(long endIndex) {
        this.endIndex = endIndex;
    }

    public synchronized long getStartIndex() {
        return startIndex;
    }

    public synchronized void setStartIndex(long startIndex) {
        this.startIndex = startIndex;
    }

    public synchronized int getThreadId() {
        return threadId;
    }

    public synchronized void setThreadId(int threadId) {
        this.threadId = threadId;
    }
}
