package com.dartmic.yo2see.ui.productDetails.adapter

import androidx.recyclerview.widget.DiffUtil
import com.dartmic.yo2see.model.comment.ListingItemComment
import com.dartmic.yo2see.model.product.event.ListingItemEvent
import com.dartmic.yo2see.model.product.job.ListingItemJob
import com.dartmic.yo2see.model.product_info.ListingItem


class AdapterCommentListCallback : DiffUtil.ItemCallback<ListingItemComment>() {

    override fun areItemsTheSame(oldItem: ListingItemComment, newItem: ListingItemComment) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: ListingItemComment, newItem: ListingItemComment): Boolean {
        return oldItem == newItem
    }
}