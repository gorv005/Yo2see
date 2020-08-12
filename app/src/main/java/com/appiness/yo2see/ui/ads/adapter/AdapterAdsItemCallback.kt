package com.appiness.yo2see.ui.ads.adapter

import androidx.recyclerview.widget.DiffUtil
import com.appiness.yo2see.model.AdsItems


class AdapterAdsItemCallback : DiffUtil.ItemCallback<AdsItems>() {

    override fun areItemsTheSame(oldItem: AdsItems, newItem: AdsItems) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: AdsItems, newItem: AdsItems): Boolean {
        return oldItem == newItem
    }
}