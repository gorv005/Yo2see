package com.dartmic.yo2see.ui.home.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dartmic.yo2see.R
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.managers.ImageRequestManager
import com.dartmic.yo2see.model.EventsItems
import com.dartmic.yo2see.model.categories.CategoryListItem
import com.dartmic.yo2see.utils.Config
import com.facebook.drawee.drawable.ScalingUtils
import kotlinx.android.synthetic.main.item_events.view.*


class AdapterHomeData(
    private val adapterViewClickListener: AdapterViewClickListener<CategoryListItem>?,
    val activity: Activity, val back: Int
) : ListAdapter<CategoryListItem, AdapterHomeData.ViewHolder>(
    AdapterHomeDataCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AdapterHomeData.ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_events, parent, false)

        /*  val displayMetrics = DisplayMetrics()
          activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
          val width = displayMetrics.widthPixels

          itemView.layoutParams = RecyclerView.LayoutParams(width - (width / 5), RecyclerView.LayoutParams.WRAP_CONTENT)
  */

        return ViewHolder(itemView, activity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), adapterViewClickListener, back)
    }

    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(
            allProducts: CategoryListItem,
            adapterViewClick: AdapterViewClickListener<CategoryListItem>?,
            back: Int
        ) {

            itemView.tvEventName?.text = allProducts.categoryName
            // itemView.ivEvents.setImageResource(allProducts.image!!)
            itemView.rlCategoryBack.setBackgroundResource(back)
            ImageRequestManager.with(itemView.ivEvents)
                .url(allProducts.catImage)
                .setPlaceholderImage(R.drawable.ic_clothing_white)
                .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                .build()
            itemView.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_CATEGORY, adapterPosition
                )
            }
        }
    }

}