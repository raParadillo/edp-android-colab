package com.example.myapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                GroceryListApp()
            }
        }
    }
}


@Composable
fun GroceryListApp() {
    // ---- 1. STATE ----
    // The current text in the input box
    var newItem by remember { mutableStateOf("") }
    // The list of grocery items (observable, so the UI updates)
    val groceries = remember { mutableStateListOf<String>() }
    var showError by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "My Grocery List", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        // ---- 2. INPUT + ADD EVENT ----
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = newItem,
                onValueChange = {
                    if (it.length <= 30) {
                        newItem = it
                        if (it.isNotBlank()) showError = false // Clear error once they start typing
                    }
                },
                label = { Text("Enter an item") },
                modifier = Modifier.weight(1f),
                isError = showError, // Turns border/label red when true

            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (newItem.isNotBlank()) {
                    groceries.add(newItem.trim())
                    newItem = ""       // Clear text field on success
                    showError = false  // Reset error state
                } else {
                    showError = true
                    newItem = ""
                }
            }) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        // ---- CLEAR ALL BUTTON & ITEM COUNT ROW ----
        if (groceries.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Items: ${groceries.size}",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Button(
                    onClick = { groceries.clear() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                ) {
                    Text("Clear All")
                }

            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // ---- 3. LIST + DELETE EVENT ----
        LazyColumn {
            items(groceries) { list ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = list, fontSize = 18.sp)
                    IconButton(onClick = { groceries.remove(list) }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light Mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun GroceryListAppPreview() {
    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            GroceryListApp()
        }
    }
}