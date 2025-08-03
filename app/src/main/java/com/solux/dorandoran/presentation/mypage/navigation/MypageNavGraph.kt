package com.solux.dorandoran.presentation.mypage.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.solux.dorandoran.presentation.mypage.screen.EmotionShareRoute
import com.solux.dorandoran.presentation.mypage.screen.MypageRoute
import com.solux.dorandoran.presentation.mypage.screen.EmotionShareNewRoute

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

    // recent_review는 review 모듈에서 관리하므로 여기서 제거
}