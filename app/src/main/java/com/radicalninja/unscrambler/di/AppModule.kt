package com.radicalninja.unscrambler.di

import android.content.Context
import com.radicalninja.unscrambler.BaseApplication
import com.radicalninja.unscrambler.data.network.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideConnectivityInterceptor(@ApplicationContext app: Context): ConnectivityInterceptor =
        ConnectivityInterceptorImpl(app)

    @Singleton
    @Provides
    fun provideApiWiktionaryService(connectivityInterceptor: ConnectivityInterceptor): ApiWiktionaryService =
        ApiWiktionaryService(connectivityInterceptor)

    @Singleton
    @Provides
    fun provideDictionaryDataSource(apiWiktionaryService: ApiWiktionaryService): DictionaryDataSource =
        DictionaryDataSourceImpl(apiWiktionaryService)
}