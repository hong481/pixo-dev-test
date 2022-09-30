package dev.hong481.pixo.test.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.hong481.pixo.test.data.AlbumRepositoryImpl
import dev.hong481.pixo.test.data.StickerRepositoryImpl
import dev.hong481.pixo.test.data.repository.AlbumRepository
import dev.hong481.pixo.test.data.repository.StickerRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun providePhotoGalleryRepository(
        @ApplicationContext context: Context
    ): AlbumRepository {
        return AlbumRepositoryImpl(context)
    }


    @Singleton
    @Provides
    fun provideSvgStickerRepository(
        @ApplicationContext context: Context
    ): StickerRepository {
        return StickerRepositoryImpl(context)
    }
}