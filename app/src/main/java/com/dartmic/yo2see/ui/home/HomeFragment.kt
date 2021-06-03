package com.dartmic.yo2see.ui.home


import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.callbacks.AdapterViewItemClickListener
import com.dartmic.yo2see.model.AdsItems
import com.dartmic.yo2see.model.Category_sub_subTosub.CategoryListItemData
import com.dartmic.yo2see.model.categories.CategoryListItem
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.SubCategoriesList.SubCategoriesFragment
import com.dartmic.yo2see.ui.categories.CategoriesViewModel
import com.dartmic.yo2see.ui.home.adapter.AdapterHomeData
import com.dartmic.yo2see.ui.home.adapter.TabFragmentAdapter
import com.dartmic.yo2see.ui.home.products.ProductFragment
import com.dartmic.yo2see.ui.product_list.ProductListFragment
import com.dartmic.yo2see.ui.search.SerachActivity
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Config
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_product.*
import kotlinx.android.synthetic.main.tree.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment<CategoriesViewModel>(CategoriesViewModel::class),
    AdapterViewClickListener<CategoryListItemData>,
    AdapterViewItemClickListener<AdsItems> {


    private var adapterEvents: AdapterHomeData? = null
    private var adapterAds: AdapterHomeData? = null
    private var indicatorWidth = 0
    private var adapterProducts: AdapterHomeData? = null
    private var adapterEventsData: AdapterHomeData? = null
    var type: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //visibilityGone()
        val fcsRed = ForegroundColorSpan(AndroidUtils.getColor(R.color.blue))
        type = arguments?.getInt(ProductListFragment.TYPE)
        if(type==Config.Constants.POST_AN_ADD){
            tvHomeWelecomeText.setText(AndroidUtils.getString(R.string.post_an_add))
            rlCat.visibility=View.GONE
            tvBrows.setText(AndroidUtils.getString(R.string.what_product_you_would))
        }
        ivSearch.setOnClickListener {
            startActivity(SerachActivity.getIntent(activity!!))
        }
        /*AndroidUtils.setTextWithSpan(
            tvHomeWelecomeText,
            AndroidUtils.getString(R.string.welcome_text), "one of the categories", fcsRed
        )*/
        val manager = GridLayoutManager(context, 4)
        rvEvents.layoutManager = manager
        activity?.let {
            adapterEvents = AdapterHomeData(this, it, R.drawable.round_circle_light_blue)

        }
        rvEvents.adapter = adapterEvents

        val manager1 = GridLayoutManager(context, 4)
        rvAds.layoutManager = manager1
        activity?.let {
            adapterAds = AdapterHomeData(this, it, R.drawable.round_circle_light_blue)

        }
        rvAds.adapter = adapterAds



        rvHomeProducts.setHasFixedSize(true)
        var itemDecoration = DividerItemDecoration(
            activity,
            DividerItemDecoration.HORIZONTAL
        )
        activity?.let {
            var d = it?.getDrawable(R.drawable.divider)!!
            itemDecoration?.setDrawable(d)
        }
        rvHomeProducts.addItemDecoration(
            itemDecoration
        )
        var itemDecoration1 = DividerItemDecoration(
            activity,
            DividerItemDecoration.VERTICAL
        )
        activity?.let {
            var d = it?.getDrawable(R.drawable.divider)!!
            itemDecoration1?.setDrawable(d)
        }
        rvHomeProducts.addItemDecoration(
            itemDecoration1
        )
        val manager4 = GridLayoutManager(context, 3)
        rvHomeEvents.layoutManager = manager4
        activity?.let {
            adapterEventsData = AdapterHomeData(this, it, R.drawable.round_circle_light_blue)

        }
        rvHomeEvents.adapter = adapterEventsData
        adapterEventsData?.submitList(getEvents())
        adapterEventsData?.notifyDataSetChanged()


        rvHomeEvents.addItemDecoration(
            itemDecoration1
        )
        rvHomeEvents.addItemDecoration(
            itemDecoration
        )
        val manager3 = GridLayoutManager(context, 3)
        rvHomeProducts.layoutManager = manager3



        val adapter = TabFragmentAdapter(activity?.getSupportFragmentManager()!!)
        adapter.addFragment(ProductFragment.getInstance(), getString(R.string.products))
        adapter.addFragment(ProductFragment.getInstance(), getString(R.string.events_))
        viewPager.setAdapter(adapter)
        tab.setupWithViewPager(viewPager)

        //Determine indicator width at runtime

        //Determine indicator width at runtime
        tab.post(Runnable {
            indicatorWidth = tab.getWidth() / tab.getTabCount()

            //Assign new width
            val indicatorParams = indicator?.getLayoutParams() as FrameLayout.LayoutParams
            indicatorParams.width = indicatorWidth
            indicator?.setLayoutParams(indicatorParams)
        })

        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            //To move the indicator as the user scroll, we will need the scroll offset values
            //positionOffset is a value from [0..1] which represents how far the page has been scrolled
            //see https://developer.android.com/reference/android/support/v4/view/ViewPager.OnPageChangeListener
            override fun onPageScrolled(i: Int, positionOffset: Float, positionOffsetPx: Int) {
                if(i==0){
                    tvEvents.visibility=View.GONE
                    tvProducts.visibility=View.VISIBLE

                    rvHomeProducts.visibility=View.VISIBLE
                    rvHomeEvents.visibility=View.GONE
                }else{
                    tvEvents.visibility=View.VISIBLE
                    tvProducts.visibility=View.GONE
                    rvHomeProducts.visibility=View.GONE
                    rvHomeEvents.visibility=View.VISIBLE

                }
                var params = indicator?.getLayoutParams() as FrameLayout.LayoutParams

                //Multiply positionOffset with indicatorWidth to get translation
                val translationOffset: Float = (positionOffset + i) * indicatorWidth
                params.leftMargin = translationOffset.toInt()
                indicator?.setLayoutParams(params)
            }

            override fun onPageSelected(i: Int) {}
            override fun onPageScrollStateChanged(i: Int) {}
        })

        subscribeLoading()
        subscribeUi()
           getCategories()
        // getEventsData()
    }

    fun getCategories() {
        if (NetworkUtil.isInternetAvailable(activity)) {
            model.getCategories("Category List", "Product")
        }
    }

    fun getEventsData() {
        if (NetworkUtil.isInternetAvailable(activity)) {
        //    model.getCategoriesEvents("Category List", "Event")
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
        model.categoryModelEvents.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            /*if (it.status) {
                //    showSnackbar(it.message, true)
                var i = 0
                while (i < it?.categoryList?.size!!) {

                    it?.categoryList.get(i).image = R.drawable.ic_suitcase_white
                    i++
                }
                adapterEvents?.submitList(it?.categoryList)
                ViewCompat.setNestedScrollingEnabled(rvEvents, false)
                activity?.let { UiUtils.hideSoftKeyboard(it) }
                runLayoutAnimation(rvEvents)

            } else {
                showSnackbar(it.message, false)

            }*/
        })

        model.categoryModelAds.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
           /* if (it.status) {
                //         showSnackbar(it.message, true)
                var i = 0
                while (i < it?.categoryList?.size!!) {

                    it?.categoryList.get(i).image = R.drawable.ic_suitcase_white
                    i++
                }
                activity?.let {
                    adapterProducts = AdapterHomeData(this, it, R.drawable.round_circle_light_blue)

                }
                rvHomeProducts.adapter = adapterProducts
                adapterAds?.submitList(it?.categoryList)
                ViewCompat.setNestedScrollingEnabled(rvAds, false)

                activity?.let { UiUtils.hideSoftKeyboard(it) }
                runLayoutAnimation(rvAds)
            } else {
                showSnackbar(it.message, false)

            }*/
        })
        model.categoryModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                //         showSnackbar(it.message, true)
               /* var i = 0
                while (i < it?.categoryList?.size!!) {

                    it?.categoryList.get(i).image = R.drawable.ic_suitcase_white
                    i++
                }*/
                activity?.let {
                    adapterProducts = AdapterHomeData(this, it, R.drawable.round_circle_light_blue)

                }
                rvHomeProducts.adapter = adapterProducts
                adapterProducts?.submitList(it?.categoryList)
                adapterProducts?.notifyDataSetChanged()
                activity?.let { UiUtils.hideSoftKeyboard(it) }
                runLayoutAnimation(rvHomeProducts)
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
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_left_to_right)
            recyclerView.layoutAnimation = controller
            recyclerView.adapter!!.notifyDataSetChanged()
            recyclerView.scheduleLayoutAnimation()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        const val TYPE = "type"

        fun getInstance(instance: Int,type: Int): HomeFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)
            bundle.putInt(TYPE, type)

            val fragment = HomeFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    fun getAds(): ArrayList<AdsItems> {
        AndroidUtils.getString(R.string.jobs)
        val ads = arrayListOf<AdsItems>()

        ads?.add(
            AdsItems(
                1,
                AndroidUtils.getString(R.string.mobile),
                R.drawable.ic_devices_white
            )
        )
        ads?.add(
            AdsItems(
                1,
                AndroidUtils.getString(R.string.electronic),
                R.drawable.ic_motar_white
            )
        )
        ads?.add(
            AdsItems(
                1,
                AndroidUtils.getString(R.string.real_estate),
                R.drawable.ic_key_white
            )
        )
        ads?.add(
            AdsItems(
                1,
                AndroidUtils.getString(R.string.clothing),
                R.drawable.ic_clothing_white
            )
        )

        return ads

    }


    fun getProducts(): ArrayList<CategoryListItem> {
        AndroidUtils.getString(R.string.jobs)
        val events = arrayListOf<CategoryListItem>()

        events?.add(
            CategoryListItem(
                "",

                AndroidUtils.getString(R.string.events), "1",
                R.drawable.ic_suitcase_white
            )
        )
        events?.add(
            CategoryListItem(
                "",

                AndroidUtils.getString(R.string.events), "1",
                R.drawable.ic_suitcase_white
            )
        )
        events?.add(
            CategoryListItem(
                "",

                AndroidUtils.getString(R.string.events), "1",
                R.drawable.ic_suitcase_white
            )
        )
        events?.add(
            CategoryListItem(
                "",

                AndroidUtils.getString(R.string.events), "1",
                R.drawable.ic_suitcase_white
            )
        )
        events?.add(
            CategoryListItem(
                "",

                AndroidUtils.getString(R.string.events), "1",
                R.drawable.ic_suitcase_white
            )
        )
        events?.add(
            CategoryListItem(
                "",

                AndroidUtils.getString(R.string.free_stuff), "1",
                R.drawable.ic_suitcase_white
            )
        )
        events?.add(
            CategoryListItem(
                "",

                AndroidUtils.getString(R.string.free_stuff), "1",
                R.drawable.ic_suitcase_white
            )
        )
        events?.add(
            CategoryListItem(
                "",

                AndroidUtils.getString(R.string.free_stuff), "1",
                R.drawable.ic_suitcase_white
            )
        )
        events?.add(
            CategoryListItem(
                "",

                AndroidUtils.getString(R.string.free_stuff), "1",
                R.drawable.ic_suitcase_white
            )
        )
        events?.add(
            CategoryListItem(
                "",

                AndroidUtils.getString(R.string.free_stuff), "1",
                R.drawable.ic_suitcase_white
            )
        )
        events?.add(
            CategoryListItem(
                "",

                AndroidUtils.getString(R.string.free_stuff), "1",
                R.drawable.ic_suitcase_white
            )
        )
        events?.add(
            CategoryListItem(
                "",

                AndroidUtils.getString(R.string.free_stuff), "1",
                R.drawable.ic_suitcase_white
            )
        )
        events?.add(
            CategoryListItem(
                "",

                AndroidUtils.getString(R.string.free_stuff), "1",
                R.drawable.ic_suitcase_white
            )
        )
        events?.add(
            CategoryListItem(
                "",

                AndroidUtils.getString(R.string.free_stuff), "1",
                R.drawable.ic_suitcase_white
            )
        )


        return events

    }

    fun getEvents(): ArrayList<CategoryListItemData> {
        AndroidUtils.getString(R.string.jobs)
        val events = arrayListOf<CategoryListItemData>()

        events?.add(
            CategoryListItemData(
                "",

                AndroidUtils.getString(R.string.jobs), null,
                "1"
            )
        )
        events?.add(
            CategoryListItemData(
                "",

                AndroidUtils.getString(R.string.jobs), null,
                "1"
            )
        )
        events?.add(
            CategoryListItemData(
                "",

                AndroidUtils.getString(R.string.jobs), null,
                "1"
            )
        )
        events?.add(
            CategoryListItemData(
                "",

                AndroidUtils.getString(R.string.jobs), null,
                "1"
            )
        )
        events?.add(
            CategoryListItemData(
                "",

                AndroidUtils.getString(R.string.jobs), null,
                "1"
            )
        )
        events?.add(
            CategoryListItemData(
                "",

                AndroidUtils.getString(R.string.jobs), null,
                "1"
            )
        )
        events?.add(
            CategoryListItemData(
                "",

                AndroidUtils.getString(R.string.jobs), null,
                "1"
            )
        )
        events?.add(
            CategoryListItemData(
                "",

                AndroidUtils.getString(R.string.jobs), null,
                "1"
            )
        )
        events?.add(
            CategoryListItemData(
                "",

                AndroidUtils.getString(R.string.jobs), null,
                "1"
            )
        )
        events?.add(
            CategoryListItemData(
                "",

                AndroidUtils.getString(R.string.jobs), null,
                "1"
            )
        )
        events?.add(
            CategoryListItemData(
                "",

                AndroidUtils.getString(R.string.jobs), null,
                "1"
            )
        )
        events?.add(
            CategoryListItemData(
                "",

                AndroidUtils.getString(R.string.jobs), null,
                "1"
            )
        )
        events?.add(
            CategoryListItemData(
                "",

                AndroidUtils.getString(R.string.jobs), null,
                "1"
            )
        )
        events?.add(
            CategoryListItemData(
                "",

                AndroidUtils.getString(R.string.jobs), null,
                "1"
            )
        )

        return events

    }

    override fun onResume() {
        super.onResume()
        activity?.let {
            (activity as LandingActivity).updateStatusBarColor(
                AndroidUtils.getColor(R.color.red_a),
                1
            )
        }
    }

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
                            .getInstance(mInt + 1,type,objectAtPosition)
                    )

                }

            }
        }
    }

    override fun onClickItemAdapterView(objectAtPosition: AdsItems, viewType: Int, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getLayoutId() = R.layout.fragment_home


    fun visibilityGone() {
        ivbarterWhite.visibility = View.GONE
        ivpostWhite.visibility = View.GONE
        ivrentWhite.visibility = View.GONE
        ivsellWhite.visibility = View.GONE

    }

}
