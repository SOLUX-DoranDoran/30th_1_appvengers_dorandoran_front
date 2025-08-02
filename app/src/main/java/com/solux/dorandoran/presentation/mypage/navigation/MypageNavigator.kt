package com.solux.dorandoran.presentation.mypage.navigation

import androidx.navigation.NavController

class MypageNavigator(
    val navController: NavController
){
    fun navigateToEmotionShareNew() {
        navController.navigate("emotion_share_new")
    }

    fun navigateToEmotionShare() {
        navController.navigate("emotion_share")
    }

    fun navigateToHome() {
        navController.navigate("main") {
            popUpTo("emotion_share") {
                inclusive = true
            }
            launchSingleTop = true
        }
    }
}