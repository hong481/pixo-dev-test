package dev.hong481.pixo.test.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.hong481.pixo.test.data.AlbumRepositoryImpl
import dev.hong481.pixo.test.data.repository.AlbumRepository
import dev.hong481.pixo.test.util.DisplayUtil
import dev.hong481.pixo.test.util.SVGUtil
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Singleton
    @Provides
    fun provideDisplayUtil(
        @ApplicationContext context: Context
    ): DisplayUtil {
        return DisplayUtil(context)
    }


    @Singleton
    @Provides
    fun provideSvgUtil(): SVGUtil {
        return SVGUtil()
    }
}