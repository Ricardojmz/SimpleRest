package com.example.simplerest.model

//cambiamos a data class para recibir tres propiedades del contenido en Json
data class UserName (
    val title: String,
    val first: String,
    val last: String
        )