package com.dartmic.yo2see.utils

import android.content.Context
import android.net.ConnectivityManager
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.NetworkCapabilities
import android.os.Build

import com.dartmic.yo2see.common.CommonBoolean


object NetworkUtil {



    @Suppress("DEPRECATION")
    fun isInternetAvailable(context: Context?): Boolean {
        var result = CommonBoolean.FALSE
        val cm = context?.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> CommonBoolean.TRUE
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> CommonBoolean.TRUE
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> CommonBoolean.TRUE
                        else -> CommonBoolean.FALSE
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = CommonBoolean.TRUE
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = CommonBoolean.TRUE
                    }
                }
            }
        }
        return result
    }

/*
    fun onShowDialog(context: Context, networkListener: NetworkListener, int: Int) {
        val builder = AlertDialog.Builder(context)
        val view = context.layoutInflater.inflate(R.layout.network_issue_layout, CommonString.Null)

        val cancelButton = view.findViewById<Button>(R.id.cancel_button)
        val tryAgain = view.findViewById<Button>(R.id.tryAgain)

        builder.setView(view)
        val dialog = builder.create()
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(CommonBoolean.FALSE)
        dialog.setCancelable(CommonBoolean.FALSE)
        dialog.show()

        cancelButton.setOnClickListener {
            networkListener.cancelFetchData()
            dialog.dismiss()
        }
        tryAgain.setOnClickListener {
            if(isInternetAvailable(context)) {
                networkListener.tryAgain(int)
                dialog.dismiss()
            }
        }



    }
*/

//    const val ONE = 1
//    const val TWO = 2
//    const val THREE = 3


}