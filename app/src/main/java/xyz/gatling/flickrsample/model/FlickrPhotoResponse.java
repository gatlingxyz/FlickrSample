package xyz.gatling.flickrsample.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gimmi on 7/22/2016.
 */

public class FlickrPhotoResponse implements Parcelable{

    Photos photos;
    String stat;

    protected FlickrPhotoResponse(Parcel in) {
        photos = in.readParcelable(Photos.class.getClassLoader());
        stat = in.readString();
    }

    public static final Creator<FlickrPhotoResponse> CREATOR = new Creator<FlickrPhotoResponse>() {
        @Override
        public FlickrPhotoResponse createFromParcel(Parcel in) {
            return new FlickrPhotoResponse(in);
        }

        @Override
        public FlickrPhotoResponse[] newArray(int size) {
            return new FlickrPhotoResponse[size];
        }
    };

    public Photos getPhotos() {
        return photos;
    }

    public String getStat() {
        return stat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(photos, flags);
        dest.writeString(stat);
    }
}
