package com.dartmic.yo2see.ui.addProduct

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.coursion.freakycoder.mediapicker.galleries.Gallery
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.common.ImageProvider
import com.dartmic.yo2see.interfaces.ResultListener
import com.dartmic.yo2see.model.Category_sub_subTosub.SubToSubListItem
import com.dartmic.yo2see.model.add_product.ImageItem
import com.dartmic.yo2see.model.add_product.RentTypeResponse
import com.dartmic.yo2see.model.login.UserList
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.product_list.ProductListFragment
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.*
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gsa.ui.login.AddProductViewModel
import kotlinx.android.synthetic.main.fragment_add_product.*
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlinx.android.synthetic.main.layout_set_barter_info.*
import kotlinx.android.synthetic.main.layout_set_location_info.*
import kotlinx.android.synthetic.main.layout_set_payment.*
import kotlinx.android.synthetic.main.layout_set_rent_sell_info.*
import kotlinx.android.synthetic.main.layout_set_user_info.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import java.io.File
import java.io.FileNotFoundException
import java.lang.Exception
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddProductFragment : BaseFragment<AddProductViewModel>(AddProductViewModel::class),
    LocationListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var subToSubListItem: SubToSubListItem
    val OPEN_MEDIA_PICKER_IMAGE_GALLRY = 2 // Request code
    var ImageList: java.util.ArrayList<ImageItem>? = null
    private var type: String? = ""
    private var isNagotiable: String? = ""
    private var isOpenTodeliver: String? = ""
    private var count: Int = 0
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    var mprovider: String? = null
    var isLocationClicked: Boolean = false

    var imageListURLs: java.util.ArrayList<String>? = null
    var rentTypeList: java.util.ArrayList<RentTypeResponse>? = null
    lateinit var userList: UserList
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImageList = java.util.ArrayList()
        imageListURLs = ArrayList<String>()
        rentTypeList = ArrayList<RentTypeResponse>()

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
        getUser()

        locationManager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS()
        } else {
            getLocation()
        }
        isLocationClicked = false
        tvUseCurrentLocation.setOnClickListener {
            isLocationClicked = true
            getAddress()
        }
        saveProductBtn.setOnClickListener {
            imageListURLs?.clear()
            showProgressDialog()
            count = 0
            if (ImageList?.size!! > 0) {
                uploadImage()
            } else {
                addProduct()
            }
        }
    }


    fun getUser() {
        if (NetworkUtil.isInternetAvailable(activity)) {
            //  listingType = "Rent"

            model.getUser(
                "User List", "" + model?.getUserID()
            )
        }
    }

    fun addProduct() {
        val imageArray = JSONArray(imageListURLs)
        rentTypeList?.clear()
        if (checkboxWeekly.isChecked) {
            rentTypeList?.add(RentTypeResponse("Weekly", etWeekly.text.toString()))
        }
        if (checkboxHourly.isChecked) {
            rentTypeList?.add(RentTypeResponse("Hourly", etHourly.text.toString()))
        }
        if (checkboxMonthly.isChecked) {
            rentTypeList?.add(RentTypeResponse("Monthly", etMonthly.text.toString()))
        }
        if (checkboxYearly.isChecked) {
            rentTypeList?.add(RentTypeResponse("Yearly", etYearly.text.toString()))
        }

        val gson = Gson()

        val listString = gson.toJson(
            rentTypeList,
            object : TypeToken<ArrayList<RentTypeResponse?>?>() {}.type
        )
        val rentArray = JSONArray(listString)

        Log.e("DEBUG json array", imageArray.toString())
        if (type.equals(Config.Constants.TYPE_SELL)) {
            model.addProduct(
                "AddListing",
                subToSubListItem.categoryId,
                subToSubListItem.subCategoryId,
                subToSubListItem.id,
                "",
                type!!,
                etSetPrice.text.toString(),
                etCountry.text.toString(),
                etState.text.toString(),
                etCity.text.toString(),
                etPincode.text.toString(),
                etAddressOne.text.toString(),
                etAddatitle.text.toString(),
                etDescription.text.toString(),
                etSelectItemCondition.text.toString(),
                isNagotiable!!,
                isOpenTodeliver!!,
                etKm.text.toString(),
                "",
                "",
                model.getUserID()!!,
                imageArray,
                rentArray,
                "" + latitude,
                "" + longitude,
                "",
                "",
                "",
                "",
                "",
                "",
                ""
            )
        } else if (type.equals(Config.Constants.TYPE_RENT)) {
            model.addProduct(
                "AddListing",
                subToSubListItem.categoryId,
                subToSubListItem.subCategoryId,
                subToSubListItem.id,
                "",
                type!!,
                "",
                etCountry.text.toString(),
                etState.text.toString(),
                etCity.text.toString(),
                etPincode.text.toString(),
                etAddressOne.text.toString(),
                etAddatitle.text.toString(),
                etDescription.text.toString(),
                etSelectItemCondition.text.toString(),
                isNagotiable!!,
                isOpenTodeliver!!,
                etKm.text.toString(),
                "",
                "",
                model.getUserID()!!,
                imageArray,
                rentArray,
                "" + latitude,
                "" + longitude,
                "",
                "",
                "",
                "",
                "",
                etProductDetails.text.toString(),
                etRentTermAndCondition.text.toString()
            )
        } else if (type.equals(Config.Constants.TYPE_BARTER)) {
            model.addProduct(
                "AddListing",
                subToSubListItem.categoryId,
                subToSubListItem.subCategoryId,
                subToSubListItem.id,
                "",
                type!!,
                "",
                etCountry.text.toString(),
                etState.text.toString(),
                etCity.text.toString(),
                etPincode.text.toString(),
                etAddressOne.text.toString(),
                "",
                "",
                etSelectItemCondition.text.toString(),
                isNagotiable!!,
                isOpenTodeliver!!,
                etKm.text.toString(),
                "",
                "",
                model.getUserID()!!,
                imageArray,
                rentArray,
                "" + latitude,
                "" + longitude,
                etwhatWouldtoLiketoBarter.text.toString(),
                etwhatWouldtoLiketoExchange.text.toString(),
                etExchangeProductTitle.text.toString(),
                etExchangeProductTitleDescription.text.toString(),
                etAdditionalDetails.text.toString(),
                "",
                ""
            )
        }

    }

    fun getStringArray(arr: ArrayList<String>): Array<String?>? {

        // Convert ArrayList to object array
        val objArr = arr.toArray()

        // convert Object array to String array
        return Arrays
            .copyOf(
                objArr, objArr.size,
                Array<String>::class.java
            )
    }

    private fun showImageProviderDialog() {
        if (ImageList?.size == 8) {
            showSnackbar(AndroidUtils.getString(R.string.image_validation), false)
        } else {
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
                            val m = 8 - ImageList?.size!!
                            val intent = Intent(activity, Gallery::class.java)
// Set the title for toolbar
                            intent.putExtra("title", AndroidUtils.getString(R.string.select_images))
// Mode 1 for both images and videos selection, 2 for images only and 3 for videos!
                            intent.putExtra("mode", 2)
                            intent.putExtra("maxSelection", m) // Optional
                            startActivityForResult(intent, OPEN_MEDIA_PICKER_IMAGE_GALLRY)

                        }

                    }
                }
            })
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
        model.uploadImageModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                /*  var obj=ImagePathResponse(it?.path!!)
                Logger.Debug("DEBUG   obj", obj.toString())
                var j=JSONObject(obj.toString())
            //    Log.e("DEBUG   obj sndnbsd", j.toString())*/

                imageListURLs?.add(it?.path!!)

                count++
                if (count == ImageList?.size) {
                    addProduct()
                } else {
                    uploadImage()
                }
            } else {
                showSnackbar(it.message, false)
            }
        })
        model.userViewModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                userList = it?.userList!!
                tvUserName.text = userList?.name
            } else {
                showSnackbar(it.message, false)
            }
        })

        model.addProductModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                hideProgressDialog()
                showSnackbar(it.message, true)

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

    fun uploadImage() {
        if (NetworkUtil.isInternetAvailable(activity)) {
            if (count < ImageList?.size!!) {
                var f = File(RealPathUtil.getRealPath(activity, ImageList?.get(count)?.fileUrl))
                var service = RequestBody.create(MediaType.parse("multipart/form-data"), "Upload")
                var user_id = RequestBody.create(MediaType.parse("multipart/form-data"), "1")
                var type = RequestBody.create(MediaType.parse("multipart/form-data"), "Product")
                var file = RequestBody.create(MediaType.parse("multipart/form-data"), f)

                var pic = MultipartBody.Part.createFormData("images", f.name, file)
                model.uploadImage(
                    service, user_id, type, pic
                )
            }
        }
    }

    fun showImages() {
        ivProduct1.setImageURI(null)
        ivProduct2.setImageURI(null)
        ivProduct3.setImageURI(null)
        ivProduct4.setImageURI(null)
        ivProduct5.setImageURI(null)
        ivProduct6.setImageURI(null)
        ivProduct7.setImageURI(null)
        ivProduct8.setImageURI(null)
        ivRemove1.visibility=View.GONE
        ivRemove2.visibility=View.GONE
        ivRemove3.visibility=View.GONE
        ivRemove4.visibility=View.GONE
        ivRemove5.visibility=View.GONE
        ivRemove6.visibility=View.GONE
        ivRemove7.visibility=View.GONE
        ivRemove8.visibility=View.GONE

        when (ImageList?.size) {


            1 -> {
                ivProduct1.setImageURI(ImageList?.get(0)?.fileUrl)
                ivRemove1.visibility=View.VISIBLE

            }
            2 -> {
                ivProduct1.setImageURI(ImageList?.get(0)?.fileUrl)
                ivProduct2.setImageURI(ImageList?.get(1)?.fileUrl)
                ivRemove1.visibility=View.VISIBLE
                ivRemove2.visibility=View.VISIBLE

            }
            3 -> {
                ivProduct1.setImageURI(ImageList?.get(0)?.fileUrl)
                ivProduct2.setImageURI(ImageList?.get(1)?.fileUrl)
                ivProduct3.setImageURI(ImageList?.get(2)?.fileUrl)
                ivRemove1.visibility=View.VISIBLE
                ivRemove2.visibility=View.VISIBLE
                ivRemove3.visibility=View.VISIBLE

            }
            4 -> {
                ivProduct1.setImageURI(ImageList?.get(0)?.fileUrl)
                ivProduct2.setImageURI(ImageList?.get(1)?.fileUrl)
                ivProduct3.setImageURI(ImageList?.get(2)?.fileUrl)
                ivProduct4.setImageURI(ImageList?.get(3)?.fileUrl)
                ivRemove1.visibility=View.VISIBLE
                ivRemove2.visibility=View.VISIBLE
                ivRemove3.visibility=View.VISIBLE
                ivRemove4.visibility=View.VISIBLE

            }
            5 -> {
                ivProduct1.setImageURI(ImageList?.get(0)?.fileUrl)
                ivProduct2.setImageURI(ImageList?.get(1)?.fileUrl)
                ivProduct3.setImageURI(ImageList?.get(2)?.fileUrl)
                ivProduct4.setImageURI(ImageList?.get(3)?.fileUrl)
                ivProduct5.setImageURI(ImageList?.get(4)?.fileUrl)
                ivRemove1.visibility=View.VISIBLE
                ivRemove2.visibility=View.VISIBLE
                ivRemove3.visibility=View.VISIBLE
                ivRemove4.visibility=View.VISIBLE
                ivRemove5.visibility=View.VISIBLE

            }
            6 -> {
                ivProduct1.setImageURI(ImageList?.get(0)?.fileUrl)
                ivProduct2.setImageURI(ImageList?.get(1)?.fileUrl)
                ivProduct3.setImageURI(ImageList?.get(2)?.fileUrl)
                ivProduct4.setImageURI(ImageList?.get(3)?.fileUrl)
                ivProduct5.setImageURI(ImageList?.get(4)?.fileUrl)
                ivProduct6.setImageURI(ImageList?.get(5)?.fileUrl)
                ivRemove1.visibility=View.VISIBLE
                ivRemove2.visibility=View.VISIBLE
                ivRemove3.visibility=View.VISIBLE
                ivRemove4.visibility=View.VISIBLE
                ivRemove5.visibility=View.VISIBLE
                ivRemove6.visibility=View.VISIBLE

            }
            7 -> {
                ivProduct1.setImageURI(ImageList?.get(0)?.fileUrl)
                ivProduct2.setImageURI(ImageList?.get(1)?.fileUrl)
                ivProduct3.setImageURI(ImageList?.get(2)?.fileUrl)
                ivProduct4.setImageURI(ImageList?.get(3)?.fileUrl)
                ivProduct5.setImageURI(ImageList?.get(4)?.fileUrl)
                ivProduct6.setImageURI(ImageList?.get(5)?.fileUrl)
                ivProduct7.setImageURI(ImageList?.get(6)?.fileUrl)
                ivRemove1.visibility=View.VISIBLE
                ivRemove2.visibility=View.VISIBLE
                ivRemove3.visibility=View.VISIBLE
                ivRemove4.visibility=View.VISIBLE
                ivRemove5.visibility=View.VISIBLE
                ivRemove6.visibility=View.VISIBLE
                ivRemove7.visibility=View.VISIBLE

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
                ivRemove1.visibility=View.VISIBLE
                ivRemove2.visibility=View.VISIBLE
                ivRemove3.visibility=View.VISIBLE
                ivRemove4.visibility=View.VISIBLE
                ivRemove5.visibility=View.VISIBLE
                ivRemove6.visibility=View.VISIBLE
                ivRemove7.visibility=View.VISIBLE
                ivRemove8.visibility=View.VISIBLE

            }
        }
    }

    fun init() {
        rbSell.isChecked = true
        rbYes.isChecked = true
        rbYesDeliver.isChecked = true
        isNagotiable = "yes"
        isOpenTodeliver = "yes"
        sellViews()
        rbYes.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rbYes.isChecked = true
                rbNO.isChecked = false
                isNagotiable = "yes"

            }
        }
        rbNO.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rbYes.isChecked = false
                rbNO.isChecked = true
                isNagotiable = "no"
            }
        }
        rbYesDeliver.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rbYesDeliver.isChecked = true
                rbNoDeliver.isChecked = false
                isOpenTodeliver = "yes"

            }
        }
        rbNoDeliver.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rbYesDeliver.isChecked = false
                rbNoDeliver.isChecked = true
                isOpenTodeliver = "no"
            }
        }
        rbSell.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                sellViews()
            }
        }
        rbRent.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                type = Config.Constants.TYPE_RENT

                rbRent.isChecked = true

                rbSell.isChecked = false
                rbBarter.isChecked = false
                layout_rent_sell.visibility = View.VISIBLE
                layout_barter_info.visibility = View.GONE

                tvSetPrice.visibility = View.GONE
                etSetPrice.visibility = View.GONE
                tvSetPriceHelperText.visibility = View.GONE
                layout_payment.visibility = View.VISIBLE

                tvProductDetails.visibility = View.VISIBLE
                etProductDetails.visibility = View.VISIBLE
                tvProductDetailsHelperText.visibility = View.VISIBLE

                tvRentTermAndCondition.visibility = View.VISIBLE
                etRentTermAndCondition.visibility = View.VISIBLE
                tvRentTermAndConditionHelperText.visibility = View.VISIBLE

                ivCurveAddProduct.setColorFilter(
                    ContextCompat.getColor(activity!!, R.color.voilet)
                )

                activity?.let {
                    (activity as LandingActivity).updateStatusBarColor(
                        AndroidUtils.getColor(R.color.voilet),
                        2
                    )
                    saveProductBtn.setBackgroundResource(R.drawable.btn_app_selected_voilet)

                }
            }
        }
        rbBarter.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                type = Config.Constants.TYPE_BARTER

                rbSell.isChecked = false
                rbRent.isChecked = false
                rbBarter.isChecked = true
                layout_rent_sell.visibility = View.GONE
                layout_barter_info.visibility = View.VISIBLE
                layout_payment.visibility = View.GONE

                tvSelectItemCondition.visibility = View.VISIBLE
                tvSelectItemConditionHelperText.visibility = View.VISIBLE
                etSelectItemCondition.visibility = View.VISIBLE

                ivCurveAddProduct.setColorFilter(
                    ContextCompat.getColor(activity!!, R.color.yellow1)
                )

                activity?.let {
                    (activity as LandingActivity).updateStatusBarColor(
                        AndroidUtils.getColor(R.color.yellow1),
                        2
                    )
                    saveProductBtn.setBackgroundResource(R.drawable.btn_app_selected_yellow)

                }

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
        ivRemove1.setOnClickListener {
            ImageList?.removeAt(0)
            showImages()
        }

    }

    fun sellViews() {
        rbSell.isChecked = true
        type = Config.Constants.TYPE_SELL
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

        tvSetPrice.visibility = View.VISIBLE
        etSetPrice.visibility = View.VISIBLE
        tvSetPriceHelperText.visibility = View.VISIBLE

        layout_payment.visibility = View.GONE

        ivCurveAddProduct.setColorFilter(
            ContextCompat.getColor(activity!!, R.color.blue1)
        )

        activity?.let {
            (activity as LandingActivity).updateStatusBarColor(
                AndroidUtils.getColor(R.color.blue1),
                2
            )
            saveProductBtn.setBackgroundResource(R.drawable.btn_app_selected)
        }
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

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                activity!!, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity!!, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        } else {
            val criteria = Criteria()

            mprovider = locationManager.getBestProvider(criteria, false)
            if (mprovider != null && !mprovider.equals("")) {
                if (ActivityCompat.checkSelfPermission(
                        activity!!,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        activity!!,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                val location = locationManager.getLastKnownLocation(mprovider)
                locationManager?.requestLocationUpdates(mprovider, 5000, 5.0f, this)
                if (location != null)
                    onLocationChanged(location)

            }
            val locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (locationGPS != null) {
                val lat = locationGPS.latitude
                val longi = locationGPS.longitude
                latitude = lat
                longitude = longi
                getAddress()
            } else {
                Toast.makeText(activity!!, "Unable to find location.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_add_product
    override fun onLocationChanged(location: Location?) {
        latitude = location?.latitude!!
        longitude = location?.longitude!!
        getAddress()

    }

    private fun OnGPS() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes",
            DialogInterface.OnClickListener { dialog, which -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    fun getAddress() {
        if (isLocationClicked) {

            try {
                val geocoder: Geocoder
                val addresses: List<Address>
                geocoder = Geocoder(activity, Locale.getDefault())

                addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1
                ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5


                val address: String =
                    addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                val city: String = addresses[0].getLocality()
                val state: String = addresses[0].getAdminArea()
                val country: String = addresses[0].getCountryName()
                val postalCode: String = addresses[0].getPostalCode()
                //  val knownName: String = addresses[0].getFeatureName()

                etAddressOne.setText(address)
                etCity.setText(city)
                etState.setText(state)
                etCountry.setText(country)
                etPincode.setText(postalCode)
            } catch (e: Exception) {

            }
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
                //    Toast.makeText(activity, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                //   Toast.makeText(activity, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}