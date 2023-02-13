package com.github.candalo.yarc.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.candalo.yarc.features.posts.domain.model.Post
import com.github.candalo.yarc.features.posts.ui.PostDetailsScreen
import com.github.candalo.yarc.features.posts.ui.PostsScreen
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString

@Composable
fun YarcAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.Posts.name
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Destinations.Posts.name) {
            PostsScreen(
                onNavigateToPostDetails = { post ->
                    val json = Json.encodeToString(post)
                    val encodedJson = Uri.encode(json)
                    navController.navigate("${Destinations.PostDetails.name}/$encodedJson")
                }
            )
        }
        composable(
            route = "${Destinations.PostDetails.name}/{post}",
            arguments = listOf(navArgument("post") { type = NavType.StringType })
        ) {
            val decodedJson = Uri.decode(it.arguments?.getString("post"))
            val post = Json.decodeFromString<Post>(decodedJson)
            PostDetailsScreen(post = post)
        }
    }
}

private enum class Destinations {
    Posts,
    PostDetails
}