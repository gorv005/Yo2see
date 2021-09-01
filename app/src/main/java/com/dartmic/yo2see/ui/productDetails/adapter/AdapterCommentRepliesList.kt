package com.dartmic.yo2see.ui.productDetails.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dartmic.yo2see.R
import com.dartmic.yo2see.model.comment.ChildArrayItem
import com.dartmic.yo2see.ui.productDetails.AdapterCommentRepliesListCallback
import kotlinx.android.synthetic.main.item_forum_comment_reply.view.*


class AdapterCommentRepliesList(

    val activity: Activity, val back: Int, val type: Int
) : ListAdapter<ChildArrayItem, AdapterCommentRepliesList.ViewHolder>(
    AdapterCommentRepliesListCallback()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        p1: Int
    ): AdapterCommentRepliesList.ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_forum_comment_reply, parent, false)

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
        holder.bind(getItem(position), back, type)

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
            allProducts: ChildArrayItem,
            back: Int, type: Int
        ) {


            itemView.tvUserName?.text = allProducts?.userName
            itemView.tvComment?.text = allProducts?.comment
            /*  val widthMeasureSpec =
                  View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.AT_MOST)
              val heightMeasureSpec =
                  View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY)
              itemView.tvComment.measure(widthMeasureSpec, heightMeasureSpec)*/
             /*itemView.tvComment.getWidthHeight().apply {
              var h=   itemView.tvComment.lineCount
                 var h1=h * second
                itemView.viewLine.layoutParams.height = h1

            }
            itemView.viewLine.layoutParams.height = getMeasuredHeight( itemView.tvComment)
            Log.e("DEBUG","count="+itemView.tvComment.getLineCount())
            Log.e("DEBUG","height="+itemView.tvComment.getLineHeight())

            val height_in_pixels: Int = itemView.tvComment.getLineCount() * itemView.tvComment.getLineHeight() //appro
            itemView.viewLine.layoutParams.height=height_in_pixels*/
        }

        private fun getMeasuredHeight(textView: TextView): Int {
            textView.measure(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            var height: Int = textView.getMeasuredHeight()
            height += (textView.getLineCount() - 1) * textView.getLineHeight()
            return height
        }
        fun View.getWidthHeight(): Pair<Int, Int> {
            // find out how big a view should be
            measure(
                // horizontal space requirements as imposed by the parent
                0, // widthMeasureSpec

                // vertical space requirements as imposed by the parent
                0 // heightMeasureSpec
            )

            // the raw measured width of this view
            val width = measuredWidth

            // the raw measured height of this view
            val height = measuredHeight

            // return view's width and height in pixels
            return Pair(width, height)
        }

    }


}