package com.example.accesologin.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.accesologin.ui.screens.HomeScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.accesologin.ui.screens.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccesoLoginApp() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            val loginViewModel: LoginViewModel = viewModel()
            HomeScreen(
                marsUiState = loginViewModel.loginUiState,
                contentPadding = it
            )
        }
    }
}