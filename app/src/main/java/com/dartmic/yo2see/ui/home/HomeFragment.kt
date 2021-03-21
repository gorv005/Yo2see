package com.dartmic.yo2see.ui.home


import android.app.ActivityOptions
import android.content.Context
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.callbacks.AdapterViewItemClickListener
import com.dartmic.yo2see.model.AdsItems
import com.dartmic.yo2see.model.EventsItems
import com.dartmic.yo2see.model.categories.CategoryListItem
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.buycategoriesList.CategoriesListFragment
import com.dartmic.yo2see.ui.categories.CategoriesViewModel
import com.dartmic.yo2see.ui.home.adapter.AdapterAdsEvents
import com.dartmic.yo2see.ui.home.adapter.AdapterHomeData
import com.dartmic.yo2see.ui.home.adapter.AdapterHomeEvents
import com.dartmic.yo2see.ui.search.SerachActivity
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Config
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import com.github.florent37.viewanimator.ViewAnimator
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.tree.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment<CategoriesViewModel>(CategoriesViewModel::class),
    AdapterViewClickListener<CategoryListItem>,
    AdapterViewItemClickListener<AdsItems> {


    private var adapterEvents: AdapterHomeData? = null
    private var adapterAds: AdapterHomeData? = null

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
        visibilityGone()
        val fcsRed = ForegroundColorSpan(AndroidUtils.getColor(R.color.blue))
        ivSearch.setOnClickListener {
            startActivity(SerachActivity.getIntent(activity!!))
        }
        AndroidUtils.setTextWithSpan(
            tvHomeWelecomeText,
            AndroidUtils.getString(R.string.welcome_text),"one of the categories", fcsRed
        )
        val manager = GridLayoutManager(context, 4)
        rvEvents.layoutManager = manager
        activity?.let {
            adapterEvents = AdapterHomeData(this, it, R.drawable.round_circle_light_blue)

        }
        rvEvents.adapter = adapterEvents

        val manager1 = GridLayoutManager(context, 4)
        rvAds.layoutManager = manager1
        activity?.let {
            adapterAds = AdapterHomeData(this, it,R.drawable.round_circle_light_blue)

        }
        rvAds.adapter = adapterAds


        ivsell.setOnClickListener {

            val p: ArrayList<Pair<View, String>> = ArrayList()
            p.add(Pair(ivsell, "buy"))

            mFragmentNavigation.pushFragment(
                CategoriesListFragment
                    .getInstance(mInt + 1, Config.Constants.SELL), p
            )

        }
        ivrent.setOnClickListener {

            val p: ArrayList<Pair<View, String>> = ArrayList()
            p.add(Pair(ivsell, "buy"))

            mFragmentNavigation.pushFragment(
                CategoriesListFragment
                    .getInstance(mInt + 1, Config.Constants.RENT), p
            )

        }
        ivbarter.setOnClickListener {

            val p: ArrayList<Pair<View, String>> = ArrayList()
            p.add(Pair(ivsell, "buy"))

            mFragmentNavigation.pushFragment(
                CategoriesListFragment
                    .getInstance(mInt + 1, Config.Constants.BARTER), p
            )

        }
        ivpost.setOnClickListener {

            val p: ArrayList<Pair<View, String>> = ArrayList()
            p.add(Pair(ivsell, "buy"))

            mFragmentNavigation.pushFragment(
                CategoriesListFragment
                    .getInstance(mInt + 1, Config.Constants.POST), p
            )

        }

        ViewAnimator
            .animate(ivsell)
            .scale(1.3f, 1f)
            .onStart({})
            .onStop({})
            .start()
        ViewAnimator
            .animate(ivpost)
            .scale(1.3f, 1f)
            .onStart({})
            .onStop({})
            .start()
        ViewAnimator
            .animate(ivbarter)
            .scale(1.3f, 1f)
            .onStart({})
            .onStop({ })
            .start()
        ViewAnimator
            .animate(ivrent)
            .scale(1.3f, 1f)
            .onStart({})
            .onStop({})
            .start()



        subscribeLoading()
        subscribeUi()
        getAdsData()
        getEventsData()
    }

    fun getAdsData(){
        if (NetworkUtil.isInternetAvailable(activity)) {
            model.getCategoriesAds("Category List","Ads")
        }
    }
    fun getEventsData(){
        if (NetworkUtil.isInternetAvailable(activity)) {
            model.getCategoriesEvents("Category List","Event")
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
            if (it.status) {
            //    showSnackbar(it.message, true)
                var i=0
                while(i<it?.categoryList?.size!!){

                    it?.categoryList.get(i).image= R.drawable.ic_suitcase_white
                    i++
                }
                adapterEvents?.submitList(it?.categoryList)
                ViewCompat.setNestedScrollingEnabled(rvEvents, false)
                activity?.let { UiUtils.hideSoftKeyboard(it) }
                runLayoutAnimation(rvEvents)

            }
            else{
                showSnackbar(it.message, false)

            }
        })

        model.categoryModelAds.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
       //         showSnackbar(it.message, true)
                var i=0
                while(i<it?.categoryList?.size!!){

                    it?.categoryList.get(i).image= R.drawable.ic_suitcase_white
                    i++
                }
                adapterAds?.submitList(it?.categoryList)
                ViewCompat.setNestedScrollingEnabled(rvAds, false)

                activity?.let { UiUtils.hideSoftKeyboard(it) }
                runLayoutAnimation(rvAds)
            }
            else{
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
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    companion object {

        fun getInstance(instance: Int): HomeFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)

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

    fun getEvents(): ArrayList<EventsItems> {
        AndroidUtils.getString(R.string.jobs)
        val events = arrayListOf<EventsItems>()

        events?.add(
            EventsItems(
                1,
                AndroidUtils.getString(R.string.jobs),
                R.drawable.ic_suitcase_white
            )
        )
        events?.add(
            EventsItems(
                1,
                AndroidUtils.getString(R.string.events),
                R.drawable.ic_calendar_white
            )
        )
        events?.add(
            EventsItems(
                1,
                AndroidUtils.getString(R.string.real_estate),
                R.drawable.ic_key_white
            )
        )
        events?.add(
            EventsItems(
                1,
                AndroidUtils.getString(R.string.free_stuff),
                R.drawable.ic_free_tag_white
            )
        )

        return events

    }

    override fun onResume() {
        super.onResume()
        activity?.let {
            (activity as LandingActivity).updateStatusBarColor(AndroidUtils.getColor(R.color.red_a),1)
        }
    }
    override fun onClickAdapterView(objectAtPosition: CategoryListItem, viewType: Int, position: Int) {
        when (viewType) {

            Config.AdapterClickViewTypes.CLICK_VIEW_CATEGORY -> {

                this?.let {

                   /* mFragmentNavigation.pushFragment(
                        CategoriesListFragment
                            .getInstance(mInt + 1)
                    )
*/

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
