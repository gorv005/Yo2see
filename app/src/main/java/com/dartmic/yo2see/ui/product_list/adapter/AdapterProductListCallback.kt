package com.dartmic.yo2see.ui.product_list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.dartmic.yo2see.model.EventsItems
import com.dartmic.yo2see.model.ProductItems


class AdapterProductListCallback : DiffUtil.ItemCallback<ProductItems>() {

    override fun areItemsTheSame(oldItem: ProductItems, newItem: ProductItems) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: ProductItems, newItem: ProductItems): Boolean {
        return oldItem == newItem
    }
}