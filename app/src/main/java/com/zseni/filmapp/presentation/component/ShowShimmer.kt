package com.zseni.filmapp.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zseni.filmapp.ui.theme.adaminaFont
import com.zseni.filmapp.util.shimmerEffect

@Composable
fun ShowHomeShimmer(
    title:String,
    paddingEnd: Dp,
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 22.dp),
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = adaminaFont,
            fontSize = 20.sp
        )
        LazyRow{
            items(10){
                Box(modifier = modifier
                    .padding(end = if (it == 9) paddingEnd else 0.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .shimmerEffect(false)
                )
            }
        }
    }
}