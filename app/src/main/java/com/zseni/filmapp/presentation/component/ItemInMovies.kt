package com.zseni.filmapp.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material.icons.twotone.ImageNotSupported
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
import com.zseni.filmapp.presentation.screens.searchScreen.components.genresProvider
import com.zseni.filmapp.ui.theme.adaminaFont
import com.zseni.filmapp.util.Constants.IMAGE_BASE_URL
import com.zseni.filmapp.util.RatingBar
import com.zseni.filmapp.util.Screen
import com.zseni.filmapp.util.getAverageColour


@Composable
fun MoviesItem(
    movie: Movie,
    navController: NavController,
    mainMovieState: MainMovieState,
    modifier: Modifier = Modifier
) {
    val imageUrl = "${IMAGE_BASE_URL}${movie.poster_path}"
    val title = movie.title
    val imagePainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .size(Size.ORIGINAL)
            .build()
    ).state
    
    val defaultDominantColor = MaterialTheme.colorScheme.primaryContainer
    var dominantColour by remember{
        mutableStateOf(defaultDominantColor)
    }
    Box(modifier = Modifier.padding(start = 16.dp, end = 8.dp, bottom = 16.dp)
    ){
        Column(modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(MaterialTheme.colorScheme.secondaryContainer, dominantColour)
                )
            )
            .clickable { navController.navigate(Screen.DetailsScreen.route
                    + "/${movie.id}") }
        ) {
            Box(modifier = Modifier
                .height(240.dp)
                .fillMaxSize()
                .padding(6.dp)
            ){
                if (imagePainter is AsyncImagePainter.State.Success){
                    val imageBitmap = imagePainter.result.drawable.toBitmap()
                    dominantColour = getAverageColour(imageBitmap.asImageBitmap())

                    Image(
                        bitmap = imageBitmap.asImageBitmap(),
                        contentDescription = title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.background)
                        )
                }
                
                if (imagePainter is AsyncImagePainter.State.Error){
                    dominantColour = MaterialTheme.colorScheme.primary
                    Icon(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.background)
                            .padding(32.dp)
                            .alpha(0.8f),
                        imageVector = Icons.TwoTone.ImageNotSupported,
                        contentDescription = title,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                if (imagePainter is AsyncImagePainter.State.Loading){
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(150.dp)
                            .align(Alignment.Center)
                            .scale(0.5f)
                    )
                }
            }
            var badgeCount by remember { mutableIntStateOf(0) }
            Text(
                modifier = Modifier.padding(
                    horizontal = 12.dp, vertical = 4.dp
                ),
                text = title,
                fontFamily = adaminaFont,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                color = Color.White,
                overflow = TextOverflow.Ellipsis,
                softWrap = true,
                onTextLayout = {textLayoutResult ->
                    if (textLayoutResult.hasVisualOverflow){
                        val lineEndIndex = textLayoutResult.getLineEnd(
                            lineIndex = 0,
                            visibleEnd = true
                        )
                        badgeCount = title.substring(lineEndIndex).count { it=='.' }
                    }
                },
            )
            
            val genres = genresProvider(
                genre_ids = movie.genre_ids,
                allGenres = if (movie.mediaType == "movie")
                mainMovieState.moviesGenresList
                else mainMovieState.tvGenresList
            )
            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = genres,
                fontFamily = adaminaFont,
                fontSize = 12.sp,
                maxLines = 1,
               color = Color.LightGray,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = {textLayoutResult ->
                    if (textLayoutResult.hasVisualOverflow){
                        val lineEndIndex = textLayoutResult.getLineEnd(
                            lineIndex = 0,
                            visibleEnd = true
                        )
                        badgeCount = genres.substring(lineEndIndex).count { it == '.' }
                    }
                }
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 4.dp, start = 11.dp,
                    end = 16.dp, bottom = 8.dp
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingBar(
                        modifier = Modifier,
                        starsModifier = Modifier.size(18.dp),
                        rating = movie.vote_average/2
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        text = movie.vote_average.toString().take(3),
                        fontFamily = adaminaFont,
                        fontSize = 14.sp,
                        maxLines = 1,
                        color = Color.LightGray
                    )
                }
            }
            Column(
                modifier = Modifier.padding(top = 2.dp, start = 10.dp,bottom = 5.dp)
            ){
                Text(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    text = movie.original_name,
                    fontFamily = adaminaFont,
                    fontSize = 15.sp,
                    maxLines = 1,
                    color = Color.Black
                )
            }
        }
    }
}


@Composable
fun ItemInMovies(
    movie:Movie,
    navController: NavController,
    modifier: Modifier = Modifier
){
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(IMAGE_BASE_URL + movie.backdrop_path)
            .size(Size.ORIGINAL)
            .build()
    ).state

    val defaultColour = MaterialTheme.colorScheme.secondaryContainer
    var dominantColour by remember{
        mutableStateOf(defaultColour)
    }

    Column(
        modifier = Modifier
            //.wrapContentHeight()
            .fillMaxHeight()
            .fillMaxWidth()
            //.width(200.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.secondaryContainer,
                        dominantColour
                    )
                )
            )
            .clickable {
                navController.navigate(Screen.DetailsScreen.route + "/${movie.id}")
            }
    ) {
        if (imageState is AsyncImagePainter.State.Error){
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .height(250.dp)
                .clip(RoundedCornerShape(22.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(70.dp),
                    imageVector = Icons.Rounded.ImageNotSupported ,
                    contentDescription = movie.title
                )
            }
        }

        if (imageState is AsyncImagePainter.State.Success){
            dominantColour = getAverageColour(
                imageBitmap = imageState.result.drawable.toBitmap().asImageBitmap()
            )

            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(6.dp)
                    .height(250.dp)
                    .clip(RoundedCornerShape(22.dp)),
                painter = imageState.painter,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 8.dp),
            text = movie.title,
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(start = 16.dp, bottom = 12.dp, top = 4.dp)
        ){
            RatingBar(
                starsModifier = Modifier.size(18.dp),
                    rating = movie.vote_average /2
                )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = movie.vote_average.toString().take(3),
                color = Color.LightGray,
                fontSize = 14.sp,
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
fun PreviewMovieItem() {
    val movie = Movie(adult = false, backdrop_path = "", genre_ids = listOf(), id = 1, category = "",
        first_air_date = "", origin_country = listOf(), original_name = "", original_language = "",
        mediaType = "", overview = "", popularity = 0.0, original_title = "", poster_path = "",
        release_date = "", title = "", video = false, vote_average = 0.0,
        vote_count = 1, name = "", runtime = 0, similarMediaList = listOf(), status = "", tagline = "", videos = listOf()
    )
    MoviesItem(
        movie = movie,
        navController = rememberNavController(),
        mainMovieState = MainMovieState()
    )
}