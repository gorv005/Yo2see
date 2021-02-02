package com.dartmic.yo2see.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.dartmic.yo2see.model.EventsItems
import com.dartmic.yo2see.model.categories.CategoryListItem


class AdapterHomeDataCallback : DiffUtil.ItemCallback<CategoryListItem>() {

    override fun areItemsTheSame(oldItem: CategoryListItem, newItem: CategoryListItem) = oldItem.categoryId == newItem.categoryId

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: CategoryListItem, newItem: CategoryListItem): Boolean {
        return oldItem == newItem
    }
}