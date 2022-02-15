package com.example.simplerest.datasourse

import com.example.simplerest.model.ApiResponse
import retrofit2.http.GET

//Llamar a la Rest API
interface RestDataSourse {

    //Crear funciones
    //Agregar las Api
    @GET("?inc=name")
    suspend fun getUserName(): ApiResponse

    @GET("?inc=location")
    suspend fun getUserLocation(): ApiResponse

    @GET("?inc=picture")
    suspend fun getUserPicture(): ApiResponse
}