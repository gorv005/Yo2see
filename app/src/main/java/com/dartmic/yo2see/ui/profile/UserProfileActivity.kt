package com.dartmic.yo2see.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseActivity
import com.dartmic.yo2see.model.product.event.ListingItemEvent
import com.dartmic.yo2see.model.product_info.ListingItem
import com.dartmic.yo2see.ui.signup.RegistrationViewModel
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import com.willy.ratingbar.BaseRatingBar
import kotlinx.android.synthetic.main.activity_user_profile.*


class UserProfileActivity : BaseActivity<RegistrationViewModel>(RegistrationViewModel::class),
    BaseRatingBar.OnRatingChangeListener {
    lateinit var listingItem: ListingItem
    lateinit var listingItemEvent: ListingItemEvent
 var userID="0"
    var rate: String = "1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.run {
            if(getBooleanExtra(ISPRODUCT,false)) {
                listingItem = getParcelableExtra(DATA)!!
                userID=listingItem?.userId
                tvUserName.text = listingItem?.userName
                tvEmail.text = listingItem?.userEmail
                tvLink.text = listingItem?.userMobile
                tvProfileName.text = listingItem?.userName + "'s" + " Profile"
                tvRatingUser.text = "Rate " + listingItem?.userName
                if (listingItem?.usersPhoto != null && !listingItem?.usersPhoto.equals("")) {
                    Glide.with(this@UserProfileActivity).load("https://yo2see.com/app/admin/" + listingItem?.usersPhoto)
                        .placeholder(R.drawable.no_image2).circleCrop()
                        .into(ivProfileL)
                }
            }else{
                listingItemEvent = getParcelableExtra(DATA)!!
                userID=listingItemEvent?.userId!!

                tvUserName.text = listingItemEvent?.userName
                tvEmail.text = listingItemEvent?.userEmail
                tvLink.text = listingItemEvent?.userMobile
                tvProfileName.text = listingItemEvent?.userName + "'s" + " Profile"
                tvRatingUser.text = "Rate " + listingItemEvent?.userName
                if (listingItemEvent?.usersPhoto != null && !listingItemEvent?.usersPhoto.equals("")) {
                    Glide.with(this@UserProfileActivity).load("https://yo2see.com/app/admin/" + listingItemEvent?.usersPhoto)
                        .placeholder(R.drawable.no_image2).circleCrop()
                        .into(ivProfileL)
                }
            }
        }

        ratingBar.setOnRatingChangeListener(this)
        btnSubmit.setOnClickListener {
            rateUser()
        }
        subscribeLoading()
        subscribeUi()
    }

    override fun layout(): Int = R.layout.activity_user_profile


    override fun tag(): String {
        TODO("Not yet implemented")
    }

    override fun title(): String {
        TODO("Not yet implemented")
    }

    override fun titleColor(): Int {
        TODO("Not yet implemented")
    }

    fun rateUser() {
        this?.let { UiUtils.hideSoftKeyboard(it) }

        if (NetworkUtil.isInternetAvailable(this)) {

            model.sendFeedback(
                "UserRating",
                model?.getUserID() + "",
                userID,
                rate
            )
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
                UiUtils.showInternetDialog(
                    this@UserProfileActivity,
                    R.string.something_went_wrong
                )
            }
        })
    }

    private fun subscribeUi() {


        model.rateUserModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                showSnackbar(it.message, true)
                this.let { UiUtils.hideSoftKeyboard(it) }
            } else {
                showSnackbar(it.message, false)

            }
        })
    }

    fun showProgressDialog() {
        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    companion object {
        const val KEY_TAB = "KEY_TAB"
        const val DATA = "data"
        const val ISPRODUCT = "ISPRODUCT"

        fun getIntent(
            context: Context, data: ListingItem,isProduct:Boolean
        ): Intent? {
            val intent = Intent(context, UserProfileActivity::class.java).putExtra(DATA, data).putExtra(ISPRODUCT, isProduct)
            return intent
        }

        fun getIntent(
            context: Context, data: ListingItemEvent,isProduct:Boolean
        ): Intent? {
            val intent = Intent(context, UserProfileActivity::class.java).putExtra(DATA, data).putExtra(ISPRODUCT, isProduct)
            return intent
        }
    }

    override fun onRatingChange(ratingBar: BaseRatingBar?, rating: Float, fromUser: Boolean) {
        rate = rating.toString()
    }

}