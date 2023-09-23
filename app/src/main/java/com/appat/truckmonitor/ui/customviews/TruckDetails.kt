package com.appat.truckmonitor.ui.customviews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.appat.truckmonitor.R
import com.appat.truckmonitor.data.models.Truck
import com.appat.truckmonitor.ui.theme.primaryColor
import com.appat.truckmonitor.ui.theme.secondaryTextColor
import com.appat.truckmonitor.utilities.DateFormatString
import com.appat.truckmonitor.utilities.DateUtils

@Composable
fun TruckDetails(truck: Truck)
{
    Row(modifier = Modifier
        .padding(20.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            modifier = Modifier
                .height(100.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp)),
            model = truck.imageURL,
            contentDescription = truck.driverName,
            contentScale = ContentScale.Crop)
        Spacer(modifier = Modifier.width(20.dp))
        Column(modifier = Modifier
            .weight(1f)
            .align(Alignment.Bottom),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            DetailsText(title = stringResource(id = R.string.plateNo), value = truck.plateNo ?: "")
            DetailsText(title = stringResource(id = R.string.driverName), value = truck.driverName ?: "")
            DetailsText(title = stringResource(id = R.string.location), value = truck.location ?: "")
            DetailsText(title = stringResource(id = R.string.lastUpdate), value = DateUtils.dateToDesc(truck.lastUpdated ?: "",
                DateFormatString.defaultFormat, DateFormatString.DateTime))
        }
    }
}

@Composable
fun DetailsText(title: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = "$title: ", style = TextStyle(
            fontSize = 14.sp,
            color = primaryColor,
            fontWeight = FontWeight.SemiBold
        ))
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = value, style = TextStyle(
            fontSize = 14.sp,
            color = secondaryTextColor
        ))
    }
}