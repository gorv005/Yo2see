package com.appiness.yo2see.ui.categories.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.appiness.yo2see.R
import com.appiness.yo2see.callbacks.AdapterFeatureViewClickListener
import com.appiness.yo2see.callbacks.AdapterViewClickListener
import com.appiness.yo2see.managers.ImageRequestManager

import com.appiness.yo2see.model.AdsItems
import com.appiness.yo2see.model.categories.CategoryListItem
import com.appiness.yo2see.model.categories.FeatureProductListItem
import com.facebook.drawee.drawable.ScalingUtils
import kotlinx.android.synthetic.main.ads_item.view.*
import kotlinx.android.synthetic.main.feature_item.view.*


class AdapterFeature(
    private val adapterViewClickListener: AdapterFeatureViewClickListener<FeatureProductListItem>?,
    val activity: Activity
) : ListAdapter<FeatureProductListItem, AdapterFeature.ViewHolder>(
    AdapterFeatureCallback()
)
{
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.feature_item, parent, false)

      /*  val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels

        itemView.layoutParams = RecyclerView.LayoutParams(width - (width / 5), RecyclerView.LayoutParams.WRAP_CONTENT)
*/

        return ViewHolder(
            itemView,
            activity
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position,adapterViewClickListener)
    }
    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(product: FeatureProductListItem,position: Int, adapterViewClick: AdapterFeatureViewClickListener<FeatureProductListItem>?) {


            itemView.tvPrice?.text = "RS."+product.listingPrice
            itemView.tvName?.text = product.categoryName
            itemView.tvDesc?.text = product.listingDescription
            itemView.tvAddress?.text = product.listingAddress

            ImageRequestManager.with(itemView.ivProduct).url(product.listingImage)
                    .setPlaceholderImage(R.drawable.yo2see1)
                    .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                    .build()

            itemView.setOnClickListener {
                adapterViewClick?.onClickFeatureAdapterView(
                    product,
                    5, adapterPosition
                )
            }

        }
    }

}