package com.ptk.testjpctmdb.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ptk.testjpctmdb.R
import com.ptk.testjpctmdb.ui.ui_state.HomeUIStates
import com.ptk.testjpctmdb.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val uiStates by homeViewModel.uiStates.collectAsState()

    HomeScreenBody(modifier, uiStates, homeViewModel::onSearchValueChange)
}

@Composable
fun HomeScreenBody(
    modifier: Modifier,
    uiStates: HomeUIStates,
    onSearchValueChanged: (String) -> Unit
) {
    Column() {
        Text(
            modifier = modifier.padding(start = 16.dp, top = 32.dp),
            text = "What are you looking for?",
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Row(horizontalArrangement = Arrangement.Center) {
            OutlinedTextField(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null
                    )
                },
                placeholder = { Text("Search for movies, events & more") },
                modifier = modifier
                    .weight(1F)
                    .padding(start = 16.dp, end = 16.dp, top = 32.dp)
                    .background(
                        color = colorResource(id = R.color.white)
                    ),
                value = uiStates.searchFieldValue,
                onValueChange = onSearchValueChanged,

                )
            IconButton(
                modifier = modifier.padding(end = 16.dp, top = 32.dp),
                onClick = {},
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_filter_list_24),
                    contentDescription = "filter_button",
                    modifier = Modifier
                        .height(70.dp)
                        .width(70.dp)
                        .background(
                            color = colorResource(id = R.color.purple_200),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(8.dp)

                )
            }
        }
        TitleList()
    }
}

@Composable
fun ColumnScope.TitleList() {
    val titleList = arrayListOf<String>("Movies", "Events", "Plays", "Sports", "Activities")
    LazyColumn(
        modifier = Modifier
            .weight(1F)
            .padding(top = 16.dp, start = 32.dp, end = 32.dp)
    ) {
        items(items = titleList) { title ->
            TitleListItem(
                title
            )
        }
    }
}

@Composable
fun TitleListItem(title: String) {
    Text(title)
}