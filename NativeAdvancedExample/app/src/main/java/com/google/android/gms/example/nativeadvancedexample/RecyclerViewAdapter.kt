package com.google.android.gms.example.nativeadvancedexample

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.gms.ads.formats.UnifiedNativeAd
import kotlin.collections.ArrayList

class RecyclerViewAdapter : RecyclerView.Adapter<ViewHolder>() {

    private val items: MutableList<Any> by lazy {
        ArrayList<Any>()
    }

    fun setItems(list: List<Any>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun setAd(ad: UnifiedNativeAd, index: Int) {
        if (items.size <= index) return
        items.add(index, ad)
        notifyItemInserted(index)
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] is UnifiedNativeAd) BANNER_AD_VIEW_TYPE else MENU_ITEM_VIEW_TYPE
    }

    /**
     * Creates a new view for a menu item view or a banner ad view
     * based on the viewType. This method is invoked by the layout manager.
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            MENU_ITEM_VIEW_TYPE -> MenuItemViewHolder.create(viewGroup)
            else -> AdViewHolder.create(viewGroup)
        }
    }

    /**
     * Replaces the content in the views that make up the menu item view and the
     * banner ad view. This method is invoked by the layout manager.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is MenuItemViewHolder -> {
                if (items[position] is MenuItem) holder.bind(items[position] as MenuItem)
            }
            is AdViewHolder -> {
                if (items[position] is UnifiedNativeAd) holder.bind(items[position] as UnifiedNativeAd)
            }
        }
    }

    companion object {
        // A menu item view type.
        private const val MENU_ITEM_VIEW_TYPE = 0

        // The banner ad view type.
        private const val BANNER_AD_VIEW_TYPE = 1
    }

    override fun getItemCount(): Int {
        return items.size
    }

}