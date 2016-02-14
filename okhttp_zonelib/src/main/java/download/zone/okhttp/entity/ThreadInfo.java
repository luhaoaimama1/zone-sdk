package download.zone.okhttp.entity;
import com.litesuits.orm.db.annotation.Column;
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
    @Column( "downloadLength")
    private long downloadLength;//已下载大小
    @Mapping(Mapping.Relation.ManyToOne)
    private DownloadInfo downloadInfo;
    @Column( "complete")
    private boolean complete;

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public long getDownloadLength() {
        return downloadLength;
    }

    public void setDownloadLength(long downloadLength) {
        this.downloadLength = downloadLength;
    }

    public DownloadInfo getDownloadInfo() {
        return downloadInfo;
    }

    public void setDownloadInfo(DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;
    }

    public long getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(long endIndex) {
        this.endIndex = endIndex;
    }

    public long getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(long startIndex) {
        this.startIndex = startIndex;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }
}
