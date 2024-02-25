package com.zseni.filmapp.util

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zseni.filmapp.ui.theme.adaminaFont

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieListShimmer(
    title:String,
    radius:Int
) {
    Column {
        LazyColumn(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
            contentPadding = PaddingValues(top = radius.dp),
            content = {
                stickyHeader {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(top = 16.dp, start = 32.dp),
                            text = title,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontFamily = adaminaFont,
                            fontSize = 20.sp
                        )
                    }
                }

            }
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(top = radius.dp),
            content = {
                items(50) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, start = 8.dp, end = 8.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        Box(
                            modifier = Modifier
                                .height(240.dp)
                                .fillMaxWidth()
                                .padding(6.dp)
                                .clip(RoundedCornerShape(radius.dp))
                                .shimmerEffect()
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(15.dp)
                                .padding(start = 12.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .shimmerEffect()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(11.dp)
                                .padding(start = 12.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .shimmerEffect()
                        )
                        Box(
                            modifier =
                            Modifier
                                .fillMaxWidth(0.6f)
                                .height(12.dp)
                                .padding(start = 11.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .shimmerEffect()
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewMovieListShimmer() {
    MovieListShimmer(title = "", radius = 0)
}