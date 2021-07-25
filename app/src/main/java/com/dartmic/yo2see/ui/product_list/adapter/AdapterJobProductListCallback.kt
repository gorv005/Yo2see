package com.dartmic.yo2see.ui.product_list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.dartmic.yo2see.model.product.job.ListingItemJob
import com.dartmic.yo2see.model.product_info.ListingItem


class AdapterJobProductListCallback : DiffUtil.ItemCallback<ListingItemJob>() {

    override fun areItemsTheSame(oldItem: ListingItemJob, newItem: ListingItemJob) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: ListingItemJob, newItem: ListingItemJob): Boolean {
        return oldItem == newItem
    }
}