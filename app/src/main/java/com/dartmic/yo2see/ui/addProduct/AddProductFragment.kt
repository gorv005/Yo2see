package com.dartmic.yo2see.ui.addProduct

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.coursion.freakycoder.mediapicker.galleries.Gallery
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.common.ImageProvider
import com.dartmic.yo2see.interfaces.ResultListener
import com.dartmic.yo2see.model.Category_sub_subTosub.SubToSubListItem
import com.dartmic.yo2see.model.add_product.ImageItem
import com.dartmic.yo2see.ui.product_list.ProductListFragment
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.*
import com.dartmic.yo2see.utils.DialogHelper
import com.github.dhaval2404.imagepicker.ImagePicker
import com.gsa.ui.login.AddProductViewModel
import com.gsa.ui.login.ProductListnViewModel
import kotlinx.android.synthetic.main.fragment_add_product.*
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlinx.android.synthetic.main.layout_set_rent_sell_info.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileNotFoundException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddProductFragment : BaseFragment<AddProductViewModel>(AddProductViewModel::class) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var subToSubListItem: SubToSubListItem
    val OPEN_MEDIA_PICKER_IMAGE_GALLRY = 2 // Request code
    var ImageList: ArrayList<ImageItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImageList = ArrayList()

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subToSubListItem = arguments?.getParcelable(ProductListFragment.DATA)!!
        tvProductPath.text =
            subToSubListItem?.categoryName + "/" + subToSubListItem?.subCategoryName + "/" + subToSubListItem?.subSubcategoryName
        init()
        subscribeLoading()
        subscribeUi()
        saveProductBtn.setOnClickListener {

            var file = File(RealPathUtil.getRealPath(activity, ImageList?.get(0)?.fileUrl))
            uploadImage(file)
        }
    }


    private fun showImageProviderDialog() {
        DialogHelper.showChooseAppDialog(activity!!, object : ResultListener<ImageProvider> {
            override fun onResult(t: ImageProvider?) {
                t?.let {
                    if (t.equals(ImageProvider.CAMERA)) {
                        ImagePicker.with(activity!!)
                            .crop()
                            .cameraOnly()                    //Crop image(Optional), Check Customization for more option
                            .compress(1024)     //Final image size will be less than 1 MB(Optional).maxResultSize(
                            /*.maxResultSize(
                                1080,
                                1080
                            )  */ //Final image resolution will be less than 1080 x 1080(Optional)
                            .start()
                    } else {
                        val intent = Intent(activity, Gallery::class.java)
// Set the title for toolbar
                        intent.putExtra("title", AndroidUtils.getString(R.string.select_images))
// Mode 1 for both images and videos selection, 2 for images only and 3 for videos!
                        intent.putExtra("mode", 2)
                        intent.putExtra("maxSelection", 8) // Optional
                        startActivityForResult(intent, OPEN_MEDIA_PICKER_IMAGE_GALLRY)

                    }

                }
            }
        })
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
        model.uploadImageModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {

            } else {
                showSnackbar(it.message, false)
            }
        })


    }

    fun showProgressDialog() {
        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
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
                        ImageList?.add(
                            ImageItem(
                                uriFromPath!!,
                                0,
                                ""
                            )
                        )

                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }

                }

                showImages()
            }

        } else {
            if (resultCode == Activity.RESULT_OK) {


                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data
                ImageList?.add(ImageItem(fileUri!!, 0, ""))
                showImages()


            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(activity, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun uploadImage(f: File) {
        if (NetworkUtil.isInternetAvailable(activity)) {
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

    fun showImages() {
        when (ImageList?.size) {

            1 -> {
                ivProduct1.setImageURI(ImageList?.get(0)?.fileUrl)

            }
            2 -> {
                ivProduct1.setImageURI(ImageList?.get(0)?.fileUrl)
                ivProduct2.setImageURI(ImageList?.get(1)?.fileUrl)

            }
            3 -> {
                ivProduct1.setImageURI(ImageList?.get(0)?.fileUrl)
                ivProduct2.setImageURI(ImageList?.get(1)?.fileUrl)
                ivProduct3.setImageURI(ImageList?.get(2)?.fileUrl)
            }
            4 -> {
                ivProduct1.setImageURI(ImageList?.get(0)?.fileUrl)
                ivProduct2.setImageURI(ImageList?.get(1)?.fileUrl)
                ivProduct3.setImageURI(ImageList?.get(2)?.fileUrl)
                ivProduct4.setImageURI(ImageList?.get(3)?.fileUrl)
            }
            5 -> {
                ivProduct1.setImageURI(ImageList?.get(0)?.fileUrl)
                ivProduct2.setImageURI(ImageList?.get(1)?.fileUrl)
                ivProduct3.setImageURI(ImageList?.get(2)?.fileUrl)
                ivProduct4.setImageURI(ImageList?.get(3)?.fileUrl)
                ivProduct5.setImageURI(ImageList?.get(4)?.fileUrl)

            }
            6 -> {
                ivProduct1.setImageURI(ImageList?.get(0)?.fileUrl)
                ivProduct2.setImageURI(ImageList?.get(1)?.fileUrl)
                ivProduct3.setImageURI(ImageList?.get(2)?.fileUrl)
                ivProduct4.setImageURI(ImageList?.get(3)?.fileUrl)
                ivProduct5.setImageURI(ImageList?.get(4)?.fileUrl)
                ivProduct6.setImageURI(ImageList?.get(5)?.fileUrl)

            }
            7 -> {
                ivProduct1.setImageURI(ImageList?.get(0)?.fileUrl)
                ivProduct2.setImageURI(ImageList?.get(1)?.fileUrl)
                ivProduct3.setImageURI(ImageList?.get(2)?.fileUrl)
                ivProduct4.setImageURI(ImageList?.get(3)?.fileUrl)
                ivProduct5.setImageURI(ImageList?.get(4)?.fileUrl)
                ivProduct6.setImageURI(ImageList?.get(5)?.fileUrl)
                ivProduct7.setImageURI(ImageList?.get(6)?.fileUrl)

            }
            8 -> {
                ivProduct1.setImageURI(ImageList?.get(0)?.fileUrl)
                ivProduct2.setImageURI(ImageList?.get(1)?.fileUrl)
                ivProduct3.setImageURI(ImageList?.get(2)?.fileUrl)
                ivProduct4.setImageURI(ImageList?.get(3)?.fileUrl)
                ivProduct5.setImageURI(ImageList?.get(4)?.fileUrl)
                ivProduct6.setImageURI(ImageList?.get(5)?.fileUrl)
                ivProduct7.setImageURI(ImageList?.get(6)?.fileUrl)
                ivProduct8.setImageURI(ImageList?.get(7)?.fileUrl)
            }
        }
    }

    fun init() {
        rbSell.isChecked = true
        sellViews()
        rbSell.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                sellViews()
            }
        }
        rbRent.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rbRent.isChecked = true

                rbSell.isChecked = false
                rbBarter.isChecked = false
                layout_rent_sell.visibility = View.VISIBLE
                layout_barter_info.visibility = View.GONE

                tvSetPrice.visibility = View.GONE
                etSetPrice.visibility = View.GONE
                tvSetPriceHelperText.visibility = View.GONE
                layout_payment.visibility = View.VISIBLE
            }
        }
        rbBarter.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rbSell.isChecked = false
                rbRent.isChecked = false
                rbBarter.isChecked = true
                layout_rent_sell.visibility = View.GONE
                layout_barter_info.visibility = View.VISIBLE
            }
        }
        ivProduct1.setOnClickListener { showImageProviderDialog() }
        ivProduct2.setOnClickListener { showImageProviderDialog() }
        ivProduct3.setOnClickListener { showImageProviderDialog() }
        ivProduct4.setOnClickListener { showImageProviderDialog() }
        ivProduct5.setOnClickListener { showImageProviderDialog() }
        ivProduct6.setOnClickListener { showImageProviderDialog() }
        ivProduct7.setOnClickListener { showImageProviderDialog() }
        ivProduct8.setOnClickListener { showImageProviderDialog() }

    }

    fun sellViews() {
        rbSell.isChecked = true

        rbRent.isChecked = false
        rbBarter.isChecked = false
        layout_rent_sell.visibility = View.VISIBLE
        layout_barter_info.visibility = View.GONE

        tvProductDetailsHelperText.visibility = View.GONE
        etProductDetails.visibility = View.GONE
        tvProductDetails.visibility = View.GONE

        etRentTermAndCondition.visibility = View.GONE
        tvRentTermAndCondition.visibility = View.GONE
        tvRentTermAndConditionHelperText.visibility = View.GONE

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

    override fun getLayoutId() = R.layout.fragment_add_product

}