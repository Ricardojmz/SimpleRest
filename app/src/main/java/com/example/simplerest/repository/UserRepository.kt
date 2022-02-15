package com.example.simplerest.repository

import androidx.lifecycle.LiveData
import androidx.room.Delete
import com.example.simplerest.datasourse.RestDataSourse
import com.example.simplerest.model.User
import com.example.simplerest.model.UserDao
import kotlinx.coroutines.delay
import javax.inject.Inject

//cambiamos la clase por interface (implementar mok-uniTest)
interface UserRepository {
    suspend fun getNewuser():User
    //Borrar usuario
    suspend fun deleteUser(toDelete: User)
    //Regresar una lista de usuarios
    fun getAllUser():LiveData<List<User>>

}

//Creamos una clase implementa UserReposity
//Creamos el constructor
class UserRepositoryImp @Inject constructor(
    private val dataSourse: RestDataSourse,
    private val userDao: UserDao

) : UserRepository{
    //Implementar getUser y llamar a la API
    override suspend fun getNewuser():User {
        val name = dataSourse.getUserName().results[0].name!!
        val location = dataSourse.getUserLocation().results[0].location!!
        val picture = dataSourse.getUserPicture().results[0].picture!!
        //Inicializar el User
        val user = User(name.first, name.last, location.city, picture.thumbnail)
        //Insertar el usuario en la BD
        userDao.insert(user)
        return user

    }
    //Eliminar usuario
    override suspend fun deleteUser(toDelete: User)  = userDao.delete(toDelete)
    //Agregar la lista de usuario que proviene de la BD
    override fun getAllUser(): LiveData<List<User>> = userDao.getAll()
}