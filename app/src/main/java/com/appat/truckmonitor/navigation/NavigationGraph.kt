package com.appat.truckmonitor.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.appat.truckmonitor.data.viewmodel.TrucksViewModel
import com.appat.truckmonitor.screens.ListScreen
import com.appat.truckmonitor.screens.MapScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    trucksViewModel: TrucksViewModel
){
    NavHost(navController, startDestination = BottomNavItem.List.screenRoute, route = Route.MainActivity.value) {
        composable(BottomNavItem.List.screenRoute) { backStackEntry ->
            ListScreen(trucksViewModel)
        }
        composable(BottomNavItem.Map.screenRoute) {  backStackEntry ->
            MapScreen(trucksViewModel)
        }
    }
}