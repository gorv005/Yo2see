package com.dartmic.yo2see.ui.product_list.adapter.business

import android.app.Activity
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dartmic.yo2see.R
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.managers.ImageRequestManager
import com.dartmic.yo2see.model.product.event.ListingItemEvent
import com.dartmic.yo2see.model.product.job.ListingItemJob
import com.dartmic.yo2see.ui.product_list.adapter.poems.AdapterPoemListCallback
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Config
import com.facebook.drawee.drawable.ScalingUtils
import kotlinx.android.synthetic.main.item_poem.view.*
import kotlinx.android.synthetic.main.item_poem.view.ivFav
import kotlinx.android.synthetic.main.item_poem.view.ivShare
import kotlinx.android.synthetic.main.item_poem.view.tvAddress
import kotlinx.android.synthetic.main.item_poem.view.tvListing
import kotlinx.android.synthetic.main.item_poem.view.tvPrice
import kotlinx.android.synthetic.main.item_product.view.*


class AdapterBusinessList(
    private val adapterViewClickListener: AdapterViewClickListener<ListingItemEvent>?,
    val activity: Activity, val back: Int, val type: Int
) : ListAdapter<ListingItemEvent, AdapterBusinessList.ViewHolder>(
    AdapterPoemListCallback()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        p1: Int
    ): AdapterBusinessList.ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_business, parent, false)

        /*  val displayMetrics = DisplayMetrics()
          activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
          val width = displayMetrics.widthPixels

          itemView.layoutParams = RecyclerView.LayoutParams(width - (width / 5), RecyclerView.LayoutParams.WRAP_CONTENT)
  */

        return ViewHolder(itemView, activity)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.clearAnimation()

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), adapterViewClickListener, back, type)
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
            allProducts: ListingItemEvent,
            adapterViewClick: AdapterViewClickListener<ListingItemEvent>?,
            back: Int, type: Int
        ) {

            itemView.tvTitle?.text = allProducts?.eTitle
            itemView.tvProductDescription?.text = allProducts?.eDescription

            itemView.tvAddress?.text = HtmlCompat.fromHtml(
                "<u>" + allProducts?.eCity + ", " + allProducts?.eCity + "</u>",
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            itemView.tvPrice?.text =
                AndroidUtils.getCurrencySymbol(AndroidUtils.getCurrencyCode()) + allProducts?.ePrice
            // "From " + allProducts?.ePublishDatetime + " " + "To " + allProducts?.eExpiryDatetime

            /*  val v = SpannableString("View " + allProducts.userName + "'s listing")
              v.setSpan(UnderlineSpan(), 0, v.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
              itemView.tvListing.text = v.toString()*/
            var v = "<u>View listing</u>"

            if (allProducts?.categoryName?.contains("Ads for Business")!!) {
                v = "<u>View Business</u>"
            } else if (allProducts?.categoryName?.contains("Blog")!!) {
                v = "<u>View Blog</u>"
                itemView.tvPrice.visibility=View.GONE
            }
            itemView.tvListing.setText(HtmlCompat.fromHtml(v, HtmlCompat.FROM_HTML_MODE_LEGACY))

            itemView.ivFav.pressOnTouch(true)


            if (allProducts?.userFavorite == 1) {
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
                    Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT, 0
                )
            }

            itemView.ivShare.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_SHARE, adapterPosition
                )
            }

        }

    }


}