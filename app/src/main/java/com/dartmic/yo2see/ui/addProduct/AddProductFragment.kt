package com.dartmic.yo2see.ui.addProduct

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.model.Category_sub_subTosub.SubToSubListItem
import com.dartmic.yo2see.ui.product_list.ProductListFragment
import kotlinx.android.synthetic.main.fragment_add_product.*
import kotlinx.android.synthetic.main.fragment_product_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddProductFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var subToSubListItem: SubToSubListItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subToSubListItem = arguments?.getParcelable(ProductListFragment.DATA)!!
        tvProductPath.text =
            subToSubListItem?.categoryName + "/" + subToSubListItem?.subCategoryName + "/" + subToSubListItem?.subSubcategoryName

    }

    companion object {
        const val TYPE = "type"
        const val DATA = "data"

        @JvmStatic
        fun getInstance(
            instance: Int, categoryListItemData: SubToSubListItem?
        ): AddProductFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)
            bundle.putParcelable(ProductListFragment.DATA, categoryListItemData)
            val fragment = AddProductFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}