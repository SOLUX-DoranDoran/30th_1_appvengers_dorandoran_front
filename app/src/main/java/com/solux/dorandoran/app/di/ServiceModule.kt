package com.solux.dorandoran.app.di

import com.solux.dorandoran.data.service.BookApiService
import com.solux.dorandoran.data.service.DiscussApiService
import com.solux.dorandoran.data.service.DiscussCommentApiService
import com.solux.dorandoran.data.service.DiscussionApiService
import com.solux.dorandoran.data.service.ExampleApiService
import com.solux.dorandoran.data.service.QuoteApiService
import com.solux.dorandoran.data.service.RecommendedBookApiService
import com.solux.dorandoran.data.service.ReviewApiService
import com.solux.dorandoran.data.service.UserApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideExampleService(
        @DoranDoranRetrofit retrofit: Retrofit
    ): ExampleApiService = retrofit.create(ExampleApiService::class.java)

    @Provides
    @Singleton
    fun provideDiscussService(
        @DoranDoranRetrofit retrofit: Retrofit
    ): DiscussApiService = retrofit.create(DiscussApiService::class.java)

    @Provides
    @Singleton
    fun provideDiscussCommentApiService(
        @DoranDoranRetrofit retrofit: Retrofit
    ): DiscussCommentApiService = retrofit.create(DiscussCommentApiService::class.java)

    @Provides
    @Singleton
    fun provideBookService(
        @DoranDoranRetrofit retrofit: Retrofit
    ): BookApiService = retrofit.create(BookApiService::class.java)

    @Provides
    @Singleton
    fun provideRecommendedBookService(
        @DoranDoranRetrofit retrofit: Retrofit
    ): RecommendedBookApiService = retrofit.create(RecommendedBookApiService::class.java)

    @Provides
    @Singleton
    fun provideQuoteService(
        @DoranDoranRetrofit retrofit: Retrofit
    ): QuoteApiService = retrofit.create(QuoteApiService::class.java)

    @Provides
    @Singleton
    fun provideReviewApiService(
        @DoranDoranRetrofit retrofit: Retrofit
    ): ReviewApiService = retrofit.create(ReviewApiService::class.java)

    @Provides
    @Singleton
    fun provideDiscussionApiService(
        @DoranDoranRetrofit retrofit: Retrofit
    ): DiscussionApiService = retrofit.create(DiscussionApiService::class.java)

    @Provides
    @Singleton
    fun provideUserApiService(
        @DoranDoranRetrofit retrofit: Retrofit
    ): UserApiService = retrofit.create(UserApiService::class.java)
}
