package com.dartmic.yo2see.ui.home


import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.callbacks.AdapterViewItemClickListener
import com.dartmic.yo2see.model.AdsItems
import com.dartmic.yo2see.model.EventsItems
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.buycategoriesList.CategoriesListFragment
import com.dartmic.yo2see.ui.categories.CategoriesViewModel
import com.dartmic.yo2see.ui.home.adapter.AdapterAdsEvents
import com.dartmic.yo2see.ui.home.adapter.AdapterHomeEvents
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Config
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.tree.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment<CategoriesViewModel>(CategoriesViewModel::class),
    AdapterViewClickListener<EventsItems>,
    AdapterViewItemClickListener<AdsItems> {


    private var adapterEvents: AdapterHomeEvents? = null
    private var adapterAds: AdapterAdsEvents? = null

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

        AndroidUtils.setTextWithSpan(
            tvHomeWelecomeText,
            AndroidUtils.getString(R.string.welcome_text),"one of the categories", fcsRed
        )
        val manager = GridLayoutManager(context, 4)
        rvEvents.layoutManager = manager
        activity?.let {
            adapterEvents = AdapterHomeEvents(this, it, R.drawable.round_circle_light_blue)

        }
        rvEvents.adapter = adapterEvents
        adapterEvents?.submitList(getEvents())
        ViewCompat.setNestedScrollingEnabled(rvEvents, false)

        val manager1 = GridLayoutManager(context, 4)
        rvAds.layoutManager = manager1
        activity?.let {
            adapterAds = AdapterAdsEvents(this, it)

        }
        rvAds.adapter = adapterAds
        adapterAds?.submitList(getAds())
        ViewCompat.setNestedScrollingEnabled(rvAds, false)

        ivsell.setOnClickListener {
            mFragmentNavigation.pushFragment(
                CategoriesListFragment
                    .getInstance(mInt + 1)
            )

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
    override fun onClickAdapterView(objectAtPosition: EventsItems, viewType: Int, position: Int) {
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
        ivbartervWhite.visibility = View.GONE
        ivpostWhite.visibility = View.GONE
        ivrentWhite.visibility = View.GONE
        ivsellWhite.visibility = View.GONE

    }

}
