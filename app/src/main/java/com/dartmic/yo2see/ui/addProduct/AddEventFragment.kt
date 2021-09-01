package com.dartmic.yo2see.ui.addProduct

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.model.Category_sub_subTosub.SubToSubListItem
import com.dartmic.yo2see.model.list_dropdown.GeneralListItem
import com.dartmic.yo2see.model.login.UserList
import com.dartmic.yo2see.ui.addProduct.adapter.DigitalSpinnerAdapter
import com.dartmic.yo2see.ui.location.MapsActivity
import com.dartmic.yo2see.ui.product_list.ProductListFragment
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Logger
import com.dartmic.yo2see.utils.NetworkUtil
import com.gsa.ui.login.AddProductViewModel
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.fragment_add_event.*
import kotlinx.android.synthetic.main.layout_set_location_info.*
import kotlinx.android.synthetic.main.layout_set_user_info.*
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddEventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddEventFragment : BaseFragment<AddProductViewModel>(AddProductViewModel::class),
    LocationListener, AdapterView.OnItemSelectedListener, TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var subToSubListItem: SubToSubListItem
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    var mprovider: String? = null
    var isLocationClicked: Boolean = false
    lateinit var userList: UserList
    private var eventTypeArray = ArrayList<GeneralListItem>()
    private lateinit var eventsTypeSpinner: DigitalSpinnerAdapter
    var eventType = ""
    var isFrom = false
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subToSubListItem = arguments?.getParcelable(ProductListFragment.DATA)!!
        tvProductPath.text =
            subToSubListItem?.categoryName
        subscribeLoading()
        subscribeUi()
        getUser()
        eventTypeSpinner.onItemSelectedListener = this

        getEventType("Event Type")
        //  etFrom.setText("20-07-2021 10:00")
        // etTo.setText("25-08-2021 10:00")
        tvSearchLocation.setOnClickListener {
            activity!!.let {
                UiUtils.hideSoftKeyboard(it)
                // Call for Location
                startActivityForResult(
                    MapsActivity.getIntent(it,2), 23
                )
            }
        }
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
        etFrom.setOnClickListener {
            isFrom = true
            addEventDate()
        }
        etTo.setOnClickListener {
            isFrom = false
            addEventDate()
        }
        saveProductBtn.setOnClickListener {
            var validatetEventname = AndroidUtils.validateName(etEventname.text.toString())
            var validatetEventDiscription =
                AndroidUtils.validateName(etEventDiscription.text.toString())
            var validateetCost =
                AndroidUtils.validateName(etCost.text.toString())
            var validateDate = null
            AndroidUtils.checkStartEndDateTimeValid(
                etFrom.text.toString(),
                etTo.text.toString()
            )
            var validateAddress = AndroidUtils.validateName(etAddressOne.text.toString())
            var validateetCity = AndroidUtils.validateName(etCity.text.toString())
            var validateetPincode = AndroidUtils.validateName(etPincode.text.toString())
            var validateetState = AndroidUtils.validateName(etState.text.toString())
            var validateetCountry = AndroidUtils.validateName(etCountry.text.toString())
            if (TextUtils.isEmpty(validatetEventname) &&
                TextUtils.isEmpty(validatetEventDiscription) &&
                TextUtils.isEmpty(validateetCost) &&
                TextUtils.isEmpty(validateDate) &&
                TextUtils.isEmpty(validateAddress) &&
                TextUtils.isEmpty(validateetCity) &&
                TextUtils.isEmpty(validateetPincode) &&
                TextUtils.isEmpty(validateetState) &&
                TextUtils.isEmpty(validateetCountry)
            ) {
                addProduct()
            } else {
                etEventname.setError(validatetEventname)
                etEventDiscription.setError(validatetEventDiscription)
                etCost.setError(validateetCost)
                etFrom.setError(validateDate)
                etTo.setError(validateDate)

                etAddressOne.setError(validateAddress)
                etCity.setError(validateetCity)
                etPincode.setError(validateetPincode)
                etState.setError(validateetState)
                etCountry.setError(validateetCountry)
            }
        }
        tvCost.setText(
            AndroidUtils.getString(R.string.cost) + " (" + AndroidUtils.getCurrencySymbol(
                AndroidUtils.getCurrencyCode()
            ) + ")"
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 23) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                etAddressOne.setText(data.getStringExtra(MapsActivity.KEY_ADDRESS1))
                etPincode.setText(data.getStringExtra(MapsActivity.KEY_PINCODE))
                etCountry.setText(data.getStringExtra(MapsActivity.KEY_COUNTRY))
                etState.setText(data.getStringExtra(MapsActivity.KEY_STATE))
                etCity.setText(data.getStringExtra(MapsActivity.KEY_CITY))
                latitude = data.getDoubleExtra(MapsActivity.KEY_LATITUDE, 0.0)
                latitude = data.getDoubleExtra(MapsActivity.KEY_LONGITUDE, 0.0)

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

    fun getEventType(type: String) {
        if (NetworkUtil.isInternetAvailable(activity)) {
            //  listingType = "Rent"

            model.getJobType(
                "General Value", type
            )
        }
    }


    fun addProduct() {
        model.addEvent(
            "AddEvent",
            model.getUserID()!!,
            subToSubListItem.categoryId,
            subToSubListItem.subCategoryId,
            subToSubListItem.id,
            etCost.text.toString(),
            etCountry.text.toString(),
            etState.text.toString(),
            etCity.text.toString(),
            etPincode.text.toString(),
            etAddressOne.text.toString(),
            etEventname.text.toString(),
            etEventDiscription.text.toString(),
            etFrom.text.toString(),
            etTo.text.toString(),
            "" + longitude,
            "" + latitude,
            eventType

        )

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
        model.userViewModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                userList = it?.userList!!
                tvUserName.text = userList?.name
            } else {
                showSnackbar(it.message, false)
            }
        })
        model.listOFJobType.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                eventTypeArray = it?.generalList!!

                var listC1: ArrayList<Boolean> = ArrayList()
                for (item in eventTypeArray) {
                    listC1.add(true)
                }


                eventsTypeSpinner = DigitalSpinnerAdapter(activity!!, eventTypeArray, listC1)
                eventTypeSpinner.adapter = eventsTypeSpinner
            } else {
                showSnackbar(it.message, false)
            }
        })

        model.addJobProductModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                hideProgressDialog()
                showSnackbar(it.message, true)
                Handler(Looper.getMainLooper()).postDelayed({
                    activity?.onBackPressed()

                }, 1000)
            } else {
                showSnackbar(it.message, false)
            }
        })
    }

    fun showProgressDialog() {
        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    companion object {
        @JvmStatic
        fun getInstance(
            instance: Int, categoryListItemData: SubToSubListItem?
        ): AddEventFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)
            bundle.putParcelable(ProductListFragment.DATA, categoryListItemData)
            val fragment = AddEventFragment()
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
                val location = locationManager.getLastKnownLocation(mprovider!!)
                locationManager?.requestLocationUpdates(mprovider!!, 5000, 5.0f, this)
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

    override fun getLayoutId() = R.layout.fragment_add_event
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

    override fun onLocationChanged(location: Location) {
        latitude = location?.latitude!!
        longitude = location?.longitude!!
        getAddress()
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.eventTypeSpinner -> {
                eventType = eventTypeArray.get(position).genValue
            }


        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    fun addEventDate() {
        val now = Calendar.getInstance()
        val dpd = DatePickerDialog.newInstance(
            this,
            now[Calendar.YEAR],  // Initial year selection
            now[Calendar.MONTH],  // Initial month selection
            now[Calendar.DAY_OF_MONTH] // Inital day selection
        )
// If you're calling this from a support Fragment
// If you're calling this from a support Fragment
        dpd.show(fragmentManager!!, "Datepickerdialog")
        //    var simpleDateFormat = SimpleDateFormat("dd/mm/YYYY HH:mm", Locale.getDefault());


        /* var calendarMin = Calendar.getInstance()
         val now = Date()
         calendarMin.setTime(now); // Set min now
         var minDate = calendarMin.getTime()

      var d=   DoubleDateAndTimePickerDialog.Builder(activity)

          d.setTimeZone(TimeZone.getDefault())
             d.minutesStep(15).mustBeOnFuture().secondDateAfterFirst(true).tab0Date(now)
                 .tab1Date(Date(now.getTime() + TimeUnit.HOURS.toMillis(1)))

                 //.bottomSheet()
             //.curved()
             //.stepSizeMinutes(15)
             .title(AndroidUtils.getString(R.string.event_time)).minDateRange(minDate)
             .tab0Text(AndroidUtils.getString(R.string.from))
             .tab1Text(AndroidUtils.getString(R.string.to))
             .listener {
                 etTo.setText(simpleDateFormat.format(it?.get(1)))
                 etFrom.setText(simpleDateFormat.format(it?.get(0)))

             }.display()*/

    }

    var fromTime = ""
    var toTime = ""
    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {
        if (isFrom) {
            fromTime = fromTime + " " + hourOfDay + ":" + minute
            etFrom.setText(fromTime)
        } else {
            toTime = toTime + " " + hourOfDay + ":" + minute
            etTo.setText(toTime)
        }
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        if (isFrom) {
            fromTime = "" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year
        } else {
            toTime = "" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year

        }
        val now = Calendar.getInstance()
        var tpd = TimePickerDialog.newInstance(
            this,
            now.get(Calendar.HOUR_OF_DAY),
            now.get(Calendar.MINUTE),
            true
        )
        tpd.show(fragmentManager!!, "Timepickerdialog");

    }
}