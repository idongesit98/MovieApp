package com.zseni.filmapp.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.zseni.filmapp.domain.model.Movie
import com.zseni.filmapp.presentation.screens.mainUiScreen.MainMovieState
import com.zseni.filmapp.presentation.screens.mainUiScreen.MainUiEvent
import com.zseni.filmapp.presentation.screens.searchScreen.components.genresProvider
import com.zseni.filmapp.ui.theme.adaminaFont
import com.zseni.filmapp.util.Constants
import com.zseni.filmapp.util.RatingBar
import com.zseni.filmapp.util.Screen
import com.zseni.filmapp.util.getAverageColour

//This is for the card that displays details of the movies
@Composable
fun AllMovieItem(
    movie: Movie,
    navController: NavController,
    mainMovieState: MainMovieState,
    onEvent:(MainUiEvent) -> Unit
){
    val imageUrl = "${Constants.IMAGE_BASE_URL}${movie.poster_path}"
    val imagePainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .size(Size.ORIGINAL)
            .build()
    )

    val imageState = imagePainter.state
    val defaultDominantColour = MaterialTheme.colorScheme.primaryContainer
    var dominantColour by remember{
        mutableStateOf(defaultDominantColour)
    }
    Box(
        modifier = Modifier
            .padding(bottom = 16.dp, start = 8.dp, end = 8.dp)
    ){
        Column(modifier =
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.secondaryContainer,
                        dominantColour
                    )
                )
            )
            .clickable {
                navController.navigate(
                    "${Screen.DetailsScreen.route}?id=${movie.id}&type =${movie.mediaType}&category=${movie.category}"
                )
            }
        ) {
            Box(modifier = Modifier
                .height(240.dp)
                .fillMaxSize()
                .padding(6.dp)
            ){
                if (imageState is AsyncImagePainter.State.Success){
                    val imageBitmap = imageState.result.drawable.toBitmap()
                    dominantColour = getAverageColour(imageBitmap.asImageBitmap())
                    Image(
                        bitmap = imageBitmap.asImageBitmap() ,
                        contentDescription = movie.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.background)
                    )
                }
                if (imageState is AsyncImagePainter.State.Loading){
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(150.dp)
                            .align(Alignment.Center)
                            .scale(0.5f)
                    )
                }
                if (imageState is AsyncImagePainter.State.Error){
                    dominantColour = MaterialTheme.colorScheme.primary
                    Icon(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.background)
                            .padding(32.dp)
                            .alpha(0.8f),
                        imageVector = Icons.Rounded.ImageNotSupported,
                        contentDescription = movie.title,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            var badgeCount by remember { mutableIntStateOf(0) }
            Text(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                text = movie.title,
                fontFamily = adaminaFont,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                color = Color.White,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { textLayoutResult ->
                    if (textLayoutResult.hasVisualOverflow){
                        val lineEndIndex = textLayoutResult.getLineEnd(
                            lineIndex = 0,
                            visibleEnd = true
                        )
                        badgeCount = movie.title.substring(lineEndIndex)
                            .count { it == '.' }
                    }
                })

            val genres = genresProvider(
                genre_ids = movie.genre_ids,
                allGenres = if (movie.mediaType == "movie")
                    mainMovieState.moviesGenresList
                else mainMovieState.tvGenresList
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 12.dp),
                text = genres,
                fontFamily = adaminaFont,
                fontSize = 14.sp,
                maxLines = 1,
                color = Color.LightGray,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = {textLayoutResult ->
                    if (textLayoutResult.hasVisualOverflow){
                        val lineEndIndex = textLayoutResult.getLineEnd(
                            lineIndex = 0,
                            visibleEnd = true
                        )
                        badgeCount = genres.substring(lineEndIndex)
                            .count { it=='.' }
                    }
                }
            )

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, start = 11.dp, end = 16.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingBar(
                        starsModifier = Modifier
                            .size(18.dp),
                        rating = movie.vote_average/2
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        text = movie.vote_average.toString().take(3),
                        fontFamily = adaminaFont,
                        fontSize = 14.sp,
                        maxLines = 1,
                        color = Color.LightGray)

                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSearchItem(){
    val navController = rememberNavController()
    val movie = Movie(adult = false, backdrop_path = "", genre_ids = listOf(), id = 1, category = "",
        first_air_date = "", origin_country = listOf(), original_name = "", original_language = "",
        mediaType = "", overview = "", popularity = 0.0, original_title = "", poster_path = "",
        release_date = "", title = "", video = false, vote_average = 0.0,
        vote_count = 1, name = "", runtime = 0, similarMediaList = listOf(), status = "", tagline = "", videos = listOf()
    )
    AllMovieItem(movie = movie, navController = navController, mainMovieState = MainMovieState(), onEvent = {} )
}