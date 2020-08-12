package com.appiness.yo2see;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;

import com.appiness.yo2see.R;
import com.appiness.yo2see.callbacks.GifEndListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class GifUtil {

   public GifEndListener gifEndListener;
    GifUtil(GifEndListener gifEndListener){
        this.gifEndListener=gifEndListener;
    }
    public   void setImage(Context context, ImageView imageView){
        Glide.with(context).asGif().load(R.drawable.tree_gif).listener(new RequestListener<GifDrawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                resource.setLoopCount(1);
                resource.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
                    @Override
                    public void onAnimationEnd(Drawable drawable) {
                        //do whatever after specified number of loops complete
                        gifEndListener.animationEnd();
                    }
                });
                return false;
            }}).into(imageView);
    }
}
