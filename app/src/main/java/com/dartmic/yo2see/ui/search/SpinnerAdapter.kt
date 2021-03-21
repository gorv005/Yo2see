package com.dartmic.yo2see.ui.search

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.dartmic.yo2see.R
import kotlinx.android.synthetic.main.item_custom_spinner.view.*

class SpinnerAdapter(ctx: Activity, countries: Array<String>) : ArrayAdapter<String>(ctx, 0, countries) {

    var updateValue=true
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            return createItemView(position, convertView, parent)
        }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            return createItemView(position, convertView, parent)
        }

    override fun isEnabled(position: Int): Boolean {

        if(position==1){
          return updateValue
        }
        return true
    }


    fun updateSpinner(flag:Boolean){
        updateValue=flag
      isEnabled(1)
        notifyDataSetChanged()
    }

    private fun createItemView(position: Int, recycledView: View?, parent: ViewGroup): View {
            val value = getItem(position)

            val view = recycledView ?: LayoutInflater.from(context).inflate(
                    R.layout.item_custom_spinner,parent,false)
        if(!updateValue && position==1) {
            // Set the disable item text color
            view.textViewValueName.setTextColor(Color.GRAY);
        }
        else {
            view.textViewValueName.setTextColor(Color.BLACK);
        }
            value?.let {
                view.textViewValueName.text = value
            }
            return view
        }
    }
