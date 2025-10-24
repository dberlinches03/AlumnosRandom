package com.example.alumnosrandom

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
fun RandomAlumnos(modifier: Modifier = Modifier
    .fillMaxSize()
    .wrapContentSize(Alignment.Center)) {
    val nombresAlumnos = listOf("David Berlinches", "Carlos Barrera", "Iker Toribio", "Benjamin Vargas", "David Salvador", "Daniel Beltrán", "Abzael Rodriguez")


    val alumnos = remember {
        val numerosAleatorios = (1..21).shuffled()
        val listaAlumnos = mutableListOf<Alumno>()
        for (i in nombresAlumnos.indices) {
            val nombre = nombresAlumnos[i]
            val numero = numerosAleatorios[i]
            listaAlumnos.add(Alumno(nombre, numero))
        }
        listaAlumnos
    }
    val alumnosSorteados = remember { mutableStateListOf<Alumno>() }
    var alumnoActual by remember { mutableStateOf<Alumno?>(null) }

    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Sorteo de Alumnos", style = MaterialTheme.typography.headlineMedium)

        Button(onClick = {
            val alumnnosRestantes = alumnos.filter { it !in alumnosSorteados }
            if (alumnnosRestantes.isNotEmpty()) {
                val alumnoSeleccionado = alumnnosRestantes.random()
                alumnoActual = alumnoSeleccionado
                alumnosSorteados.add(alumnoSeleccionado)
            } else {
                alumnoActual = null
            }
        }) {
            Text("Sortear alumno")
        }
        if (alumnoActual != null) {
            Text("Alumno sorteado: ${alumnoActual!!.nombre} (Nº ${alumnoActual!!.numero})",
                style = MaterialTheme.typography.titleLarge)

        } else if (alumnosSorteados.size == alumnos.size) {
            Text("Ya han salido todos los alumnos", color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AlumnosRandomTheme {
        RandomAlumnos()
    }
}