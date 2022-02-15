package com.example.simplerest.model

//Crear una lista para los resultados
data class ApiResponse (
    val results: List<Results> = emptyList(),
        )

//Crear una data class para los result del archivo en Json pudiendo ser nulo uno de los tres
data class Results (
    val name: UserName?,
    val location: UserLocation?,
    val picture: UserPicture?,
        )