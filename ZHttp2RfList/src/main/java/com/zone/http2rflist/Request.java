package com.zone.http2rflist;

import com.zone.http2rflist.callback.NetworkListener;
import com.zone.http2rflist.entity.HttpTypeNet;

/**
 * Created by Administrator on 2016/3/25.
 */
public class Request {
  public  final  RequestParamsNet params;
  public final NetworkListener listener;
  public  final  String urlString;
  public final int handlerTag;
  public final Object cancelTag;
  public final HttpTypeNet httpTypeNet;

    private Request(Builder builder) {
        this.params = builder.params;
        this.listener = builder.listener;
        this.urlString = builder.urlString;
        this.handlerTag = builder.handlerTag;
        this.cancelTag = builder.cancelTag;
        this.httpTypeNet = builder.httpTypeNet;
    }


    public static class Builder {

        private RequestParamsNet params;
        private NetworkListener listener;
        private String urlString;
        private int handlerTag=-1;
        private HttpTypeNet httpTypeNet=HttpTypeNet.GET;
        private Object cancelTag;

        public Builder() {
        }

        public Builder url(String urlString) {
            this.urlString = urlString;
            return this;
        }

        public Builder networkListener(NetworkListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder handlerTag(int handlerTag) {
            this.handlerTag = handlerTag;
            return this;
        }
        public Builder cancelTag(Object cancelTag) {
            this.cancelTag = cancelTag;
            return this;
        }

        public Builder params(RequestParamsNet params) {
            this.params = params;
            return this;
        }
        public Builder get(){
            return this;
        }
        public Builder head(){
            httpTypeNet=HttpTypeNet.HEAD;
            return this;
        }
        public Builder delete(){
            httpTypeNet=HttpTypeNet.DELETE;
            return this;
        }
        public Builder post(){
            httpTypeNet=HttpTypeNet.POST;
            return this;
        }

        public Builder postJson(){
            httpTypeNet=HttpTypeNet.POST.postJson();
            return this;
        }

        public Builder put(){
            httpTypeNet=HttpTypeNet.PUT;
            return this;
        }
        public Builder patch(){
            httpTypeNet=HttpTypeNet.PATCH;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }
}
