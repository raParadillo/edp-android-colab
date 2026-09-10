package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.AppViewModelFactory
import com.example.myapplication.ui.PostsScreen
import com.example.myapplication.ui.PostsViewModel
import com.example.myapplication.ui.ProfileScreen
import com.example.myapplication.ui.ThemeViewModel
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val factory = AppViewModelFactory(applicationContext)
            val postsVm: PostsViewModel = viewModel(factory = factory)
            val themeVm: ThemeViewModel = viewModel(factory = factory)

            // TODO 13a: read the saved theme
            val darkTheme by themeVm.isDarkTheme.collectAsStateWithLifecycle()

            // TODO 13b: pass darkTheme into your theme, and switch dynamic colour off
            MyApplicationTheme(darkTheme = darkTheme, dynamicColor = false) {
                MySocialApp(postsVm, themeVm)
            }
        }
    }
}

// Given — copy exactly. This draws the bottom bar and swaps the two pages.
@Composable
fun MySocialApp(postsVm: PostsViewModel, themeVm: ThemeViewModel) {
    var tab by rememberSaveable { mutableIntStateOf(0) }
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = tab == 0, onClick = { tab = 0 },
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Posts") },
                )
                NavigationBarItem(
                    selected = tab == 1, onClick = { tab = 1 },
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("Profile") },
                )
            }
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            if (tab == 0) PostsScreen(postsVm) else ProfileScreen(postsVm, themeVm)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppPreview() {
    MyApplicationTheme(dynamicColor = false) {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = true,
                        onClick = {},
                        icon = { Icon(Icons.Default.Home, null) },
                        label = { Text("Posts") },
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = {},
                        icon = { Icon(Icons.Default.Person, null) },
                        label = { Text("Profile") },
                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {}) {
                    Icon(Icons.Default.Add, contentDescription = "New post")
                }
            }
        ) { padding ->
            Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) {
                Text("No posts yet. Tap + to write your first one.")
            }
        }
    }
}