package com.dartmic.yo2see.ui.categories.adapter

import androidx.recyclerview.widget.DiffUtil
import com.dartmic.yo2see.model.categories.FeatureProductListItem


class AdapterFeatureCallback : DiffUtil.ItemCallback<FeatureProductListItem>() {

    override fun areItemsTheSame(oldItem: FeatureProductListItem, newItem: FeatureProductListItem) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: FeatureProductListItem, newItem: FeatureProductListItem): Boolean {
        return oldItem == newItem
    }
}