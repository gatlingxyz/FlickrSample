package xyz.gatling.flickrsample.adapters;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

/**
 * Created by gimmi on 7/22/2016.
 */

public class FlickrViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;

    public FlickrViewHolder(ImageView itemView) {
        super(itemView);
        this.imageView = itemView;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
