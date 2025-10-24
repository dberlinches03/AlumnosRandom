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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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

    val alumnos = remember { mutableStateListOf(
        Alumno("David Berlinches", 1),
        Alumno("Carlos Barrera", 2),
        Alumno("Iker Toribio", 3),
        Alumno("Benjamin Vargas", 4),
        Alumno("David Salvador", 5),
        Alumno("Daniel Beltrán", 6),
        Alumno("Abzael Rodriguez", 7)
    ) }

    val alumnosSorteados = remember { mutableStateListOf<Alumno>() }
    var alumnoActual by remember { mutableStateOf<Alumno?>(null) }
    var nuevoAlumno by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }

    val alumnosDisponibles = alumnos.filter { it !in alumnosSorteados}

    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Sorteo de Alumnos", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = nuevoAlumno,
            onValueChange = { nuevoAlumno = it },
            label = { Text("Nuevo alumno") }
        )

        if (mensajeError.isNotBlank()) {
            Text(mensajeError, color = MaterialTheme.colorScheme.error)
        }

        Button(onClick = {
            if (nuevoAlumno.isNotBlank()) {
                val nombreExiste = alumnos.any { it.nombre.equals(nuevoAlumno.trim(), ignoreCase = true)}
                if (!nombreExiste) {
                    val nuevoNumero = (1..21).firstOrNull { n-> alumnos.none() {it.numero == n} } ?: (alumnos.size + 1)
                    alumnos.add(Alumno(nuevoAlumno.trim(), nuevoNumero))
                    nuevoAlumno = ""
                    mensajeError = ""
                } else {
                    mensajeError = "El alumno ya existe en la lista"
                }
            }
        }) {
            Text("Añadir alumno")
        }

        Button(onClick = {
            if (alumnosDisponibles.isNotEmpty()) {
                val seleccionado = alumnosDisponibles.random()
                alumnoActual = seleccionado
                alumnosSorteados.add(seleccionado)
            } else {
                alumnoActual = null
            }
        }) {
            Text("Sortear alumno")
        }

        Button(onClick = {
            alumnoActual = null
            alumnosSorteados.clear()
        }) {
            Text("Reiniciar sorteo")
        }

        if (alumnoActual != null) {
            Text("Alumno sorteado: ${alumnoActual!!.nombre} (Nº ${alumnoActual!!.numero})",
                style = MaterialTheme.typography.titleLarge)
        } else if (alumnosSorteados.size == alumnos.size) {
            Text("Ya han salido todos los alumnos", color = MaterialTheme.colorScheme.primary)
        }

        Text("Alumnos disponibles: ", style = MaterialTheme.typography.titleMedium)
        for (alumno in alumnosDisponibles) {
            Text("- ${alumno.nombre} (Nº ${alumno.numero})")
        }

        Text("Alumnos que ya han salido: ", style = MaterialTheme.typography.titleMedium)
        for (alumno in alumnosSorteados) {
            Text("- ${alumno.nombre} (Nº ${alumno.numero})")
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