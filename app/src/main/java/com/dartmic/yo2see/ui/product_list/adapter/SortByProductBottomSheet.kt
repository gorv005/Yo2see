package com.dartmic.yo2see.ui.product_list.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.dartmic.yo2see.R
import com.dartmic.yo2see.common.CommonBoolean
import com.dartmic.yo2see.interfaces.SortImpl
import com.dartmic.yo2see.utils.AndroidUtils
import com.dartmic.yo2see.utils.Config
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.sortby_product.*
import org.jetbrains.anko.find

class SortByProductBottomSheet(activity: Activity, var sortImpl: SortImpl, var sort_by: String) :
    BottomSheetDialogFragment() {

    private lateinit var radioPopularity: RadioButton
    private lateinit var radioPrice_high_to_low: RadioButton
    private lateinit var radioPrice_low_high: RadioButton
    private lateinit var radioOpen_to_nagotiation: RadioButton
    private lateinit var sortRadioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme)

    }

    @Nullable
    override fun onCreateView(
        @NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sortby_product, container, CommonBoolean.FALSE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        radioPopularity = view.find(R.id.radioPopularity)
        radioPrice_high_to_low = view.find(R.id.radioPrice_high_to_low)
        radioPrice_low_high = view.find(R.id.radioPrice_low_high)
        radioOpen_to_nagotiation = view.find(R.id.radioOpen_to_nagotiation)
        sortRadioGroup = view.find(R.id.radio_group)


        if (sort_by.equals(AndroidUtils.getString(R.string.popularity))) {

            radioPopularity.isChecked = true
            radioPrice_high_to_low.isChecked = false
            radioPrice_low_high.isChecked = false
            radioOpen_to_nagotiation.isChecked = false

        } else if (sort_by.equals(AndroidUtils.getString(R.string.price_high_to_low))) {

            radioPopularity.isChecked = false
            radioPrice_high_to_low.isChecked = true
            radioPrice_low_high.isChecked = false
            radioOpen_to_nagotiation.isChecked = false


        } else if (sort_by.equals(AndroidUtils.getString(R.string.price_low_high))) {

            radioPopularity.isChecked = false
            radioPrice_high_to_low.isChecked = false
            radioPrice_low_high.isChecked = true
            radioOpen_to_nagotiation.isChecked = false

        } else if (sort_by.equals(AndroidUtils.getString(R.string.open_to_nagotiation_))) {

            radioPopularity.isChecked = false
            radioPrice_high_to_low.isChecked = false
            radioPrice_low_high.isChecked = false
            radioOpen_to_nagotiation.isChecked = true
        }
        sortRadioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = view.findViewById(checkedId)
                /*Toast.makeText(context," On checked change : ${radio.tag}",
                    Toast.LENGTH_SHORT).show()*/
                sortImpl.sortBy(radio?.text.toString())
                dismiss()
            })

        ivCancel.setOnClickListener { dismiss() }
    }


    /* @SuppressLint("SetTextI18n")
     fun selectSortOption(currentSize: Int, max: Int, progress: Int) {

         progressbar.progress = progress
         percentCount.text = "$progress%"
         statusCount.text = "$currentSize/$max"
         val string = getString(R.string.uploading_the_data_for_n_school_s, max)
         size.text = setBoldSpannable(string, max)

     }
 */

    companion object {
        const val TAG = "UploadProgessBottomSheet"
    }


}
