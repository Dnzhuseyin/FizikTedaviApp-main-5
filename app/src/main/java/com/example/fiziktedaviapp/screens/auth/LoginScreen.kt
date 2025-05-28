package com.example.fiziktedaviapp.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fiziktedaviapp.navigation.Screen
import com.example.fiziktedaviapp.ui.theme.Primary
import com.example.fiziktedaviapp.ui.theme.PrimaryLight
import com.example.fiziktedaviapp.ui.theme.Surface

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    
    // For form handling and keyboard actions
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()
    
    // Handle login logic
    val handleLogin = {
        isLoading = true
        keyboardController?.hide()
        // TODO: Validate and login
        // For now, simulate a login process
        navController.navigate(Screen.Dashboard.route)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Surface),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            
            // Logo and app name
            Icon(
                imageVector = Icons.Outlined.HealthAndSafety,
                contentDescription = "Logo",
                tint = Primary,
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 16.dp)
            )
            
            Text(
                text = "Fizik Tedavi Uygulaması",
                style = MaterialTheme.typography.headlineMedium,
                color = Primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Email field with improved keyboard handling
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-posta") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = PrimaryLight
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                isError = email.isNotEmpty() && !email.contains("@")
            )

            // Helper text for email validation
            if (email.isNotEmpty() && !email.contains("@")) {
                Text(
                    text = "Lütfen geçerli bir e-posta adresi giriniz",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, bottom = 8.dp)
                )
            }

            // Password field with improved keyboard handling
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Şifre") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password") },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        if (email.isNotEmpty() && password.isNotEmpty() && email.contains("@")) {
                            handleLogin()
                        }
                    }
                ),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = PrimaryLight
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                isError = password.isNotEmpty() && password.length < 6
            )
            
            // Helper text for password validation
            if (password.isNotEmpty() && password.length < 6) {
                Text(
                    text = "Şifre en az 6 karakter olmalıdır",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, bottom = 8.dp)
                )
            }

            // Forgot password with improved styling
            TextButton(
                onClick = { navController.navigate(Screen.ForgotPassword.route) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = "Şifremi unuttum",
                    color = Primary,
                    style = MaterialTheme.typography.labelMedium
                )
            }

            // Login button with loading state
            Button(
                onClick = { 
                    if (email.isNotEmpty() && password.isNotEmpty() && email.contains("@") && password.length >= 6) {
                        handleLogin()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(vertical = 8.dp),
                enabled = !isLoading && email.isNotEmpty() && password.isNotEmpty() && email.contains("@") && password.length >= 6
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = "Giriş Yap",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            // Register button with improved styling
            OutlinedButton(
                onClick = { navController.navigate(Screen.Register.route) },
                border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Kayıt Ol",
                    color = Primary,
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            
            // Divider with "or" text
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(modifier = Modifier.weight(1f))
                Text(
                    text = "  VEYA  ",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline
                )
                Divider(modifier = Modifier.weight(1f))
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            // Social login buttons with improved styling
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Google ile giriş butonu
                OutlinedButton(
                    onClick = { /* TODO: Implement Google login */ },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                ) {
                    Text(
                        text = "Google ile Giriş",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                // Facebook ile giriş butonu
                OutlinedButton(
                    onClick = { /* TODO: Implement Facebook login */ },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                ) {
                    Text(
                        text = "Facebook ile Giriş",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
} 