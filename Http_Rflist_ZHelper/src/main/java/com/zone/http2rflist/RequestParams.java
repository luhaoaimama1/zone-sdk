package com.zone.http2rflist;
import com.zone.http2rflist.callback.NetworkListener;
import com.zone.http2rflist.entity.HttpTypeNet;

/**
 * Created by Administrator on 2016/3/25.
 */
public class RequestParams {
  public  final NetworkParams params;
  public final NetworkListener listener;
  public  final  String urlString;
  public final int handlerTag;
  public final Object cancelTag;
  public final HttpTypeNet httpTypeNet;

    private RequestParams(Builder builder) {
        this.params = builder.params;
        this.listener = builder.listener;
        this.urlString = builder.urlString;
        this.handlerTag = builder.handlerTag;
        this.cancelTag = builder.cancelTag;
        this.httpTypeNet = builder.httpTypeNet;
        this.params.setHttpTypeNet(httpTypeNet);
    }
    public static Builder get(String url){
        return  new RequestParams.Builder().get().url(url);
    }
    public static Builder head(String url){
        return new RequestParams.Builder().head().url(url);
    }
    public static Builder delete(String url){
        return new RequestParams.Builder().delete().url(url);
    }
    public static Builder post(String url){
        return new RequestParams.Builder().post().url(url);
    }

    public static Builder postJson(String url){
        return new RequestParams.Builder().postJson().url(url);
    }

    public static Builder put(String url){
        return  new RequestParams.Builder().put().url(url);
    }
    public static Builder patch(String url){
        return  new RequestParams.Builder().patch().url(url);
    }

    public static class Builder {

        private NetworkParams params;
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

        public Builder listener(NetworkListener listener) {
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

        public Builder params(NetworkParams params) {
            this.params = params;
            return this;
        }
        public  Builder get(){
            return this;
        }
        public  Builder head(){
            httpTypeNet=HttpTypeNet.HEAD;
            return this;
        }
        public  Builder delete(){
            httpTypeNet=HttpTypeNet.DELETE;
            return new RequestParams.Builder();
        }
        public  Builder post(){
            httpTypeNet=HttpTypeNet.POST;
            return this;
        }

        public  Builder postJson(){
            httpTypeNet=HttpTypeNet.POST.postJson();
            return this;
        }

        public  Builder put(){
            httpTypeNet=HttpTypeNet.PUT;
            return this;
        }
        public  Builder patch(){
            httpTypeNet=HttpTypeNet.PATCH;
            return this;
        }

        public RequestParams build() {
            return new RequestParams(this);
        }
    }
}
