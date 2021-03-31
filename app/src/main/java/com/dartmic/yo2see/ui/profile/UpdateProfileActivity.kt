package com.dartmic.yo2see.ui.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.coursion.freakycoder.mediapicker.galleries.Gallery
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseActivity
import com.dartmic.yo2see.common.ImageProvider
import com.dartmic.yo2see.interfaces.ResultListener
import com.dartmic.yo2see.ui.signup.RegistrationViewModel
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.*
import com.dartmic.yo2see.utils.DialogHelper
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_update_profile.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileNotFoundException

class UpdateProfileActivity : BaseActivity<RegistrationViewModel>(RegistrationViewModel::class) {
    lateinit var user: com.dartmic.yo2see.model.login.UserList
    val OPEN_MEDIA_PICKER_IMAGE_GALLRY = 2 // Request code
    var fileUrl: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.run {
            user = getParcelableExtra(DATA)
            etName.setText(user?.name)
            etPhonenumber.setText(user?.phone)
            etEmail.setText(user?.email)
            etProfileLink.setText(user?.usertype)
            if (user?.picture != null&& !user?.picture.equals("")) {
                Glide.with(this@UpdateProfileActivity).load("https://yo2see.com/app/admin/"+user?.picture)
                    .placeholder(R.drawable.ic_clothing_white).circleCrop()
                    .into(ivProfile)
            }
        }
        tvChangeImage.setOnClickListener {
            showImageProviderDialog()
        }
        btnUpdateProfile.setOnClickListener {
            if (fileUrl != null) {
                uploadImage()
            } else {
                updateProfile(user.picture!!)
            }
        }
        ivBackUpdateProfile.setOnClickListener {
            onBackPressed()
        }
        subscribeLoading()
        subscribeUi()
    }

    fun updateProfile(pic: String) {
        this?.let { UiUtils.hideSoftKeyboard(it) }

        if (NetworkUtil.isInternetAvailable(this)) {
            model.updateUser(
                "Update Profile",
                user.userId!!,
                etName.text.toString(),
                etEmail.text.toString(),
                etPhonenumber.text.toString(),
                pic
            )
        }
    }

    companion object {
        const val KEY_TAB = "KEY_TAB"
        const val DATA = "DATA"

        fun getIntent(context: Context, data: com.dartmic.yo2see.model.login.UserList): Intent? {
            val intent = Intent(context, UpdateProfileActivity::class.java)
            intent.putExtra(DATA, data)
            return intent
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
                    this@UpdateProfileActivity,
                    R.string.something_went_wrong
                )
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Check which request we're responding to
        if (requestCode == OPEN_MEDIA_PICKER_IMAGE_GALLRY) {

            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK && data != null) {
                val selectionResult = data.getStringArrayListExtra("result")
                selectionResult.forEach {
                    try {
                        Log.d("MyApp", "Image Path : " + it)
                        val uriFromPath = Uri.fromFile(File(it))
                        fileUrl=uriFromPath
                        ivProfile.setImageURI(uriFromPath)

                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }

                }
            }

        } else {
            if (resultCode == Activity.RESULT_OK) {


                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data
                fileUrl=fileUri
                ivProfile.setImageURI(fileUri)

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showImageProviderDialog() {

        DialogHelper.showChooseAppDialog(this!!, object : ResultListener<ImageProvider> {
            override fun onResult(t: ImageProvider?) {
                t?.let {
                    if (t.equals(ImageProvider.CAMERA)) {
                        ImagePicker.with(this@UpdateProfileActivity)
                            .crop()
                            .cameraOnly()                    //Crop image(Optional), Check Customization for more option
                            .compress(1024)     //Final image size will be less than 1 MB(Optional).maxResultSize(
                            /*.maxResultSize(
                            1080,
                            1080
                        )  */ //Final image resolution will be less than 1080 x 1080(Optional)
                            .start()
                    } else {

                        val intent = Intent(this@UpdateProfileActivity, Gallery::class.java)
// Set the title for toolbar
                        intent.putExtra(
                            "title",
                            AndroidUtils.getString(R.string.select_images)
                        )
// Mode 1 for both images and videos selection, 2 for images only and 3 for videos!
                        intent.putExtra("mode", 2)
                        intent.putExtra("maxSelection", 1) // Optional
                        startActivityForResult(intent, OPEN_MEDIA_PICKER_IMAGE_GALLRY)
                    }


                }
            }
        })
    }

    fun uploadImage() {
        if (NetworkUtil.isInternetAvailable(this)) {
            if (fileUrl != null) {
                var f = File(RealPathUtil.getRealPath(this, fileUrl))
                var service = RequestBody.create(MediaType.parse("multipart/form-data"), "Upload")
                var user_id = RequestBody.create(MediaType.parse("multipart/form-data"), "1")
                var type = RequestBody.create(MediaType.parse("multipart/form-data"), "Profile")
                var file = RequestBody.create(MediaType.parse("multipart/form-data"), f)

                var pic = MultipartBody.Part.createFormData("images", f.name, file)
                model.uploadImage(
                    service, user_id, type, pic
                )
            }
        }
    }

    private fun subscribeUi() {

        model.uploadImageModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                updateProfile(it?.path!!)
                //  imageListURLs?.add(it?.path!!)

            } else {
                showSnackbar(it.message, false)
            }
        })

        model.userInfoSaveViewModel.observe(this, Observer {
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

    override fun layout(): Int = R.layout.activity_update_profile


    override fun tag(): String {
        TODO("Not yet implemented")
    }

    override fun title(): String {
        TODO("Not yet implemented")
    }

    override fun titleColor(): Int {
        TODO("Not yet implemented")
    }
}