package com.dartmic.yo2see.ui.chat_list.adapter

import android.app.Activity
import android.content.Context

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dartmic.yo2see.R
import com.dartmic.yo2see.model.chat.ChatMessage
import com.dartmic.yo2see.model.chat.User
import com.dartmic.yo2see.utils.AndroidUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.latest_message_row.view.*


/**
 * Created by ansh on 04/09/18.
 */
class LatestMessageRow(val chatMessage: ChatMessage, val context: Context) : Item<ViewHolder>() {

    var chatPartnerUser: User? = null

    override fun getLayout(): Int {
        return R.layout.latest_message_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.latest_message_textview.text = chatMessage.text

        val chatPartnerId: String
        if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
            chatPartnerId = chatMessage.toId
        } else {
            chatPartnerId = chatMessage.fromId
        }

        val ref = FirebaseDatabase.getInstance().getReference("/users/$chatPartnerId")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                chatPartnerUser = p0.getValue(User::class.java)
                viewHolder.itemView.username_textview_latest_message.text = chatPartnerUser?.name
                viewHolder.itemView.latest_msg_time.text = AndroidUtils.getFormattedTime(chatMessage.timestamp)

                if (chatPartnerUser?.profileImageUrl!=null && !chatPartnerUser?.profileImageUrl?.isEmpty()!!) {
                    val requestOptions = RequestOptions().placeholder(R.drawable.yo2see1)

                   /* Glide.with(viewHolder.itemView.imageview_latest_message.context)
                            .load(chatPartnerUser?.profileImageUrl)
                            .apply(requestOptions)
                            .into(viewHolder.itemView.imageview_latest_message)*/

                   /* viewHolder.itemView.imageview_latest_message.setOnClickListener {
                        BigImageDialog.newInstance(chatPartnerUser?.profileImageUrl!!).show((context as Activity).fragmentManager
                                , "")
                    }
*/
                }
            }

        })


    }

}