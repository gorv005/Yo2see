package com.dartmic.yo2see.ui.productDetails.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.dartmic.yo2see.R
import com.dartmic.yo2see.managers.ImageRequestManager
import com.dartmic.yo2see.model.product.GalleryListItem
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.view.SimpleDraweeView


class ProductImagesAdapter(var list: List<GalleryListItem>) : PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val item = LayoutInflater.from(container.context).inflate(
            R.layout.item_product_images, container,
            false
        )
        val ivBanner: SimpleDraweeView = item.findViewById(R.id.ivOnboarding)
        //    ivBanner.setImageResource(list[position])
        ImageRequestManager.with(ivBanner).url(list?.get(position).imageName!!)
            .setScaleType(
                ScalingUtils.ScaleType.FIT_XY
            )
            .build()
        container.addView(item)
        return item
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}