package com.example.yemeim.di

import com.example.yemeim.data.datasource.SepetDataSource
import com.example.yemeim.data.datasource.YemeklerDataSource
import com.example.yemeim.data.repo.SepetRepo
import com.example.yemeim.data.repo.YemekRepo
import com.example.yemeim.data.retrofit.ApiUtils
import com.example.yemeim.data.retrofit.SepetDao
import com.example.yemeim.data.retrofit.YemeklerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule { //Uygulama genelindeki bağımlılıkları sağlayan bir sınıf tanımlar.

    @Provides
    @Singleton
    fun provideYemeklerRepository(yds : YemeklerDataSource) : YemekRepo { //YemeklerRepository nesnesini sağlayan metod.
        return YemekRepo(yds) //YemeklerDataSource'u kullanarak YemeklerRepository oluşturur.
    }

    @Provides
    @Singleton
    fun provideYemeklerDataSource(ydao : YemeklerDao) : YemeklerDataSource{ //YemeklerDataSource nesnesini sağlayan metod.
        return YemeklerDataSource(ydao) //YemeklerDao'yu kullanarak YemeklerDataSource oluşturur.
    }

    @Provides
    @Singleton
    fun provideYemeklerDao() : YemeklerDao{ //YemeklerDao nesnesini sağlayan metod.
        return ApiUtils.getYemekDao() //ApiUtils sınıfından YemeklerDao nesnesini alır.
    }

    @Provides
    @Singleton
    fun provideSepetRepository(sds : SepetDataSource) : SepetRepo
    { //SepetRepository nesnesini sağlayan metod.
        return SepetRepo(sds) //SepetDataSource'u kullanarak SepetRepository oluşturur.
    }


    @Provides
    @Singleton
    fun provideSepetDataSource(sdao : SepetDao) : SepetDataSource{ //SepetDataSource nesnesini sağlayan metod.
        return SepetDataSource(sdao) //SepetDao'yu kullanarak SepetDataSource oluşturur.
    }

    @Provides
    @Singleton
    fun provideSepetDao() : SepetDao{ //SepetDao nesnesini sağlayan metod.
        return ApiUtils.getSepetDao() //ApiUtils sınıfından SepetDao nesnesini alır.
    }

}