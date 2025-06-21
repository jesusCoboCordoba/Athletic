package com.politecnico.athleticapp.ui.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.politecnico.athleticapp.R // Importa tu R general

class NavigationRVAdapter(
    private var items: List<NavItem>,
    private val onItemClick: (NavItem) -> Unit
) : RecyclerView.Adapter<NavigationRVAdapter.ViewHolder>() {

    private var selectedItemPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_nav_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<NavItem>) {
        items = newItems
        // Encuentra el ítem seleccionado inicialmente si existe
        selectedItemPosition = items.indexOfFirst { it.isSelected }
        notifyDataSetChanged()
    }

    fun setSelectedItem(navItemId: Int) {
        val newPosition = items.indexOfFirst { it.id == navItemId }
        if (newPosition != -1 && newPosition != selectedItemPosition) {
            if (selectedItemPosition != -1 && selectedItemPosition < items.size) { // Boundary check
                items[selectedItemPosition].isSelected = false
                notifyItemChanged(selectedItemPosition)
            }
            items[newPosition].isSelected = true
            selectedItemPosition = newPosition
            notifyItemChanged(selectedItemPosition)
        } else if (newPosition != -1 && newPosition == selectedItemPosition && !items[newPosition].isSelected) {
            // Caso en que el ítem ya era el 'selectedItemPosition' pero su estado isSelected era false
             items[newPosition].isSelected = true
             notifyItemChanged(newPosition)
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.nav_item_icon)
        private val title: TextView = itemView.findViewById(R.id.nav_item_title)
        private val arrow: ImageView = itemView.findViewById(R.id.nav_item_arrow_icon)
        private val container: ConstraintLayout = itemView as ConstraintLayout // El root de custom_nav_item.xml

        fun bind(item: NavItem) {
            title.text = itemView.context.getString(item.titleResId)
            icon.setImageResource(item.iconResId)

            if (item.isSelected) {
                container.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.nav_item_selected_background))
                title.setTextColor(ContextCompat.getColor(itemView.context, R.color.nav_item_text_selected))
                icon.setColorFilter(ContextCompat.getColor(itemView.context, R.color.nav_item_text_selected))
                arrow.setColorFilter(ContextCompat.getColor(itemView.context, R.color.nav_item_text_selected))
            } else {
                container.setBackgroundResource(android.R.color.transparent)
                title.setTextColor(ContextCompat.getColor(itemView.context, R.color.nav_item_text_default))
                icon.setColorFilter(ContextCompat.getColor(itemView.context, R.color.nav_item_icon_tint_default))
                arrow.setColorFilter(ContextCompat.getColor(itemView.context, R.color.nav_item_icon_tint_default))
            }

            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }
} 