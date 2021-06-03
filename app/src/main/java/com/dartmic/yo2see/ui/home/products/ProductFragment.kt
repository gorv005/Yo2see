package com.dartmic.yo2see.ui.home.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.model.Category_sub_subTosub.CategoryListItemData
import com.dartmic.yo2see.model.EventsItems
import com.dartmic.yo2see.model.categories.CategoryListItem
import com.dartmic.yo2see.ui.home.adapter.AdapterHomeData
import com.dartmic.yo2see.ui.postAdd.PostAnAddFragment
import com.dartmic.yo2see.utils.AndroidUtils
import kotlinx.android.synthetic.main.fragment_product.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductFragment : Fragment(),
    AdapterViewClickListener<CategoryListItemData> {

    private var adapterProducts: AdapterHomeData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvProducts.setHasFixedSize(true);
        rvProducts.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.HORIZONTAL
            )
        )
        rvProducts.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        val manager1 = GridLayoutManager(context, 3)
        rvProducts.layoutManager = manager1
        activity?.let {
            adapterProducts = AdapterHomeData(this, it, R.drawable.round_circle_light_blue)

        }
        rvProducts.adapter = adapterProducts
        adapterProducts?.submitList(getEvents())
        adapterProducts?.notifyDataSetChanged()
       // ViewCompat.setNestedScrollingEnabled(rvProducts, false)
    }

    companion object {
        const val TYPE = "type"

        @JvmStatic
        fun getInstance(): ProductFragment {
            val bundle = Bundle()
            val fragment = ProductFragment()
            fragment.arguments = bundle
            return fragment
        }

    }


    fun getEvents(): ArrayList<CategoryListItemData> {
        AndroidUtils.getString(R.string.jobs)
        val events = arrayListOf<CategoryListItemData>()
        return events

    }


    override fun onClickAdapterView(
        objectAtPosition: CategoryListItemData,
        viewType: Int,
        position: Int
    ) {
        TODO("Not yet implemented")
    }
}