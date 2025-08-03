package com.solux.dorandoran.data.repositoryimpl

import android.content.Context
import com.solux.dorandoran.data.datasource.DiscussDataSource
import com.solux.dorandoran.data.mapper.toDiscussPageEntity
import com.solux.dorandoran.data.mapper.toDiscussPageEntityList
import com.solux.dorandoran.domain.entity.DiscussPageEntity
import com.solux.dorandoran.domain.repository.DiscussRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DiscussRepositoryImpl @Inject constructor(
    private val discussDataSource: DiscussDataSource,
    @ApplicationContext private val context: Context
) : DiscussRepository {

    private suspend fun getAccessToken(): String {
        // 수정: 실제 토큰 관리 로직으로 대체 필요
        return "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTA0NDM0Nzk3NDYyMjYwOTcxMjIiLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNzU0OTk5OTUyfQ.WvghkrfFxUIWnQjwVS8OJHx_LQrnnxldh9A7nUG26is"
    }

    override suspend fun getDiscussions(
        page: Int,
        size: Int
    ): Result<List<DiscussPageEntity>> {
        return runCatching {
            val token = getAccessToken()
            val response = discussDataSource.getDiscussions(token, page, size)
            response.toDiscussPageEntityList()
        }
    }

    override suspend fun createDiscussion(
        bookId: String,
        title: String,
        content: String
    ): Result<Int> {
        return runCatching {
            val token = getAccessToken()
            val response = discussDataSource.createDiscussion(token, bookId, title, content)
            response.boardId
        }
    }

    override suspend fun getDiscussionDetail(
        discussionId: Int
    ): Result<DiscussPageEntity> {
        return runCatching {
            val token = getAccessToken()
            val response = discussDataSource.getDiscussionDetail(token, discussionId)
            response.toDiscussPageEntity()
        }
    }
}