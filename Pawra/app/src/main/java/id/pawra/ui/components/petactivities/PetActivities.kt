package id.pawra.ui.components.petactivities

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.R
import id.pawra.data.ViewModelFactory
import id.pawra.data.remote.response.TagsItem
import id.pawra.ui.common.NoRippleTheme
import id.pawra.ui.common.UiState
import id.pawra.ui.components.dialog.ResultDialog
import id.pawra.ui.components.general.SearchBar
import id.pawra.ui.components.loading.LoadingBox
import id.pawra.ui.navigation.Screen
import id.pawra.ui.screen.pet.activities.ActivitiesViewModel
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.LightGray
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Red
import id.pawra.ui.theme.White
import id.pawra.utils.DateConverter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PetActivities(
    navController: NavController,
    activitiesViewModel: ActivitiesViewModel,
    petId: Int,
    modifier: Modifier = Modifier
) {
    val query by remember { activitiesViewModel.query }
    var activeFilter by remember { mutableStateOf(FilterActivities.Latest.name) }

    LaunchedEffect(Unit){
        activitiesViewModel.getSpecificActivities(petId, "", activeFilter)
    }

    var isLoading by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(true) }

    if (isLoading) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxSize()
        ) {
            LoadingBox()
        }
    }

    Column(
        modifier = modifier
            .padding(horizontal = 22.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = modifier
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            SearchBar(
                query = query,
                onQueryChange = {activitiesViewModel.getSpecificActivities(petId, it, activeFilter)},
                onSearch = {},
                active = false,
                onActiveChange = {},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Gray
                    )
                },
                placeholder = {
                    Text(
                        "Search activity",
                        color = Gray
                    )
                },
                modifier = modifier.weight(1f)
            ) {}

            IconButton(
                onClick = {
                    navController.navigate(Screen.PetActivitiesAdd.createRoute(petId))
                },
                modifier = modifier
                    .background(LightGreen, RoundedCornerShape(10.dp))
                    .size(56.dp)
            ) {
                Icon(
                    Icons.Filled.Add,
                    "Add Activity",
                    tint = DarkGreen,
                    modifier = modifier
                )
            }
        }

        CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(top = 20.dp, bottom = 20.dp)
            ) {
                for (filter in FilterActivities.entries) {
                    Box(
                        modifier = modifier
                            .clip(shape = RoundedCornerShape(20.dp))
                            .background(if (activeFilter == filter.name) LightGreen else White)
                            .border(
                                2.dp, if (activeFilter == filter.name) LightGreen else LightGray,
                                RoundedCornerShape(20.dp)
                            )
                            .padding(vertical = 5.dp, horizontal = 25.dp)
                            .clickable {
                                activeFilter = filter.name

                                activitiesViewModel.getSpecificActivities(petId, query, activeFilter)
                            }
                    ) {
                        Text(
                            text = filter.name,
                            fontSize = 13.sp,
                            color = if (activeFilter == filter.name) DarkGreen else LightGray,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }
        }
        activitiesViewModel.activitiesState.collectAsState().value.let { activitiesState ->
            when (activitiesState) {
                is UiState.Loading -> {
                    isLoading = true
                }
                is UiState.Success -> {
                    isLoading = false

                    LazyColumn(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(top = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(activitiesState.data, key = { it.id }) { data ->
                            val listTags: List<TagsItem> = data.tags ?: listOf()
                            Row(
                                modifier = modifier
                                    .clip(shape = RoundedCornerShape(15.dp))
                                    .background(DisabledGreen)
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 15.dp)
                                    .clickable {
                                        navController.navigate(Screen.PetActivitiesPrev.createRoute(data.id))
                                    },
                                horizontalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                Column(
                                    modifier = modifier.weight(1f)
                                ) {
                                    Text(
                                        text = DateConverter.convertStringToDate(data.createdAt ?: ""),
                                        fontSize = 11.sp,
                                        color = DarkGreen,
                                        fontWeight = FontWeight.SemiBold,
                                    )

                                    Text(
                                        text = data.description ?: "",
                                        fontSize = 13.sp,
                                        color = DarkGreen,
                                        maxLines = 2,
                                        lineHeight = 18.sp,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = modifier.padding(bottom = 10.dp)
                                    )

                                    LazyRow(
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        items(listTags, key = { it.id }) { tag ->
                                            Text(
                                                text = tag.name ?: "",
                                                fontSize = 10.sp,
                                                color = DisabledGreen,
                                                modifier = modifier
                                                    .clip(shape = RoundedCornerShape(15.dp))
                                                    .background(
                                                        color = DarkGreen
                                                    )
                                                    .padding(vertical = 2.dp, horizontal = 10.dp),
                                            )
                                        }

                                    }
                                }

                                Column(
                                    modifier = modifier,
                                    verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    IconButton(
                                        onClick = {  },
                                        modifier = modifier
                                            .background(DarkGreen, CircleShape)
                                            .size(35.dp)
                                    ) {
                                        Icon(
                                            painterResource(id = R.drawable.edit_square),
                                            "Edit Activity",
                                            tint = White,
                                            modifier = modifier
                                        )
                                    }

                                    IconButton(
                                        onClick = {
                                            activitiesViewModel.deleteActivity(data.id)
                                            activitiesViewModel.getSpecificActivities(petId, query, activeFilter)
                                                  },
                                        modifier = modifier
                                            .background(Red, CircleShape)
                                            .size(35.dp)
                                    ) {
                                        Icon(
                                            painterResource(id = R.drawable.delete_rounded),
                                            "Delete Activity",
                                            tint = White,
                                            modifier = modifier
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                is UiState.Error -> {
                    if(showDialog) {
                        isLoading = false
                        ResultDialog(
                            success = false,
                            message = activitiesState.errorMessage,
                            setShowDialog = {
                                showDialog = it
                            }
                        )
                    }
                }

                else -> {}
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun PetActivitiesPreview(
    activitiesViewModel: ActivitiesViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    )
) {
    PawraTheme {
        PetActivities(
            navController = rememberNavController(),
            activitiesViewModel = viewModel(
                factory = ViewModelFactory(LocalContext.current)
            ),
            petId = 0
        )
    }
}