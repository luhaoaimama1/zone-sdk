package com.zone.view.ninegridview.preview;
import java.io.Serializable;
public class ImageInfo implements Serializable {
    public String thumbnailUrl;
    public String bigImageUrl;

    public ImageInfo(String thumbnailUrl, String bigImageUrl) {
        this.thumbnailUrl = thumbnailUrl;
        this.bigImageUrl = bigImageUrl;
    }

    public ImageInfo() {
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getBigImageUrl() {
        return bigImageUrl;
    }

    public void setBigImageUrl(String bigImageUrl) {
        this.bigImageUrl = bigImageUrl;
    }

    @Override
    public String toString() {
        return "ImageInfo{" +
                ", bigImageUrl='" + bigImageUrl + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                '}';
    }
}
