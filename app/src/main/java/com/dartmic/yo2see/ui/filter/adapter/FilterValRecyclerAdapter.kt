package com.dartmic.yo2see.ui.filter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.dartmic.yo2see.R
import com.dartmic.yo2see.model.filter.FilterDefaultMultipleListModel



import java.util.ArrayList

class FilterValRecyclerAdapter(
    private val context: FragmentActivity,
    private val resource: Int,
    private val filterModels: ArrayList<FilterDefaultMultipleListModel>,
    private val type: Int
) : RecyclerView.Adapter<FilterValRecyclerAdapter.ValueViewHolder>() {
    internal var mItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ValueViewHolder {
        val v = LayoutInflater.from(this.context)
            .inflate(resource, viewGroup, false)
        return ValueViewHolder(v, this.type)
    }

    override fun onBindViewHolder(personViewHolder: ValueViewHolder, i: Int) {
        personViewHolder.subCategoryName.text = filterModels[i].name
        personViewHolder.cbSelected.isChecked = filterModels[i].isChecked

    }

    override fun getItemCount(): Int {
        return filterModels.size
    }

    fun setItemSelected(position: Int) {
        if (position != -1) {
            filterModels[position].isChecked = !filterModels[position].isChecked
            notifyDataSetChanged()
        }
    }


    interface OnItemClickListener {

        fun onItemClick(view: View, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.mItemClickListener = mItemClickListener
    }

    inner class ValueViewHolder internal constructor(itemView: View, var type: Int) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var subCategoryName: TextView
        internal var cbSelected: CheckBox
        internal var colorView: View

        init {
            itemView.setOnClickListener(this)
            subCategoryName = itemView.findViewById<View>(R.id.txt_item_list_title) as TextView
            cbSelected = itemView.findViewById<View>(R.id.cbSelected) as CheckBox
            colorView = itemView.findViewById(R.id.colored_bar)
        }

        override fun onClick(v: View) {
            if (mItemClickListener != null) {
                mItemClickListener!!.onItemClick(v, position)
            }
        }
    }
}
