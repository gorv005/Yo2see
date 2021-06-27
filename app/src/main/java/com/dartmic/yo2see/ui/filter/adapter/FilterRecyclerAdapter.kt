package com.dartmic.yo2see.ui.filter.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.dartmic.yo2see.R
import com.dartmic.yo2see.model.filter.MainFilterModel


import java.util.ArrayList

class FilterRecyclerAdapter(
    private val context: FragmentActivity,
    private val resource: Int,
    internal var filterModels: ArrayList<MainFilterModel>
) : RecyclerView.Adapter<FilterRecyclerAdapter.PersonViewHolder>() {
    internal var mItemClickListener: OnItemClickListener? = null


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): PersonViewHolder {
        val v = LayoutInflater.from(this.context)
            .inflate(resource, viewGroup, false)
        return PersonViewHolder(v)
    }

    override fun onBindViewHolder(filterViewHolder: PersonViewHolder, i: Int) {

        filterViewHolder.parentView.isSelected = filterModels[i].isSelected

        if (filterViewHolder.filterName.isSelected) {
            if (filterModels[i].title == "Category") {
                   filterViewHolder.filterName.setTextColor(Color.parseColor("#ef4076"))
                filterViewHolder.filterName.text = "Category"
            } else if (filterModels[i].title == "Brand") {
                filterViewHolder.filterName.setTextColor(Color.parseColor("#ef4076"))
                filterViewHolder.filterName.text = "Brand"
            } else if (filterModels[i].title == "Price") {
                filterViewHolder.filterName.setTextColor(Color.parseColor("#ef4076"))
                filterViewHolder.filterName.text = "Price"
            }
        } else {
            if (filterModels[i].title == "Category") {
                    filterViewHolder.filterName.setTextColor(Color.parseColor("#000000"))
                filterViewHolder.filterName.text = "Category"
            } else if (filterModels[i].title == "Brand") {
                      filterViewHolder.filterName.setTextColor(Color.parseColor("#000000"))
                filterViewHolder.filterName.text = "Brand"
            } else if (filterModels[i].title == "Price") {
                      filterViewHolder.filterName.setTextColor(Color.parseColor("#000000"))
                filterViewHolder.filterName.text = "Price"
            }
        }


    }

    override fun getItemCount(): Int {
        return filterModels.size
    }

    fun setItemSelected(position: Int) {
        for (filterModel in filterModels) {
            filterModel.isSelected=false


        }
        if (position != -1) {
            filterModels[position].isSelected=true
            notifyDataSetChanged()
        }

    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.mItemClickListener = mItemClickListener
    }


    interface OnItemClickListener {

        fun onItemClick(view: View, position: Int)


    }

    inner class PersonViewHolder internal constructor(var parentView: View) :
        RecyclerView.ViewHolder(parentView), View.OnClickListener {

        internal var filterName: TextView

        init {
            parentView.setOnClickListener(this)
            filterName = parentView.findViewById<View>(R.id.tv_filter_type_name) as TextView

        }

        override fun onClick(v: View) {
            if (mItemClickListener != null) {
                mItemClickListener!!.onItemClick(v, position)
            }
        }
    }
}


