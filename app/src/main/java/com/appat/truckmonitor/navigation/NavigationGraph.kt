package com.appat.truckmonitor.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.appat.truckmonitor.screens.ListScreen
import com.appat.truckmonitor.screens.MapScreen

@Composable
fun NavigationGraph(
    navController: NavHostController
){
    NavHost(navController, startDestination = BottomNavItem.List.screenRoute) {
        composable(BottomNavItem.List.screenRoute) {
            ListScreen()
        }
        composable(BottomNavItem.Map.screenRoute) {
            MapScreen()
        }
    }
}