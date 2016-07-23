package xyz.gatling.flickrsample.model;

public class FlickrErrorImage extends Photo{
    private int resId;

    public FlickrErrorImage(int resId) {
        this.resId = resId;
    }

    public int getResId() {
        return resId;
    }
}
