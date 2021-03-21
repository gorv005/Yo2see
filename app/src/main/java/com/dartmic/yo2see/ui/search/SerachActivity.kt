package com.dartmic.yo2see.ui.search

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dartmic.yo2see.R
import com.dartmic.yo2see.model.profile.UserList
import com.dartmic.yo2see.ui.LandingActivity
import com.dartmic.yo2see.ui.profile.UpdateProfileActivity
import kotlinx.android.synthetic.main.activity_serach.*

class SerachActivity : AppCompatActivity() {
    private lateinit var categoryAdapter: SpinnerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serach)
        val category_type = resources.getStringArray(R.array.category_type)
        categoryAdapter = SpinnerAdapter(this, category_type)
        categoryType.adapter = categoryAdapter
        btnApply.setOnClickListener {
            var i = Intent(this, LandingActivity::class.java)
            i.putExtra("query", etSearchQuery.text.toString())
            i.putExtra("location", locationSearch.text.toString())
            i.putExtra("type", "Rent")
            i.putExtra("from", "search")

            startActivity(i)
        }
        ivBackSearch.setOnClickListener {
            onBackPressed()
        }
    }

    companion object {
        const val KEY_TAB = "KEY_TAB"

        fun getIntent(context: Context): Intent? {
            val intent = Intent(context, SerachActivity::class.java)
            return intent
        }
    }

}