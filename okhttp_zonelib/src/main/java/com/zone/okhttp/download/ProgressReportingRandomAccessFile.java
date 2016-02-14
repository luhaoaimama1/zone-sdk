package com.zone.okhttp.download;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 文件读写
 */
public  class ProgressReportingRandomAccessFile extends RandomAccessFile {
    private long lastDownloadLength = 0;
    private long curDownloadLength = 0;
    private long lastRefreshUiTime;

    public ProgressReportingRandomAccessFile(File file, String mode, long lastDownloadLength)
            throws FileNotFoundException {
        super(file, mode);
        this.lastDownloadLength = lastDownloadLength;
        this.lastRefreshUiTime = System.currentTimeMillis();
    }

    @Override
    public void write(byte[] buffer, int offset, int count) throws IOException {
        super.write(buffer, offset, count);

        //已下载大小
        long downloadLength = lastDownloadLength + count;
        curDownloadLength += count;
        lastDownloadLength = downloadLength;
//        mDownloadInfo.setDownloadLength(downloadLength);
//
//        //计算下载速度
//        long totalTime = (System.currentTimeMillis() - mPreviousTime)/1000;
//        if ( totalTime == 0 ) {
//            totalTime += 1;
//        }
//        long networkSpeed = curDownloadLength / totalTime;
//        mDownloadInfo.setNetworkSpeed(networkSpeed);
//
//        //下载进度
//        int progress = (int)(downloadLength * 100 / mDownloadInfo.getTotalLength());
//        mDownloadInfo.setProgress(progress);
//        long curTime = System.currentTimeMillis();
//        if ( curTime - lastRefreshUiTime >= 1000 || progress == 100) {
//            publishProgress(progress);
//            lastRefreshUiTime = System.currentTimeMillis();
//        }
    }
}