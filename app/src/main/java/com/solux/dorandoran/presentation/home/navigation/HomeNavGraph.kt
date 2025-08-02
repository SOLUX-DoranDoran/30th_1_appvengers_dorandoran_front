package com.solux.dorandoran.presentation.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.solux.dorandoran.presentation.home.screen.HomeRoute
import com.solux.dorandoran.presentation.review.navigation.ReviewNavigator
import com.solux.dorandoran.presentation.review.screen.ReviewDetailRoute

fun NavGraphBuilder.homeNavGraph(
    navigator: HomeNavigator
) {
    composable(route = "home") {
        HomeRoute(navigator = navigator)
    }

    composable(
        route = "review_detail/{bookId}",
        arguments = listOf(
            navArgument("bookId") {
                type = NavType.LongType
            }
        )
    ) { backStackEntry ->
        val bookId = backStackEntry.arguments?.getLong("bookId") ?: 0L
        ReviewDetailRoute(
            navigator = ReviewNavigator(navigator.navController),
            bookId = bookId
        )
    }
}