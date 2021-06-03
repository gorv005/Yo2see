package com.dartmic.yo2see.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.dartmic.yo2see.model.Category_sub_subTosub.CategoryListItemData
import com.dartmic.yo2see.model.EventsItems
import com.dartmic.yo2see.model.categories.CategoryListItem


class AdapterHomeDataCallback : DiffUtil.ItemCallback<CategoryListItemData>() {

    override fun areItemsTheSame(oldItem: CategoryListItemData, newItem: CategoryListItemData) = oldItem.categoryId == newItem.categoryId

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: CategoryListItemData, newItem: CategoryListItemData): Boolean {
        return oldItem == newItem
    }
}