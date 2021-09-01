package com.pepsidrc.ui.navigation.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.dartmic.yo2see.model.EventsItems
import com.dartmic.yo2see.model.my_history.MyListingItem


class AdapterPostHistoryCallback : DiffUtil.ItemCallback<MyListingItem>() {

    override fun areItemsTheSame(oldItem: MyListingItem, newItem: MyListingItem) = oldItem.tableId == newItem.tableId

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: MyListingItem, newItem: MyListingItem): Boolean {
        return oldItem == newItem
    }
}