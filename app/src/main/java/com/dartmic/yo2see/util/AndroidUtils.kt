package com.dartmic.yo2see.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.text.*
import android.text.style.StyleSpan
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseApplication
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*


fun EditText?.getTextTrimmed() = this?.text?.toString()?.trim() ?: ""

class AndroidUtils {

    companion object {

        fun getColor(@ColorRes id: Int) = ContextCompat.getColor(BaseApplication.getInstance(), id)

        fun getDimension(@DimenRes id: Int) =
            BaseApplication.getInstance().resources.getDimensionPixelSize(id)

        fun getInteger(@IntegerRes id: Int) = BaseApplication.getInstance().resources.getInteger(id)

        fun getBackgroundDrawable(@DrawableRes id: Int) =
            BaseApplication.getInstance().resources.getDrawable(id)

        fun replaceFragment(
            fragmentManager: FragmentManager?, @IdRes id: Int,
            fragment: Fragment,
            tag: String = fragment::class.java.name
        ) {
            fragmentManager?.beginTransaction()
                ?.add(id, fragment, tag)?.addToBackStack(tag)
                ?.commit()
        }

        @JvmStatic
        fun getString(@StringRes id: Int, vararg objects: Any?) = if (objects.isEmpty()) {
            BaseApplication.getInstance().resources.getString(id)
        } else {
            BaseApplication.getInstance().resources.getString(id, *objects)
        }

        fun getStringArray(@ArrayRes id: Int): Array<String>? {
            return BaseApplication.getInstance().resources.getStringArray(id)
        }

        fun openPlayStore(context: Context?, packageName: String) {
            try {
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context?.startActivity(intent)
            } catch (anfe: android.content.ActivityNotFoundException) {
                try {
                    val appPackageName = context?.getPackageName()
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context?.startActivity(intent)
                } catch (e: Exception) {
                    LogUtils.e(e)
                }
            }
        }

        @Suppress("DEPRECATION")
        fun getHtmlText(source: String): Spanned? {
            try {
                val result: Spanned

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    result = Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    result = Html.fromHtml(source)
                }

                return result
            } catch (e: Exception) {
                LogUtils.e(e)
            }
            return null
        }

        @Suppress("DEPRECATION")
        fun convertFromSpannedToHtml(source: Spanned): String? {
            try {
                val result: String

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    result = Html.toHtml(source, Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE)
                } else {
                    result = Html.toHtml(source)
                }
                return result
            } catch (e: Exception) {
                LogUtils.e(e)
            }
            return null
        }

        fun openBrowser(context: Context?, urlToLoad: String?) {

            if (context == null || urlToLoad == null || urlToLoad.isEmpty())
                return

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(urlToLoad))
            context.startActivity(browserIntent)
        }

        private fun isValidEmail(target: CharSequence): Boolean {
            return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }

        @JvmStatic
        fun getDate(date: String?): String {
            val i = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val o = SimpleDateFormat("dd/MM/yyyy")
            var d = i.parse(date)
            return o.format(d)
        }

        @JvmStatic
        fun getDateOrder(date: String?): String {
            val i = SimpleDateFormat("yyyyMMdd")
            val o = SimpleDateFormat("yyyy-MM-dd")
            var d = i.parse(date)
            return o.format(d)
        }


        fun validateMatchPassword(password: String, confirmPassword: String): CharSequence? {

            if (TextUtils.isEmpty(confirmPassword)) {
                return AndroidUtils.getString(R.string.error_field_cant_blank)
            } else
                if (password.equals(confirmPassword)) {
                    return null


                } else {
                    return AndroidUtils.getString(
                        R.string.error_password_not_match
                    )

                }


            return null
        }

        fun validateName(name: String): CharSequence? {

            if (TextUtils.isEmpty(name)) {
                return AndroidUtils.getString(R.string.error_field_cant_blank)
            }


            return null
        }
        fun validateCode(name: String): CharSequence? {

            if (TextUtils.isEmpty(name)) {
                return AndroidUtils.getString(R.string.error_field_country_code)
            }


            return null
        }

        fun validateAddress(address: String): CharSequence? {


            if (TextUtils.isEmpty(address)) {
                return AndroidUtils.getString(R.string.error_field_cant_blank_address)
            }


            return null

        }

        fun validateEmail(email: String): CharSequence? {

            if (TextUtils.isEmpty(email)) {
                return AndroidUtils.getString(R.string.error_field_cant_blank)
            } else if (!isValidEmail(email)) {
                return AndroidUtils.getString(R.string.error_field_not_valid_email)

            }


            return null
        }

        fun id(context: Context): String? {
            var uniqueID = UUID.randomUUID().toString()
            return uniqueID
        }

        @SuppressLint("PackageManagerGetSignatures")
        fun printHashKey(pContext: Context) {
            try {
                val info = pContext.getPackageManager()
                    .getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES)
                for (signature in info.signatures) {
                    val md = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    val hashKey = String(Base64.encode(md.digest(), 0))
                    Log.i(TAG, "printHashKey() Hash Key: $hashKey")
                }
            } catch (e: NoSuchAlgorithmException) {
                Log.e(TAG, "printHashKey()", e)
            } catch (e: Exception) {
                Log.e(TAG, "printHashKey()", e)
            }

        }
    }
    fun validateAddress(address: String) {




    }



}