package com.appiness.yo2see.view.ads.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.appiness.yo2see.R
import com.appiness.yo2see.callbacks.AdapterViewClickListener
import com.appiness.yo2see.manager.ImageRequestManager
import com.appiness.yo2see.model.AdsItems
import com.facebook.drawee.drawable.ScalingUtils
import kotlinx.android.synthetic.main.ads_item.view.*


class AdapterAdsItems(
    private val adapterViewClickListener: AdapterViewClickListener<AdsItems>?,
    val activity: Activity
) : ListAdapter<AdsItems, AdapterAdsItems.ViewHolder>(
    AdapterAdsItemCallback()
)
{
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AdapterAdsItems.ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.ads_item, parent, false)

      /*  val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels

        itemView.layoutParams = RecyclerView.LayoutParams(width - (width / 5), RecyclerView.LayoutParams.WRAP_CONTENT)
*/

        return ViewHolder(itemView, activity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position,adapterViewClickListener)
    }
    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(product: AdsItems,position: Int, adapterViewClick: AdapterViewClickListener<AdsItems>?) {

            var p=position+1
            itemView.gridview_text?.text = "Adv"+" "+p
                ImageRequestManager.with(itemView.gridview_image)
                    .setPlaceholderImage(R.drawable.yo2see1)
                    .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                    .build()

            itemView.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    product,
                    5, adapterPosition
                )
            }

        }
    }

}