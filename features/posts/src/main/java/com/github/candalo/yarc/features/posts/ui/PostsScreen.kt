@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.github.candalo.yarc.features.posts.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.github.candalo.yarc.features.posts.domain.model.Post

@Composable
fun PostsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(Modifier.size(8.dp))
        Search()
        Posts()
    }
}

@Composable
private fun Search(
    modifier: Modifier = Modifier,
    viewModel: PostsViewModel = hiltViewModel(),
) {
    var text by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Search subreddits") },
        maxLines = 1,
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                viewModel.updateSubreddit(text)
                keyboardController?.hide()
            }
        )
    )
}

@Composable
private fun Posts(
    modifier: Modifier = Modifier,
    viewModel: PostsViewModel = hiltViewModel(),
) {
    val subreddit by remember { viewModel.subreddit }

    if (subreddit.isNotEmpty()) {
        val posts = viewModel.getPosts(subreddit).collectAsLazyPagingItems()

        LazyColumn(modifier = modifier) {
            items(items = posts, key = { it.id }) {
                it?.let {
                    PostItem(
                        post = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 80.dp)
                    )
                }
                Divider(Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
private fun PostItem(
    post: Post,
    modifier: Modifier = Modifier
) {
    Surface(modifier) {
        ConstraintLayout {
            val (
                thumbnail,
                title,
                author,
                upvote,
                comments,
                publicationTime
            ) = createRefs()

            PostItemThumbnail(
                thumbnailUrl = post.media.thumbnailUrl,
                modifier = Modifier.constrainAs(thumbnail) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    visibility =
                        if (post.media.thumbnailUrl != null) Visibility.Visible else Visibility.Gone
                })

            PostItemTitle(
                title = post.title,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(thumbnail.end, 8.dp, 8.dp)
                    end.linkTo(parent.end, 8.dp)
                    width = Dimension.fillToConstraints
                }
            )

            PostItemAuthor(
                author = post.authorName,
                modifier = Modifier.constrainAs(author) {
                    top.linkTo(title.bottom, 16.dp)
                    start.linkTo(title.start)
                    end.linkTo(title.end)
                    width = Dimension.fillToConstraints
                }
            )

            PostItemUpvotes(
                upvoteCount = post.upvotesCount,
                modifier = Modifier.constrainAs(upvote) {
                    top.linkTo(author.bottom, 8.dp)
                    start.linkTo(author.start)
                }
            )

            PostItemComments(
                commentsCount = post.commentsCount,
                modifier = Modifier.constrainAs(comments) {
                    top.linkTo(upvote.top)
                    start.linkTo(upvote.end, 16.dp)
                }
            )

            PostItemPublication(
                publication = post.publicationElapsedTime,
                modifier = Modifier.constrainAs(publicationTime) {
                    top.linkTo(comments.top)
                    start.linkTo(comments.end, 16.dp)
                }
            )
        }
    }
}

@Composable
private fun PostItemThumbnail(thumbnailUrl: String?, modifier: Modifier = Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(thumbnailUrl)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Crop,
        contentDescription = null,
        modifier = modifier
            .padding(8.dp)
            .size(96.dp)
            .clip(RoundedCornerShape(4.dp))
    )
}

@Composable
private fun PostItemTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        style = typography.titleMedium,
        modifier = modifier
    )
}

@Composable
private fun PostItemAuthor(author: String, modifier: Modifier = Modifier) {
    Text(
        text = author,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = typography.bodyMedium,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@Composable
private fun PostItemUpvotes(upvoteCount: Int, modifier: Modifier = Modifier) {
    PostItemDetails(
        icon = Icons.Default.ArrowUpward,
        info = upvoteCount.toString(),
        modifier = modifier
    )
}

@Composable
private fun PostItemComments(commentsCount: Int, modifier: Modifier = Modifier) {
    PostItemDetails(
        icon = Icons.Default.Forum,
        info = commentsCount.toString(),
        modifier = modifier
    )
}

@Composable
private fun PostItemPublication(publication: String, modifier: Modifier = Modifier) {
    PostItemDetails(
        icon = Icons.Default.Schedule,
        info = publication,
        modifier = modifier
    )
}

@Composable
private fun PostItemDetails(icon: ImageVector, info: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Icon(imageVector = icon, contentDescription = null)
        Text(text = info)
    }
}