package com.dartmic.yo2see.ui.post_history.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dartmic.yo2see.R
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.model.EventsItems
import com.dartmic.yo2see.model.my_history.MyListingItem
import com.dartmic.yo2see.utils.Config
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterChatCallback
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterPostHistoryCallback
import kotlinx.android.synthetic.main.item_chat.view.*
import kotlinx.android.synthetic.main.item_chat.view.llBack
import kotlinx.android.synthetic.main.item_post_history.view.*
import org.jetbrains.anko.backgroundColor


class AdapterPostHistory(
    private val adapterViewClickListener: AdapterViewClickListener<MyListingItem>?,
    val activity: Activity
) : ListAdapter<MyListingItem, AdapterPostHistory.ViewHolder>(
    AdapterPostHistoryCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_post_history, parent, false)
        return ViewHolder(itemView, activity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position, adapterViewClickListener)
    }

    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(
            obj: MyListingItem,
            position: Int,
            adapterViewClick: AdapterViewClickListener<MyListingItem>?
        ) {
            itemView.tvPostTitle.text = obj.title
            itemView.tvPostDate.text = obj.description

            itemView.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    obj,
                    Config.AdapterClickViewTypes.CLICK_VIEW_DELETE, position
                )
            }
        }
    }

}