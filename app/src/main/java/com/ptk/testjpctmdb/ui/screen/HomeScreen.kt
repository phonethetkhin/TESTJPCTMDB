package com.ptk.testjpctmdb.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ptk.testjpctmdb.R
import com.ptk.testjpctmdb.data.dto.MovieModel
import com.ptk.testjpctmdb.ui.ui_resource.composables.*
import com.ptk.testjpctmdb.ui.ui_resource.navigation.Routes
import com.ptk.testjpctmdb.ui.ui_state.HomeUIStates
import com.ptk.testjpctmdb.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val uiStates by homeViewModel.uiStates.collectAsState()

    HomeScreenBody(
        modifier,
        uiStates,
        homeViewModel::onSearchValueChange,
        homeViewModel = homeViewModel,
        navController = navController
    )
}

@Composable
fun HomeScreenBody(
    modifier: Modifier,
    uiStates: HomeUIStates,
    onSearchValueChanged: (String) -> Unit,
    homeViewModel: HomeViewModel,
    navController: NavController,
) {

    LaunchedEffect("") {
        if (uiStates.recommendedMovies.isNullOrEmpty()) {
            homeViewModel.getPopularMovie()
            homeViewModel.getUpcomingMovie()
        }
    }
    Log.d("helloWorld", "${uiStates.recommendedMovies}")
    val recommendedList = uiStates.recommendedMovies
    val upcomingList = uiStates.upcomingMovies
    LazyColumn(Modifier.padding(16.dp)) {
        item {
            Text(
                modifier = modifier.padding(top = 16.dp),
                text = "What are you looking for?",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Row(
                verticalAlignment = Alignment.Top,
                modifier = modifier.padding(top = 16.dp)
            ) {
                SearchView()
                IconButton(
                    onClick = {},
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.filter_svgrepo),
                        tint = Color.White,
                        contentDescription = "filter_button",
                        modifier = Modifier
                            .height(35.dp)
                            .width(35.dp)
                            .background(
                                color = colorResource(id = R.color.blue),
                            )
                            .padding(4.dp)


                    )
                }
            }
            TitleList(uiStates, homeViewModel = homeViewModel)
            Text(
                text = "Recommended",
                fontSize = 22.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(top = 16.dp)
            )

            RecommendedList(recommendedList ?: arrayListOf(), homeViewModel, navController)
            Text(
                text = "Upcoming Movies",
                fontSize = 22.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
        this@LazyColumn.upcomingList(upcomingList ?: arrayListOf(), homeViewModel, navController)

    }
}

@Composable
fun RowScope.SearchView() {
    Card(
        modifier = Modifier
            .weight(1F)
            .padding(end = 8.dp, top = 5.dp),
        elevation = 8.dp

    ) {
        CustomTextField(
            leadingIcon = {
                Icon(
                    Icons.Filled.Search,
                    null,
                    tint = Color.LightGray
                )
            },
            text = "",
            trailingIcon = null,
            modifier = Modifier
                .background(
                    Color.White,
                    RoundedCornerShape(percent = 50)
                )
                .padding(4.dp)
                .height(30.dp),
            fontSize = 16.sp,
            placeholderText = "Search for movies, events & more"
        )
    }
}

@Composable
fun TitleList(uiStates: HomeUIStates, homeViewModel: HomeViewModel) {

    val list =
        listOf(TabItem.Movies, TabItem.Events, TabItem.Plays, TabItem.Sports, TabItem.Activities)

    Column(modifier = Modifier.fillMaxWidth()) {
        Tabs(tabs = list, currentPage = uiStates.currentPage, homeViewModel)
    }
}


@Composable
fun Tabs(tabs: List<TabItem>, currentPage: Int, homeViewModel: HomeViewModel) {

    CustomScrollableTabRow(
        selectedTabIndex = currentPage,
        backgroundColor = Color.White,
        edgePadding = 0.dp,
        indicator = { tabPositions ->
            Box(
                modifier = Modifier
                    .customTabIndicatorOffset(tabPositions[currentPage])
                    .height(4.dp)
                    .clip(RoundedCornerShape(8.dp)) // clip modifier not working
                    .padding(horizontal = 8.dp)
                    .background(color = Color.Blue)
            )
        },
        divider = {}
    ) {
        tabs.forEachIndexed { index, tabItem ->

            CustomTab(
                modifier = Modifier.padding(8.dp),
                selected = currentPage == index,
                onClick = {
                    homeViewModel.togglePageChanged(index)
                },
                text = { Text(tabItem.title, fontSize = 16.sp) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Gray,
                enabled = true
            )

        }


    }


}

@Composable
fun RecommendedList(
    recommendedList: List<MovieModel>,
    homeViewModel: HomeViewModel,
    navController: NavController
) {

    LazyRow() {
        items(recommendedList) { movie ->
            RecommendedListItem(movie = movie, homeViewModel, navController)
        }
    }
}

@Composable
fun RecommendedListItem(
    movie: MovieModel,
    homeViewModel: HomeViewModel,
    navController: NavController
) {
    Column(
        Modifier
            .padding(top = 8.dp, end = 8.dp)
            .width(120.dp)
            .clickable {
                navController.navigate(Routes.DetailScreen.route + "?isFav=${movie.isFav}&movieId=${movie.id}")
            }
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/original${movie.posterPath}",
            modifier = Modifier
                .width(120.dp)
                .height(160.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
            contentScale = ContentScale.FillBounds,
            placeholder = painterResource(id = R.drawable.placeholder),
            contentDescription = "photo"
        )
        Text(
            modifier = Modifier
                .defaultMinSize(minHeight = 50.dp)
                .padding(top = 8.dp),
            textAlign = TextAlign.Center,
            text = movie.title ?: "",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp,
            color = Color.Black,
        )
        Log.d("movieIsFav", movie.isFav.toString())
        Row {
            Icon(
                modifier = Modifier.clickable {
//                    homeViewModel.toggleFav(false, movie)
                },
                painter = painterResource(R.drawable.baseline_favorite_24),
                tint = if (movie.isFav) Color.Red else Color.Black,
                contentDescription = "Favorite Icon",

                )
            Text(
                text = "${movie.voteAverage ?: 0}%",
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 8.dp),
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}

fun LazyListScope.upcomingList(
    upcomingList: List<MovieModel>,
    homeViewModel: HomeViewModel,
    navController: NavController
) {
    items(upcomingList) { movie ->
        UpcomingListItem(movie, homeViewModel)
    }
}

@Composable
fun UpcomingListItem(movie: MovieModel, homeViewModel: HomeViewModel) {
    Row(
        Modifier
            .padding(top = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .height(160.dp)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/original${movie.posterPath}",
            modifier = Modifier
                .width(120.dp)
                .height(160.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
            contentScale = ContentScale.FillBounds,
            placeholder = painterResource(id = R.drawable.placeholder),
            contentDescription = "photo"
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 8.dp, end = 8.dp)
        ) {
            Column(
                modifier = Modifier.weight(1F)
            ) {
                Text(
                    fontWeight = FontWeight.Bold,
                    text = movie.title ?: "",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    color = Color.Black,
                )
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    text = movie.overview ?: "",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    color = Color.Black,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Log.d("testFav", movie.isFav.toString())
                Icon(
                    painter = painterResource(id = R.drawable.baseline_favorite_24),
                    tint = if (movie.isFav) Color.Red else Color.Black,
                    contentDescription = "Favorite Icon",
                    modifier = Modifier.clickable {
//                        homeViewModel.toggleFav(true, movie)
                    }
                )
                Text(
                    text = "${movie.voteAverage ?: 0}%",
                    modifier = Modifier.padding(start = 8.dp),
                    fontSize = 14.sp,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Icon(

                    painter = painterResource(id = R.drawable.baseline_chat_bubble_24),
                    contentDescription = "Favorite Icon",
                    tint = Color.Yellow,
                    modifier = Modifier.padding(start = 16.dp),

                    )
                Text(
                    text = "${movie.popularity ?: 0}",
                    modifier = Modifier.padding(start = 8.dp),
                    fontSize = 14.sp,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }


        }
    }
}

