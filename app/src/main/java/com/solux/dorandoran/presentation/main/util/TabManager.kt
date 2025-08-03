package com.solux.dorandoran.presentation.main.util

import android.util.Log

object TabManager {
    private var tabChangeCallback: ((Int) -> Unit)? = null

    fun setTabChangeCallback(callback: (Int) -> Unit) {
        tabChangeCallback = callback
    }

    fun changeTab(tabIndex: Int) {
        if (tabChangeCallback != null) {
            tabChangeCallback?.invoke(tabIndex)
        } else {
            Log.e("TabManager", "TabChangeCallback이 null입니다!")
        }
    }

    // 탭 인덱스 상수
    const val HOME_TAB = 0
    const val DISCUSS_TAB = 1
    const val REVIEW_TAB = 2
    const val MYPAGE_TAB = 3
}