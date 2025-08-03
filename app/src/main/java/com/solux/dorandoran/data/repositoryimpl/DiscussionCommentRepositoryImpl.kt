package com.solux.dorandoran.data.repositoryimpl

import android.content.Context
import android.util.Log
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

    companion object {
        private const val TAG = "DiscussCommentRepositoryImpl"
    }

    override suspend fun getDiscussComments(
        boardId: Int,
        page: Int,
        size: Int
    ): Result<List<DiscussCommentEntity>> {
        return runCatching {
            Log.d(TAG, "댓글 목록 조회 시작 - boardId: $boardId, page: $page, size: $size")

            val response = discussCommentDataSource.getDiscussComments(
                boardId = boardId,
                page = page,
                size = size
            )
            val result = response.toDiscussCommentEntityList()

            Log.d(TAG, "댓글 목록 조회 성공 - ${result.size}개")
            result
        }.onFailure { exception ->
            Log.e(TAG, "댓글 목록 조회 실패 - boardId: $boardId", exception)
        }
    }

    override suspend fun createDiscussComment(
        boardId: Int,
        content: String,
        parentId: Int?
    ): Result<DiscussCommentEntity> {
        return runCatching {
            val commentType = if (parentId == null) "원댓글" else "대댓글"
            Log.d(TAG, "$commentType 작성 시작 - boardId: $boardId, parentId: $parentId")

            val response = discussCommentDataSource.createDiscussComment(
                boardId = boardId,
                content = content,
                parentId = parentId
            )
            val result = response.toDiscussCommentEntity()

            Log.d(TAG, "$commentType 작성 성공 - commentId: ${result.id}")
            result
        }.onFailure { exception ->
            val commentType = if (parentId == null) "원댓글" else "대댓글"
            Log.e(TAG, "$commentType 작성 실패 - boardId: $boardId", exception)
        }
    }
}