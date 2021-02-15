package com.dartmic.yo2see.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.dartmic.yo2see.R
import com.dartmic.yo2see.common.ImageProvider
import com.dartmic.yo2see.interfaces.ResultListener
import kotlinx.android.synthetic.main.dialog_choose_app.view.*


internal object DialogHelper {

    /**
     * Show Image Provide Picker Dialog. This will streamline the code to pick/capture image
     *
     */
    fun showChooseAppDialog(context: Context, listener: ResultListener<ImageProvider>) {
        val layoutInflater = LayoutInflater.from(context)
        val customView = layoutInflater.inflate(R.layout.dialog_choose_app, null)

        val dialog = AlertDialog.Builder(context)
            .setTitle(R.string.title_choose_image_provider)
            .setView(customView)
            .setOnCancelListener {
                listener.onResult(null)
            }
            .setNegativeButton(R.string.action_cancel) { _, _ ->
                listener.onResult(null)
            }
            .show()

        // Handle Camera option click
        customView.lytCameraPick.setOnClickListener {
            listener.onResult(ImageProvider.CAMERA)
            dialog.dismiss()
        }

        // Handle Gallery option click
        customView.lytGalleryPick.setOnClickListener {
            listener.onResult(ImageProvider.GALLERY)
            dialog.dismiss()
        }
    }


}