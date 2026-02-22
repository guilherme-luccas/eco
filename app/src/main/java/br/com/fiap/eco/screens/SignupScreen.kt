package br.com.fiap.eco.screens

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.eco.model.User
import br.com.fiap.eco.navigation.Destination
import br.com.fiap.eco.repository.RoomUserRepository
import br.com.fiap.eco.repository.UserRepository

@Composable
fun SignupScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }

    var isNameError by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    var isCityError by remember { mutableStateOf(false) }

    var showDialogError by remember { mutableStateOf<Boolean?>(null) }
    var showDialogSuccess by remember { mutableStateOf(false) }

    fun validate(): Boolean {
        isNameError = name.length < 3
        isEmailError = email.length < 3 || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        isPasswordError = password.length < 3
        isCityError = city.length < 2
        return !isNameError && !isEmailError && !isPasswordError && !isCityError
    }

    val userRepository: UserRepository = RoomUserRepository(LocalContext.current)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Criar Conta 🌱",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Junte-se à comunidade eco",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Seu nome", style = MaterialTheme.typography.labelSmall) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Ícone nome",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    isError = isNameError,
                    trailingIcon = {
                        if (isNameError) {
                            Icon(imageVector = Icons.Default.Error, contentDescription = "")
                        }
                    },
                    supportingText = {
                        if (isNameError) {
                            Text(
                                text = "Nome é obrigatório",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Seu e-mail", style = MaterialTheme.typography.labelSmall) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Ícone e-mail",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    isError = isEmailError,
                    trailingIcon = {
                        if (isEmailError) {
                            Icon(imageVector = Icons.Default.Error, contentDescription = "")
                        }
                    },
                    supportingText = {
                        if (isEmailError) {
                            Text(
                                text = "E-mail válido é obrigatório",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("Sua cidade", style = MaterialTheme.typography.labelSmall) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.LocationCity,
                            contentDescription = "Ícone cidade",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    isError = isCityError,
                    trailingIcon = {
                        if (isCityError) {
                            Icon(imageVector = Icons.Default.Error, contentDescription = "")
                        }
                    },
                    supportingText = {
                        if (isCityError) {
                            Text(
                                text = "Cidade é obrigatória",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Sua senha", style = MaterialTheme.typography.labelSmall) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Ícone senha",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword,
                        imeAction = ImeAction.Done
                    ),
                    isError = isPasswordError,
                    trailingIcon = {
                        if (isPasswordError) {
                            Icon(imageVector = Icons.Default.Error, contentDescription = "")
                        }
                    },
                    supportingText = {
                        if (isPasswordError) {
                            Text(
                                text = "Senha é obrigatória",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        if (validate()) {
                            userRepository.saveUser(
                                User(
                                    name = name,
                                    email = email,
                                    password = password,
                                    city = city
                                )
                            )
                            showDialogSuccess = true
                        } else {
                            showDialogError = true
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = "Criar conta",
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }

        if (showDialogSuccess) {
            AlertDialog(
                onDismissRequest = {
                    showDialogSuccess = false
                    navController.navigate(Destination.LoginScreen.route)
                },
                title = { Text(text = "Sucesso") },
                text = { Text(text = "Sua conta foi criada com sucesso!") },
                confirmButton = {
                    Button(onClick = {
                        showDialogSuccess = false
                        navController.navigate(Destination.LoginScreen.route)
                    }) {
                        Text(text = "OK")
                    }
                }
            )
        }
        if (showDialogError == true) {
            AlertDialog(
                onDismissRequest = { showDialogError = false },
                title = { Text(text = "Erro") },
                text = { Text(text = "Por favor, verifique os campos e tente novamente.") },
                confirmButton = {
                    Button(onClick = { showDialogError = false }) {
                        Text(text = "OK")
                    }
                }
            )
        }
    }
}
