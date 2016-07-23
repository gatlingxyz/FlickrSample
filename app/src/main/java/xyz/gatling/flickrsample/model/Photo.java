package xyz.gatling.flickrsample.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.ViewGroup;

/**
 * Created by gimmi on 7/22/2016.
 */

public class Photo implements Parcelable{

    private String owner;
    private String secret;
    private String id;
    private String server;
    private int farm;
    private String title;
    private int ispublic;
    private int isfriend;
    private int isfamily;
    private String originalsecret;
    private String originalformat;
    private Integer height = ViewGroup.LayoutParams.WRAP_CONTENT;
    private Integer width = ViewGroup.LayoutParams.WRAP_CONTENT;
    private boolean shouldInvalidateCache = false;

    protected Photo(){}

    protected Photo(Parcel in) {
        owner = in.readString();
        secret = in.readString();
        id = in.readString();
        server = in.readString();
        farm = in.readInt();
        title = in.readString();
        ispublic = in.readInt();
        isfriend = in.readInt();
        isfamily = in.readInt();
        originalsecret = in.readString();
        originalformat = in.readString();

        height = ViewGroup.LayoutParams.WRAP_CONTENT;
        width = ViewGroup.LayoutParams.WRAP_CONTENT;

    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getOwner() {
        return owner;
    }

    public String getSecret() {
        return secret;
    }

    public String getId() {
        return id;
    }

    public String getServer() {
        return server;
    }

    public String getOriginalsecret() {
        return originalsecret;
    }

    public String getOriginalformat() {
        return originalformat;
    }

    public int getFarm() {
        return farm;
    }

    public String getTitle() {
        return title;
    }

    public int getIsPublic() {
        return ispublic;
    }

    public int getIsFriend() {
        return isfriend;
    }

    public int getIsFamily() {
        return isfamily;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(owner);
        dest.writeString(secret);
        dest.writeString(id);
        dest.writeString(server);
        dest.writeInt(farm);
        dest.writeString(title);
        dest.writeInt(ispublic);
        dest.writeInt(isfriend);
        dest.writeInt(isfamily);
        dest.writeString(originalsecret);
        dest.writeString(originalformat);
    }

    public boolean shouldInvalidateCache() {
        return shouldInvalidateCache;
    }
}
