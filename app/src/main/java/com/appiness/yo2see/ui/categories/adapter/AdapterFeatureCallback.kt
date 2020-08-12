package com.appiness.yo2see.ui.categories.adapter

import androidx.recyclerview.widget.DiffUtil
import com.appiness.yo2see.model.AdsItems
import com.appiness.yo2see.model.categories.CategoryListItem
import com.appiness.yo2see.model.categories.FeatureProductListItem


class AdapterFeatureCallback : DiffUtil.ItemCallback<FeatureProductListItem>() {

    override fun areItemsTheSame(oldItem: FeatureProductListItem, newItem: FeatureProductListItem) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: FeatureProductListItem, newItem: FeatureProductListItem): Boolean {
        return oldItem == newItem
    }
}