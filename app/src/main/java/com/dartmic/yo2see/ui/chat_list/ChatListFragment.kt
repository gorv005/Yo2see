package com.dartmic.yo2see.ui.chat_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dartmic.yo2see.R
import com.dartmic.yo2see.base.BaseFragment
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.model.EventsItems
import com.dartmic.yo2see.ui.chat_list.adapter.AdapterChat
import com.dartmic.yo2see.utils.AndroidUtils
import com.gsa.ui.login.ProductListnViewModel
import kotlinx.android.synthetic.main.fragment_chat_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatListFragment : BaseFragment<ProductListnViewModel>(ProductListnViewModel::class),
    AdapterViewClickListener<EventsItems> {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var adapterChat: AdapterChat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun getLayoutId() = R.layout.fragment_chat_list


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = RecyclerView.VERTICAL
        rvChat.layoutManager = layoutManager
        activity?.let {
            adapterChat = AdapterChat(this, it)
        }
        ivChatFav.setOnClickListener {
            onBackPressed()
        }
        rvChat.adapter = adapterChat
        adapterChat.submitList(getEvents())
        tvUserName.setText(model?.getLoggedInUserName())
        Glide.with(this).load("https://yo2see.com/app/admin/" + model?.getUserImage())
            .placeholder(R.drawable.ic_clothing_white).circleCrop()
            .into(ivProfileChat)
    }

    fun getEvents(): ArrayList<EventsItems> {
        AndroidUtils.getString(R.string.jobs)
        val events = arrayListOf<EventsItems>()

        events?.add(
            EventsItems(
                1,
                "Gaurav",
                AndroidUtils.getColor(R.color.light_red_1)
            )
        )
        events?.add(
            EventsItems(
                1,
                "Lovish",
                AndroidUtils.getColor(R.color.light_red)
            )
        )
        events?.add(
            EventsItems(
                1,
                "Deepak",
                AndroidUtils.getColor(R.color.light_blue)
            )
        )
        events?.add(
            EventsItems(
                1,
                "Opal",
                AndroidUtils.getColor(R.color.light_yellow)
            )
        )
        events?.add(
            EventsItems(
                1,
                "Shree",
                AndroidUtils.getColor(R.color.light_red_1)
            )
        )
        events?.add(
            EventsItems(
                1,
                "Bruce",
                AndroidUtils.getColor(R.color.light_blue)
            )
        )

        return events

    }

    companion object {

        fun getInstance(instance: Int): ChatListFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)

            val fragment = ChatListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onClickAdapterView(objectAtPosition: EventsItems, viewType: Int, position: Int) {
    }


}