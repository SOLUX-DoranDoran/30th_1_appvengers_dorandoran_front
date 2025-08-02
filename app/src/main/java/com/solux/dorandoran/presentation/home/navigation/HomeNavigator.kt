package com.solux.dorandoran.presentation.home.navigation

import androidx.navigation.NavController

class HomeNavigator(
    val navController: NavController
){
    // 최근 리뷰 화면으로 이동
    fun navigateToRecentReview() {
        navController.navigate("recent_review")
    }

    // 리뷰 상세 화면으로 이동
    fun navigateToReviewDetail(bookId: Long) {
        navController.navigate("review_detail/$bookId")
    }

    // 전체 토론 화면으로 이동
    fun navigateToDiscussDetail() {
        navController.navigate("discuss_detail")
    }

    // 토론 중 화면으로 이동
    fun navigateToDiscussing() {
        navController.navigate("discussing")
    }

    // 감성 공유 화면으로 이동
    fun navigateToEmotionShare() {
        navController.navigate("emotion_share")
    }


}