package com.solux.dorandoran.data.datasourceimpl

import com.solux.dorandoran.data.datasource.DiscussCommentDataSource
import com.solux.dorandoran.data.dto.request.RequestCreateDiscussCommentDto
import com.solux.dorandoran.data.dto.response.ResponseCreateDiscussCommentDto
import com.solux.dorandoran.data.dto.response.ResponseGetDiscussCommentsDto
import com.solux.dorandoran.data.service.DiscussCommentApiService
import javax.inject.Inject

class DiscussCommentDataSourceImpl @Inject constructor(
    private val discussCommentApiService: DiscussCommentApiService
) : DiscussCommentDataSource {

    override suspend fun getDiscussComments(
        boardId: Int,
        page: Int,
        size: Int
    ): ResponseGetDiscussCommentsDto {
        return discussCommentApiService.getDiscussComments(
            boardId = boardId,
            page = page,
            size = size
        )
    }

    override suspend fun createDiscussComment(
        boardId: Int,
        content: String,
        parentId: Int?
    ): ResponseCreateDiscussCommentDto {
        return discussCommentApiService.createDiscussComment(
            boardId = boardId,
            request = RequestCreateDiscussCommentDto(
                content = content,
                parentId = parentId
            )
        )
    }
}