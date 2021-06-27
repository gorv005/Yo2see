package com.pepsidrc.ui.navigation.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.dartmic.yo2see.model.EventsItems
import com.dartmic.yo2see.model.add_product.ImageItem


class AdapterImageCallback : DiffUtil.ItemCallback<ImageItem>() {

    override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
        return oldItem == newItem
    }
}