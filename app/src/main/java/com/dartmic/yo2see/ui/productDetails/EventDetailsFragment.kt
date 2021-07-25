package com.dartmic.yo2see.ui.productDetails

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.model.chat.User
import com.dartmic.yo2see.model.product.event.ListingItemEvent
import com.dartmic.yo2see.model.product.job.ListingItemJob
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.chat_list.NewMessageChatActivity
import com.dartmic.yo2see.ui.product_list.ProductListFragment
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Logger
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.gsa.ui.login.ProductListnViewModel
import kotlinx.android.synthetic.main.fragment_event_details.*
import kotlinx.android.synthetic.main.layout_set_user_info_product_detail.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EventDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EventDetailsFragment : BaseFragment<ProductListnViewModel>(ProductListnViewModel::class) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var type: Int? = 0
    lateinit var listingItem: ListingItemEvent
    var clickPos: Int? = 0

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

    /* fun getProductDetails() {
         if (NetworkUtil.isInternetAvailable(activity)) {
             //  listingType = "Rent"

             model.getProductDetails(
                 "Detail", model.getUserID()!!, listingItem.id
             )
         }
     }*/

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
        model.productDetailsViewModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status.equals("true")) {

            } else {
                showSnackbar(it.message, false)
            }
        })


    }

    fun showProgressDialog() {
        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    private var indicatorWidth = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            type = arguments?.getInt(ProductListFragment.TYPE)
            clickPos = arguments?.getInt(CLICKPOSITION)

            listingItem = arguments?.getParcelable(DATA)!!


            btnStartMessage.setOnClickListener {
                fetchCurrentUser()
            }

            /*  tvViewProfile.setOnClickListener {
                  activity?.let {
                      startActivity(UserProfileActivity?.getIntent(it, listingItem))
                  }
              }*/
            init()
            subscribeUi()
            subscribeLoading()
            tvUserName.setText(listingItem.userName)
            //  getProductDetails()


        } catch (e: Exception) {

        }
        ivBackDetails.setOnClickListener {
            activity?.onBackPressed()
        }


    }


    companion object {
        const val TYPE = "type"
        const val DATA = "data"
        var currentUser: User? = null
        const val CLICKPOSITION = "CLICKPostion"

        fun getInstance(
            instance: Int,
            type: Int?,
            data: ListingItemEvent,
            clickPos: Int
        ): EventDetailsFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)
            bundle.putInt(CLICKPOSITION, clickPos)

            val fragment = EventDetailsFragment()
            bundle.putInt(TYPE, type!!)
            bundle.putParcelable(DATA, data!!)
            fragment.arguments = bundle
            return fragment
        }
    }

    fun init() {
        tvProductName.text = listingItem?.eTitle
        tvAddress.text = listingItem?.eCity
        tvSalaryDetails.text = listingItem?.ePrice
        tvJoblevel.text = listingItem?.eventType
        tvDesc.text = listingItem?.eDescription
        tvEventDate.text = HtmlCompat.fromHtml(
            "From " +
                    "<b>" + listingItem?.ePublishDatetime + "</b>" + " " + "To " + "<b>" + listingItem?.eExpiryDatetime + "</b>",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

    }

    override fun getLayoutId() = R.layout.fragment_event_details

    private fun fetchCurrentUser() {
        //    val uid = "hUJc668PNFeZTtyYMZPS0lpdjv93"
        val uid = listingItem?.userUID
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                currentUser = dataSnapshot.getValue(User::class.java)
                val intent = Intent(activity, NewMessageChatActivity::class.java)

                intent.putExtra(NewMessageChatActivity.USER_KEY, currentUser)
                //     intent.putExtra(NewMessageChatActivity.UID, uid)

                startActivity(intent)
            }

        })
    }
}