package com.dartmic.yo2see.ui.postAdd

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ExpandableListAdapter
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.model.Category_sub_subTosub.CategoryListItemData
import com.dartmic.yo2see.model.EventsItems
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.SubCategoriesList.SubCategoriesFragment
import com.dartmic.yo2see.ui.buycategoriesList.CategoriesListFragment
import com.dartmic.yo2see.ui.categories.CategoriesViewModel
import com.dartmic.yo2see.ui.categories.adapter.AdapterCategories
import com.dartmic.yo2see.ui.home.HomeFragment
import com.dartmic.yo2see.ui.home.adapter.AdapterHomeEvents
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Config
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import kotlinx.android.synthetic.main.fragment_categories_list.*
import kotlinx.android.synthetic.main.fragment_categories_list.rvBuyList
import kotlinx.android.synthetic.main.fragment_post_an_add.*
import kotlinx.android.synthetic.main.tree.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PostAnAddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PostAnAddFragment : BaseFragment<CategoriesViewModel>(CategoriesViewModel::class),
    AdapterViewClickListener<CategoryListItemData> {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var adapterEvents: AdapterHomeEvents? = null
    private var adapterCategory: AdapterCategories? = null
    internal var adapter: ExpandableListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manager = GridLayoutManager(context, 4)
        rvPostList.layoutManager = manager


        ivBackAddanPost.setOnClickListener {
            activity?.onBackPressed()
        }

        subscribeUi()
        subscribeLoading()
        getCategoryData()

    }

    fun getCategoryData() {
        if (NetworkUtil.isInternetAvailable(activity)) {
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
                UiUtils.showInternetDialog(activity, R.string.something_went_wrong)
            }
        })
    }

    private fun subscribeUi() {
        model.categoryModelMenu.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {

                activity?.let {
                    adapterCategory =
                        AdapterCategories(this, it, R.drawable.round_circle_red)

                }

                rvPostList.adapter = adapterCategory
                adapterCategory?.submitList(it?.categoryList)
            } else {
                showSnackbar(it.message, false)
            }
        })


    }

    fun showProgressDialog() {
        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    private fun runLayoutAnimation(recyclerView: RecyclerView) {
        try {
            val context: Context = recyclerView.context
            val controller: LayoutAnimationController =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
            recyclerView.layoutAnimation = controller
            recyclerView.adapter!!.notifyDataSetChanged()
            recyclerView.scheduleLayoutAnimation()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        const val TYPE = "type"

        @JvmStatic
        fun getInstance(instance: Int): PostAnAddFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)

            val fragment = PostAnAddFragment()
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun onResume() {
        super.onResume()
        activity?.let {
            (activity as LandingActivity).hideVisibleBottomBar(
                View.VISIBLE
            )
        }

    }

    override fun getLayoutId() = R.layout.fragment_post_an_add
    override fun onClickAdapterView(
        objectAtPosition: CategoryListItemData,
        viewType: Int,
        position: Int
    ) {

        when (viewType) {
            Config.AdapterClickViewTypes.CLICK_VIEW_CATEGORY -> {

                this?.let {

                    mFragmentNavigation.pushFragment(
                        SubCategoriesFragment
                            .getInstance(mInt + 1, Config.Constants.POST_AN_ADD, objectAtPosition)
                    )

                }

            }
        }


    }


}