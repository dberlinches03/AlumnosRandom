package com.example.alumnosrandom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.alumnosrandom.ui.theme.AlumnosRandomTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlumnosRandomTheme {
                RandomAlumnos()
            }
        }
    }
}

@Composable
fun RandomAlumnos() {
    val nombresAlumnos = listOf("David Berlinches", "Carlos Barrera", "Iker Toribio", "Benjamin Vargas", "David Salvador", "Daniel Beltrán")


    val alumnos = remember {
        val alumnosAleatorios = nombresAlumnos.random()
        val numerosAleatorios = (1..21).shuffled()
        val listaAlumnos = mutableListOf<Alumno>()
        for (i in nombresAlumnos.indices) {
            val nombre = nombresAlumnos[i]
            val numero = numerosAleatorios[i]
            listaAlumnos.add(Alumno(nombre, numero))
        }
        listaAlumnos

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AlumnosRandomTheme {

    }
}