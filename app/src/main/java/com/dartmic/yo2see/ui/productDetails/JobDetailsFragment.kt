package com.dartmic.yo2see.ui.productDetails

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.model.chat.User
import com.dartmic.yo2see.model.product.job.ListingItemJob
import com.dartmic.yo2see.model.product_info.ListingItem
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.chat_list.NewMessageChatActivity
import com.dartmic.yo2see.ui.home.adapter.TabFragmentAdapter
import com.dartmic.yo2see.ui.home.products.ProductFragment
import com.dartmic.yo2see.ui.productDetails.adapter.ProductImagesAdapter
import com.dartmic.yo2see.ui.product_list.ProductListFragment
import com.dartmic.yo2see.ui.profile.UserProfileActivity
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.gsa.ui.login.ProductListnViewModel
import kotlinx.android.synthetic.main.fragment_job_details.*
import kotlinx.android.synthetic.main.layout_set_user_info_product_detail.*
import pl.pzienowicz.autoscrollviewpager.AutoScrollViewPager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JobDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class JobDetailsFragment : BaseFragment<ProductListnViewModel>(ProductListnViewModel::class),
    OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var type: Int? = 0
    lateinit var listingItem: ListingItemJob
    var clickPos: Int? = 0

    var mGoogleMap: GoogleMap? = null
    var mapFrag: MapView? = null
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
            mapFrag = view.findViewById(R.id.map) as MapView
            mapFrag?.onCreate(savedInstanceState)
            //   mapFrag?.getMap
            mapFrag?.getMapAsync(this)

            btnStartMessage.setOnClickListener {
                fetchCurrentUser()
            }

            /*  tvViewProfile.setOnClickListener {
                  activity?.let {
                      startActivity(UserProfileActivity?.getIntent(it, listingItem,false))
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
            data: ListingItemJob,
            clickPos: Int
        ): JobDetailsFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)
            bundle.putInt(CLICKPOSITION, clickPos)

            val fragment = JobDetailsFragment()
            bundle.putInt(TYPE, type!!)
            bundle.putParcelable(DATA, data!!)
            fragment.arguments = bundle
            return fragment
        }
    }

    fun init() {
        tvProductName.text = listingItem?.subCategoryName
        tvAddress.text = listingItem?.eCity
        tvSalaryDetails.text = AndroidUtils.getCurrencySymbol(AndroidUtils.getCurrencyCode())+listingItem?.ePrice
        tvJoblevel.text = listingItem?.jobLevel+ " ("+listingItem.jobType+")"
        tvDesc.text = listingItem?.eDescription
        tvJobResponsibilities.text = listingItem?.jobResponsibility
        tvRequiredSkill.text = listingItem?.jobSkills

    }

    override fun getLayoutId() = R.layout.fragment_job_details

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
    override fun onMapReady(googleMap: GoogleMap) {
        try {
            mGoogleMap = googleMap
            //  val cameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng(listingItem?.latitude?.toDouble()!!, listingItem?.longitude?.toDouble()!!), 10f)
            //    mGoogleMap!!.animateCamera(cameraUpdate)
            val sydney =
                LatLng(listingItem?.latitude?.toDouble()!!, listingItem?.longitude?.toDouble()!!)
            mGoogleMap?.addMarker(MarkerOptions().position(sydney))
            mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 2f))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}