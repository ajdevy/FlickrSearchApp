package com.flickr.photoapp.photos.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flickr.photoapp.R;
import com.flickr.photoapp.photos.Photo;
import com.flickr.photoapp.photos.PhotoUrlGenerator;
import com.flickr.photoapp.ui.RecyclerArrayAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class PhotoArrayAdapter extends RecyclerArrayAdapter<Photo, PhotoArrayAdapter.PhotoViewHolder> {

    private final LayoutInflater layoutInflater;

    public PhotoArrayAdapter(Context context, List<Photo> objects) {
        super(objects);
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemLayout = layoutInflater.inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(itemLayout);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        holder.bindView(getItem(position));
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;

        public PhotoViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.ivPhoto);
            image.setImageResource(android.R.color.transparent);
        }

        public void bindView(Photo photo) {
            final ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(PhotoUrlGenerator.generate(photo), image);
        }
    }
}