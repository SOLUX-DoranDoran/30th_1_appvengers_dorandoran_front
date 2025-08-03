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
    // 수정: "discuss" 경로 유지 - BoardViewModel 사용
    composable(route = "discuss") {
        DiscussRoute(
            navigator = navigator
            // BoardViewModel은 hiltViewModel()로 자동 주입
        )
    }

    // 수정: 기존 "discussionRoom/{discussionId}" 경로 유지 - BoardViewModel 사용
    composable(
        route = "discussionRoom/{discussionId}",
        arguments = listOf(
            navArgument("discussionId") { type = NavType.IntType }
        )
    ) { backStackEntry ->
        val discussionId = backStackEntry.arguments?.getInt("discussionId") ?: 0

        DiscussionRoomRoute(
            navigator = navigator,
            discussionId = discussionId
            // BoardViewModel은 hiltViewModel()로 자동 주입
        )
    }

    // 수정: 기존 "discussionTopic/{discussionId}/{argumentId}" 경로 유지 - BoardViewModel + DiscussViewModel 사용
    composable(
        route = "discussionTopic/{discussionId}/{argumentId}",
        arguments = listOf(
            navArgument("discussionId") { type = NavType.IntType },
            navArgument("argumentId") { type = NavType.IntType }
        )
    ) { backStackEntry ->
        val discussionId = backStackEntry.arguments?.getInt("discussionId") ?: 0
        val argumentId = backStackEntry.arguments?.getInt("argumentId") ?: 0

        DiscussionTopicRoute(
            navigator = navigator,
            discussionId = discussionId,
            argumentId = argumentId
            // BoardViewModel과 DiscussViewModel은 hiltViewModel()로 자동 주입
        )
    }
}