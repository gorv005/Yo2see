package com.dartmic.yo2see.ui.addProduct.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dartmic.yo2see.R
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.model.EventsItems
import com.dartmic.yo2see.model.add_product.ImageItem
import com.dartmic.yo2see.utils.Config
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterChatCallback
import com.pepsidrc.ui.navigation.ui.home.adapter.AdapterImageCallback
import kotlinx.android.synthetic.main.item_image.view.*
import kotlinx.android.synthetic.main.item_chat.view.*
import org.jetbrains.anko.backgroundColor


class AdapterImage(
    private val adapterViewClickListener: AdapterViewClickListener<ImageItem>?,
    val activity: Activity
) : ListAdapter<ImageItem, RecyclerView.ViewHolder>(
    AdapterImageCallback()
) {
    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }
    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {

        if (p1 == VIEW_TYPE_ONE) {
            val itemView2=
                LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
            return ViewHolder1(itemView2, activity)
        }else {
            val itemView2 =
                LayoutInflater.from(parent.context).inflate(R.layout.item_add_image, parent, false)

            return ViewHolder2(itemView2, activity)
        }
        // return ViewHolder1(itemView, activity)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
      //  holder.bind(getItem(position), position, adapterViewClickListener)
        if (getItem(position).viewType === VIEW_TYPE_ONE) {
            (holder as ViewHolder1).bind(getItem(position), position, adapterViewClickListener)
        } else {
            (holder as ViewHolder2).bind(getItem(position), position, adapterViewClickListener)
        }
    }

    class ViewHolder1(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(
            obj: ImageItem,
            position: Int,
            adapterViewClick: AdapterViewClickListener<ImageItem>?
        ) {
            itemView.ivProduct.setImageURI(obj.fileUrl)


            itemView.ivRemove.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    obj,
                    Config.AdapterClickViewTypes.CLICK_REMOVE_IMAGE, position
                )
            }
        }
    }
    class ViewHolder2(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(
            obj: ImageItem,
            position: Int,
            adapterViewClick: AdapterViewClickListener<ImageItem>?
        ) {


            itemView.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    obj,
                    Config.AdapterClickViewTypes.CLICK_ADD_IMAGE, position
                )
            }
        }
    }

}