package com.appat.truckmonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.appat.truckmonitor.data.viewmodel.TrucksViewModel
import com.appat.truckmonitor.navigation.AppBottomNavigation
import com.appat.truckmonitor.navigation.NavigationGraph
import com.appat.truckmonitor.ui.customviews.SearchField
import com.appat.truckmonitor.ui.customviews.SortButton
import com.appat.truckmonitor.ui.theme.TruckMonitorTheme
import com.appat.truckmonitor.ui.theme.primaryColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        MapsInitializer.initialize(this)
        enableEdgeToEdge()
        setContent {
            TruckMonitorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreenView()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenView() {
    val trucksViewModel: TrucksViewModel = hiltViewModel()
    val uiState = trucksViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit, block = {
        trucksViewModel.fetchTrucks()
    })
    val navController = rememberNavController()
    val (isChecked, setChecked) = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryColor,
                    titleContentColor = Color.White,
                ),
                actions = {
                    SortButton(
                        isChecked = uiState.value.isSorted,
                        onClick = { trucksViewModel.toggleSort() }
                    )
                },
            )
        },
        bottomBar = {
            AppBottomNavigation(navController = navController)
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Box(modifier = Modifier
                .background(color = primaryColor)
                .padding(20.dp)) {
                SearchField(placeholder = stringResource(id = R.string.search), trucksViewModel)
            }
            NavigationGraph(navController = navController, trucksViewModel)
        }
    }
}