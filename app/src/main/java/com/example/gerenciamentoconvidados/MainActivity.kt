package com.exemplo.gerenciamentoconvidados

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.exemplo.gerenciamentoconvidados.data.Guest  // ← ADICIONA
import com.exemplo.gerenciamentoconvidados.viewmodel.GuestViewModel  // ← ADICIONA

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GuestApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuestApp(viewModel: GuestViewModel = viewModel()) {
    val guests by viewModel.allGuests.collectAsState(initial = emptyList())
    val confirmedCount by viewModel.confirmedCount.collectAsState(initial = 0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gerenciamento de Convidados") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Card com estatísticas
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$confirmedCount",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32)
                        )
                        Text("Confirmados", style = MaterialTheme.typography.bodyMedium)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${guests.size}",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text("Total", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Formulário de adição
            AddGuestSection(onAdd = { viewModel.insertGuest(it) })

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de convidados
            Text(
                "Lista de Convidados",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(guests) { guest ->
                    GuestItem(
                        guest = guest,
                        onDelete = { viewModel.deleteGuest(it) },
                        onUpdate = { viewModel.updateGuest(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun GuestItem(
    guest: Guest,
    onDelete: (Guest) -> Unit,
    onUpdate: (Guest) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = guest.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    if (!guest.group_name.isNullOrBlank()) {
                        Text(
                            text = "Grupo: ${guest.group_name}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Badge de status
                StatusBadge(status = guest.rsvp)
            }

            if (!guest.drinks.isNullOrBlank() || !guest.notes.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                if (!guest.drinks.isNullOrBlank()) {
                    Text(
                        text = "🍷 ${guest.drinks}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                if (!guest.notes.isNullOrBlank()) {
                    Text(
                        text = "📝 ${guest.notes}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { showDialog = true }) {
                    Text("Editar Status")
                }
                TextButton(
                    onClick = { onDelete(guest) },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Deletar")
                }
            }
        }
    }

    if (showDialog) {
        StatusDialog(
            currentStatus = guest.rsvp,
            onDismiss = { showDialog = false },
            onConfirm = { newStatus ->
                onUpdate(guest.copy(rsvp = newStatus))
                showDialog = false
            }
        )
    }
}

@Composable
fun StatusBadge(status: String) {
    val (color, text) = when (status) {
        "SIM" -> Color(0xFF2E7D32) to "✓ CONFIRMADO"
        "NAO" -> Color(0xFFC62828) to "✗ NÃO VAI"
        else -> Color(0xFFF57C00) to "? TALVEZ"
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = color.copy(alpha = 0.15f),
        border = BorderStroke(1.dp, color)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun AddGuestSection(onAdd: (Guest) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var group by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf("TALVEZ") }
    var drinks by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Adicionar Convidado",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = { expanded = !expanded }) {
                    Text(if (expanded) "Ocultar" else "Mostrar")
                }
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome *") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = group,
                    onValueChange = { group = it },
                    label = { Text("Grupo (opcional)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text("Status:", style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatusButton(
                        text = "SIM",
                        isSelected = selectedStatus == "SIM",
                        color = Color(0xFF2E7D32),
                        modifier = Modifier.weight(1f)
                    ) { selectedStatus = "SIM" }

                    StatusButton(
                        text = "TALVEZ",
                        isSelected = selectedStatus == "TALVEZ",
                        color = Color(0xFFF57C00),
                        modifier = Modifier.weight(1f)
                    ) { selectedStatus = "TALVEZ" }

                    StatusButton(
                        text = "NÃO",
                        isSelected = selectedStatus == "NAO",
                        color = Color(0xFFC62828),
                        modifier = Modifier.weight(1f)
                    ) { selectedStatus = "NAO" }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = drinks,
                    onValueChange = { drinks = it },
                    label = { Text("Bebidas (opcional)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notas (opcional)") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        if (name.isNotBlank()) {
                            onAdd(
                                Guest(
                                    name = name,
                                    group_name = group.ifBlank { null },
                                    rsvp = selectedStatus,
                                    drinks = drinks.ifBlank { null },
                                    notes = notes.ifBlank { null }
                                )
                            )
                            name = ""
                            group = ""
                            selectedStatus = "TALVEZ"
                            drinks = ""
                            notes = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = name.isNotBlank()
                ) {
                    Text("Adicionar Convidado")
                }
            }
        }
    }
}

@Composable
fun StatusButton(
    text: String,
    isSelected: Boolean,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = if (isSelected) {
            ButtonDefaults.buttonColors(containerColor = color)
        } else {
            ButtonDefaults.outlinedButtonColors()
        },
        border = if (!isSelected) BorderStroke(1.dp, color) else null
    ) {
        Text(text, color = if (isSelected) Color.White else color)
    }
}

@Composable
fun StatusDialog(
    currentStatus: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var selectedStatus by remember { mutableStateOf(currentStatus) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Alterar Status") },
        text = {
            Column {
                listOf("SIM", "TALVEZ", "NAO").forEach { status ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedStatus == status,
                            onClick = { selectedStatus = status }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            when (status) {
                                "SIM" -> "✓ Confirmado"
                                "NAO" -> "✗ Não vai"
                                else -> "? Talvez"
                            }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(selectedStatus) }) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}