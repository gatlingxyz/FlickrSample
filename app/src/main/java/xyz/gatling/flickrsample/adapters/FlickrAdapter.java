package xyz.gatling.flickrsample.adapters;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import xyz.gatling.flickrsample.R;
import xyz.gatling.flickrsample.model.FlickrErrorImage;
import xyz.gatling.flickrsample.model.Photo;

/**
 * Created by gimmi on 7/22/2016.
 */

public class FlickrAdapter extends RecyclerView.Adapter<FlickrViewHolder> {

    private List<Photo> photos = new ArrayList<>();
    private Drawable progress;
    private int screenWidth, screenHeight;
    private boolean isPortrait;

    public FlickrAdapter(Drawable progress, int screenWidth, int screenHeight, boolean isPortrait) {
        photos.add(new FlickrErrorImage(R.drawable.flick_banner));
        this.progress = progress;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.isPortrait = isPortrait;
    }

    @Override
    public FlickrViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return new FlickrViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(final FlickrViewHolder holder, final int position) {
        Photo photo = photos.get(position);
        if(photo instanceof FlickrErrorImage){
            Picasso.with(holder.getImageView().getContext())
                    .load(((FlickrErrorImage)photo).getResId())
                    .resize(screenWidth, 0)
                    .into(holder.getImageView());
            return;
        }
        String url = holder.getImageView().getContext().getString(
                R.string.flickr_photo_format,
                String.valueOf(photo.getFarm()),
                photo.getServer(),
                photo.getId(),
                photo.getSecret(),
                "n",
                "jpg"
        );

        if(isPortrait) {
        /*
            First pass, this is WRAP_CONTENT. But, on the second pass, because of recycling,
            it looks a little awkward, I think, so let's actually use the image's metric which we found
            out in the onSuccess.
         */
            holder.getImageView().getLayoutParams().height = photo.getHeight();
        }

//        Picasso.with(holder.getImageView().getContext()).setIndicatorsEnabled(true);
        Picasso.with(holder.getImageView().getContext())
                .load(url)
                .resize(screenWidth, 0)
                .placeholder(progress)
                .into(holder.getImageView(), new Callback() {
                    @Override
                    public void onSuccess() {
                        if(isPortrait) {
                            photos.get(position).setHeight(holder.getImageView().getDrawable().getBounds().height());
                        }
                    }

                    @Override
                    public void onError() {

                    }
                })
        ;
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void updateItems(List<Photo> photos){
        this.photos = photos;
        notifyDataSetChanged();
    }

    public void clearItems(){
        updateItems(new ArrayList<Photo>());
    }
}
