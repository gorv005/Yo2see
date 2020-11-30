package com.dartmic.yo2see.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.dartmic.yo2see.model.AdsItems
import com.dartmic.yo2see.model.EventsItems



class AdapterHomeAdsCallback : DiffUtil.ItemCallback<AdsItems>() {

    override fun areItemsTheSame(oldItem: AdsItems, newItem: AdsItems) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: AdsItems, newItem: AdsItems): Boolean {
        return oldItem == newItem
    }
}