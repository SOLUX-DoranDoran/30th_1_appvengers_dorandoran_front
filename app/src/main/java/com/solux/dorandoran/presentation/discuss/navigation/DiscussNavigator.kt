package com.solux.dorandoran.presentation.discuss.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class DiscussNavigator(
    val navController: NavController
){

    fun navigateToDiscussionRoom(discussionId: Int) {
        navController.navigate("discussion_room/$discussionId")
    }

    fun navigateUp() {
        navController.navigateUp()
    }

    fun navigateToDiscussionTopic(discussionId: Int, argumentId: Int) {
        val route = "discussionTopic/$discussionId/$argumentId"
        println("🚀 Navigate to topic: $route")

        // 현재 등록된 모든 destinations 출력
        navController.graph.forEach { destination ->
            println("📍 Available destination: ${destination.route}")
        }

        try {
            navController.navigate(route)
            println("✅ Topic navigation successful")
        } catch (e: Exception) {
            println("❌ Topic navigation failed: ${e.message}")
            e.printStackTrace()
        }
    }
}

