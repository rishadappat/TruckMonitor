package com.appat.truckmonitor.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.appat.truckmonitor.ui.theme.primaryColor
import com.appat.truckmonitor.ui.theme.secondaryColor

@Composable
fun AppBottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.List,
        BottomNavItem.Map)
    NavigationBar(
        containerColor = primaryColor,
        contentColor = Color.White,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                label = {
                    Text(text = stringResource(id = item.title))
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = secondaryColor,
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.White,
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White
                ),
                icon = {
                    Crossfade(
                        targetState = currentRoute == item.screenRoute,
                        label = ""
                    ) { isChecked ->
                        val iconPainter = if (isChecked)
                            item.activeIcon
                        else
                            item.inactiveIcon
                        Icon(
                            iconPainter, contentDescription = stringResource(
                                id = item.title
                            ), tint = Color.White
                        )
                    }
                },
                selected = currentRoute == item.screenRoute,
                onClick = {
                    navController.navigate(item.screenRoute) {
                        navController.graph.startDestinationRoute?.let { screenRoute ->
                            popUpTo(screenRoute) {
                                saveState = true
                                inclusive = false
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}