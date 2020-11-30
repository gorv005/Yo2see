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
import com.dartmic.yo2see.utils.Config
import com.facebook.drawee.drawable.ScalingUtils
import kotlinx.android.synthetic.main.item_events.view.*


class AdapterHomeEvents(
    private val adapterViewClickListener: AdapterViewClickListener<EventsItems>?,
    val activity: Activity, val back:Int
) : ListAdapter<EventsItems, AdapterHomeEvents.ViewHolder>(
    AdapterHomeEventsCallback()
)
{
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AdapterHomeEvents.ViewHolder {
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
        holder.bind(getItem(position), adapterViewClickListener,back)
    }
    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(allProducts: EventsItems, adapterViewClick: AdapterViewClickListener<EventsItems>?,back: Int) {

            itemView.tvEventName?.text = allProducts.text
            itemView.ivEvents.setImageResource(allProducts.image!!)
            itemView.rlCategoryBack.setBackgroundResource(back)
           /* ImageRequestManager.with(itemView.imgSource)
                .url(allProducts.image?.avatar?.url)
                .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                .build()*/
            itemView.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_CATEGORY, adapterPosition
                )
            }
        }
    }

}