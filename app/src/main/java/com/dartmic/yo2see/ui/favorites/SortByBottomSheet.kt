package com.dartmic.yo2see.ui.favorites

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

import kotlinx.android.synthetic.main.item_product.*
import kotlinx.android.synthetic.main.sortby_bottomsheetlayout.*
import org.jetbrains.anko.find

class SortByBottomSheet(activity: Activity, var sortImpl: SortImpl, var sort_by: String) :
    BottomSheetDialogFragment() {

    private lateinit var radioWeekBack: RadioButton
    private lateinit var radioMonthBack: RadioButton
    private lateinit var radioYearBack: RadioButton
    private lateinit var view_all_post: RadioButton
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
        return inflater.inflate(R.layout.sortby_bottomsheetlayout, container, CommonBoolean.FALSE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        radioWeekBack = view.find(R.id.radioWeekBack)
        radioMonthBack = view.find(R.id.radioMonthBack)
        radioYearBack = view.find(R.id.radioYearBack)
        view_all_post = view.find(R.id.view_all_post)
        sortRadioGroup = view.find(R.id.radio_group)


        if (sort_by.equals(AndroidUtils.getString(R.string.the_pos_user_like_week_back))) {

            radioWeekBack.isChecked = true
            radioMonthBack.isChecked = false
            radioYearBack.isChecked = false
            view_all_post.isChecked = false

        } else if (sort_by.equals(AndroidUtils.getString(R.string.the_pos_user_like_month_back))) {

            radioWeekBack.isChecked = false
            radioMonthBack.isChecked = true
            radioYearBack.isChecked = false
            view_all_post.isChecked = false


        } else if (sort_by.equals(AndroidUtils.getString(R.string.the_pos_user_like_year_back))) {

            radioWeekBack.isChecked = false
            radioMonthBack.isChecked = false
            radioYearBack.isChecked = true
            view_all_post.isChecked = false

        } else if (sort_by.equals(AndroidUtils.getString(R.string.view_all_post))) {

            radioWeekBack.isChecked = false
            radioMonthBack.isChecked = false
            radioYearBack.isChecked = false
            view_all_post.isChecked = true
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
