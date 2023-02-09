package com.github.candalo.yarc.features.posts.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.github.candalo.yarc.features.posts.domain.model.Post
import com.github.candalo.yarc.features.posts.domain.model.PostComment
import com.github.candalo.yarc.features.posts.domain.model.TreeNode
import io.noties.markwon.Markwon

@Composable
fun PostDetailsScreen(post: Post, modifier: Modifier = Modifier) {
    val viewModel: PostDetailsViewModel = hiltViewModel()
    val postComments by viewModel
        .getPostDetails(post.permalink)
        .collectAsStateWithLifecycle(initialValue = listOf())
    var toggleComment: Boolean by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PostImage(post = post)
        PostDescription(post = post)
        Spacer(modifier = Modifier.size(16.dp))
        PostAuthor(post = post, modifier = Modifier.align(Alignment.Start))
        Spacer(modifier = Modifier.size(8.dp))
        PostMetadata(post = post, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.size(16.dp))
        PostComments(
            commentsTree = postComments,
            toggleComment = toggleComment,
            onCommentClicked = { toggleComment = it },
            modifier = Modifier.wrapContentHeight()
        )
    }
}

@Composable
private fun PostImage(post: Post, modifier: Modifier = Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(post.media.mediaUrl)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Crop,
        contentDescription = null,
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
    )
}

@Composable
private fun PostDescription(post: Post, modifier: Modifier = Modifier) {
    MarkdownText(
        text = post.description,
        style = typography.bodyMedium,
        modifier = modifier
    )
}

@Composable
private fun PostAuthor(post: Post, modifier: Modifier = Modifier) {
    Text(
        text = post.authorName,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = typography.bodyMedium,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@Composable
private fun PostMetadata(post: Post, modifier: Modifier = Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        PostUpvotes(post = post)
        PostCommentsCount(post = post)
        PostPublication(post = post)
    }
}

@Composable
private fun PostUpvotes(post: Post, modifier: Modifier = Modifier) {
    PostDetails(
        icon = Icons.Default.ArrowUpward,
        info = post.upvotesCount.toString(),
        modifier = modifier
    )
}

@Composable
private fun PostCommentsCount(post: Post, modifier: Modifier = Modifier) {
    PostDetails(
        icon = Icons.Default.Forum,
        info = post.commentsCount.toString(),
        modifier = modifier
    )
}

@Composable
private fun PostPublication(post: Post, modifier: Modifier = Modifier) {
    PostDetails(
        icon = Icons.Default.Schedule,
        info = post.publicationElapsedTime,
        modifier = modifier
    )
}

@Composable
private fun PostDetails(icon: ImageVector, info: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Icon(imageVector = icon, contentDescription = null)
        Text(text = info)
    }
}

@Composable
private fun PostComments(
    commentsTree: List<TreeNode<PostComment>>,
    toggleComment: Boolean,
    onCommentClicked: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = commentsTree, key = { it.value.id }) {
            PostComment(treeNode = it)
            Divider(thickness = 1.dp)
        }
    }
}

@Composable
private fun PostComment(treeNode: TreeNode<PostComment>, modifier: Modifier = Modifier) {
    Surface(modifier = modifier.clickable(enabled = treeNode.hasChildren()) { }) {
        MarkdownText(text = treeNode.value.body)
    }
}

@Composable
private fun MarkdownText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current
) {
    val markdownText = Markwon.create(LocalContext.current).toMarkdown(text).toString()
    Text(text = buildAnnotatedString { append(markdownText) }, modifier = modifier, style = style)
}
