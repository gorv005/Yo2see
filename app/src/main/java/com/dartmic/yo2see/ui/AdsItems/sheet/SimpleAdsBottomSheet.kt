package com.dartmic.yo2see.ui.AdsItems.sheet

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.dartmic.yo2see.R
import com.arthurivanets.bottomsheets.BaseBottomSheet
import com.arthurivanets.bottomsheets.config.BaseConfig
import com.arthurivanets.bottomsheets.config.Config

class SimpleAdsBottomSheet(
    hostActivity : Activity,
    config : BaseConfig = Config.Builder(hostActivity).build()
) : BaseBottomSheet(hostActivity, config) {


    override fun onCreateSheetContentView(context: Context): View {
        return LayoutInflater.from(context).inflate(
            R.layout.view_ads_bottom_sheet,
            this,
            false
        )
    }
}