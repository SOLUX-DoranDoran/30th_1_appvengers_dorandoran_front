package com.solux.dorandoran.presentation.review.navigation

import androidx.navigation.NavController

class ReviewNavigator(
    val navController: NavController
){
    fun navigateToReviewDetail(bookId: Long) {
        navController.navigate("review_detail/$bookId")
    }

    fun navigateToRecentReview() {
        navController.navigate("recent_review")
    }

    fun navigateToHome() {
        navController.navigate("home")
    }
}