package com.example.auth.di

import com.example.auth.data.datasource.FakeAuthDataSource
import com.example.auth.data.repository.AuthRepositoryImpl
import com.example.auth.domain.repository.AuthRepository
import com.example.auth.domain.usecase.ForgotPasswordUseCase
import com.example.auth.domain.usecase.LoginUseCase
import com.example.auth.domain.usecase.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideFakeAuthDataSource(): FakeAuthDataSource = FakeAuthDataSource()

    @Provides
    @Singleton
    fun provideAuthRepository(
        dataSource: FakeAuthDataSource
    ): AuthRepository = AuthRepositoryImpl(dataSource)

    @Provides
    fun provideLoginUseCase(repository: AuthRepository): LoginUseCase =
        LoginUseCase(repository)

    @Provides
    fun provideRegisterUseCase(repository: AuthRepository): RegisterUseCase =
        RegisterUseCase(repository)

    @Provides
    fun provideForgotPasswordUseCase(repository: AuthRepository): ForgotPasswordUseCase =
        ForgotPasswordUseCase(repository)
}
