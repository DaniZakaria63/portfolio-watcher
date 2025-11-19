package dev.daniza.portfoliowatcher.ui.news

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.daniza.portfoliowatcher.model.news.NewsHeadline
import dev.daniza.portfoliowatcher.presenter.NewsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewsScreen(
    viewModel: NewsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val news: LazyPagingItems<NewsHeadline> = viewModel.newsHeadline.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.getNewsHeadline()
    }

    LaunchedEffect(key1 = news.loadState) {
        if (news.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (news.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Surface {
        if (news.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize()
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(news.itemCount) { position ->
                    if (news[position] != null) {
                        SingleNewsCard(
                            modifier = Modifier,
                            source = news[position]!!
                        )
                    }
                }
                item {
                    if (news.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun SingleNewsCard(
    modifier: Modifier = Modifier,
    source: NewsHeadline
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(source.urlToImage)
                    .crossfade(true)
                    .build(),
                contentDescription = "image.${source.title}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Display the title
            Text(
                text = source.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Display the author
            Text(
                text = "Author: ${source.author ?: "Unknown"}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Display the source name
            Text(
                text = "Source: ${source.source?.name}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Display the published date
            Text(
                text = "Published At: ${source.publishedAt}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Display the description
            Text(
                text = source.description ?: "No description available",
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
@Preview
fun PreviewSingleNewsCard() {
    SingleNewsCard(
        source = NewsHeadline(
            title = "Sample News Title",
            author = "John Doe",
            description = "This is a sample description for the news article.",
            urlToImage = "https://picsum.photos/seed/picsum/200/300",
            publishedAt = "2023-10-01T12:00:00Z",
            source = NewsHeadline.Source(id = "1", name = "Sample Source"),
            content = "",
            url = "https://example.com/sample-news",
        )
    )
}