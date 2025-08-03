package com.solux.dorandoran.presentation.mypage.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.solux.dorandoran.presentation.mypage.screen.EmotionShareRoute
import com.solux.dorandoran.presentation.mypage.screen.MypageRoute
import com.solux.dorandoran.presentation.mypage.screen.EmotionShareNewRoute
import com.solux.dorandoran.presentation.review.screen.RecentReviewRoute

fun NavGraphBuilder.mypageNavGraph(
    navigator: MypageNavigator
) {
    composable(route = "mypage") {
        MypageRoute(navigator = navigator)
    }

    composable(route = "emotion_share") {
        EmotionShareRoute(navigator = navigator)
    }

    composable(route = "emotion_share_new") {
        EmotionShareNewRoute(navigator = navigator)
    }

    // TODO: 사용자가 입력한 리뷰 목록으로 이동
//    composable(route = "recent_review") {
//        RecentReviewRoute(navigator = navigator)
//    }
}