package com.solux.dorandoran.data.repositoryimpl

import android.content.Context
import com.solux.dorandoran.data.auth.TokenManager
import com.solux.dorandoran.data.datasource.DiscussionDataSource
import com.solux.dorandoran.data.mapper.toDiscussionEntity
import com.solux.dorandoran.domain.entity.DiscussionEntity
import com.solux.dorandoran.domain.repository.BookRepository
import com.solux.dorandoran.domain.repository.DiscussionRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DiscussionRepositoryImpl @Inject constructor(
    private val discussionDataSource: DiscussionDataSource,
    private val bookRepository: BookRepository,
    @ApplicationContext private val context: Context
) : DiscussionRepository {

    private suspend fun getAccessToken(): String {
        val accessToken = TokenManager.getAccessToken(context).first()
        return if (!accessToken.isNullOrEmpty()) {
            "Bearer $accessToken"
        } else {
            throw IllegalStateException("Access token is not available")
        }
    }

    private suspend fun enrichDiscussionWithBookInfo(
        discussionDto: com.solux.dorandoran.data.dto.response.ResponseGetDiscussionDto
    ): DiscussionEntity {
        val baseDiscussion = discussionDto.toDiscussionEntity()

        return try {
            val bookResult = bookRepository.getBookInfo(discussionDto.bookId)

            bookResult.fold(
                onSuccess = { book ->

                    // TODO: UserRepository 구현 후 사용자 정보도 가져오기
                    // val userResult = userRepository.getUserInfo(discussionDto.memberId)
                    // val user = userResult.getOrNull()

                    baseDiscussion.copy(
                        bookTitle = book.title,
                        author = book.author,
                        imageUrl = book.coverImageUrl,
                        user = baseDiscussion.user?.copy(
                            nickname = "${discussionDto.memberId}"
                        )
                    )
                },
                onFailure = { error ->
                    baseDiscussion
                }
            )
        } catch (e: Exception) {
            baseDiscussion
        }
    }

    override suspend fun getRecentDiscussions(): Result<List<DiscussionEntity>> {
        return runCatching {
            val token = getAccessToken()
            val discussions = discussionDataSource.getRecentDiscussions(token)

            coroutineScope {
                discussions.map { discussionDto ->
                    async {
                        enrichDiscussionWithBookInfo(discussionDto)
                    }
                }.awaitAll()
            }
        }
    }

    override suspend fun getRecentDiscussion(): Result<DiscussionEntity?> {
        return runCatching {
            val token = getAccessToken()
            val discussions = discussionDataSource.getRecentDiscussions(token)
            val firstDiscussion = discussions.firstOrNull()

            firstDiscussion?.let { discussionDto ->
                enrichDiscussionWithBookInfo(discussionDto)
            }
        }
    }
}