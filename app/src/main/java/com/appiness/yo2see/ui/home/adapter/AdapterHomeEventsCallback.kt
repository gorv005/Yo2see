package com.appiness.yo2see.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.appiness.yo2see.model.EventsItems



class AdapterHomeEventsCallback : DiffUtil.ItemCallback<EventsItems>() {

    override fun areItemsTheSame(oldItem: EventsItems, newItem: EventsItems) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: EventsItems, newItem: EventsItems): Boolean {
        return oldItem == newItem
    }
}