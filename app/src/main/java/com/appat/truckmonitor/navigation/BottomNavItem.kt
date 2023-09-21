package com.appat.truckmonitor.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Map
import androidx.compose.ui.graphics.vector.ImageVector
import com.appat.truckmonitor.R

enum class Route(val value: String)
{
    List("list"),
    Map("map"),
}

sealed class BottomNavItem(@StringRes var title: Int, var activeIcon: ImageVector, var inactiveIcon: ImageVector, var screenRoute: String){
    object List : BottomNavItem(R.string.list, Icons.Filled.List, Icons.Outlined.List, Route.List.value)
    object Map : BottomNavItem(R.string.map, Icons.Filled.Map, Icons.Outlined.Map, Route.Map.value)
}