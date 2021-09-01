package com.dartmic.yo2see.ui.productDetails

import androidx.recyclerview.widget.DiffUtil
import com.dartmic.yo2see.model.comment.ChildArrayItem
import com.dartmic.yo2see.model.comment.ListingItemComment
import com.dartmic.yo2see.model.product.event.ListingItemEvent
import com.dartmic.yo2see.model.product.job.ListingItemJob
import com.dartmic.yo2see.model.product_info.ListingItem


class AdapterCommentRepliesListCallback : DiffUtil.ItemCallback<ChildArrayItem>() {

    override fun areItemsTheSame(oldItem: ChildArrayItem, newItem: ChildArrayItem) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: ChildArrayItem, newItem: ChildArrayItem): Boolean {
        return oldItem == newItem
    }
}