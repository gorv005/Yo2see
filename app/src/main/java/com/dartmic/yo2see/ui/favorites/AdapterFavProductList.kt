package com.dartmic.yo2see.ui.favorites

import android.app.Activity
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dartmic.yo2see.R
import com.dartmic.yo2see.callbacks.AdapterViewClickListener
import com.dartmic.yo2see.managers.ImageRequestManager
import com.dartmic.yo2see.model.product_info.ListingItem
import com.dartmic.yo2see.ui.product_list.adapter.AdapterProductListCallback
import com.dartmic.yo2see.utils.Config
import com.facebook.drawee.drawable.ScalingUtils
import kotlinx.android.synthetic.main.fragment_product_details.*
import kotlinx.android.synthetic.main.item_product.view.*


class AdapterFavProductList(
    private val adapterViewClickListener: AdapterViewClickListener<ListingItem>?,
    val activity: Activity, val back: Int
) : ListAdapter<ListingItem, AdapterFavProductList.ViewHolder>(
    AdapterProductListCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AdapterFavProductList.ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_product, parent, false)

        /*  val displayMetrics = DisplayMetrics()
          activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
          val width = displayMetrics.widthPixels

          itemView.layoutParams = RecyclerView.LayoutParams(width - (width / 5), RecyclerView.LayoutParams.WRAP_CONTENT)
  */

        return ViewHolder(itemView, activity)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.clearAnimation();

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), adapterViewClickListener, back)
        setAnimation(holder.itemView, position)
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
            allProducts: ListingItem,
            adapterViewClick: AdapterViewClickListener<ListingItem>?,
            back: Int
        ) {                itemView.tvModel?.text = allProducts.listingTitle


            itemView.tvModel?.text = allProducts?.listingTitle

            itemView.tvAddress?.text = HtmlCompat.fromHtml("<u>"+allProducts.listingCity+"</u>", HtmlCompat.FROM_HTML_MODE_LEGACY)
            itemView.tvDate?.text = allProducts.listingPublishDatetime.split(" ")[0]

            /*  val v = SpannableString("View " + allProducts.userName + "'s listing")
              v.setSpan(UnderlineSpan(), 0, v.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
              itemView.tvListing.text = v.toString()*/
            val v="<u>View"+allProducts.userName + "'s listing</u>"
            itemView.tvListing.setText(HtmlCompat.fromHtml(v, HtmlCompat.FROM_HTML_MODE_LEGACY))

            itemView.ivFav.pressOnTouch(true)
            var s="This product is available\nfor "
            var p=""
            if(allProducts?.isSell!=null &&allProducts?.isSell?.equals("yes")){
                p="<b>Sell $</b>"+allProducts?.listingPrice
                s=s+"Buy"
                itemView.ivBuy.visibility=View.VISIBLE
                if(allProducts?.isRent!=null &&allProducts?.isRent?.equals("yes") || allProducts?.isBarter!=null &&allProducts?.isBarter?.equals("yes")){
                    p=p+ "<br />"
                }
            }
            else{
                itemView.ivBuy.visibility=View.GONE
            }
            if(allProducts?.isRent!=null &&allProducts?.isRent?.equals("yes")){
                p=p+"<b>Rent $</b>"+allProducts?.rentType?.get(0)?.payment+"/"+allProducts?.rentType?.get(0)?.rentType
                s=s+", Rent"
                itemView.ivRent.visibility=View.VISIBLE
                if( allProducts?.isBarter!=null &&allProducts?.isBarter?.equals("yes")){
                    p=p+ "<br />"
                }
            }else{
                itemView.ivRent.visibility=View.GONE
            }
            if(allProducts?.isBarter!=null &&allProducts?.isBarter?.equals("yes")){
                p=p+"<b>Barter</b> "+allProducts?.barterText
                s=s+", Barter"
                itemView.ivBarter.visibility=View.VISIBLE
            }else{
                itemView.ivBarter.visibility=View.GONE

            }

            itemView.tvPrice.setText(HtmlCompat.fromHtml(p, HtmlCompat.FROM_HTML_MODE_LEGACY))

            itemView.tvProductAvailability.setText(s)
            if (allProducts?.userFavorite == 1) {
                itemView.ivFav.setChecked(true);
                itemView.ivFav.playAnimation()
            } else {
                itemView.ivFav.setChecked(false)

            }
            ImageRequestManager.with(itemView.imageProduct).url(allProducts.listingCoverImage)
                .setPlaceholderImage(R.drawable.download)
                .setScaleType(ScalingUtils.ScaleType.FIT_XY)
                .build()
            itemView.ivFav.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_FAV, adapterPosition
                )

            }
            itemView.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT, adapterPosition
                )
            }
        }
    }

}