package com.solux.dorandoran.presentation.navigator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.solux.dorandoran.presentation.auth.navigation.AuthNavigator
import com.solux.dorandoran.presentation.auth.screen.SignInRoute
import com.solux.dorandoran.presentation.discuss.navigation.DiscussNavigator
import com.solux.dorandoran.presentation.discuss.navigation.discussNavGraph
import com.solux.dorandoran.presentation.home.navigation.HomeNavigator
import com.solux.dorandoran.presentation.home.navigation.homeNavGraph
import com.solux.dorandoran.presentation.main.navigation.MainNavigator
import com.solux.dorandoran.presentation.main.navigation.mainNavGraph
import com.solux.dorandoran.presentation.main.screen.MainRoute
import com.solux.dorandoran.presentation.mypage.navigation.MypageNavigator
import com.solux.dorandoran.presentation.mypage.navigation.mypageNavGraph
import com.solux.dorandoran.presentation.review.navigation.ReviewNavigator
import com.solux.dorandoran.presentation.review.navigation.reviewNavGraph


@Composable
fun DoranDoranNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    authNavigator: AuthNavigator,
    mainNavigator: MainNavigator,
    homeNavigator: HomeNavigator,
    discussNavigator: DiscussNavigator,
    reviewNavigator: ReviewNavigator,
    mypageNavigator: MypageNavigator,
    startDestination: String = "signin"
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
        ) {
            composable("signin") {
                SignInRoute(authNavigator)
            }
            composable("main") {
                MainRoute(mainNavigator)
            }

            mainNavGraph(
                mainNavigator,
                homeNavigator,
                discussNavigator,
                reviewNavigator,
                mypageNavigator
            )
            homeNavGraph(homeNavigator)
            reviewNavGraph(reviewNavigator)
            mypageNavGraph(mypageNavigator)

        }
    }
}