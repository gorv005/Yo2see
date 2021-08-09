package com.dartmic.yo2see.ui.search

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseActivity
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.model.Category_sub_subTosub.CategoryListItemData
import com.dartmic.yo2see.model.profile.UserList
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.addProduct.adapter.CategoriesSpinnerAdapter
import com.dartmic.yo2see.ui.addProduct.adapter.DigitalSpinnerAdapter
import com.dartmic.yo2see.ui.categories.CategoriesViewModel
import com.dartmic.yo2see.ui.home.adapter.AdapterHomeData
import com.dartmic.yo2see.ui.location.MapsActivity
import com.dartmic.yo2see.ui.profile.UpdateProfileActivity
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_serach.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_set_location_info.*

class SerachActivity : BaseActivity<CategoriesViewModel>(CategoriesViewModel::class),
    AdapterView.OnItemSelectedListener {
    private lateinit var categoryAdapter: SpinnerAdapter
    private var eventDataResponseList = ArrayList<CategoryListItemData>()
    private var productDataResponseList = ArrayList<CategoryListItemData>()
    private lateinit var eventsTypeSpinner: CategoriesSpinnerAdapter
    private lateinit var productTypeSpinner: CategoriesSpinnerAdapter
    var catId = "0"
    var catName = ""

    var latitude = 0.0
    var longitude = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val category_type = resources.getStringArray(R.array.category_type)
        categoryAdapter = SpinnerAdapter(this, category_type)
        categoryType.adapter = categoryAdapter
        locationSearch.setOnClickListener {
            let {
                UiUtils.hideSoftKeyboard(it)
                // Call for Location
                startActivityForResult(
                    MapsActivity.getIntent(it, 2), 23
                )
            }
        }
        btnApply.setOnClickListener {
            if (etSearchQuery.text.toString().equals("")) {
                showSnackbar("Please enter something", false)
                etSearchQuery.error = "Please enter something"
                etSearchQuery.requestFocus()
            } else {
                var lat = ""
                var longi = ""
                if (rbEvent.isChecked || rbProduct.isChecked) {
                    if (latitude == 0.0) {
                        lat = ""
                        longi = ""
                    } else {
                        lat = "" + latitude
                        longi = "" + longitude
                    }
                    var i = Intent(this, LandingActivity::class.java)
                    i.putExtra("query", etSearchQuery.text.toString())
                    i.putExtra("location", locationSearch.text.toString())
                    i.putExtra("type", "Rent")
                    i.putExtra("from", "search")
                    i.putExtra("latitude", "" + lat)
                    i.putExtra("longitude", "" + longi)
                    i.putExtra("categoryName", catName)
                    i.putExtra("categoryId", catId)

                    startActivity(i)
                } else {
                    showSnackbar("Please select type of category", false)
                }
            }
        }
        rbProduct.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rbEvent.isChecked = false
                llCategoryProduct.visibility = View.VISIBLE
                llCategoryEvent.visibility = View.GONE
                categoryType.setSelection(0)
            }
        }
        rbEvent.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rbProduct.isChecked = false
                llCategoryProduct.visibility = View.GONE
                llCategoryEvent.visibility = View.VISIBLE
                EventType.setSelection(0)
            }
        }
        ivBackSearch.setOnClickListener {
            onBackPressed()
        }
        EventType.onItemSelectedListener = this
        categoryType.onItemSelectedListener = this

        subscribeLoading()
        subscribeUi()
        getCategories()
        getEventsData()
    }

    override fun layout(): Int = R.layout.activity_serach


    override fun tag(): String {
        TODO("Not yet implemented")
    }

    override fun title(): String {
        TODO("Not yet implemented")
    }

    override fun titleColor(): Int {
        TODO("Not yet implemented")
    }

    companion object {
        const val KEY_TAB = "KEY_TAB"

        fun getIntent(context: Context): Intent? {
            val intent = Intent(context, SerachActivity::class.java)
            return intent
        }
    }

    fun getCategories() {
        if (NetworkUtil.isInternetAvailable(this)) {
            model.getCategories("Category List", "Product")
        }
    }

    fun getEventsData() {
        if (NetworkUtil.isInternetAvailable(this)) {
            model.getCategoriesEvents("Category List", "Event")
        }
    }


    private fun subscribeLoading() {

        model.searchEvent.observe(this, Observer {
            if (it.isLoading) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
            it.error?.run {
                UiUtils.showInternetDialog(this@SerachActivity, R.string.something_went_wrong)
            }
        })
    }

    private fun subscribeUi() {
        model.categoryModelEvents.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            eventDataResponseList = it?.categoryList!!

            var listC1: ArrayList<Boolean> = ArrayList()
            for (item in eventDataResponseList) {
                listC1.add(true)
            }


            eventsTypeSpinner = CategoriesSpinnerAdapter(this, eventDataResponseList, listC1)
            EventType.adapter = eventsTypeSpinner
        })

        model.categoryModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                productDataResponseList = it?.categoryList!!
                var listC1: ArrayList<Boolean> = ArrayList()
                for (item in productDataResponseList) {
                    listC1.add(true)
                }


                productTypeSpinner = CategoriesSpinnerAdapter(this, productDataResponseList, listC1)
                categoryType.adapter = productTypeSpinner
            } else {
                showSnackbar(it.message, false)

            }
        })

    }

    fun showProgressDialog() {
        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 23) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                locationSearch.setText(data.getStringExtra(MapsActivity.KEY_ADDRESS1))

                latitude = data.getDoubleExtra(MapsActivity.KEY_LATITUDE, 0.0)
                latitude = data.getDoubleExtra(MapsActivity.KEY_LONGITUDE, 0.0)

            }


        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.EventType -> {
                catId = eventDataResponseList.get(position).categoryId
                catName = eventDataResponseList.get(position).categoryName
            }
            R.id.categoryType -> {
                catId = productDataResponseList.get(position).categoryId
                catName = productDataResponseList.get(position).categoryName

            }

        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}