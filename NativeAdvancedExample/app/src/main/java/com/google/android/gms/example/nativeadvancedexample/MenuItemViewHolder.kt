package com.google.android.gms.example.nativeadvancedexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.menu_item_container.view.*

/**
 * Created by sinhphan on 4/30/20.
 * Email: pnsinh.hg92@gmail.com
 **/
class MenuItemViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {

    fun bind(menuItem: MenuItem) {
        // Get the menu item image resource ID.
        val imageName = menuItem.imageName
        val imageResID = itemView.context.resources.getIdentifier(imageName, "drawable",
                itemView.context.packageName)

        // Add the menu item details to the menu item view.
        itemView.menu_item_image.setImageResource(imageResID)
        itemView.menu_item_name.text = menuItem.name
        itemView.menu_item_price.text = menuItem.price
        itemView.menu_item_category.text = menuItem.category
        itemView.menu_item_description.text = menuItem.description
    }

    companion object {
        fun create(parent: ViewGroup): MenuItemViewHolder {
            return MenuItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.menu_item_container, parent, false))
        }
    }
}