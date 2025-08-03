package com.solux.dorandoran.presentation.review.navigation

import androidx.navigation.NavController
import com.solux.dorandoran.presentation.main.util.TabManager

class ReviewNavigator(
    val navController: NavController
){
    fun navigateToReviewDetail(bookId: Long) {
        navController.navigate("review_detail/$bookId")
    }

    fun navigateToRecentReview() {
        navController.navigate("recent_review")
    }

    fun navigateToHomeTab() {
        TabManager.changeTab(TabManager.HOME_TAB)
    }

    fun navigateBackSafely() {
        if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
        } else {
            navigateToHomeTab()
        }
    }
}