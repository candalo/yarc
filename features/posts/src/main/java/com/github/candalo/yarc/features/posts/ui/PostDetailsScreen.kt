package com.github.candalo.yarc.features.posts.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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
    val postComments by remember {
        viewModel.getPostDetails(post.permalink)
    }.collectAsStateWithLifecycle(initialValue = listOf())

    Column(
        modifier = modifier.fillMaxSize().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PostImage(post = post)
        PostDescription(post = post)
        Spacer(modifier = Modifier.size(16.dp))
        PostAuthor(post = post, modifier = Modifier.align(Alignment.Start))
        Spacer(modifier = Modifier.size(4.dp))
        PostMetadata(post = post, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.size(16.dp))
        PostComments(commentsTree = postComments, modifier = Modifier.wrapContentHeight())
    }
}

@Composable
private fun PostImage(post: Post, modifier: Modifier = Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(post.media.mediaUrl)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.FillBounds,
        contentDescription = null,
        modifier = modifier
            .padding(8.dp)
            .size(300.dp)
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
private fun PostComments(commentsTree: List<TreeNode<PostComment>>, modifier: Modifier = Modifier) {
    val expandedItems = remember { mutableStateListOf<TreeNode<PostComment>>() }
    LazyColumn(modifier = modifier) {
        nodes(
            nodes = commentsTree,
            isExpanded = {
                expandedItems.contains(it)
            },
            toggleExpanded = {
                if (expandedItems.contains(it)) {
                    expandedItems.remove(it)
                } else {
                    expandedItems.add(it)
                }
            }
        )
    }
}

private fun LazyListScope.nodes(
    nodes: List<TreeNode<PostComment>>,
    isExpanded: (TreeNode<PostComment>) -> Boolean,
    toggleExpanded: (TreeNode<PostComment>) -> Unit,
    modifier: Modifier = Modifier
) {
    nodes.forEach { node ->
        node(
            node,
            isExpanded = isExpanded,
            toggleExpanded = toggleExpanded,
            modifier = modifier
        )
    }
}

private fun LazyListScope.node(
    node: TreeNode<PostComment>,
    isExpanded: (TreeNode<PostComment>) -> Boolean,
    toggleExpanded: (TreeNode<PostComment>) -> Unit,
    modifier: Modifier = Modifier
) {
    item {
        PostComment(node, toggleExpanded, modifier)
    }

    if (isExpanded(node)) {
        nodes(
            node.children,
            isExpanded = isExpanded,
            toggleExpanded = toggleExpanded,
            modifier = modifier.padding(start = 16.dp)
        )
    }
}

@Composable
private fun PostComment(
    treeNode: TreeNode<PostComment>,
    toggleExpanded: (TreeNode<PostComment>) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.clickable(enabled = treeNode.hasChildren()) { toggleExpanded(treeNode) }
    ) {
        Column {
            Row {
                Text(text = treeNode.value.authorName, style = typography.bodyMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.size(32.dp))
                Icon(imageVector = Icons.Filled.ArrowUpward, contentDescription = null)
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = treeNode.value.upvotesCount.toString())
                Spacer(modifier = Modifier.weight(1F).fillMaxHeight())
                Text(text = treeNode.value.publicationElapsedTime)
            }
            Spacer(modifier = Modifier.size(8.dp))
            MarkdownText(text = treeNode.value.body)
            Spacer(modifier = Modifier.size(8.dp))
        }
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
