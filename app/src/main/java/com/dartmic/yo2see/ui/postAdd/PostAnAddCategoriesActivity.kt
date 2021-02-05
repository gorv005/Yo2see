package com.dartmic.yo2see.ui.postAdd

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ExpandableListAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseActivity
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.model.Category_sub_subTosub.CategoryListItemData
import com.dartmic.yo2see.ui.categories.CategoriesViewModel
import com.dartmic.yo2see.ui.categories.adapter.AdapterCategories
import com.dartmic.yo2see.ui.login.LoginActivity
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Config
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import com.gsa.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.fragment_categories_list.*

class PostAnAddCategoriesActivity : BaseActivity<CategoriesViewModel>(CategoriesViewModel::class),
    AdapterViewClickListener<CategoryListItemData> {
    private var adapterCategory: AdapterCategories? = null

    var type: Int? = 0
    internal var adapter: ExpandableListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val manager = GridLayoutManager(this, 4)
        rvBuyList.layoutManager = manager
        subscribeLoading()
        subscribeUi()
        getCategoryData()
    }


    override fun layout() = R.layout.activity_post_an_add_categories

    fun getCategoryData() {
        if (NetworkUtil.isInternetAvailable(this)) {
            model.getCategoriesMenu("Category List", "Menu")
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
                it.error?.run {
                    UiUtils.showInternetDialog(
                        this@PostAnAddCategoriesActivity,
                        R.string.something_went_wrong
                    )
                }
            }
        })
    }

    private fun subscribeUi() {
        model.categoryModelMenu.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                AdapterCategories(this, this, R.drawable.round_circle_red)

                rvBuyList.adapter = adapterCategory
                adapterCategory?.submitList(it?.categoryList)
            } else {
                showSnackbar(it.message, false)
            }
        })


    }

    fun showProgressDialog() {
        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    override fun tag(): String {
        TODO("Not yet implemented")
    }

    override fun title(): String {
        TODO("Not yet implemented")
    }

    override fun titleColor(): Int {
        TODO("Not yet implemented")
    }

    override fun onClickAdapterView(
        objectAtPosition: CategoryListItemData,
        viewType: Int,
        position: Int
    ) {
        TODO("Not yet implemented")
    }


    companion object {
        const val KEY_TAB = "KEY_TAB"

        fun getIntent(context: Context): Intent? {
            val intent = Intent(context, PostAnAddCategoriesActivity::class.java)
            return intent
        }
    }

}