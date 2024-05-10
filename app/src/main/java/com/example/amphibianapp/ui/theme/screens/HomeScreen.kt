package com.example.amphibianapp.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibianapp.data.AmphibianData
import com.example.amphibianapp.ui.theme.AmphibianAppTheme


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val amphibianViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
    val amphibianState = amphibianViewModel.uiState.collectAsState().value
    when (amphibianState) {
        is AmphibianApiState.Success -> AmphibianList(
            amphibians = amphibianState.amphibianData,
            modifier = modifier
        )

        is AmphibianApiState.Error -> {}
        is AmphibianApiState.Loading -> {}
    }

}

@Composable
fun AmphibianList(amphibians: List<AmphibianData>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        items(amphibians) { amphibianData ->
            AmphibianCard(amphibianData = amphibianData)
        }
    }
}

@Composable
fun AmphibianCard(modifier: Modifier = Modifier, amphibianData: AmphibianData) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column {
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(amphibianData.imgSrc).crossfade(true).build(),
                    contentDescription = amphibianData.name,
                    contentScale = ContentScale.Crop
                )
            }

            Text(text = amphibianData.name, style = MaterialTheme.typography.titleSmall)
            Text(text = amphibianData.type, style = MaterialTheme.typography.bodyMedium)
            Text(text = amphibianData.description, style = MaterialTheme.typography.bodySmall)

        }
    }
}

@Preview
@Composable
fun AmphibianCardPreview() {
    AmphibianAppTheme {
        AmphibianCard(
            amphibianData = AmphibianData(
                name = "Great Basin Spadefoot",
                type = "Toad",
                description = "This toad spends most of its life underground due to the arid desert conditions in which it lives. Spadefoot toads earn the name because of their hind legs which are wedged to aid in digging. They are typically grey, green, or brown with dark spots.",
                imgSrc = "https://developer.android.com/codelabs/basic-android-kotlin-compose-amphibians-app/img/great-basin-spadefoot.png"
            ),
            modifier = Modifier.fillMaxSize()
        )
    }
}