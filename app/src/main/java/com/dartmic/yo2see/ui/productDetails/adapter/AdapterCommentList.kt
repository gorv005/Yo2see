package com.dartmic.yo2see.ui.productDetails.adapter

import android.app.Activity
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dartmic.yo2see.R
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.model.comment.ListingItemComment
import com.dartmic.yo2see.utils.Config
import kotlinx.android.synthetic.main.fragment_forum_detail.*

import kotlinx.android.synthetic.main.item_forum_comment.view.*


class AdapterCommentList(
    private val adapterViewClickListener: AdapterViewClickListener<ListingItemComment>?,
    val activity: Activity, val back: Int, val type: Int
) : ListAdapter<ListingItemComment, AdapterCommentList.ViewHolder>(
    AdapterCommentListCallback()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        p1: Int
    ): AdapterCommentList.ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_forum_comment, parent, false)

        /*  val displayMetrics = DisplayMetrics()
          activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
          val width = displayMetrics.widthPixels

          itemView.layoutParams = RecyclerView.LayoutParams(width - (width / 5), RecyclerView.LayoutParams.WRAP_CONTENT)
  */

        return ViewHolder(itemView, activity)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), adapterViewClickListener, back, type)

    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        /*  if (position > lastPosition) {
              val animation: Animation =
                  AnimationUtils.loadAnimation(activity, R.anim.slide_left_to_right)
              viewToAnimate.startAnimation(animation)
              lastPosition = position
          } else {
              val animation: Animation =
                  AnimationUtils.loadAnimation(activity, R.anim.slide_in_from_right)
              viewToAnimate.startAnimation(animation)
              lastPosition = position
          }*/
    }

    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(
            allProducts: ListingItemComment,
            adapterViewClick: AdapterViewClickListener<ListingItemComment>?,
            back: Int, type: Int
        ) {

            itemView.tvUserName?.text = allProducts?.userName
            itemView.tvComment?.text = allProducts?.comment

            var manager = LinearLayoutManager(
                activity!!,
                LinearLayoutManager.VERTICAL, false
            )
            itemView.rvProductForumCommentRepliesList.layoutManager = manager
            var adapterCommentList: AdapterCommentRepliesList? = null

            activity?.let {
                adapterCommentList =
                    AdapterCommentRepliesList(it, R.drawable.round_circle_blue, type!!)

                itemView.rvProductForumCommentRepliesList.adapter = adapterCommentList
                if(!allProducts?.isOpen) {
                    itemView.llReplies.visibility = View.GONE
                }
            }
            itemView.tvReadComment.setOnClickListener {
                itemView.llReplies.visibility = View.VISIBLE
                adapterCommentList?.submitList(allProducts?.childArray)
                adapterCommentList?.notifyDataSetChanged()
            }
            itemView.ivReply.setOnClickListener {
                itemView.llReplies.visibility = View.VISIBLE
                adapterCommentList?.submitList(allProducts?.childArray)
                adapterCommentList?.notifyDataSetChanged()
            }

            itemView.addCommentBtn.setOnClickListener {
                if (itemView.etForumReply.text.toString().length > 0) {
                    allProducts?.comment = itemView.etForumReply.text.toString()
                    adapterViewClick?.onClickAdapterView(
                        allProducts,
                        Config.AdapterClickViewTypes.CLICK_COMMENT, adapterPosition
                    )
                }
            }

        }

    }


}