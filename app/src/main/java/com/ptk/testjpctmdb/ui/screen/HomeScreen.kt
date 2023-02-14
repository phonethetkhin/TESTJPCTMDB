package com.ptk.testjpctmdb.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ptk.testjpctmdb.R
import com.ptk.testjpctmdb.ui.ui_resource.composables.*
import com.ptk.testjpctmdb.ui.ui_state.HomeUIStates
import com.ptk.testjpctmdb.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val uiStates by homeViewModel.uiStates.collectAsState()

    HomeScreenBody(
        modifier,
        uiStates,
        homeViewModel::onSearchValueChange,
        homeViewModel = homeViewModel
    )
}

@Composable
fun HomeScreenBody(
    modifier: Modifier,
    uiStates: HomeUIStates,
    onSearchValueChanged: (String) -> Unit,
    homeViewModel: HomeViewModel,
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Text(
            modifier = modifier.padding(start = 16.dp, top = 16.dp),
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
                        .padding(end = 8.dp)
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
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )
        RecommendedList()
        Text(
            text = "Upcoming Movies",
            fontSize = 22.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )
        UpcomingList()
    }
}

@Composable
fun RowScope.SearchView() {
    Card(
        modifier = Modifier
            .weight(1F)
            .padding(start = 16.dp, end = 8.dp, top = 5.dp),
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
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
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
                text = { Text(tabItem.title, fontSize = 14.sp) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Gray,
                enabled = true
            )

        }


    }


}

@Composable
fun RecommendedList() {
    val dummyList =
        arrayListOf(
            "SpiderMan",
            "BatMan",
            "AntMan",
            "Wonder Woman",
            "Flash",
            "Spectre",
            "Haunt",
            "Conjuring"
        )
    LazyRow(Modifier.padding(start = 16.dp)) {
        items(dummyList) { movie ->
            RecommendedListItem(movie = movie)
        }
    }
}

@Composable
fun RecommendedListItem(movie: String) {
    Column(
        Modifier
            .padding(top = 8.dp, end = 8.dp)
            .width(80.dp)
    ) {
        Image(
            modifier = Modifier
                .width(80.dp)
                .height(100.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
            contentScale = ContentScale.FillBounds,
            painter = painterResource(id = R.drawable.spd),
            contentDescription = "photo"
        )
        Text(
            modifier = Modifier
                .defaultMinSize(minHeight = 50.dp)
                .padding(top = 8.dp),
            text = movie,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp,
            color = Color.Black,
        )
        Row {
            Icon(
                painter = painterResource(id = R.drawable.baseline_favorite_24),
                "Favorite Icon"
            )
            Text(
                text = "8.2 %",
                fontSize = 14.sp,
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}

@Composable
fun UpcomingList() {
    val dummyList =
        arrayListOf(
            "SpiderMan",
            "BatMan",
            "AntMan",
            "Wonder Woman",
            "Flash",
            "Spectre",
            "Haunt",
            "Conjuring"
        )
    LazyRow(Modifier.padding(start = 16.dp)) {
        items(dummyList) { movie ->
            UpcomingListItem(movieName = movie, desc = "Blah BLah Blah BLah Blah BLah Blah Blah")
        }
    }
}

@Composable
fun UpcomingListItem(movieName: String, desc: String) {
    Row(
        Modifier
            .padding(top = 8.dp, end = 8.dp)
            .width(80.dp)
    ) {
        Image(
            modifier = Modifier
                .width(80.dp)
                .height(100.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
            contentScale = ContentScale.FillBounds,
            painter = painterResource(id = R.drawable.spd),
            contentDescription = "photo"
        )

        Column(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                modifier = Modifier
                    .padding(top = 8.dp),
                text = movieName,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp,
                color = Color.Black,
            )
            Text(
                modifier = Modifier
                    .padding(top = 8.dp),
                text = desc,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp,
                color = Color.Black,
            )
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_favorite_24),
                    "Favorite Icon"
                )
                Text(
                    text = "8.2 %",
                    fontSize = 14.sp,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Icon(
                    painter = painterResource(id = R.drawable.baseline_favorite_24),
                    "Favorite Icon"
                )
                Text(
                    text = "8.2 %",
                    fontSize = 14.sp,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

    }
}

