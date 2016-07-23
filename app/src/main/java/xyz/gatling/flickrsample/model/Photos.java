package xyz.gatling.flickrsample.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by gimmi on 7/22/2016.
 */

public class Photos implements Parcelable {

    private int page;
    private int pages;
    private int perPage;
    private String total;
    private List<Photo> photo;

    protected Photos(Parcel in) {
        page = in.readInt();
        pages = in.readInt();
        perPage = in.readInt();
        total = in.readString();
        photo = in.createTypedArrayList(Photo.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeInt(pages);
        dest.writeInt(perPage);
        dest.writeString(total);
        dest.writeTypedList(photo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Photos> CREATOR = new Creator<Photos>() {
        @Override
        public Photos createFromParcel(Parcel in) {
            return new Photos(in);
        }

        @Override
        public Photos[] newArray(int size) {
            return new Photos[size];
        }
    };

    public int getPage() {
        return page;
    }

    public int getPages() {
        return pages;
    }

    public int getPerPage() {
        return perPage;
    }

    public String getTotal() {
        return total;
    }

    public List<Photo> getPhoto() {
        return photo;
    }
}
