package com.dartmic.yo2see.ui.product_list.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dartmic.yo2see.R
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.managers.ImageRequestManager
import com.dartmic.yo2see.model.product.ListingItem
import com.dartmic.yo2see.utils.Config
import com.facebook.drawee.drawable.ScalingUtils
import kotlinx.android.synthetic.main.item_product.view.*


class AdapterProductList(
    private val adapterViewClickListener: AdapterViewClickListener<ListingItem>?,
    val activity: Activity, val back:Int
) : ListAdapter<ListingItem, AdapterProductList.ViewHolder>(
    AdapterProductListCallback()
)
{
    private var lastPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AdapterProductList.ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_product, parent, false)

      /*  val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels

        itemView.layoutParams = RecyclerView.LayoutParams(width - (width / 5), RecyclerView.LayoutParams.WRAP_CONTENT)
*/

        return ViewHolder(itemView, activity)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.clearAnimation();

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), adapterViewClickListener,back)
        setAnimation(holder.itemView,position)
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation: Animation =
                AnimationUtils.loadAnimation(activity, R.anim.slide_left_to_right)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }else{
            val animation: Animation =
                AnimationUtils.loadAnimation(activity, R.anim.slide_in_from_right)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }
    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(allProducts: ListingItem, adapterViewClick: AdapterViewClickListener<ListingItem>?,back: Int) {

            itemView.tvPrice?.text = allProducts.listingPrice
            itemView.tvModel?.text = allProducts.listingTitle
            itemView.tvAddress?.text = allProducts.listingAddress
            itemView.tvDate?.text = allProducts.listingPublishDatetime

           ImageRequestManager.with(itemView.imageProduct)
                .setPlaceholderImage(R.drawable.download)
                .setScaleType(ScalingUtils.ScaleType.FIT_XY)
                .build()
            itemView.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT, adapterPosition
                )
            }
        }
    }

}