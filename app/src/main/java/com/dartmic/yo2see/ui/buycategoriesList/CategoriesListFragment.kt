package com.dartmic.yo2see.ui.buycategoriesList

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.style.StyleSpan
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ExpandableListAdapter
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
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
import com.dartmic.yo2see.ui.categories.CategoriesViewModel
import com.dartmic.yo2see.ui.categories.adapter.AdapterCategories
import com.dartmic.yo2see.ui.home.adapter.AdapterHomeEvents
import com.dartmic.yo2see.ui.product_list.ProductListFragment
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Config
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import com.github.florent37.viewanimator.ViewAnimator
import kotlinx.android.synthetic.main.fragment_categories_list.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_sub_categories.*
import kotlinx.android.synthetic.main.tree.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoriesListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoriesListFragment : BaseFragment<CategoriesViewModel>(CategoriesViewModel::class),
    AdapterViewClickListener<CategoryListItemData> {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var adapterEvents: AdapterHomeEvents? = null
    private var adapterCategory: AdapterCategories? = null

    var type: Int? = 0
    internal var adapter: ExpandableListAdapter? = null
    internal var titleList: List<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        type = arguments?.getInt(TYPE)
        treeVisibility()


        val manager = GridLayoutManager(context, 4)
        rvBuyList.layoutManager = manager


        ivBackCategories.setOnClickListener {
            activity?.onBackPressed()
        }

        ivsell.setOnClickListener {

            if (type != Config.Constants.SELL) {
                val p: ArrayList<Pair<View, String>> = ArrayList()
                p.add(Pair(ivsell, "buy"))

                mFragmentNavigation.pushFragment(
                    CategoriesListFragment
                        .getInstance(mInt, Config.Constants.SELL), p
                )
            }

        }
        ivrent.setOnClickListener {
            if (type != Config.Constants.RENT) {
                val p: ArrayList<Pair<View, String>> = ArrayList()
                p.add(Pair(ivsell, "buy"))

                mFragmentNavigation.pushFragment(
                    CategoriesListFragment
                        .getInstance(mInt, Config.Constants.RENT), p
                )
            }
        }
        ivbarter.setOnClickListener {
            if (type != Config.Constants.BARTER) {
                val p: ArrayList<Pair<View, String>> = ArrayList()
                p.add(Pair(ivsell, "buy"))

                mFragmentNavigation.pushFragment(
                    CategoriesListFragment
                        .getInstance(mInt, Config.Constants.BARTER), p
                )
            }

        }
        ivpost.setOnClickListener {
            if (type != Config.Constants.POST) {
                val p: ArrayList<Pair<View, String>> = ArrayList()
                p.add(Pair(ivsell, "buy"))

                mFragmentNavigation.pushFragment(
                    CategoriesListFragment
                        .getInstance(mInt, Config.Constants.POST), p
                )
            }
        }
        subscribeUi()
        subscribeLoading()
        getCategoryData()
        /*ViewAnimator
            .animate(ivsell)
            .scale(1.3f, 1f)
            .onStart({})
            .onStop({ })
            .start()
        runLayoutAnimation(rvBuyList)*/
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

                when (type) {
                    Config.Constants.SELL -> {

                        activity?.let {
                            adapterCategory =
                                AdapterCategories(this, it, R.drawable.round_circle_blue)

                        }


                    }
                    Config.Constants.RENT -> {

                        activity?.let {
                            adapterCategory =
                                AdapterCategories(this, it, R.drawable.round_circle_purple)

                        }


                    }
                    Config.Constants.BARTER -> {


                        activity?.let {
                            adapterCategory =
                                AdapterCategories(this, it, R.drawable.round_circle_yellow)

                        }


                    }
                    Config.Constants.POST -> {

                        activity?.let {
                            adapterCategory =
                                AdapterCategories(this, it, R.drawable.round_circle_light_blue)

                        }

                    }

                }

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
        fun getInstance(instance: Int, type: Int): CategoriesListFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)
            bundle.putInt(TYPE, type)

            val fragment = CategoriesListFragment()
            fragment.arguments = bundle
            return fragment
        }

    }

    fun getEvents(): ArrayList<EventsItems> {
        AndroidUtils.getString(R.string.jobs)
        val events = arrayListOf<EventsItems>()

        events?.add(
            EventsItems(
                1,
                AndroidUtils.getString(R.string.mobile),
                R.drawable.ic_devices_white
            )
        )
        events?.add(
            EventsItems(
                1,
                AndroidUtils.getString(R.string.electronic),
                R.drawable.ic_motar_white
            )
        )
        events?.add(
            EventsItems(
                1,
                AndroidUtils.getString(R.string.tools),
                R.drawable.ic_motar_white
            )
        )
        events?.add(
            EventsItems(
                1,
                AndroidUtils.getString(R.string.clothing),
                R.drawable.ic_clothing_white
            )
        )

        return events

    }


    val data: HashMap<String, List<String>>
        get() {
            val listData = HashMap<String, List<String>>()

            val redmiMobiles = ArrayList<String>()
            redmiMobiles.add("Redmi Y2")
            redmiMobiles.add("Redmi S2")
            redmiMobiles.add("Redmi Note 5 Pro")
            redmiMobiles.add("Redmi Note 5")
            redmiMobiles.add("Redmi 5 Plus")
            redmiMobiles.add("Redmi Y1")
            redmiMobiles.add("Redmi 3S Plus")

            val micromaxMobiles = ArrayList<String>()
            micromaxMobiles.add("Micromax Bharat Go")
            micromaxMobiles.add("Micromax Bharat 5 Pro")
            micromaxMobiles.add("Micromax Bharat 5")
            micromaxMobiles.add("Micromax Canvas 1")
            micromaxMobiles.add("Micromax Dual 5")

            val appleMobiles = ArrayList<String>()
            appleMobiles.add("iPhone 8")
            appleMobiles.add("iPhone 8 Plus")
            appleMobiles.add("iPhone X")
            appleMobiles.add("iPhone 7 Plus")
            appleMobiles.add("iPhone 7")
            appleMobiles.add("iPhone 6 Plus")

            val samsungMobiles = ArrayList<String>()
            samsungMobiles.add("Samsung Galaxy S9+")
            samsungMobiles.add("Samsung Galaxy Note 7")
            samsungMobiles.add("Samsung Galaxy Note 5 Dual")
            samsungMobiles.add("Samsung Galaxy S8")
            samsungMobiles.add("Samsung Galaxy A8")
            samsungMobiles.add("Samsung Galaxy Note 4")

            listData["Redmi"] = redmiMobiles
            listData["Micromax"] = micromaxMobiles
            listData["Apple"] = appleMobiles
            listData["Samsung"] = samsungMobiles

            return listData
        }

    override fun getLayoutId() = R.layout.fragment_categories_list
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
                            .getInstance(mInt + 1, type,objectAtPosition)
                    )

                }

            }
        }


    }

    fun treeVisibility() {
        ivbarterWhite.visibility = View.VISIBLE
        ivpostWhite.visibility = View.VISIBLE
        ivrentWhite.visibility = View.VISIBLE
        ivsellWhite.visibility = View.VISIBLE
        when (type) {
            Config.Constants.SELL -> {
                ivsellWhite.visibility = View.GONE
                activity?.let {
                    (activity as LandingActivity).updateStatusBarColor(
                        AndroidUtils.getColor(R.color.blue1),
                        2
                    )
                }

                categoriesExpandableListView.setChildDivider(activity!!.getDrawable(R.drawable.child_divider_blue))
                ivCornerImage.setColorFilter(
                    ContextCompat.getColor(activity!!, R.color.blue1)
                )
                /* activity?.let {
                     adapterEvents = AdapterHomeEvents(this, it, R.drawable.round_circle_blue)

                 }*/

                var sb = AndroidUtils.setTextWithSpan(
                    tvCategoriesHeader,
                    AndroidUtils.getString(R.string.buy_header_text),
                    "Buy",
                    AndroidUtils.getColor(R.color.blue)
                )
                AndroidUtils.setTextWithSpan(
                    tvCategoriesHeader,
                    AndroidUtils.getString(R.string.buy_header_text),
                    " Buy",
                    sb,
                    AndroidUtils.getColor(R.color.blue1)
                )
            }
            Config.Constants.RENT -> {
                ivrentWhite.visibility = View.GONE
                activity?.let {
                    (activity as LandingActivity).updateStatusBarColor(
                        AndroidUtils.getColor(R.color.voilet),
                        2
                    )
                }

                ivCornerImage.setColorFilter(
                    ContextCompat.getColor(activity!!, R.color.voilet)
                )
                categoriesExpandableListView.setChildDivider(activity!!.getDrawable(R.drawable.child_divider_purple))

                /* activity?.let {
                     adapterEvents = AdapterHomeEvents(this, it, R.drawable.round_circle_purple)

                 }*/
                tvCategoriesHeader.setText(AndroidUtils.getString(R.string.rent_header_text))

                var sb = AndroidUtils.setTextWithSpan(
                    tvCategoriesHeader,
                    AndroidUtils.getString(R.string.rent_header_text),
                    "Rent",
                    AndroidUtils.getColor(R.color.voilet)
                )
                var bss = StyleSpan(Typeface.BOLD); // Span to make text bold
                /* sb.setSpan(
                     bss,
                     AndroidUtils.getString(R.string.buy_header_text).length - 5,
                     AndroidUtils.getString(R.string.buy_header_text).length,
                     Spannable.SPAN_INCLUSIVE_INCLUSIVE
                 )
                 tvCategoriesHeader.setText(sb)*/

                AndroidUtils.setTextWithSpan(
                    tvCategoriesHeader,
                    AndroidUtils.getString(R.string.rent_header_text),
                    " Rent",
                    sb,
                    AndroidUtils.getColor(R.color.voilet)
                )
            }
            Config.Constants.BARTER -> {
                ivbarterWhite.visibility = View.GONE
                categoriesExpandableListView.setChildDivider(activity!!.getDrawable(R.drawable.child_divider_yellow))
                activity?.let {
                    (activity as LandingActivity).updateStatusBarColor(
                        AndroidUtils.getColor(R.color.yellow1),
                        2
                    )
                }
                ivCornerImage.setColorFilter(
                    ContextCompat.getColor(activity!!, R.color.yellow1)
                )

                /* activity?.let {
                     adapterEvents = AdapterHomeEvents(this, it, R.drawable.round_circle_yellow)

                 }
 */
                tvCategoriesHeader.setText(AndroidUtils.getString(R.string.barter_header_text))

                var sb = AndroidUtils.setTextWithSpan(
                    tvCategoriesHeader,
                    AndroidUtils.getString(R.string.barter_header_text),
                    "Barter",
                    AndroidUtils.getColor(R.color.yellow1)
                )

                AndroidUtils.setTextWithSpan(
                    tvCategoriesHeader,
                    AndroidUtils.getString(R.string.barter_header_text),
                    " Barter",
                    sb,
                    AndroidUtils.getColor(R.color.yellow1)
                )
            }
            Config.Constants.POST -> {
                ivpostWhite.visibility = View.GONE
                ivCornerImage.setColorFilter(
                    ContextCompat.getColor(activity!!, R.color.blue)
                )
                activity?.let {
                    (activity as LandingActivity).updateStatusBarColor(
                        AndroidUtils.getColor(R.color.blue),
                        2
                    )
                }
                categoriesExpandableListView.setChildDivider(activity!!.getDrawable(R.drawable.child_divider_blue))

                /* activity?.let {
                     adapterEvents = AdapterHomeEvents(this, it, R.drawable.round_circle_light_blue)

                 }*/
                tvCategoriesHeader.setText(AndroidUtils.getString(R.string.post_header_text))

                var sb = AndroidUtils.setTextWithSpan(
                    tvCategoriesHeader,
                    AndroidUtils.getString(R.string.post_header_text),
                    "Post",
                    AndroidUtils.getColor(R.color.blue)
                )


                AndroidUtils.setTextWithSpan(
                    tvCategoriesHeader,
                    AndroidUtils.getString(R.string.post_header_text),
                    " Post",
                    sb,
                    AndroidUtils.getColor(R.color.blue)
                )
            }

        }


    }

}