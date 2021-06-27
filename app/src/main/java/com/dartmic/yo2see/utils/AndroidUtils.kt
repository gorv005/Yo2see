package com.dartmic.yo2see.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.text.*
import android.text.format.DateUtils
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
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
import java.util.regex.Pattern


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

        fun mobilePassword(mobile: String): CharSequence? {
            val REGEX: String? = "^(?:\\+971|00971|0)((?:3|4|5|6|7|9|50|51|52|55|56)[0-9]{7,})\$"
            var pattern = Pattern.compile(REGEX)
            if (TextUtils.isEmpty(mobile)) {
                return AndroidUtils.getString(R.string.error_field_cant_blank)
            } else
                if (!pattern.matcher(mobile).matches()) {
                    return AndroidUtils.getString(
                        R.string.error_mobile_not_validation
                    )

                }

            return null
        }

        fun mobilePasswordValidation(mobile: String): CharSequence? {
            if (TextUtils.isEmpty(mobile)) {
                return AndroidUtils.getString(R.string.error_field_cant_blank)
            } else

                if (mobile?.length < 10 || mobile?.length > 10) {
                    return AndroidUtils.getString(
                        R.string.error_mobile_not_validation
                    )

                }

            return null
        }

        fun validatePassword(password: String): CharSequence? {
            val REGEX: String? = ".*[\$@\$!#&].*"
            var pattern = Pattern.compile(REGEX)
            if (TextUtils.isEmpty(password)) {
                return AndroidUtils.getString(R.string.error_field_cant_blank)
            } else
                if (password.length < AndroidUtils.getInteger(R.integer.password_min_char)) {
                    return AndroidUtils.getString(
                        R.string.error_password_min_length,
                        getInteger(R.integer.password_min_char)
                    )

                } else
                    if (password.length > AndroidUtils.getInteger(R.integer.password_max_char)) {
                        return AndroidUtils.getString(
                            R.string.error_password_max_length,
                            getInteger(R.integer.password_min_char)
                        )

                    } else
                        if (!pattern.matcher(password).matches()) {
                            return AndroidUtils.getString(
                                R.string.error_password_not_validation
                            )

                        }

            return null
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

        @JvmStatic
        fun setTextWithSpan(
            textView: TextView,
            text: String,
            spanText: String,
            style: ForegroundColorSpan?
        ) {
            val sb = SpannableStringBuilder(text)
            val start = text.indexOf(spanText)
            val end = start + spanText.length
            sb.setSpan(style, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            textView.text = sb
        }

        @JvmStatic
        fun setTextWithSpan(
            textView: TextView,
            text1: String,
            spanText: String,
            color: Int
        ): SpannableStringBuilder {
            val fcsRed = ForegroundColorSpan(color)

            val sb = SpannableStringBuilder(text1)
            val start = text1.indexOf(spanText)
            val end = start + spanText.length
            sb.setSpan(fcsRed, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            sb.setSpan(RelativeSizeSpan(1.5f), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            return sb
            //textView.text = sb
        }

        @JvmStatic
        fun setTextWithSpan(
            textView: TextView,
            text1: String,
            spanText: String,
            sb: SpannableStringBuilder,
            color: Int
        ) {
            val fcsRed = ForegroundColorSpan(color)

            val start = text1.indexOf(spanText)
            val end = start + spanText.length
            sb.setSpan(fcsRed, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            sb.setSpan(RelativeSizeSpan(1.5f), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            textView.text = sb
        }


        fun getType(type: String): Int {
            when (type) {
                Config.Constants.TYPE_SELL -> {
                    return 1
                }
                Config.Constants.TYPE_RENT -> {
                    return 2
                }
                Config.Constants.TYPE_POST -> {
                    return 4
                }
                Config.Constants.TYPE_BARTER -> {
                    return 3
                }
            }
            return 2
        }
        val onlyTime = SimpleDateFormat("h:mm a", Locale.US) // the format of your date
        val onlyDate = SimpleDateFormat("d MMM", Locale.US) // the format of your date

        fun getFormattedTime(timeInMilis: Long): String {
            val date = Date(timeInMilis * 1000L) // *1000 is to convert seconds to milliseconds

            return when {
                isToday(date) -> onlyTime.format(date)
                isYesterday(date) -> "Yesterday"
                else -> onlyDate.format(date)
            }

        }
        fun isYesterday(d: Date): Boolean {
            return DateUtils.isToday(d.time + DateUtils.DAY_IN_MILLIS)
        }

        fun isToday(d: Date): Boolean {
            return DateUtils.isToday(d.time)
        }


        fun getFormattedTimeChatLog(timeInMilis: Long): String {
            val date = Date(timeInMilis * 1000L) // *1000 is to convert seconds to milliseconds
            val fullFormattedTime = SimpleDateFormat("d MMM, h:mm a", Locale.US) // the format of your date
            val onlyTime = SimpleDateFormat("h:mm a", Locale.US) // the format of your date

            return when {
                isToday(date) -> onlyTime.format(date)
                else -> fullFormattedTime.format(date)
            }

        }
    }

}