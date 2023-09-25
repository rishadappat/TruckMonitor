package com.appat.truckmonitor.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.appat.truckmonitor.R
import com.appat.truckmonitor.data.models.Truck
import com.appat.truckmonitor.data.viewmodel.TrucksViewModel
import com.appat.truckmonitor.ui.customviews.TruckDetails
import com.appat.truckmonitor.utilities.Utility.bitmapDescriptor
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MapScreen(trucksViewModel: TrucksViewModel) {
    val uiState = trucksViewModel.uiState.collectAsState()
    LaunchedEffect(key1 = uiState.value.searchText, block = {
        trucksViewModel.searchTrucks()
    })
    LaunchedEffect(key1 = uiState.value.isSorted, block = {
        trucksViewModel.sortTrucks()
    })
    val pagerState = rememberPagerState(pageCount = {
        uiState.value.trucks.size
    })
    val defaultLocation by remember {
        mutableStateOf(LatLng(25.0762424,55.062682))
    }
    var selectedTruck: Truck? by remember {
        mutableStateOf(null)
    }
    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(mapToolbarEnabled = false,
                myLocationButtonEnabled = false,
                zoomControlsEnabled = false)
        )
    }
    val properties by remember {
        mutableStateOf(MapProperties(isMyLocationEnabled = false, isBuildingEnabled = true))
    }
    val positionState = remember {
        CameraPosition.fromLatLngZoom(LatLng(selectedTruck?.lat ?: defaultLocation.latitude,
            selectedTruck?.lng ?: defaultLocation.longitude), 12f)
    }
    val cameraPositionState = rememberCameraPositionState {
        position = positionState
    }
    LaunchedEffect(key1 = uiState.value.trucks, block = {
        if(uiState.value.trucks.isNotEmpty()) {
            selectedTruck = uiState.value.trucks[0]
            pagerState.animateScrollToPage(0)
        }
    })
    LaunchedEffect(key1 = selectedTruck) {
        cameraPositionState.animate(
            update = CameraUpdateFactory.newCameraPosition(
                CameraPosition(LatLng(selectedTruck?.lat ?: defaultLocation.latitude,
                    selectedTruck?.lng ?: defaultLocation.longitude), 12f, 0f, 0f)
            ),
            durationMs = 600
        )
    }
    LaunchedEffect(pagerState.currentPage) {
        Timber.d(pagerState.currentPage.toString())
        snapshotFlow { pagerState.currentPage }.collect { page ->
            if(uiState.value.trucks.isNotEmpty()) {
                selectedTruck = uiState.value.trucks[page]
            }
        }
    }
    val scope = rememberCoroutineScope()
    Box(
        Modifier
            .fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = mapUiSettings,
            properties = properties,
            contentPadding = PaddingValues(bottom = 160.dp),
        ) {
            uiState.value.trucks.forEach { truck ->
                TruckMapMarker(
                    state = LatLng(truck.lat, truck.lng),
                    title = truck.plateNo ?: "",
                    snippet = truck.driverName ?: "",
                    iconResourceId = R.drawable.truck_marker,
                    onClick = {
                        selectedTruck = truck
                        scope.launch {
                            pagerState.animateScrollToPage(uiState.value.trucks.indexOf(truck))
                        }
                        return@TruckMapMarker true
                    }
                )
            }
        }
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            state = pagerState,
            verticalAlignment = Alignment.Bottom,
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 20.dp),) { page ->
            if(uiState.value.trucks.isNotEmpty()) {
                PagerItem(truck = uiState.value.trucks[page])
            }
        }
    }
}

@Composable
fun PagerItem(truck: Truck)
{
    Card(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(10.dp)
    ) {
        TruckDetails(truck = truck)
    }
}

@Composable
fun TruckMapMarker(
    state: LatLng,
    title: String,
    snippet: String,
    @DrawableRes iconResourceId: Int,
    onClick: (Marker)->Boolean
) {
    val context = LocalContext.current
    val icon = bitmapDescriptor(
        context, iconResourceId
    )

    val markerState = MarkerState(position = state)

    Marker(
        state = markerState,
        title = title,
        snippet = snippet,
        icon = icon,
        onClick = onClick
    )
}