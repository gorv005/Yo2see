package com.dartmic.yo2see.ui.buycategoriesList

import android.os.Bundle
import android.view.View
import android.widget.ExpandableListAdapter
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.model.EventsItems
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.SubCategoriesList.SubCategoriesFragment
import com.dartmic.yo2see.ui.buycategoriesList.adapter.CategoriesExpandableListView
import com.dartmic.yo2see.ui.categories.CategoriesViewModel
import com.dartmic.yo2see.ui.home.adapter.AdapterHomeEvents
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Config
import kotlinx.android.synthetic.main.fragment_categories_list.*
import kotlinx.android.synthetic.main.fragment_home.*
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
    AdapterViewClickListener<EventsItems> {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var adapterEvents: AdapterHomeEvents? = null

    internal var adapter: ExpandableListAdapter? = null
    internal var titleList: List<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            (activity as LandingActivity).updateStatusBarColor(AndroidUtils.getColor(R.color.blue1),2)
        }

        treeVisibility()


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

        val manager = GridLayoutManager(context, 4)
        rvBuyList.layoutManager = manager
        activity?.let {
            adapterEvents = AdapterHomeEvents(this, it, R.drawable.round_circle_blue)

        }
        rvBuyList.adapter = adapterEvents
        adapterEvents?.submitList(getEvents())

        val listData = data
        titleList = ArrayList(listData.keys)
        activity?.let {
            adapter =
                CategoriesExpandableListView(it, titleList as ArrayList<String>, listData)
            categoriesExpandableListView!!.setAdapter(adapter)
//            ViewCompat.setNestedScrollingEnabled(categoriesExpandableListView, false)

            categoriesExpandableListView!!.setOnGroupExpandListener { groupPosition ->
                /*Toast.makeText(
                    it,
                    (titleList as ArrayList<String>)[groupPosition] + " List Expanded.",
                    Toast.LENGTH_SHORT
                ).show()*/
            }

            categoriesExpandableListView!!.setOnGroupCollapseListener { groupPosition ->
                /* Toast.makeText(
                     it,
                     (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.",
                     Toast.LENGTH_SHORT
                 ).show()*/
            }

            categoriesExpandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
                /* Toast.makeText(
                     it,
                     "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(titleList as ArrayList<String>)[groupPosition]]!!.get(
                         childPosition
                     ),
                     Toast.LENGTH_SHORT
                 ).show()*/
                false
            }

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CategoriesListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun getInstance(instance: Int): CategoriesListFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)
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
    override fun onClickAdapterView(objectAtPosition: EventsItems, viewType: Int, position: Int) {

        when (viewType) {
        Config.AdapterClickViewTypes.CLICK_VIEW_CATEGORY -> {

                this?.let {

                    mFragmentNavigation.pushFragment(
                        SubCategoriesFragment
                            .getInstance(mInt + 1)
                    )

                }

            }
        }


    }

    fun treeVisibility() {
        ivbartervWhite.visibility = View.VISIBLE
        ivpostWhite.visibility = View.VISIBLE
        ivrentWhite.visibility = View.VISIBLE
        ivsellWhite.visibility = View.GONE

    }

}