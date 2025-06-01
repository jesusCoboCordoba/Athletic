package com.politecnico.athleticapp.ui.menu // Nuevo subpaquete para cosas comunes del menú

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class NavItem(
    val id: Int, // Corresponderá al ID de destino en tu nav_graph.xml
    @StringRes val titleResId: Int,
    @DrawableRes val iconResId: Int,
    var isSelected: Boolean = false
) 