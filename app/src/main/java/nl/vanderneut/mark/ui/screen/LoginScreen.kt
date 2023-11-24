package nl.vanderneut.mark.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nl.vanderneut.mark.R
import nl.vanderneut.mark.Screens
import nl.vanderneut.mark.SharedPreferencesHelper
import nl.vanderneut.mark.api.Repository
import nl.vanderneut.mark.components.EmailInput
import nl.vanderneut.mark.components.LoginErrorUI
import nl.vanderneut.mark.components.PasswordInput
import nl.vanderneut.mark.components.PopupMessage


@SuppressLint("StateFlowValueCalledInComposition")
@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    navController: NavController,
    repository: Repository,
    sharedPreferencesHelper: SharedPreferencesHelper,
    viewModel: LoginScreenViewModel = remember {
        LoginScreenViewModel(repository = repository, sharedPreferencesHelper = sharedPreferencesHelper)
    }
) {
    var showPopup by remember { mutableStateOf(false) }
    var popupMessage by remember { mutableStateOf("") }
    val isLoading by viewModel.loading.collectAsState()
    val errorState by viewModel.isError.collectAsState()
    var showLoginForm by remember { mutableStateOf(true) }

    // Define email and password here
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Check if the user is already signed in using SharedPreferencesHelper
    val isUserSignedIn = sharedPreferencesHelper.isLoggedIn()

    // In LaunchedEffect, set showPopup and popupMessage based on conditions
    LaunchedEffect(viewModel.registrationSuccessful.value, viewModel.isError.value) {
        if (viewModel.registrationSuccessful.value) {
            viewModel.clearRegistrationSuccess()
            showPopup = true
            popupMessage = "Registration successful!"
        } else if (viewModel.isError.value) {
            viewModel.clearError()
            showPopup = true
            popupMessage = "Registration failed. Please try again."
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        when {
            isLoading -> {
                // Show a loading indicator
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .padding(16.dp)
                )
            }
            showPopup -> {
                // Show the pop-up message
                PopupMessage(
                    message = popupMessage,
                    onDismiss = { showPopup = false }
                )
            }
            isUserSignedIn -> {
                // Redirect to TopNews if the user is already signed in
                navController.navigate(Screens.TopNews.name)
            }
            else -> {
                // Show the login form or registration form based on showLoginForm.value
                if (showLoginForm) {
                    UserForm(
                        loading = false,
                        isCreateAccount = false,
                        onDone = { userEmail, userPassword ->
                            email = userEmail
                            password = userPassword
                            viewModel.handleLoginOrRegister(email, password, true) {
                                navController.navigate(Screens.TopNews.name)
                            }
                        }
                    )
                } else {
                    UserForm(
                        loading = false,
                        isCreateAccount = true,
                        onDone = { userEmail, userPassword ->
                            email = userEmail
                            password = userPassword
                            viewModel.handleLoginOrRegister(email, password, false){
                                navController.navigate(Screens.LoginScreen.name)
                            }

                        }
                    )
                    // Registration form
                    // Note: Since we handled registration in LaunchedEffect, no need to do anything here
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.padding(15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val text = if (showLoginForm) stringResource(R.string.signUp) else stringResource(R.string.Login)
            Text(text = stringResource(R.string.NewUser))
            Text(
                text,
                modifier = Modifier
                    .clickable {
                        showLoginForm = !showLoginForm
                    }
                    .padding(start = 5.dp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}




@ExperimentalComposeUiApi
@Preview
@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = { email, pwd -> }
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }

    // Use remember for isError
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .height(250.dp)
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isCreateAccount) Text(text = stringResource(R.string.RegisterAcc))

        EmailInput(
            emailState = email,
            enabled = !loading,
            onAction = KeyboardActions {
                passwordFocusRequest.requestFocus()
            },
        )
        PasswordInput(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            passwordState = password,
            labelId = stringResource(R.string.pwdLabel),
            enabled = !loading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
            })

        SubmitButton(
            textId = if (isCreateAccount) stringResource(R.string.Register) else stringResource(R.string.login),
            loading = loading,
            validInputs = valid
        ) {
            // Reset error state
            isError = false

            // Perform action
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }

        // Display loading indicator
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .padding(16.dp)
            )
        }

        // Display error UI if there is an error
        if (isError) {
            LoginErrorUI(
                errorMessage = stringResource(R.string.default_error_message),
                onDismiss = {
                    // Clear error state
                    isError = false
                }
            )
        }
    }
}


@Composable
fun SubmitButton(
    textId: String,
    loading: Boolean,
    validInputs: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = CircleShape
    ) {
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = textId, modifier = Modifier.padding(5.dp))
    }
}



