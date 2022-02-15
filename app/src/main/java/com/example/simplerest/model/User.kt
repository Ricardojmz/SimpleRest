package com.example.simplerest.model

import androidx.lifecycle.LiveData
import androidx.room.*

//Encapsular las tres peticiones
//crear una tabla user
@Entity(tableName = "user")
data class User (
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "lastname") val lastName: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "thumbnail") val thumbnail: String,
    @PrimaryKey(autoGenerate = true)val id: Int = 0 //llave primaria
        )

//Crear un interface para query
@Dao
interface UserDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)
    //Seleccionar de la tabla user por id
    @Query("SELECT * FROM user ORDER BY id DESC")
    //regresar LiveData (cambiar automaticamente)
    fun getAll(): LiveData<List<User>>

    //Eliminar el usuario
    @Delete
    fun delete(user: User)
}
