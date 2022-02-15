package com.example.simplerest.di

import android.content.Context
import androidx.room.Room
import com.example.simplerest.datasourse.DbDataSourse
import com.example.simplerest.datasourse.RestDataSourse
import com.example.simplerest.model.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext

import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

//HAcer inyeccion de dependencias
@Module
@InstallIn(SingletonComponent::class)
class DataSourseModule {

    //Crear una función para traer el URL base
    @Singleton
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl() = "https://randomuser.me/api/"

    //Inicializar retrofit
    //Inyectar automaticamente por medio de la url
    @Singleton
    @Provides
    fun provideRetrofit(@Named("BaseUrl") baseUrl: String): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    //Crear una funcion para regresar un datasourse para inyectar un retrofit
    @Singleton
    @Provides
    fun restDataSourse(retrofit: Retrofit): RestDataSourse =
        retrofit.create(RestDataSourse::class.java)

    //inyectar el contexto para inicializar la BD
    @Singleton
    @Provides
    fun dbDataSourse(@ApplicationContext context: Context):DbDataSourse {
        //Iniciar Room y pasar el contexto
        return Room.databaseBuilder(context,DbDataSourse::class.java, "user_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    //Inyectar el objeto Dao para tener acceso mas facil
    @Singleton
    @Provides//inyectar UserDao en cualquier parte de la aplicación
    fun userDao(db: DbDataSourse): UserDao = db.userDao()

}