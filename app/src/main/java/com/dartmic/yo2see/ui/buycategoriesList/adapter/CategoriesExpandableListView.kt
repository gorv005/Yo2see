package com.dartmic.yo2see.ui.buycategoriesList.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import com.dartmic.yo2see.R
import com.dartmic.yo2see.model.Category_sub_subTosub.SubCatListItem
import com.dartmic.yo2see.utils.Config

class CategoriesExpandableListView internal constructor(
    private val context: Context,
    private val titleList: List<SubCatListItem>,
    private var type: Int
) : BaseExpandableListAdapter() {

    override fun getChild(listPosition: Int, expandedListPosition: Int): String? {
        return this.titleList[listPosition].subToSubList?.get(expandedListPosition)?.subSubcategoryName
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(
        listPosition: Int,
        expandedListPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val expandedListText = getChild(listPosition, expandedListPosition) as String
        if (convertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.item_subcategory, null)
        }
        val expandedListTextView = convertView!!.findViewById<TextView>(R.id.expandedListItem)
        val llChildView = convertView!!.findViewById<LinearLayout>(R.id.llChildView)

        expandedListTextView.text = expandedListText


        when (type) {
            Config.Constants.SELL -> {
                llChildView.setBackgroundColor(ContextCompat.getColor(context, R.color.light_blue))

            }
            Config.Constants.RENT -> {
                llChildView.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.light_purple
                    )
                )

            }
            Config.Constants.BARTER -> {
                llChildView.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.light_yellow
                    )
                )

            }
            Config.Constants.POST -> {
                llChildView.setBackgroundColor(ContextCompat.getColor(context, R.color.light_blue))

            }
            Config.Constants.POST_AN_ADD -> {
                llChildView.setBackgroundColor(ContextCompat.getColor(context, R.color.light_red))

            }
        }

        return convertView
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return this.titleList[listPosition]?.subToSubList?.size!!
    }

    override fun getGroup(listPosition: Int): Any {
        return this.titleList[listPosition].subCategoryName
    }

    override fun getGroupCount(): Int {
        return this.titleList.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getGroupView(
        listPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val listTitle = getGroup(listPosition) as String
        if (convertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.item_list_category_name, null)
        }
        val listTitleTextView = convertView!!.findViewById<TextView>(R.id.listTitle)
        val rlParentView = convertView!!.findViewById<RelativeLayout>(R.id.rlParentView)
        val ivIndicator = convertView!!.findViewById<ImageView>(R.id.ivIndicator)

        if (isExpanded) {
            ivIndicator.setImageResource(R.drawable.ic_minus_icon)
        } else {
            ivIndicator.setImageResource(R.drawable.ic_plus_white)

        }
        when (type) {
            Config.Constants.SELL -> {
                rlParentView.setBackgroundResource(R.drawable.rounded_corners_app_blue_back)

            }
            Config.Constants.RENT -> {
                rlParentView.setBackgroundResource(R.drawable.rounded_corners_app_voilet_back)

            }
            Config.Constants.BARTER -> {
                rlParentView.setBackgroundResource(R.drawable.rounded_corners_app_yellow_back)

            }
            Config.Constants.POST -> {
                rlParentView.setBackgroundResource(R.drawable.rounded_corners_app_light_blue_back)

            }
            Config.Constants.POST_AN_ADD -> {
                rlParentView.setBackgroundResource(R.drawable.rounded_corners_app_red_back)

            }
        }
        listTitleTextView.text = listTitle
        return convertView
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }
}