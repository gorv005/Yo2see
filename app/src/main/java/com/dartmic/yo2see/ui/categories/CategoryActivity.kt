package com.dartmic.yo2see.ui.categories

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseActivity
import com.dartmic.yo2see.callbacks.AdapterFeatureViewClickListener
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.model.categories.CategoriesResponse
import com.dartmic.yo2see.model.categories.CategoryListItem
import com.dartmic.yo2see.model.categories.FeatureListResponse
import com.dartmic.yo2see.model.categories.FeatureProductListItem
import com.dartmic.yo2see.ui.categories.adapter.AdapterCategories
import com.dartmic.yo2see.ui.categories.adapter.AdapterFeature
import com.dartmic.yo2see.util.AndroidUtils
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : BaseActivity<CategoriesViewModel>(CategoriesViewModel::class),
    AdapterViewClickListener<CategoryListItem>,
    AdapterFeatureViewClickListener<FeatureProductListItem> {




    override fun layout(): Int = R.layout.activity_category


    override fun tag(): String {
        TODO("Not yet implemented")
    }

    override fun title(): String {
        TODO("Not yet implemented")
    }

    override fun titleColor(): Int {
        TODO("Not yet implemented")
    }
    override fun onClickFeatureAdapterView(
        objectAtPosition: FeatureProductListItem,
        viewType: Int,
        position: Int
    ) {
        TODO("Not yet implemented")
    }


    override fun onClickAdapterView(
        objectAtPosition: CategoryListItem,
        viewType: Int,
        position: Int
    ) {
        TODO("Not yet implemented")
    }
   // private var adapterCategory: AdapterCategories? = null
    internal var categoryList: ArrayList<CategoryListItem>? = null

    private var adapterFeature: AdapterFeature? = null
    internal var featureProductList: ArrayList<FeatureProductListItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val manager1 = GridLayoutManager(this, 3)
        rv_categories.layoutManager = manager1
        val manager2= GridLayoutManager(this, 2)
        rv_feature_products.layoutManager = manager2

        let {
           // adapterCategory = AdapterCategories(this, it)
            adapterFeature = AdapterFeature(this, it)

        }
     //   rv_categories.adapter = adapterCategory
        rv_feature_products.adapter = adapterFeature

        subscribeLoading()
        subscribeUi()
        getData()
    }

    private fun getData() {


        if (NetworkUtil.isInternetAvailable(this)) {
          //  model.getCategories("Category List")
        }
        if (NetworkUtil.isInternetAvailable(this)) {
            model.getFeatureProduct("List")
        }
    }

    private fun subscribeLoading() {

        model.searchEvent.observe(this, Observer {
            if (it.isLoading) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
            it.error?.let {
                UiUtils.showInternetDialog(this, R.string.something_went_wrong)
            }
        })
    }

    private fun subscribeUi() {

        model.categoryModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            //showData(it)

        })
        model.featureProductyModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showData(it)

        })

    }

    private fun showData(data: FeatureListResponse?) {
        featureProductList = data?.featureProductList
        featureProductList?.let {

            adapterFeature?.submitList(it)

            ViewCompat.setNestedScrollingEnabled(rv_feature_products, false)

            adapterFeature?.notifyDataSetChanged()
        }
    }

    private fun showData(data: CategoriesResponse?) {
       /* categoryList = data?.categoryList
        categoryList?.let {

            adapterCategory?.submitList(it)

            ViewCompat.setNestedScrollingEnabled(rv_categories, false)

            adapterCategory?.notifyDataSetChanged()
        }*/
    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    companion object {
        fun getIntent(context: Context?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, CategoryActivity::class.java)


        }
    }


}