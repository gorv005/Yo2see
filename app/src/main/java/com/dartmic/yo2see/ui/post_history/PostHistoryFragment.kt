package com.dartmic.yo2see.ui.post_history

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.model.EventsItems
import com.dartmic.yo2see.model.chat.ChatMessage
import com.dartmic.yo2see.model.chat.User
import com.dartmic.yo2see.model.my_history.MyListingItem
import com.dartmic.yo2see.model.product_info.ListingItem
import com.dartmic.yo2see.ui.chat_list.ChatListFragment
import com.dartmic.yo2see.ui.chat_list.NewMessageChatActivity
import com.dartmic.yo2see.ui.chat_list.adapter.AdapterChat
import com.dartmic.yo2see.ui.chat_list.adapter.LatestMessageRow
import com.dartmic.yo2see.ui.post_history.adapter.AdapterPostHistory
import com.dartmic.yo2see.ui.signup.SignUpActivity
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Config
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.gsa.ui.login.ProductListnViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_post_history.*
import kotlinx.android.synthetic.main.fragment_product_list.*
import org.json.JSONArray

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PostHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PostHistoryFragment : BaseFragment<ProductListnViewModel>(ProductListnViewModel::class),
    AdapterViewClickListener<MyListingItem> {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var adapterPostHistory: AdapterPostHistory
    private var listingItemList = ArrayList<MyListingItem>()
    var pos_ = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        // performLogin()
    }

    override fun getLayoutId() = R.layout.fragment_post_history


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var manager = LinearLayoutManager(
            activity!!,
            LinearLayoutManager.VERTICAL, false
        )
        rvPostHistory.layoutManager = manager
        activity?.let {
            adapterPostHistory = AdapterPostHistory(this, it)
        }
        rvPostHistory.adapter = adapterPostHistory

        getProductData()
        subscribeLoading()
        subscribeUi()

    }

    fun getProductData() {
        if (NetworkUtil.isInternetAvailable(activity)) {

            model.getPostHistory(
                "MyList",
                model?.getUserID()!!
            )

        }
    }

    fun deletePost(pos: Int) {
        pos_ = pos
        if (NetworkUtil.isInternetAvailable(activity)) {

            model.deleteHistory(
                "DeleteList",
                model?.getUserID()!!,
                listingItemList?.get(pos)?.tableId!!,
                listingItemList?.get(pos)?.tableName!!
            )

        }
    }

    override fun onClickAdapterView(objectAtPosition: MyListingItem, viewType: Int, position: Int) {
        when (viewType) {
            Config.AdapterClickViewTypes.CLICK_VIEW_DELETE -> {
                this?.let {
                    deletePost(position)

                }
            }

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
        model.productHistory1.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                listingItemList = it?.myListing!!
                adapterPostHistory?.submitList(listingItemList)
                adapterPostHistory?.notifyDataSetChanged()
                //runLayoutAnimation(rvProductJobList)

            } else {
                showSnackbar(it.message, false)
            }
        })

        model.deleteHistory.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                showSnackbar(it.message, true)
                listingItemList.removeAt(pos_)
                adapterPostHistory?.notifyDataSetChanged()

            } else {
                showSnackbar(it.message, false)
            }
        })

    }

    fun showProgressDialog() {
        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }


    companion object {
        val TAG = PostHistoryFragment::class.java.simpleName!!
        var currentUser: User? = null

        fun getInstance(instance: Int): PostHistoryFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)

            val fragment = PostHistoryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


}