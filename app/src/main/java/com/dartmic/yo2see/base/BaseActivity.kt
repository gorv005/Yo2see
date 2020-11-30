package com.dartmic.yo2see.base

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.lifecycle.ViewModel
import com.dartmic.yo2see.util.UiUtils
import com.dartmic.yo2see.R
import com.dartmic.yo2see.common.CommonBoolean
import com.dartmic.yo2see.common.CommonInt
import com.dartmic.yo2see.common.CommonInt.BLOCKED_OR_NEVER_ASKED
import com.dartmic.yo2see.common.CommonInt.DENIED
import com.dartmic.yo2see.common.CommonInt.GRANTED
import com.dartmic.yo2see.interfaces.LayoutImplemment
import com.dartmic.yo2see.interfaces.PermissionStatus
import com.dartmic.yo2see.utils.PermissionUtils.PERMISSIONS_REQUEST_READ_SMS
import com.tedpark.tedpermission.rx2.TedRx2Permission
import kotlinx.android.synthetic.main.heading_layout.*
import kotlinx.android.synthetic.main.heading_layout_without_back.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.jetbrains.anko.textColor
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass


abstract class BaseActivity<T : ViewModel>(clazz: KClass<T>) : AppCompatActivity(),
    LayoutImplemment {

    private val TAG: String by lazy { tag() }

    val model: T by viewModel(clazz)

    var overrideOnCreate: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout())
        if (!overrideOnCreate) {
            setContentView(layout())
        }
        toolbar?.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.run {
                setDefaultDisplayHomeAsUpEnabled(CommonBoolean.TRUE)
                setDisplayHomeAsUpEnabled(CommonBoolean.TRUE)
                title = title()
            }
        }

        back?.run {
            ImageViewCompat.setImageTintMode(this, PorterDuff.Mode.SRC_ATOP)
            ImageViewCompat.setImageTintList(
                this,
                ColorStateList.valueOf(ContextCompat.getColor(context, titleColor()))
            )
            setOnClickListener {
                hideKeyboard()
                finish()
            }
        }

        titleOne?.run {
            textColor = ContextCompat.getColor(context, titleColor())
            text = title()
        }

        titleTwo?.run {
            textColor = ContextCompat.getColor(context, titleColor())
            text = title()
        }


    }

    private fun hideSystemUI() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    override fun onRestart() {
        super.onRestart()
        hideKeyboard()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            hideKeyboard()
            return CommonBoolean.TRUE
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onBackPressed() {
        hideKeyboard()
        super.onBackPressed()
    }

    protected fun hideKeyboard() {
        val view = this.currentFocus
        view?.let { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, CommonInt.Zero)
        }
    }

    protected fun showKeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, CommonInt.Zero)
    }

    private fun SmsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECEIVE_SMS
            ) != PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            requestPermissions(
                arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS),
                PERMISSIONS_REQUEST_READ_SMS
            )

        }
    }

    protected fun checkSmsPermissions(): Boolean {
        val SMS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
        val RECEIVE = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)

        if (SMS == CommonInt.Zero && RECEIVE == CommonInt.Zero) {
            return CommonBoolean.TRUE
        }
        return CommonBoolean.FALSE
    }


    protected fun checkPermissions(vararg permissions: String): Boolean {

        permissions.forEach {
            val permission = ContextCompat.checkSelfPermission(this, it)
            if (permission != CommonInt.Zero)
                return CommonBoolean.FALSE
        }

        return CommonBoolean.TRUE
    }

    protected fun goToSettings() {
        val myAppSettings = Intent(ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName"))
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT)
        myAppSettings.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(myAppSettings)
        finish()
    }

    fun <T> asList(vararg ts: T): List<T> {
        val result = ArrayList<T>()
        for (t in ts) // ts is an Array
            result.add(t)
        return result
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_READ_SMS -> {

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    fun showSnackbar(string: String?, positive: Boolean) {
          UiUtils.showSnackBar(this, string, positive)
    }
    @PermissionStatus
   protected fun getPermissionStatus(androidPermissionName: String): Int {
        return if (ContextCompat.checkSelfPermission(
                this,
                androidPermissionName
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, androidPermissionName)) {
                BLOCKED_OR_NEVER_ASKED
            } else DENIED
        } else GRANTED
    }

    @SuppressLint("CheckResult")
    protected fun permissonCheck(@StringRes title: Int, @StringRes msg: Int, @StringRes deniedMsg: Int, permissions: String) {
        TedRx2Permission.with(this)
            .setRationaleTitle(title)
            .setRationaleMessage(msg)
            .setPermissions(permissions)
            .setDeniedMessage(deniedMsg)
            .setGotoSettingButtonText("Setting")
            .request()
            .subscribe({
                if (it.isGranted) {

                } else {

                }
            }, {
                toast(R.string.something_went_wrong)
            })
    }

    fun showProgressDialog(title: String?, message: String?, cancelable: Boolean = false) {
        UiUtils.showProgressDialog(this, title, message, cancelable)
    }
    fun hideProgressDialog() {
        UiUtils.dismissProgressDialog()
    }

}