package dev.hong481.pixo.test.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.hong481.pixo.test.util.BitmapUtil
import dev.hong481.pixo.test.util.SVGUtil
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Singleton
    @Provides
    fun provideSvgUtil(): SVGUtil {
        return SVGUtil()
    }

    @Singleton
    @Provides
    fun provideBitmapUtil() : BitmapUtil {
        return BitmapUtil()
    }
}