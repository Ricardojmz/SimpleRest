package com.example.simplerest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.simplerest.model.User
import com.example.simplerest.ui.theme.SimpleRestTheme
import com.valentinilk.shimmer.shimmer
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleRestTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MyApp1()
                }
            }
        }
    }
}

@Composable
fun MyApp1(
    viewModel: UserViewModel = hiltViewModel()
) {//Observar el estado inicial
    val users by viewModel.users.observeAsState(arrayListOf())
    val isLoading by viewModel.isLoading.observeAsState(false)

    MyApp(onAddClick = {
        viewModel.addUser()
    }, onDeleteClick = {
        viewModel.deleteUser(it)
    }, users, isLoading)


}

@Composable
fun MyApp(
    onAddClick: (() -> Unit)? = null,
    onDeleteClick: ((toDelete: User) -> Unit)? = null,
    users: List<User>,
    isLoading: Boolean,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Simple Rest + Room") },
                actions = {
                    IconButton(onClick = {
                        onAddClick?.invoke()
                    }) {
                        Icon(Icons.Filled.Add, "Add")
                    }
                }
            )
        }
    ) {//Lista
        LazyColumn {
            var itemCount = users.size
            if (isLoading) itemCount++
            //cuantos item hay
            items(count = itemCount) { index ->
                var auxIndex = index;
                if (isLoading) {
                    if (auxIndex == 0)
                        return@items LoadingCard()
                    auxIndex--
                }
                val user = users[auxIndex]
                Card(
                    shape = RoundedCornerShape(8.dp),
                    elevation = 1.dp,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .fillMaxWidth(),
                ) {
                    Row(modifier = Modifier.padding(8.dp)) {
                        Image(
                            modifier = Modifier.size(50.dp),
                            //Dibuje la imagen
                            painter = rememberImagePainter(
                                data = user.thumbnail,
                                builder = {
                                    //Imagen de reemplazo
                                    placeholder(R.drawable.charla)
                                    error(R.drawable.charla)
                                }
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.FillHeight
                        )
                        Spacer()
                        Column(
                            Modifier.weight(1f),
                        ) {
                            Text("${user.name} ${user.lastName}")
                            Text(user.city)
                        }
                        Spacer()
                        IconButton(onClick = {
                            onDeleteClick?.invoke(user)
                        }) {
                            Icon(Icons.Filled.Delete, "Remove")
                        }
                    }
                }
            }
        }
    }
}
//Interfaz de usuario con animacion de cargar
@Composable
fun LoadingCard() {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 1.dp,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .testTag("loadingCard")
    ) {//Renglon para ver la imagen
        Row(modifier = Modifier.padding(8.dp)) {
            ImageLoading()
            Spacer()
            Column {//Animacion del texto
                Spacer()//Cargando
                Box(modifier = Modifier.shimmer()) {
                    Column {
                        Box(
                            modifier = Modifier
                                .height(15.dp)
                                .fillMaxWidth()
                                .background(Gray)
                        )
                        Spacer()
                        Box(//Animación
                            modifier = Modifier
                                .height(15.dp)
                                .fillMaxWidth()
                                .background(Gray)
                        )
                    }
                }
            }

        }
    }
}
//Cuando esta cargando la imagen
@Composable
fun ImageLoading() {
    Box(modifier = Modifier.shimmer()) {
        Box(//caja
            modifier = Modifier
                .size(50.dp)
                .background(Gray)
        )
    }
}

@Composable
fun Spacer(size: Int = 8) = Spacer(modifier = Modifier.size(size.dp))

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    //JetpackComposeSimpreRestApiTheme {
        MyApp(isLoading = true, users = arrayListOf())
    }
//}