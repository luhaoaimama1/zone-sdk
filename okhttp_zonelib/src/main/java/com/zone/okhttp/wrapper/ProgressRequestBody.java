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

package com.zone.okhttp.wrapper;
import com.zone.okhttp.OkHttpUtils;
import com.zone.okhttp.entity.LoadingParams;
import com.zone.okhttp.utils.MainHandlerUtils;

import java.io.IOException;

import zone.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Desction:包装的请求体，处理进度
 * Author:pengjianbo(借用下~)
 * Date:15/12/10 下午5:31
 */
public class ProgressRequestBody extends RequestBody{
    //实际的待包装请求体
    private final RequestBody requestBody;
    //进度回调接口
    private final Callback.ProgressCallback mProgressCallback;
    //包装完成的BufferedSink
    private BufferedSink bufferedSink;
    //开始下载时间，用户计算加载速度
    private long mPreviousTime;

    private LoadingParams mLoadingParams;
    private boolean upLoadingOver;

    /**
     * 构造函数，赋值
     * @param requestBody 待包装的请求体
     */
    public ProgressRequestBody(RequestBody requestBody, Callback.ProgressCallback mProgressCallback) {
        this.requestBody = requestBody;
        this.mProgressCallback=mProgressCallback;
//        this.progressListener = progressListener;
        mLoadingParams=new LoadingParams();
    }

    /**
     * 重写调用实际的响应体的contentType
     * @return MediaType
     */
    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    /**
     * 重写调用实际的响应体的contentLength
     * @return contentLength
     * @throws IOException 异常
     */
    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    /**
     * 重写进行写入
     * @param sink BufferedSink
     * @throws IOException 异常
     */
    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            //包装
            bufferedSink = Okio.buffer(sink(sink));
        }
        //写入
        requestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();

    }

    /**
     * 写入，回调进度接口
     * @param sink Sink
     * @return Sink
     */
    private Sink sink(Sink sink) {
        mPreviousTime = System.currentTimeMillis();
        return new ForwardingSink(sink) {

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                //回调
                if (mProgressCallback!=null) {
                    if ( mLoadingParams.total == 0) {
                        //获得contentLength的值，后续不再调用
                        mLoadingParams.total= contentLength();
                    }
                    //增加当前写入的字节数
                    mLoadingParams.current += byteCount;

                    //计算下载速度
                    long totalTime = (System.currentTimeMillis() - mPreviousTime)/1000;
                    if ( totalTime == 0 ) {
                        totalTime += 1;
                    }
                    mLoadingParams.networkSpeed =  mLoadingParams.current / totalTime;
                    mLoadingParams.progress = (int)( mLoadingParams.current * 100 /  mLoadingParams.total);
                    mLoadingParams.isUploading=mLoadingParams.current != mLoadingParams.total;
                    if (!upLoadingOver) {
                        if (mLoadingParams.progress == 100 && !mLoadingParams.isUploading)
                            upLoadingOver = true;
                        MainHandlerUtils.onLoading(mProgressCallback, mLoadingParams.total, mLoadingParams.current,
                                mLoadingParams.networkSpeed, mLoadingParams.isUploading);
                    }
                }
            }
        };
    }

}
