package com.dartmic.yo2see.ui.product_list.adapter

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
import android.widget.ImageView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dartmic.yo2see.R
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.managers.ImageRequestManager
import com.dartmic.yo2see.model.product_info.ListingItem
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Config
import com.facebook.drawee.drawable.ScalingUtils
import kotlinx.android.synthetic.main.fragment_product_details.*
import kotlinx.android.synthetic.main.item_product.view.*


class AdapterProductList(
    private val adapterViewClickListener: AdapterViewClickListener<ListingItem>?,
    val activity: Activity, val back: Int, val type: Int
) : ListAdapter<ListingItem, AdapterProductList.ViewHolder>(
    AdapterProductListCallback()
) {
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
            allProducts: ListingItem,
            adapterViewClick: AdapterViewClickListener<ListingItem>?,
            back: Int, type: Int
        ) {

            if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() > 0) {

                itemView.ivStar1.setImageResource(R.drawable.ic_empty_star)
                itemView.ivStar1.visibility = View.VISIBLE
                itemView.ivStar2.setImageResource(R.drawable.ic_empty_star)
                itemView.ivStar2.visibility = View.VISIBLE
                itemView.ivStar3.setImageResource(R.drawable.ic_empty_star)
                itemView.ivStar3.visibility = View.VISIBLE
                itemView.ivStar4.setImageResource(R.drawable.ic_empty_star)
                itemView.ivStar4.visibility = View.VISIBLE
                itemView.ivStar5.setImageResource(R.drawable.ic_empty_star)
                itemView.ivStar5.visibility = View.VISIBLE

                if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 0.1) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_star_one_01)
                    itemView.ivStar1.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 0.2) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_star_two_01)
                    itemView.ivStar1.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 0.3) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_star_three_01)
                    itemView.ivStar1.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 0.4) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_star_four_01)
                    itemView.ivStar1.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 0.5) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_star_five_01)
                    itemView.ivStar1.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 0.6) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_star_six_01)
                    itemView.ivStar1.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 0.7) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_star_seven_01)
                    itemView.ivStar1.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 0.8) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_star_eight_01)
                    itemView.ivStar1.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 0.9) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_star_nine_01)
                    itemView.ivStar1.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 1.0) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 1.1) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_star_one_01)
                    itemView.ivStar2.visibility = View.VISIBLE

                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 1.2) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_star_two_01)
                    itemView.ivStar2.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 1.3) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_star_three_01)
                    itemView.ivStar2.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 1.4) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_star_four_01)
                    itemView.ivStar2.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 1.5) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_star_five_01)
                    itemView.ivStar2.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 1.6) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_star_six_01)
                    itemView.ivStar2.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 1.7) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_star_seven_01)
                    itemView.ivStar2.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 1.8) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_star_eight_01)
                    itemView.ivStar2.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 1.9) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_star_nine_01)
                    itemView.ivStar2.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 2.0) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 2.1) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_star_one_01)
                    itemView.ivStar3.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 2.2) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_star_two_01)
                    itemView.ivStar3.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 2.3) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_star_three_01)
                    itemView.ivStar3.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 2.4) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_star_four_01)
                    itemView.ivStar3.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 2.5) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_star_five_01)
                    itemView.ivStar3.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 2.6) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_star_six_01)
                    itemView.ivStar3.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 2.7) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_star_seven_01)
                    itemView.ivStar3.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 2.8) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_star_eight_01)
                    itemView.ivStar3.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 2.9) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_star_nine_01)
                    itemView.ivStar3.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 3.0) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar3.visibility = View.VISIBLE
                }


               else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 3.1) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar3.visibility = View.VISIBLE
                    itemView.ivStar4.setImageResource(R.drawable.ic_star_one_01)
                    itemView.ivStar4.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 3.2) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar3.visibility = View.VISIBLE
                    itemView.ivStar4.setImageResource(R.drawable.ic_star_two_01)
                    itemView.ivStar4.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 3.3) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar3.visibility = View.VISIBLE
                    itemView.ivStar4.setImageResource(R.drawable.ic_star_three_01)
                    itemView.ivStar4.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 3.4) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar3.visibility = View.VISIBLE
                    itemView.ivStar4.setImageResource(R.drawable.ic_star_four_01)
                    itemView.ivStar4.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 3.5) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar3.visibility = View.VISIBLE
                    itemView.ivStar4.setImageResource(R.drawable.ic_star_five_01)
                    itemView.ivStar4.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 3.6) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar3.visibility = View.VISIBLE
                    itemView.ivStar4.setImageResource(R.drawable.ic_star_six_01)
                    itemView.ivStar4.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 3.7) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar3.visibility = View.VISIBLE
                    itemView.ivStar4.setImageResource(R.drawable.ic_star_seven_01)
                    itemView.ivStar4.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 3.8) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar3.visibility = View.VISIBLE
                    itemView.ivStar4.setImageResource(R.drawable.ic_star_eight_01)
                    itemView.ivStar4.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 3.9) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar3.visibility = View.VISIBLE
                    itemView.ivStar4.setImageResource(R.drawable.ic_star_nine_01)
                    itemView.ivStar4.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 4.0) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar3.visibility = View.VISIBLE
                    itemView.ivStar4.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar4.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 4.1) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar3.visibility = View.VISIBLE
                    itemView.ivStar4.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar4.visibility = View.VISIBLE
                    itemView.ivStar5.setImageResource(R.drawable.ic_star_one_01)
                    itemView.ivStar5.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 4.2) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar3.visibility = View.VISIBLE
                    itemView.ivStar4.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar4.visibility = View.VISIBLE
                    itemView.ivStar5.setImageResource(R.drawable.ic_star_two_01)
                    itemView.ivStar5.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 4.3) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar3.visibility = View.VISIBLE
                    itemView.ivStar4.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar4.visibility = View.VISIBLE
                    itemView.ivStar5.setImageResource(R.drawable.ic_star_three_01)
                    itemView.ivStar5.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 4.4) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar3.visibility = View.VISIBLE
                    itemView.ivStar4.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar4.visibility = View.VISIBLE
                    itemView.ivStar5.setImageResource(R.drawable.ic_star_four_01)
                    itemView.ivStar5.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 4.5) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar3.visibility = View.VISIBLE
                    itemView.ivStar4.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar4.visibility = View.VISIBLE
                    itemView.ivStar5.setImageResource(R.drawable.ic_star_five_01)
                    itemView.ivStar5.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 4.6) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar3.visibility = View.VISIBLE
                    itemView.ivStar4.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar4.visibility = View.VISIBLE
                    itemView.ivStar5.setImageResource(R.drawable.ic_star_six_01)
                    itemView.ivStar5.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 4.7) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar3.visibility = View.VISIBLE
                    itemView.ivStar4.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar4.visibility = View.VISIBLE
                    itemView.ivStar5.setImageResource(R.drawable.ic_star_seven_01)
                    itemView.ivStar5.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 4.8) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar3.visibility = View.VISIBLE
                    itemView.ivStar4.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar4.visibility = View.VISIBLE
                    itemView.ivStar5.setImageResource(R.drawable.ic_star_eight_01)
                    itemView.ivStar5.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 4.9) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar3.visibility = View.VISIBLE
                    itemView.ivStar4.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar4.visibility = View.VISIBLE
                    itemView.ivStar5.setImageResource(R.drawable.ic_star_nine_01)
                    itemView.ivStar5.visibility = View.VISIBLE
                } else if (allProducts.AvgRatingUser != null && allProducts.AvgRatingUser.toDouble() <= 5.0) {
                    itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar1.visibility = View.VISIBLE
                    itemView.ivStar2.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar2.visibility = View.VISIBLE
                    itemView.ivStar3.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar3.visibility = View.VISIBLE
                    itemView.ivStar4.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar4.visibility = View.VISIBLE
                    itemView.ivStar5.setImageResource(R.drawable.ic_full_star)
                    itemView.ivStar5.visibility = View.VISIBLE
                }

            }
            itemView.tvModel?.text = allProducts?.listingTitle

            itemView.tvAddress?.text = HtmlCompat.fromHtml(
                "<u>" + allProducts.listingCity + "</u>",
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            itemView.tvDate?.text = allProducts.listingPublishDatetime.split(" ")[0]

            /*  val v = SpannableString("View " + allProducts.userName + "'s listing")
              v.setSpan(UnderlineSpan(), 0, v.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
              itemView.tvListing.text = v.toString()*/
            val v = "<u>View " + allProducts.userName + "'s listing</u>"
            itemView.tvListing.setText(HtmlCompat.fromHtml(v, HtmlCompat.FROM_HTML_MODE_LEGACY))

            itemView.ivFav.pressOnTouch(true)
            var s = "This product is available\nfor "
            var p = ""
            if (allProducts?.isSell != null && allProducts?.isSell?.equals("yes")) {
                p =
                    "<b>Sell " + AndroidUtils.getCurrencySymbol(AndroidUtils.getCurrencyCode()) + "</b>" + allProducts?.listingPrice
                s = s + "Buy"
                itemView.ivBuy.visibility = View.VISIBLE
                if (allProducts?.isRent != null && allProducts?.isRent?.equals("yes") || allProducts?.isBarter != null && allProducts?.isBarter?.equals(
                        "yes"
                    )
                ) {
                    p = p + "<br />"
                }
            } else {
                itemView.ivBuy.visibility = View.GONE
            }
            if (allProducts?.isRent != null && allProducts?.isRent?.equals("yes")) {
                p =
                    p + "<b>Rent " + AndroidUtils.getCurrencySymbol(AndroidUtils.getCurrencyCode()) + "</b>" + allProducts?.rentType?.get(
                        0
                    )?.payment + "/" + allProducts?.rentType?.get(0)?.rentType
                s = s + ", Rent"
                itemView.ivRent.visibility = View.VISIBLE
                if (allProducts?.isBarter != null && allProducts?.isBarter?.equals("yes")) {
                    p = p + "<br />"
                }
            } else {
                itemView.ivRent.visibility = View.GONE
            }
            if (allProducts?.isBarter != null && allProducts?.isBarter?.equals("yes")) {
                p = p + "<b>Barter</b> " + allProducts?.barterText
                s = s + ", Barter"
                itemView.ivBarter.visibility = View.VISIBLE
            } else {
                itemView.ivBarter.visibility = View.GONE

            }

            itemView.tvPrice.setText(HtmlCompat.fromHtml(p, HtmlCompat.FROM_HTML_MODE_LEGACY))

            itemView.tvProductAvailability.setText(s)
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
            itemView.ivBuy.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT,
                    getClickPosition(allProducts, "S")
                )
            }
            itemView.ivRent.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT,
                    getClickPosition(allProducts, "R")
                )
            }
            itemView.ivBarter.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT,
                    getClickPosition(allProducts, "B")
                )
            }
            itemView.ivShare.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_SHARE, getClickPosition(allProducts, "SH")
                )
            }

        }

        fun getClickPosition(product: ListingItem, type: String): Int {
            if (product?.isSell.equals("yes") && type.equals("S")) {
                return 0
            } else if (product?.isSell.equals("yes") && product?.isRent.equals("yes") && type.equals(
                    "R"
                )
            ) {
                return 1
            } else if (product?.isSell.equals("yes") && product?.isRent.equals("yes") && product?.isBarter.equals(
                    "yes"
                ) && type.equals("B")
            ) {
                return 2
            } else if (!product?.isSell.equals("yes") && product?.isRent.equals("yes") && type.equals(
                    "R"
                )
            ) {
                return 0
            } else if (!product?.isSell.equals("yes") && product?.isRent.equals("yes") && product?.isBarter.equals(
                    "yes"
                ) && type.equals("B")
            ) {
                return 1
            } else if (!product?.isSell.equals("yes") && !product?.isRent.equals("yes") && product?.isBarter.equals(
                    "yes"
                ) && type.equals("B")
            ) {
                return 0
            }
            return 0
        }


        fun star(ivStar: ImageView, rate: Double) {
            if (rate != null && rate <= 0.1) {
                itemView.ivStar1.setImageResource(R.drawable.ic_star_one_01)
                itemView.ivStar1.visibility = View.VISIBLE
            } else if (rate != null && rate <= 0.2) {
                itemView.ivStar1.setImageResource(R.drawable.ic_star_two_01)
                itemView.ivStar1.visibility = View.VISIBLE
            } else if (rate != null && rate <= 0.3) {
                itemView.ivStar1.setImageResource(R.drawable.ic_star_three_01)
                itemView.ivStar1.visibility = View.VISIBLE
            } else if (rate != null && rate <= 0.4) {
                itemView.ivStar1.setImageResource(R.drawable.ic_star_four_01)
                itemView.ivStar1.visibility = View.VISIBLE
            } else if (rate != null && rate <= 0.5) {
                itemView.ivStar1.setImageResource(R.drawable.ic_star_five_01)
                itemView.ivStar1.visibility = View.VISIBLE
            } else if (rate != null && rate <= 0.6) {
                itemView.ivStar1.setImageResource(R.drawable.ic_star_six_01)
                itemView.ivStar1.visibility = View.VISIBLE
            } else if (rate != null && rate <= 0.7) {
                itemView.ivStar1.setImageResource(R.drawable.ic_star_seven_01)
                itemView.ivStar1.visibility = View.VISIBLE
            } else if (rate != null && rate <= 0.8) {
                itemView.ivStar1.setImageResource(R.drawable.ic_star_eight_01)
                itemView.ivStar1.visibility = View.VISIBLE
            } else if (rate != null && rate <= 0.9) {
                itemView.ivStar1.setImageResource(R.drawable.ic_star_nine_01)
                itemView.ivStar1.visibility = View.VISIBLE
            } else if (rate != null && rate <= 1.0) {
                itemView.ivStar1.setImageResource(R.drawable.ic_full_star)
                itemView.ivStar1.visibility = View.VISIBLE
            }
        }

    }


}