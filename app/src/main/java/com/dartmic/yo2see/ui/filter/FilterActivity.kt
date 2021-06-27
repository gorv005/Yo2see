package com.dartmic.yo2see.ui.filter

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.dartmic.yo2see.R
import com.dartmic.yo2see.model.filter.FilterDefaultMultipleListModel
import com.dartmic.yo2see.model.filter.MainFilterModel
import com.dartmic.yo2see.ui.filter.adapter.FilterRecyclerAdapter
import com.dartmic.yo2see.ui.filter.adapter.FilterValRecyclerAdapter
import com.dartmic.yo2see.utils.AndroidUtils
import com.stfalcon.pricerangebar.model.BarEntry
import kotlinx.android.synthetic.main.activity_filter.*
import java.util.*

class FilterActivity : AppCompatActivity() {
    private var adapter: FilterRecyclerAdapter? = null
    private var filterValAdapter: FilterValRecyclerAdapter? = null
    internal var minPrice: String? = "0"
    internal var maxPrice: String? = "10000"
    private var rangeBarEntries = java.util.ArrayList<BarEntry>()
    internal var minPriceS: String? = "0"
    internal var maxPriceS: String? = "10000"
    private var rootFilters: List<String>? = null

    private var categoryMultipleListModels = ArrayList<FilterDefaultMultipleListModel>()
    private var brandMultipleListModels = ArrayList<FilterDefaultMultipleListModel>()

    private val filterModels = ArrayList<MainFilterModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        tv_tool_title.text = getString(R.string.filter_by)
        initRangeBar()

        rootFilters = Arrays.asList(*this.resources.getStringArray(R.array.filter_type))
        for (i in rootFilters!!.indices) {
            /* Create new MainFilterModel object and set array value to @model
             * Description:
             * -- Class: MainFilterModel.java
             * -- Package:main.shop.javaxerp.com.shoppingapp.model
             * */
            val model = MainFilterModel()
            /*Title for list item*/
            model.title = rootFilters!![i]
            /*Subtitle for list item*/
            model.setSub("All")
            /*Example:
             * --------------------------------------------
             * Brand => title
             * All => subtitle
             * --------------------------------------------
             * Color => title
             * All => subtitle
             * --------------------------------------------
             * */

            /*add MainFilterModel object @model to ArrayList*/
            filterModels.add(model)
        }
        adapter = FilterRecyclerAdapter(this, R.layout.item_filter_type, filterModels)
        filter_dialog_listview!!.adapter = adapter
        filter_dialog_listview!!.layoutManager = LinearLayoutManager(this)
        filter_dialog_listview!!.setHasFixedSize(true)

        filterValAdapter = FilterValRecyclerAdapter(
            this,
            R.layout.item_category_filter,
            categoryMultipleListModels,
            MainFilterModel.Model.CATEGORY
        )
        filter_value_listview!!.adapter = filterValAdapter
        filter_value_listview!!.layoutManager = LinearLayoutManager(this)
        filter_value_listview!!.setHasFixedSize(true)
        adapter!!.setOnItemClickListener(object : FilterRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                filterItemListClicked(position, v)
                adapter!!.setItemSelected(position)
            }
        })

        filterItemListClicked(0, null)
        adapter!!.setItemSelected(0)

    }




    private fun filterItemListClicked(position: Int, v: View?) {
        if (position == 0) {
            rlRange.visibility = View.GONE
            filter_value_listview.visibility = View.VISIBLE
            filterValAdapter = FilterValRecyclerAdapter(
                this,
                R.layout.item_category_filter,
                categoryMultipleListModels,
                MainFilterModel.Model.CATEGORY
            )
        } else if (position == 1) {
            rlRange.visibility = View.GONE
            filter_value_listview.visibility = View.VISIBLE
            filterValAdapter = FilterValRecyclerAdapter(
                this,
                R.layout.item_category_filter,
                brandMultipleListModels,
                MainFilterModel.Model.BRAND
            )
        } else {
            /*filterValAdapter = FilterValRecyclerAdapter(
                this,
                R.layout.item_category_filter,
                priceMultipleListModels,
                MainFilterModel.Model.PRICE
            )*/
            rlRange.visibility = View.VISIBLE
            filter_value_listview.visibility = View.GONE

        }

        filter_value_listview!!.adapter = filterValAdapter

        filterValAdapter!!.setOnItemClickListener(object :
            FilterValRecyclerAdapter.OnItemClickListener {

            override fun onItemClick(view: View, position: Int) {
                filterValitemListClicked(position)
            }
        })
        filterValAdapter!!.notifyDataSetChanged()
    }


    private fun filterValitemListClicked(position: Int) {
        filterValAdapter!!.setItemSelected(position)
    }

    private fun initRangeBar() {
        rangeBar.onRangeChanged = { leftPinValue, rightPinValue ->
            rangeBarLeftValue.text =
                leftPinValue.toString()
            rangeBarRightValue.text =
                rightPinValue.toString()
            minPrice = leftPinValue
            maxPrice = rightPinValue

        }
        rangeBar.onLeftPinChanged = { index, leftPinValue ->
            Log.d(this.javaClass.canonicalName, "$index $leftPinValue")
        }
        rangeBar.onRightPinChanged = { index, rightPinValue ->
            Log.d(this.javaClass.canonicalName, "$index $rightPinValue")
        }
        rangeBar.onSelectedEntriesSizeChanged = { selectedEntriesSize ->
            Log.d(this.javaClass.canonicalName, "$selectedEntriesSize")
        }

        for (x in 0..10000) {
            rangeBarEntries.add(BarEntry(x.toFloat(), x.toFloat()))
        }

        rangeBarLeftValue.text = 0.toString()
        rangeBarRightValue.text = 10000.toString()
        rangeBar.setEntries(rangeBarEntries)
        rangeBar.setSelectedEntries(minPriceS!!.toInt(), maxPriceS!!.toInt())

    }
    companion object {
        const val KEY_CATEGORY_LIST = "KEY_CATEGORY_LIST"
        const val KEY_CAT_MODEL = "KEY_CAT_MODEL"
        const val KEY_BRAND_LIST = "KEY_BRAND_LIST"
        const val KEY_BRAND_MODEL = "KEY_BRAND_MODEL"
        const val KEY_MIN_PRICE = "KEY_MIN_PRICE"
        const val KEY_MAX_PRICE = "KEY_MAX_PRICE"
        const val KEY_SUB_ID = "KEY_SUB_ID"
        const val KEY_SORT_BY = "KEY_SORT_BY"
        const val KEY_COLUMN = "KEY_COLUMN"
        const val KEY_OFFER_ID = "KEY_OFFER_ID"

        fun getIntent(
            context: Context
        ): Intent? {
            val intent = Intent(context, FilterActivity::class.java)
            return intent
        }
    }

}