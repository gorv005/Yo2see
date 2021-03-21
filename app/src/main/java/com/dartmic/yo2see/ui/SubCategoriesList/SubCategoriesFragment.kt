package com.dartmic.yo2see.ui.SubCategoriesList

import android.graphics.Typeface
import android.os.Bundle
import android.text.style.StyleSpan
import android.view.View
import android.widget.ExpandableListAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.model.Category_sub_subTosub.CategoryListItemData
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.addProduct.AddProductFragment
import com.dartmic.yo2see.ui.buycategoriesList.CategoriesListFragment
import com.dartmic.yo2see.ui.buycategoriesList.adapter.CategoriesExpandableListView
import com.dartmic.yo2see.ui.categories.CategoriesViewModel
import com.dartmic.yo2see.ui.home.adapter.AdapterHomeEvents
import com.dartmic.yo2see.ui.product_list.ProductListFragment
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Config
import kotlinx.android.synthetic.main.fragment_categories_list.*
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlinx.android.synthetic.main.fragment_sub_categories.*
import kotlinx.android.synthetic.main.tree.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SubCategoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SubCategoriesFragment : BaseFragment<CategoriesViewModel>(CategoriesViewModel::class) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    internal var titleList: List<String>? = null
    internal var adapter: ExpandableListAdapter? = null
    var type: Int? = 0
    lateinit var categoryListItemData: CategoryListItemData

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
        val listData = data
        titleList = ArrayList(listData.keys)
        type = arguments?.getInt(TYPE)
        categoryListItemData = arguments?.getParcelable(DATA)!!
        init()
        activity?.let {
            adapter =
                CategoriesExpandableListView(it, categoryListItemData?.subCatList!!, type!!)
            subategoriesExpandableListView!!.setAdapter(adapter)

            subategoriesExpandableListView!!.setOnGroupExpandListener { groupPosition ->
                /*Toast.makeText(
                    it,
                    (titleList as ArrayList<String>)[groupPosition] + " List Expanded.",
                    Toast.LENGTH_SHORT
                ).show()*/
            }

            subategoriesExpandableListView!!.setOnGroupCollapseListener { groupPosition ->
                /* Toast.makeText(
                     it,
                     (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.",
                     Toast.LENGTH_SHORT
                 ).show()*/
            }

            subategoriesExpandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
                if (type == Config.Constants.POST_AN_ADD) {
                    mFragmentNavigation.pushFragment(
                        AddProductFragment
                            .getInstance(mInt + 1,categoryListItemData?.subCatList?.get(groupPosition)?.subToSubList?.get(
                                childPosition
                            ))
                    )

                } else {
                    mFragmentNavigation.pushFragment(
                        ProductListFragment
                            .getInstance(
                                mInt + 1,
                                type,"","",
                                categoryListItemData?.subCatList?.get(groupPosition)?.subToSubList?.get(
                                    childPosition
                                )
                            )
                    )
                }
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
        ivBackSubCategories.setOnClickListener {
            activity?.onBackPressed()
        }

    }

    companion object {
        const val TYPE = "type"
        const val DATA = "data"

        @JvmStatic
        fun getInstance(
            instance: Int, type: Int?, categoryListItemData: CategoryListItemData
        ): SubCategoriesFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)
            val fragment = SubCategoriesFragment()
            bundle.putInt(TYPE, type!!)
            bundle.putParcelable(DATA, categoryListItemData)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId() = R.layout.fragment_sub_categories
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

    fun init() {
        tvSubTitleValue.text = categoryListItemData?.categoryName + "/"
        when (type) {
            Config.Constants.SELL -> {

                subategoriesExpandableListView.setChildDivider(activity!!.getDrawable(R.drawable.child_divider_blue))
                ivCurve.setColorFilter(
                    ContextCompat.getColor(activity!!, R.color.blue1)
                )

            }
            Config.Constants.RENT -> {


                ivCurve.setColorFilter(
                    ContextCompat.getColor(activity!!, R.color.voilet)
                )
                subategoriesExpandableListView.setChildDivider(activity!!.getDrawable(R.drawable.child_divider_purple))


            }
            Config.Constants.BARTER -> {
                subategoriesExpandableListView.setChildDivider(activity!!.getDrawable(R.drawable.child_divider_yellow))

                ivCurve.setColorFilter(
                    ContextCompat.getColor(activity!!, R.color.yellow1)
                )

            }
            Config.Constants.POST -> {

                subategoriesExpandableListView.setChildDivider(activity!!.getDrawable(R.drawable.child_divider_blue))
                ivCurve.setColorFilter(
                    ContextCompat.getColor(activity!!, R.color.blue)
                )

            }
            Config.Constants.POST_AN_ADD -> {

                subategoriesExpandableListView.setChildDivider(activity!!.getDrawable(R.drawable.child_divider_red))
                ivCurve.setColorFilter(
                    ContextCompat.getColor(activity!!, R.color.red_a)
                )
                activity?.let {
                    (activity as LandingActivity).updateStatusBarColor(
                        AndroidUtils.getColor(R.color.red_a),
                        2
                    )
                }
            }

        }
    }

}