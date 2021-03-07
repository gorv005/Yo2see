package com.dartmic.yo2see.ui.categories.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dartmic.yo2see.R
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.managers.ImageRequestManager
import com.dartmic.yo2see.model.Category_sub_subTosub.CategoryListItemData

import com.dartmic.yo2see.model.categories.CategoryListItem
import com.dartmic.yo2see.utils.Config
import com.facebook.drawee.drawable.ScalingUtils
import kotlinx.android.synthetic.main.ads_item.view.*
import kotlinx.android.synthetic.main.item_ads.view.*
import kotlinx.android.synthetic.main.item_ads.view.rlCategoryBack
import kotlinx.android.synthetic.main.item_ads.view.tvEventName
import kotlinx.android.synthetic.main.item_events.view.*


class AdapterCategories(
    private val adapterViewClickListener: AdapterViewClickListener<CategoryListItemData>?,
    val activity: Activity, val back:Int
) : ListAdapter<CategoryListItemData, AdapterCategories.ViewHolder>(
    AdapterCategoriesCallback()
)
{
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_ads, parent, false)

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
        holder.bind(getItem(position), position,adapterViewClickListener,back)
    }
    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(product: CategoryListItemData,position: Int, adapterViewClick: AdapterViewClickListener<CategoryListItemData>?, back: Int) {

                itemView.tvEventName.text=product.categoryName
            itemView.rlCategoryBack.setBackgroundResource(back)

            itemView.gridview_text?.text = product.categoryName
               /* ImageRequestManager.with(itemView.ivCategory).url(product.catImage)
                    .setPlaceholderImage(R.drawable.ic_clothing_white)
                    .setScaleType(ScalingUtils.ScaleType.FIT_XY)
                    .build()*/
            Glide.with(activity).load(product.catImage)
                .placeholder(R.drawable.ic_clothing_white)
                .into(itemView.ivCategory)
            itemView.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    product,
                    Config.AdapterClickViewTypes.CLICK_VIEW_CATEGORY, adapterPosition
                )
            }

        }
    }

}