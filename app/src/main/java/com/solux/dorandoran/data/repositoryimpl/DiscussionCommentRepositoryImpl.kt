package com.solux.dorandoran.data.repositoryimpl

import android.content.Context
import com.solux.dorandoran.data.datasource.DiscussCommentDataSource
import com.solux.dorandoran.data.mapper.toDiscussCommentEntity
import com.solux.dorandoran.data.mapper.toDiscussCommentEntityList
import com.solux.dorandoran.domain.entity.DiscussCommentEntity
import com.solux.dorandoran.domain.repository.DiscussCommentRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DiscussCommentRepositoryImpl @Inject constructor(
    private val discussCommentDataSource: DiscussCommentDataSource,
    @ApplicationContext private val context: Context
) : DiscussCommentRepository {

    private suspend fun getAccessToken(): String {
        // 수정: 실제 토큰 관리 로직으로 대체 필요
        return "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTA0NDM0Nzk3NDYyMjYwOTcxMjIiLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNzU0OTk5OTUyfQ.WvghkrfFxUIWnQjwVS8OJHx_LQrnnxldh9A7nUG26is"
    }

    override suspend fun getDiscussComments(
        boardId: Int,
        page: Int,
        size: Int
    ): Result<List<DiscussCommentEntity>> {
        return runCatching {
            val token = getAccessToken()
            val response = discussCommentDataSource.getDiscussComments(
                token = token,
                boardId = boardId,
                page = page,
                size = size
            )
            response.toDiscussCommentEntityList()
        }
    }

    override suspend fun createDiscussComment(
        boardId: Int,
        content: String,
        parentId: Int?
    ): Result<DiscussCommentEntity> {
        return runCatching {
            val token = getAccessToken()
            val response = discussCommentDataSource.createDiscussComment(
                token = token,
                boardId = boardId,
                content = content,
                parentId = parentId
            )
            response.toDiscussCommentEntity()
        }
    }
}