package com.example.tunewave.ui.screens.homescreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tunewave.R
import com.example.tunewave.domain.HomeFeedCarousel
import com.example.tunewave.domain.HomeFeedCarouselCardInfo
import com.example.tunewave.domain.HomeFeedFilters
import com.example.tunewave.ui.components.*

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    timeBasedGreeting: String,
    homeFeedFilters: List<HomeFeedFilters>,
    currentlySelectedHomeFeedFilter: HomeFeedFilters,
    onHomeFeedFilterClick: (HomeFeedFilters) -> Unit,
    carousels: List<HomeFeedCarousel>,
    onHomeFeedCarouselCardClick: (HomeFeedCarouselCardInfo) -> Unit,
    onErrorRetryButtonClick:()->Unit,
    isLoading: Boolean,
    isErrorMessageVisible: Boolean,
) {
    val lazyColumState = rememberLazyListState()
    val isStatusbarSpacerVisible = remember {
        derivedStateOf { lazyColumState.firstVisibleItemIndex > 1 }
    }
    val lazyColumBottomPaddingValues = remember {
        MusifyBottomNavigationConstants.navigationHeight + MusifyMiniPlayerConstants.miniPlayerHeight
    }
    val errorMessageItem = @Composable { modifier: Modifier ->
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DefaultMusifyErrorMessage(
                title = "Oops! Something doesn't look right",
                subtitle = "Please check the internet connection",
                onRetryButtonClicked = onErrorRetryButtonClick
            )
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Capa 1: Fondo principal
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .scale(5f)
        )

        // Capa 2: Fondo oscurecido
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.transparente),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = (500).dp)            )

            // Capa 3: Oscurecer fondo
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0f, 0f, 0f, 0.6f))
                    .offset(y = (15).dp)
            )
        }
        LazyColumn(
            state = lazyColumState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = lazyColumBottomPaddingValues)
        ) {
            item {
                HeaderRow(
                    timeBasedGreeting = "Tunewave",
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 32.dp)
                )
            }
            stickyHeader {
                if (isStatusbarSpacerVisible.value) {
                    Spacer(
                        modifier = Modifier
                            .background(MaterialTheme.colors.background)
                            .fillMaxWidth()
                            .windowInsetsTopHeight(WindowInsets.statusBars)
                    )
                }
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                   
                }
            }
            if (isErrorMessageVisible) {
                item {
                    errorMessageItem(
                        Modifier
                            .fillParentMaxSize()
                            .padding(bottom = lazyColumBottomPaddingValues)
                    )
                }
            } else {
                // not using keys because the items do not change
                items(carousels) { carousel ->
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = carousel.title,
                        color = Color(0xFF60FBE2),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h5
                    )
                    CarouselLazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        carousel = carousel,
                        onHomeFeedCardClick = { onHomeFeedCarouselCardClick(it) }
                    )
                }
            }
        }
        DefaultMusifyLoadingAnimation(
            isVisible = isLoading,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@ExperimentalMaterialApi
@Composable
private fun CarouselLazyRow(
    carousel: HomeFeedCarousel,
    onHomeFeedCardClick: (HomeFeedCarouselCardInfo) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp)
) {
    LazyRow(
        modifier = modifier,
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(carousel.associatedCards) { card ->
            Card(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(Color(R.color.blueTunewave))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(Color(0x8D535252))
                        .clickable { onHomeFeedCardClick(card) },
                    contentAlignment = Alignment.Center
                ) {

                    HomeFeedCard(
                        imageUrlString = card.imageUrlString,
                        caption = "",
                        onClick = { onHomeFeedCardClick(card) },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .clip(shape = RoundedCornerShape(20.dp))
                    )

                    Text(
                        text = card.caption.take(12) + if (card.caption.length > 12) "..." else "",
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp),
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

@Composable
private fun HeaderRow(modifier: Modifier = Modifier, timeBasedGreeting: String) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = timeBasedGreeting,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.blueTunewave),
            style = MaterialTheme.typography.h5
        )

        Row {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_listening_history),
                    contentDescription = null,
                    tint = Color.White
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}
