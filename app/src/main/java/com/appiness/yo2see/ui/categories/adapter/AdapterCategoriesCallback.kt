package com.appiness.yo2see.ui.categories.adapter

import androidx.recyclerview.widget.DiffUtil
import com.appiness.yo2see.model.AdsItems
import com.appiness.yo2see.model.categories.CategoryListItem


class AdapterCategoriesCallback : DiffUtil.ItemCallback<CategoryListItem>() {

    override fun areItemsTheSame(oldItem: CategoryListItem, newItem: CategoryListItem) = oldItem.categoryName == newItem.categoryName

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: CategoryListItem, newItem: CategoryListItem): Boolean {
        return oldItem == newItem
    }
}