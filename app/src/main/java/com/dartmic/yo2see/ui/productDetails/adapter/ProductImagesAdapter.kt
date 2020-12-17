package com.dartmic.yo2see.ui.productDetails.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.dartmic.yo2see.R
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.view.SimpleDraweeView


class ProductImagesAdapter() :PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val item = LayoutInflater.from(container.context).inflate(
            R.layout.item_product_images, container,
            false)
        val ivBanner : ImageView = item.findViewById(R.id.ivOnboarding)
    //    ivBanner.setImageResource(list[position])
       /* ImageRequestManager.with(ivBanner).setPlaceholderImage(list[position].avatarUrl).setScaleType(
            ScalingUtils.ScaleType.FIT_XY)
            .build()*/
        container.addView(item)
        return item
    }

    override fun getCount(): Int {
        return 5
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}