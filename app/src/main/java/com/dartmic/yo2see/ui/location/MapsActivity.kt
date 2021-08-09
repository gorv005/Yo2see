package com.dartmic.yo2see.ui.location

import `in`.madapps.placesautocomplete.PlaceAPI
import `in`.madapps.placesautocomplete.adapter.PlacesAutoCompleteAdapter
import `in`.madapps.placesautocomplete.listener.OnPlacesDetailsListener
import `in`.madapps.placesautocomplete.model.Address
import `in`.madapps.placesautocomplete.model.Place
import `in`.madapps.placesautocomplete.model.PlaceDetails
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dartmic.yo2see.R
import com.dartmic.yo2see.managers.PreferenceManager
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Config
import com.google.android.gms.location.*
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.custon_toolbar_layout.*
import org.jetbrains.anko.Android

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    var mGoogleMap: GoogleMap? = null
    var mapFrag: SupportMapFragment? = null
    var mLocationRequest: LocationRequest? = null
    var mLastLocation: Location? = null
    var mCurrLocationMarker: Marker? = null
    var mFusedLocationClient: FusedLocationProviderClient? = null

    val placesApi = PlaceAPI.Builder()
        .apiKey(AndroidUtils.getString(R.string.google_maps_key))
        .build(this@MapsActivity)

    var street = ""
    var city = ""
    var state = ""
    var country = ""
    var zipCode = ""
    var type1 = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        intent?.run {
            type1 = getIntExtra(KEY_TYPE, 0)!!
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mapFrag = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFrag!!.getMapAsync(this)
        autoCompleteEditText.setAdapter(PlacesAutoCompleteAdapter(this, placesApi))
        tvTitle.setText(AndroidUtils.getString(R.string.location))
        autoCompleteEditText.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val place = parent.getItemAtPosition(position) as Place
                autoCompleteEditText.setText(place.description)
                getPlaceDetails(place.id, place.description)
            }
        ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap

        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = 120000 // two minute interval

        mLocationRequest!!.fastestInterval = 120000
        mLocationRequest!!.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                //Location Permission already granted
                mFusedLocationClient!!.requestLocationUpdates(
                    mLocationRequest, mLocationCallback,
                    Looper.myLooper()
                )
                mGoogleMap!!.isMyLocationEnabled = true
            } else {
                //Request Location Permission
                checkLocationPermission()
            }
        } else {
            mFusedLocationClient!!.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
            )
            mGoogleMap!!.isMyLocationEnabled = true
        }
    }

    var mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val locationList = locationResult.locations
            if (locationList.size > 0) {
                //The last location in the list is the newest
                val location = locationList[locationList.size - 1]
                mLastLocation = location
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker!!.remove()
                }

                //move map camera
                val latLng = LatLng(location.latitude, location.longitude)
                val cameraPosition =
                    CameraPosition.Builder().target(LatLng(latLng.latitude, latLng.longitude))
                        .zoom(16f).build()
                mGoogleMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            }
        }
    }

    val MY_PERMISSIONS_REQUEST_LOCATION = 99
    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(this)
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton(
                        "OK"
                    ) { dialogInterface, i -> //Prompt the user once explanation has been shown
                        ActivityCompat.requestPermissions(
                            this@MapsActivity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            MY_PERMISSIONS_REQUEST_LOCATION
                        )
                    }
                    .create()
                    .show()
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        mFusedLocationClient!!.requestLocationUpdates(
                            mLocationRequest,
                            mLocationCallback, Looper.myLooper()
                        )
                        mGoogleMap!!.isMyLocationEnabled = true
                    }
                } else {
                    // if not allow a permission, the application will exit
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_HOME)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                    System.exit(0)
                }
            }
        }

    }

    private fun getPlaceDetails(placeId: String, placedesc: String) {

        placesApi.fetchPlaceDetails(placeId, object :
            OnPlacesDetailsListener {
            override fun onError(errorMessage: String) {
                try {
                    Log.e("DEBUG", "error" + errorMessage)
                    //Toast.makeText(this@PlacesActivity, errorMessage, Toast.LENGTH_SHORT).show()
                    val result = Intent()
                    result.putExtra(KEY_ADDRESS, placedesc)
                    result.putExtra(KEY_LATITUDE, 0.0)
                    result.putExtra(KEY_LONGITUDE, 0.0)

                    setResult(Activity.RESULT_OK, result)
                    finish()
                } catch (e: Exception) {

                }
            }

            override fun onPlaceDetailsFetched(placeDetails: PlaceDetails) {
                setupUI(placeDetails, placedesc)
            }
        })

    }

    companion object {
        const val KEY_ADDRESS = "KEY_ADDRESS"
        const val KEY_LATITUDE = "KEY_LATITUDE"
        const val KEY_LONGITUDE = "KEY_LONGITUDE"
        const val KEY_STATE = "KEY_STATE"
        const val KEY_COUNTRY = "KEY_COUNTRY"
        const val KEY_PINCODE = "KEY_PINCODE"
        const val KEY_CITY = "KEY_CITY"
        const val KEY_ADDRESS1 = "KEY_ADDRESS1"
        const val KEY_TYPE = "KEY_TYPE"

        fun getIntent(context: Context, type: Int): Intent? {
            val intent = Intent(context, MapsActivity::class.java).putExtra(KEY_TYPE, type)
            return intent
        }
    }

    lateinit var preferenceManager: PreferenceManager

    private fun setupUI(placeDetails: PlaceDetails, placeDes: String) {
        preferenceManager = PreferenceManager(this)

        val address = placeDetails.address
        parseAddress(address)
        runOnUiThread {

            if (type1 == 0) {
                preferenceManager.savePreference(Config.Constants.ADDRESS, placeDes)
                preferenceManager.savePreference(
                    Config.Constants.LATITUDE,
                    placeDetails.lat.toString()
                )
                preferenceManager.savePreference(
                    Config.Constants.LONGITUDE,
                    placeDetails.lng.toString()
                )
            }

            /* streetTextView.text = street
             cityTextView.text = city
             stateTextView.text = state
             countryTextView.text = country
             zipCodeTextView.text = zipCode
             latitudeTextView.text = placeDetails.lat.toString()
             longitudeTextView.text = placeDetails.lng.toString()
             placeIdTextView.text = placeDetails.placeId
             urlTextView.text = placeDetails.url
             utcOffsetTextView.text = placeDetails.utcOffset.toString()
             vicinityTextView.text = placeDetails.vicinity
             compoundCodeTextView.text = placeDetails.compoundPlusCode
             globalCodeTextView.text = placeDetails.globalPlusCode*/
            UiUtils.hideSoftKeyboard(this)

            // set selected data
            val result = Intent()
            result.putExtra(KEY_ADDRESS, placeDes)
            result.putExtra(KEY_PINCODE, zipCode)
            result.putExtra(KEY_COUNTRY, country)
            result.putExtra(KEY_STATE, state)
            result.putExtra(KEY_CITY, city)
            result.putExtra(KEY_ADDRESS1, street)

            result.putExtra(KEY_LATITUDE, placeDetails.lat)
            result.putExtra(KEY_LONGITUDE, placeDetails.lng)

            setResult(Activity.RESULT_OK, result)
            finish()
        }
    }

    private fun parseAddress(address: ArrayList<Address>) {
        (0 until address.size).forEach { i ->
            when {
                address[i].type.contains("sublocality_level_2") -> street += address[i].longName + ", "
                address[i].type.contains("route") -> street += address[i].longName + ", "
                address[i].type.contains("sublocality_level_1") -> street += address[i].longName
                address[i].type.contains("locality") -> city += address[i].longName
                address[i].type.contains("administrative_area_level_1") -> state += address[i].longName
                address[i].type.contains("country") -> country += address[i].longName
                address[i].type.contains("postal_code") -> zipCode += address[i].longName
            }
        }
    }
}