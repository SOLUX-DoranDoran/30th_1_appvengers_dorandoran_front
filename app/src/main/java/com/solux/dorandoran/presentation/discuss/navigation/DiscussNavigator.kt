package com.solux.dorandoran.presentation.discuss.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class DiscussNavigator(
    val navController: NavController
){

    fun navigateToDiscussionRoom(discussionId: Int) {
        val route = "discussionRoom/$discussionId"
        navController.navigate(route)
    }

    fun navigateUp() {
        navController.navigateUp()
    }

    fun navigateToDiscussionTopic(discussionId: Int, argumentId: Int) {

        val route = "discussionTopic/$discussionId/$argumentId"

        // 현재 사용 가능한 destination들 출력
        navController.graph.forEach { destination ->
        }

        try {
            navController.navigate(route)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

