package com.tuccro.imgseek.model;

/**
 * Created by tuccro on 11/2/15.
 */
public class ImageDescriptor {

    String imageUrl;
    String thumbnailUrl;

    String imageLocalUrl;
    String thumbnailLocalUrl;

    String description;

    public ImageDescriptor(String description, String thumbnailUrl, String imageUrl) {
        this.imageUrl = imageUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLocalUrl() {
        return imageLocalUrl;
    }

    public void setImageLocalUrl(String imageLocalUrl) {
        this.imageLocalUrl = imageLocalUrl;
    }

    public String getThumbnailLocalUrl() {
        return thumbnailLocalUrl;
    }

    public void setThumbnailLocalUrl(String thumbnailLocalUrl) {
        this.thumbnailLocalUrl = thumbnailLocalUrl;
    }
}
