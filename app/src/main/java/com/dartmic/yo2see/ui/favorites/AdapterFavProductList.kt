package com.dartmic.yo2see.ui.favorites

import android.app.Activity
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
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
import com.dartmic.yo2see.ui.product_list.adapter.AdapterProductListCallback
import com.dartmic.yo2see.utils.Config
import com.facebook.drawee.drawable.ScalingUtils
import kotlinx.android.synthetic.main.fragment_product_details.*
import kotlinx.android.synthetic.main.item_product.view.*


class AdapterFavProductList(
    private val adapterViewClickListener: AdapterViewClickListener<ListingItem>?,
    val activity: Activity, val back: Int
) : ListAdapter<ListingItem, AdapterFavProductList.ViewHolder>(
    AdapterProductListCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AdapterFavProductList.ViewHolder {
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
        holder.bind(getItem(position), adapterViewClickListener, back)
        setAnimation(holder.itemView, position)
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
      /*  if (position > lastPosition) {
            val animation: Animation =
                AnimationUtils.loadAnimation(activity, R.anim.slide_left_to_right)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        } else {
            val animation: Animation =
                AnimationUtils.loadAnimation(activity, R.anim.slide_in_from_right)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }*/
    }

    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(
            allProducts: ListingItem,
            adapterViewClick: AdapterViewClickListener<ListingItem>?,
            back: Int
        ) {
            if (allProducts.listingType.equals( Config.Constants.TYPE_BARTER)) {
                itemView.tvPrice?.text = allProducts.barterText
                itemView.tvModel?.text = "In exchange with " + allProducts.barterExchangeText
            } else if (allProducts.listingType.equals( Config.Constants.TYPE_RENT)) {

                itemView.tvPrice?.text = allProducts.payment + "/" + allProducts.rent_type
                itemView.tvModel?.text = allProducts.listingTitle
            } else {
                itemView.tvPrice?.text = allProducts.listingPrice
                itemView.tvModel?.text = allProducts.listingTitle
            }
            itemView.tvAddress?.text = allProducts.listingCity + ", " + allProducts.listingState
            itemView.tvDate?.text = allProducts.listingPublishDatetime

            val v = SpannableString("View " + allProducts.userName + "'s listing")
            v.setSpan(UnderlineSpan(), 0, v.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            itemView.tvListing.text = v.toString()
            itemView.ivFav.pressOnTouch(true);

            if (allProducts?.UserFavorite == 1) {
                itemView.ivFav.setChecked(true);
                itemView.ivFav.playAnimation()
            } else {
                itemView.ivFav.setChecked(false)

            }
            ImageRequestManager.with(itemView.imageProduct).url(allProducts.listingCoverImage)
                .setPlaceholderImage(R.drawable.download)
                .setScaleType(ScalingUtils.ScaleType.FIT_XY)
                .build()
            itemView.ivFav.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_FAV, adapterPosition
                )

            }
            itemView.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT, adapterPosition
                )
            }
        }
    }

}