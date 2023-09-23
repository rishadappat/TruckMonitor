package com.appat.truckmonitor.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.appat.truckmonitor.data.models.Truck
import com.appat.truckmonitor.data.viewmodel.TrucksViewModel
import com.appat.truckmonitor.ui.customviews.ShimmerView
import com.appat.truckmonitor.ui.customviews.TruckDetails
import com.appat.truckmonitor.ui.theme.bgColor


@Composable
fun ListScreen(){
    val scrollState = rememberLazyListState()
    val truckViewModel: TrucksViewModel = hiltViewModel()
    val response = truckViewModel.trucksList.collectAsState()
    LaunchedEffect(key1 = Unit, block = {
        truckViewModel.readTrucks()
    })
    Box(
        Modifier
            .fillMaxSize()
            .background(color = bgColor)) {
        AnimatedContent(targetState = response.value.data == null,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) togetherWith
                        fadeOut(animationSpec = tween(300))
            }, label = "") {
            if(it)
            {
                List(size = response.value.data?.size ?: 10) {
                    ShimmerList()
                }
            }
            else {
                LazyColumn(
                    contentPadding = PaddingValues(10.dp),
                    modifier = Modifier.fillMaxSize(),
                    state = scrollState) {
                    itemsIndexed(items = response.value.data ?: listOf(),
                        itemContent = { _, truck ->
                            ListItem(truck = truck)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ListItem(truck: Truck)
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
fun ShimmerList() {
    LazyColumn(modifier = Modifier.fillMaxSize(),
        userScrollEnabled = false) {
        items(count = 10) {
            ShimmerView(brush = linearGradient(
                listOf(
                    Color.LightGray.copy(alpha = 0.9f),
                    Color.LightGray.copy(alpha = 0.4f),
                    Color.LightGray.copy(alpha = 0.9f)
                )
            )
            )
        }
    }
}