package com.example.simplerest.datasourse

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.simplerest.model.User
import com.example.simplerest.model.UserDao


//Crear una instancia de la clase
//Inicializar la BD de tipo abstracta
@Database(entities = [User::class], version = 1)
abstract class DbDataSourse :RoomDatabase() {
    abstract fun userDao(): UserDao
}