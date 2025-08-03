package com.solux.dorandoran.presentation.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.solux.dorandoran.presentation.discuss.navigation.DiscussNavigator
import com.solux.dorandoran.presentation.discuss.screen.DiscussRoute
import com.solux.dorandoran.presentation.discuss.screen.DiscussionRoomRoute
import com.solux.dorandoran.presentation.home.navigation.HomeNavigator
import com.solux.dorandoran.presentation.main.screen.MainRoute
import com.solux.dorandoran.presentation.mypage.navigation.MypageNavigator
import com.solux.dorandoran.presentation.review.navigation.ReviewNavigator

fun NavGraphBuilder.mainNavGraph(
    mainNavigator: MainNavigator,
    homeNavigator: HomeNavigator,
    discussNavigator: DiscussNavigator,
    writingNavigator: ReviewNavigator,
    mypageNavigator: MypageNavigator
) {
    composable(route = "main") {
        MainRoute(navigator = mainNavigator)
    }

    composable("discuss") {
        DiscussRoute(navigator = discussNavigator)
    }

    composable("discussionRoom/{discussionId}") { backStackEntry ->
        val discussionId = backStackEntry.arguments?.getString("discussionId")?.toIntOrNull()
        DiscussionRoomRoute(
            navigator = discussNavigator,
            discussionId = discussionId?:1)
    }

}