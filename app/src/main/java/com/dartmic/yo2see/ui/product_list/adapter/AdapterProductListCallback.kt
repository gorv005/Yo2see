package com.dartmic.yo2see.ui.product_list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.dartmic.yo2see.model.EventsItems
import com.dartmic.yo2see.model.ProductItems
import com.dartmic.yo2see.model.product.ListingItem


class AdapterProductListCallback : DiffUtil.ItemCallback<ListingItem>() {

    override fun areItemsTheSame(oldItem: ListingItem, newItem: ListingItem) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: ListingItem, newItem: ListingItem): Boolean {
        return oldItem == newItem
    }
}