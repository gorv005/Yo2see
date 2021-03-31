package com.dartmic.yo2see.ui.chat_list.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dartmic.yo2see.R
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.model.EventsItems
import com.dartmic.yo2see.utils.Config
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterChatCallback
import kotlinx.android.synthetic.main.item_chat.view.*
import org.jetbrains.anko.backgroundColor


class AdapterChat(
    private val adapterViewClickListener: AdapterViewClickListener<EventsItems>?,
    val activity: Activity
) : ListAdapter<EventsItems, AdapterChat.ViewHolder>(
    AdapterChatCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_chat, parent, false)
        return ViewHolder(itemView, activity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position, adapterViewClickListener)
    }

    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(
            obj: EventsItems,
            position: Int,
            adapterViewClick: AdapterViewClickListener<EventsItems>?
        ) {
            itemView.tvUserName.text = obj.text
            itemView.llBack.setBackgroundColor(obj?.image!!)

            itemView.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    obj,
                    Config.AdapterClickViewTypes.CLICK_VIEW_FAV, position
                )
            }
        }
    }

}