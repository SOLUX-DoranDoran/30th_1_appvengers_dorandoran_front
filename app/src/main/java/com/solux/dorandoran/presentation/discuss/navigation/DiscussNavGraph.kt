package com.solux.dorandoran.presentation.discuss.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.solux.dorandoran.presentation.discuss.screen.DiscussRoute
import com.solux.dorandoran.presentation.discuss.screen.DiscussionRoomRoute
import com.solux.dorandoran.presentation.discuss.screen.DiscussionTopicRoute

@Composable
fun DiscussNavHost() {
    val discussNavController = rememberNavController()
    val discussNavigator = DiscussNavigator(discussNavController)

    NavHost(
        navController = discussNavController,
        startDestination = "discuss"
    ) {
        discussNavGraph(navigator = discussNavigator)
    }
}


fun NavGraphBuilder.discussNavGraph(
    navigator: DiscussNavigator
) {
    composable(route = "discuss") {
        DiscussRoute(navigator = navigator)
    }

    composable(
        route = "discussionRoom/{discussionId}",
        arguments = listOf(navArgument("discussionId") {type = NavType.IntType})
    ) { backStackEntry ->
        println("📱 DiscussionRoom composable loaded")
        val discussionId = backStackEntry.arguments?.getInt("discussionId")?:0
        println("🔍 DiscussionRoom - discussionId: $discussionId")
        DiscussionRoomRoute(
            navigator = navigator,
            discussionId = discussionId
        )
    }

    composable(
        route = "discussionTopic/{discussionId}/{argumentId}",
        arguments = listOf(
            navArgument("discussionId") { type = NavType.IntType },
            navArgument("argumentId") { type = NavType.IntType }
        )
    ) { backStackEntry ->
        println("DiscussionTopic composable registered")
        val discussionId = backStackEntry.arguments?.getInt("discussionId") ?: 0
        val argumentId = backStackEntry.arguments?.getInt("argumentId") ?: 0
        println("🔍 DiscussionTopic - discussionId: $discussionId, argumentId: $argumentId")
        DiscussionTopicRoute(
            navigator = navigator,
            discussionId = discussionId,
            argumentId = argumentId
        )
    }
}