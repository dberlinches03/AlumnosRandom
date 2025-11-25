package com.example.alumnosrandom

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

// Actividad principal de la aplicación
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Habilita el modo de borde a borde para que la app ocupe toda la pantalla
        enableEdgeToEdge()
        // Establece el contenido de la actividad con un Composable de Jetpack Compose
        setContent {
            AlumnosRandomTheme {
                // Llama al Composable principal que contiene la lógica de la UI
                RandomAlumnos()
            }
        }
    }
}

/**
 * Composable principal que define la interfaz de usuario y la lógica para el sorteo de alumnos.
 * @param modifier Modificador para personalizar el layout.
 */
@Composable
fun RandomAlumnos(modifier: Modifier = Modifier
    .fillMaxSize()
    .wrapContentSize(Alignment.Center)) {

    // --- ESTADOS DE LA UI ---

    // Lista mutable de alumnos inicial. `remember` para que el estado persista entre recomposiciones.
    val alumnos = remember { mutableStateListOf(
        Alumno("David Berlinches", 1),
        Alumno("Carlos Barrera", 2),
        Alumno("Iker Toribio", 3),
        Alumno("Benjamin Vargas", 4),
        Alumno("David Salvador", 5),
        Alumno("Daniel Beltrán", 6),
        Alumno("Abzael Rodriguez", 7)
    ) }

    // Lista mutable para guardar los alumnos que ya han sido sorteados.
    val alumnosSorteados = remember { mutableStateListOf<Alumno>() }
    // Estado para almacenar el alumno seleccionado en el sorteo actual. Puede ser nulo.
    var alumnoActual by remember { mutableStateOf<Alumno?>(null) }
    // Estado para el texto del campo de entrada donde se escribe el nombre del nuevo alumno.
    var nuevoAlumno by remember { mutableStateOf("") }
    // Estado para mostrar mensajes de error, por ejemplo, si se intenta añadir un alumno duplicado.
    var mensajeError by remember { mutableStateOf("") }

    // Lista calculada de alumnos que todavía no han sido sorteados. Se actualiza automáticamente.
    val alumnosDisponibles = alumnos.filter { it !in alumnosSorteados}



    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Sorteo de Alumnos", style = MaterialTheme.typography.headlineMedium)

        // Campo de texto para añadir un nuevo alumno.
        OutlinedTextField(
            value = nuevoAlumno,
            onValueChange = { nuevoAlumno = it },
            label = { Text("Nuevo alumno") }
        )

        // Muestra un mensaje de error si no está vacío.
        if (mensajeError.isNotBlank()) {
            Text(mensajeError, color = MaterialTheme.colorScheme.error)
        }

        // Botón para añadir un nuevo alumno a la lista.
        Button(onClick = {
            if (nuevoAlumno.isNotBlank()) {
                // Comprueba si el alumno ya existe (ignorando mayúsculas/minúsculas y espacios).
                val nombreExiste = alumnos.any { it.nombre.equals(nuevoAlumno.trim(), ignoreCase = true)}
                if (!nombreExiste) {
                    // Asigna un número único al nuevo alumno.
                    val nuevoNumero = (1..21).firstOrNull { n-> alumnos.none() {it.numero == n} } ?: (alumnos.size + 1)
                    alumnos.add(Alumno(nuevoAlumno.trim(), nuevoNumero))
                    nuevoAlumno = "" // Limpia el campo de texto.
                    mensajeError = "" // Limpia el mensaje de error.
                } else {
                    mensajeError = "El alumno ya existe en la lista"
                }
            }
        }) {
            Text("Añadir alumno")
        }

        // Botón para realizar el sorteo.
        Button(onClick = {
            if (alumnosDisponibles.isNotEmpty()) {
                // Selecciona un alumno al azar de la lista de disponibles.
                val seleccionado = alumnosDisponibles.random()
                alumnoActual = seleccionado
                alumnosSorteados.add(seleccionado) // Añade el alumno a la lista de sorteados.
            } else {
                // Si no hay alumnos disponibles, no se muestra ningún alumno actual.
                alumnoActual = null
            }
        }) {
            Text("Sortear alumno")
        }

        // Botón para reiniciar el sorteo.
        Button(onClick = {
            alumnoActual = null // Limpia el alumno actual.
            alumnosSorteados.clear() // Vacía la lista de alumnos sorteados.
        }) {
            Text("Reiniciar sorteo")
        }

        // Muestra el alumno sorteado si existe.
        if (alumnoActual != null) {
            Text("Alumno sorteado: ${alumnoActual!!.nombre} (Nº ${alumnoActual!!.numero})",
                style = MaterialTheme.typography.titleLarge)
        } else if (alumnosSorteados.size == alumnos.size && alumnos.isNotEmpty()) {
            // Muestra un mensaje cuando todos han sido sorteados.
            Text("Ya han salido todos los alumnos", color = MaterialTheme.colorScheme.primary)
        }

        // --- LISTAS DE ALUMNOS ---

        Text("Alumnos disponibles: ", style = MaterialTheme.typography.titleMedium)
        // Itera y muestra cada alumno disponible.
        for (alumno in alumnosDisponibles) {
            Text("- ${alumno.nombre} (Nº ${alumno.numero})")
        }

        Text("Alumnos que ya han salido: ", style = MaterialTheme.typography.titleMedium)
        // Itera y muestra cada alumno que ya ha sido sorteado.
        for (alumno in alumnosSorteados) {
            Text("- ${alumno.nombre} (Nº ${alumno.numero})")
        }
    }
}

/**
 * Previsualización del Composable `RandomAlumnos` en el editor de Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AlumnosRandomTheme {
        RandomAlumnos()
    }
}
