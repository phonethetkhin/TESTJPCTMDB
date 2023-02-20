package com.ptk.testjpctmdb.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ptk.testjpctmdb.R
import com.ptk.testjpctmdb.data.dto.CastItem
import com.ptk.testjpctmdb.ui.ui_resource.theme.Blue
import com.ptk.testjpctmdb.ui.ui_resource.theme.Pink
import com.ptk.testjpctmdb.ui.ui_state.HomeUIStates
import com.ptk.testjpctmdb.util.convertHoursAndMinutes
import com.ptk.testjpctmdb.util.cutHoursAndMinutes
import com.ptk.testjpctmdb.viewmodel.HomeViewModel

@Composable
fun DetailScreen(
    movieId: Int,
    isFav: Boolean,
    homeViewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val uiStates by homeViewModel.uiStates.collectAsState()
    Log.d("testFavasdfasd", isFav.toString())
    LaunchedEffect("") {
        homeViewModel.getMovieDetail(movieId = movieId)
        homeViewModel.getCast(movieId = movieId)

    }
    if (uiStates.movieDetail != null) {
        homeViewModel.setIsFav(isFav)
    }
    DetailScreenBody(navController, uiStates, homeViewModel)
}

@Composable
fun DetailScreenBody(
    navController: NavController,
    uiStates: HomeUIStates,
    homeViewModel: HomeViewModel
) {
    val movieDetail = uiStates.movieDetail
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Box() {

            AsyncImage(
                model = "https://image.tmdb.org/t/p/original/${movieDetail?.backdropPath}",
                contentDescription = "movie photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.FillBounds
            )
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                contentDescription = "back arrow icon",
                tint = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { navController.popBackStack() }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = movieDetail?.title ?: "",
                fontSize = 24.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1F)
                    .padding(end = 16.dp)
            )

            Row() {
                Log.d("favTest", movieDetail?.isFav.toString())
                Icon(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable {
//                            homeViewModel.toggleFav(movieDetail!!)
                        },
                    painter = painterResource(id = R.drawable.baseline_favorite_24),
                    tint = if (movieDetail == null) {
                        Color.Black
                    } else {
                        if (movieDetail.isFav) {
                            Color.Red
                        } else {
                            Color.Black
                        }
                    },
                    contentDescription = "Fav Icon",
                )
                Text("${movieDetail?.voteAverage ?: 0}%", fontSize = 16.sp, color = Color.Gray)

            }

        }

        TextRowItem(
            firstText = "${movieDetail?.productionCountries?.get(0)?.iso31661 ?: ""} | ${movieDetail?.releaseDate ?: ""}",
            secondText = "${movieDetail?.voteCount ?: 0} votes"
        )
        val genreList = movieDetail?.genres?.joinToString { it?.name!! } ?: ""
        val spokenLanguage =
            movieDetail?.spokenLanguages?.joinToString { it?.name!! }?.replace(",", "") ?: ""
        val duration =
            movieDetail?.runtime?.toString()?.convertHoursAndMinutes()?.cutHoursAndMinutes()
        TextRowItem(
            firstText = "$duration | $genreList",
            secondText = spokenLanguage,
            textColor = Color.Blue
        )

        Text(
            "Movie Description",
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.padding(top = 32.dp)
        )
        Text(
            movieDetail?.overview ?: "",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )
        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(16.dp), modifier = Modifier.padding(top = 8.dp),
        ) {
            Column(Modifier.padding(16.dp)) {
                TextRowItem(
                    firstText = "Cast",
                    secondText = "View all",
                    fontSize = 20.sp,
                    textColor = Color.Black,
                    isViewAllRow = true
                )
                CastList(uiStates)
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = { }, colors = ButtonDefaults.buttonColors(backgroundColor = Blue),
        ) {
            Text("Book Tickets", fontSize = 20.sp, color = Color.White)
        }
    }
}

@Composable
fun TextRowItem(
    firstText: String,
    secondText: String,
    textColor: Color = Color.Gray,
    fontSize: TextUnit = 16.sp,
    isViewAllRow: Boolean = false,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = if (isViewAllRow) 0.dp else 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = firstText,
            fontSize = fontSize,
            color = textColor,
            modifier = Modifier
                .weight(1F)
                .padding(end = 16.dp)
        )

        Row(Modifier.weight(0.5F), horizontalArrangement = Arrangement.End) {

            Text(secondText, fontSize = 16.sp, color = if (isViewAllRow) Pink else Color.Gray)

        }

    }
}

@Composable
fun CastList(uiStates: HomeUIStates) {
    val castList = uiStates.castModel?.cast ?: emptyList()
    LazyRow(modifier = Modifier.padding(top = 16.dp)) {
        items(castList) { castItm ->
            CastListItem(castItm!!)
        }
    }
}

@Composable
fun CastListItem(castItem: CastItem) {
    Column(
        Modifier
            .width(120.dp)
            .padding(end = 16.dp)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/original/${castItem.profilePath}",
            contentDescription = "movie photo",
            modifier = Modifier
                .width(120.dp)
                .height(140.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = "${castItem.name}",
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp)
                .defaultMinSize(minHeight = 50.dp)
                .padding(top = 8.dp)
        )
    }
}