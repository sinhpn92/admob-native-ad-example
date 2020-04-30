package com.google.android.gms.example.nativeadvancedexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.VideoController
import com.google.android.gms.ads.formats.UnifiedNativeAd
import kotlinx.android.synthetic.main.ad_unified.view.*
import java.util.*

/**
 * Created by sinhphan on 4/30/20.
 * Email: pnsinh.hg92@gmail.com
 **/
class AdViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {

    fun bind(nativeAd: UnifiedNativeAd) {
        itemView.ad_view.mediaView = itemView.ad_view.ad_media

        // Set other ad assets.
        itemView.ad_view.headlineView = itemView.ad_view.ad_headline
        itemView.ad_view.bodyView = itemView.ad_view.ad_body
        itemView.ad_view.callToActionView = itemView.ad_view.ad_call_to_action
        itemView.ad_view.iconView = itemView.ad_view.ad_app_icon
        itemView.ad_view.priceView = itemView.ad_view.ad_price
        itemView.ad_view.starRatingView = itemView.ad_view.ad_stars
        itemView.ad_view.storeView = itemView.ad_view.ad_store
        itemView.ad_view.advertiserView = itemView.ad_view.ad_advertiser

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        (itemView.ad_view.headlineView as TextView).text = nativeAd.headline
        itemView.ad_view.mediaView.setMediaContent(nativeAd.mediaContent)

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null) {
            itemView.ad_view.bodyView.visibility = View.INVISIBLE
        } else {
            itemView.ad_view.bodyView.visibility = View.VISIBLE
            (itemView.ad_view.bodyView as TextView).text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            itemView.ad_view.callToActionView.visibility = View.INVISIBLE
        } else {
            itemView.ad_view.callToActionView.visibility = View.VISIBLE
            (itemView.ad_view.callToActionView as Button).text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            itemView.ad_view.iconView.visibility = View.GONE
        } else {
            (itemView.ad_view.iconView as ImageView).setImageDrawable(
                    nativeAd.icon.drawable)
            itemView.ad_view.iconView.visibility = View.VISIBLE
        }

        if (nativeAd.price == null) {
            itemView.ad_view.priceView.visibility = View.INVISIBLE
        } else {
            itemView.ad_view.priceView.visibility = View.VISIBLE
            (itemView.ad_view.priceView as TextView).text = nativeAd.price
        }

        if (nativeAd.store == null) {
            itemView.ad_view.storeView.visibility = View.INVISIBLE
        } else {
            itemView.ad_view.storeView.visibility = View.VISIBLE
            (itemView.ad_view.storeView as TextView).text = nativeAd.store
        }

        if (nativeAd.starRating == null) {
            itemView.ad_view.starRatingView.visibility = View.INVISIBLE
        } else {
            (itemView.ad_view.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
            itemView.ad_view.starRatingView.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            itemView.ad_view.advertiserView.visibility = View.INVISIBLE
        } else {
            (itemView.ad_view.advertiserView as TextView).text = nativeAd.advertiser
            itemView.ad_view.advertiserView.visibility = View.VISIBLE
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        itemView.ad_view.setNativeAd(nativeAd)
    }

    companion object {
        fun create(parent: ViewGroup): AdViewHolder {
            return AdViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ad_unified, parent, false))
        }
    }
}