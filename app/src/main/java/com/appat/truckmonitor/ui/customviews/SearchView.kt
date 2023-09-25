package com.appat.truckmonitor.ui.customviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appat.truckmonitor.data.viewmodel.TrucksViewModel
import com.appat.truckmonitor.utilities.clearFocusOnKeyboardDismiss

@Composable
fun SearchField(placeholder: String, trucksViewModel: TrucksViewModel) {
    val uiState = trucksViewModel.uiState.collectAsState()
    Row(modifier = Modifier
        .background(shape = RoundedCornerShape(10.dp), color = Color.White.copy(alpha = 0.2f)),
        verticalAlignment = Alignment.CenterVertically) {
        val searchViewTextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.width(12.dp))
        Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = Color.White)
        val focusRequester = remember { FocusRequester() }

        BasicTextField(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .clearFocusOnKeyboardDismiss(),
            value = uiState.value.searchText,
            onValueChange = { value ->
                trucksViewModel.updateSearchString(value)
            },
            textStyle = searchViewTextStyle,
            singleLine = true,
            cursorBrush = SolidColor(Color.White),
            decorationBox = { innerTextField ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    if (uiState.value.searchText.isEmpty()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = placeholder,
                            color = Color.White,
                            style = searchViewTextStyle
                        )
                    }
                }
                innerTextField()
            }
        )
    }
}