package com.dartmic.yo2see.ui.addProduct.adapter

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.dartmic.yo2see.R
import com.dartmic.yo2see.model.Category_sub_subTosub.CategoryListItemData
import com.dartmic.yo2see.model.list_dropdown.GeneralListItem
import kotlinx.android.synthetic.main.item_custom_spinner.view.*

class CategoriesSpinnerAdapter(ctx: Activity, countries: ArrayList<CategoryListItemData>, var list: ArrayList<Boolean>) :
    ArrayAdapter<CategoryListItemData>(ctx, 0, countries) {
  //  private var updateValue=true
 //   private var updatePos=0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    override fun isEnabled(position: Int): Boolean {
          if(list.get(position)) {

              return true
          }
        return false

    }


    fun updateSpinner(flag:Boolean,pos: Int){


        list.set(pos,flag)

       // isEnabled()
        notifyDataSetChanged()

    }

    private fun createItemView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val value = getItem(position)?.categoryName

        val view = recycledView ?: LayoutInflater.from(context).inflate(
                R.layout.item_custom_spinner,parent,false)

        if(!list.get(position)) {
            // Set the disable item text color
         //   Log.w("updateValue and ", "updatePos= $updateValue and $updatePos")

            view.textViewValueName.setTextColor(Color.GRAY)
        }
        else {
            view.textViewValueName.setTextColor(Color.BLACK)
        }
        value?.let {
            view.textViewValueName.text = value
        }
        return view
    }
}
