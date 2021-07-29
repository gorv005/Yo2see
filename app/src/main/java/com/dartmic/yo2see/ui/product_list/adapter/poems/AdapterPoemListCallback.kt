package com.dartmic.yo2see.ui.product_list.adapter.poems

import androidx.recyclerview.widget.DiffUtil
import com.dartmic.yo2see.model.product.event.ListingItemEvent
import com.dartmic.yo2see.model.product.job.ListingItemJob
import com.dartmic.yo2see.model.product_info.ListingItem


class AdapterPoemListCallback : DiffUtil.ItemCallback<ListingItemEvent>() {

    override fun areItemsTheSame(oldItem: ListingItemEvent, newItem: ListingItemEvent) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: ListingItemEvent, newItem: ListingItemEvent): Boolean {
        return oldItem == newItem
    }
}