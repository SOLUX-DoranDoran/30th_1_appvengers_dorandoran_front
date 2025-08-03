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
        navController.navigate("discuss")
    }

    // 토론 중 화면으로 이동
    fun navigateToDiscussing(discussionId: Long = 1) {
        navController.navigate("discussionRoom/$discussionId") //수정: discussionRoom으로 이동, 기본값 1
    }

    // 감성 공유 화면으로 이동
    fun navigateToEmotionShare() {
        navController.navigate("emotion_share")
    }
}