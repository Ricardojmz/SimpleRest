package com.example.simplerest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Delete
import com.example.simplerest.model.User
import com.example.simplerest.repository.UserRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepo: UserRepositoryImp
): ViewModel() {

    //Variable para llamar a la API si responde es verdadera de lo contrario falsa
    private val _isLoading:MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    //LLamar al repositorio para inicializar y obtiene de la BD
    val users: LiveData<List<User>> by lazy {
        userRepo.getAllUser()
    }

    //Crear un getter para transformar el mutable
    val isLoading:LiveData<Boolean> get() = _isLoading

    //Crear una función para verificar si esta cargando
    //false -> agregar un usuario nuevo //corrutina
    fun  addUser(){
        if (_isLoading.value == false)
            viewModelScope.launch (Dispatchers.IO){
                _isLoading.postValue(true)
                //Agrega un nuevo usuario en la BD
                userRepo.getNewuser()
                _isLoading.postValue(false)
            }
    }

    //Crear función para eliminar un usuario
    fun deleteUser(toDelete: User){
        viewModelScope.launch (Dispatchers.IO){
            userRepo.deleteUser(toDelete)
        }
    }
}

