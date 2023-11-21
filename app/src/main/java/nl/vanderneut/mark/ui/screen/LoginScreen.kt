package nl.vanderneut.mark.ui.screen

import android.util.Log
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
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import nl.vanderneut.mark.components.EmailInput
import nl.vanderneut.mark.components.ErrorUI
import nl.vanderneut.mark.components.PasswordInput


@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val showLoginForm = rememberSaveable { mutableStateOf(true) }
    val isError by viewModel.isError.collectAsState()
    Log.d("login", "here")

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {


            when {
                isError -> {
                    Log.d("login", "iserrorui")
                    ErrorUI()
                }
                else -> {
                    if (showLoginForm.value) UserForm(
                        loading = false,
                        isCreateAccount = false

                    ) { email, password ->
                        viewModel.signInWithEmailAndPassword(email, password) {
                            navController.navigate(Screens.TopNews.name)
                            Log.d("login", "nav to topnews.name")
                        }
                    }
                    else {
                        UserForm(loading = false, isCreateAccount = true) { email, password ->
                            viewModel.createUserWithEmailAndPassword(email, password) {
                                navController.navigate(Screens.TopNews.name)
                                Log.d("login", "also nav to topnews,ame")
                            }
                        }
                    }
                }
            }


        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.padding(15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val text = if (showLoginForm.value) stringResource(R.string.signUp) else stringResource(
                R.string.Login
            )
            Text(text = stringResource(R.string.NewUser))
            Text(text,
                modifier = Modifier
                    .clickable {
                        showLoginForm.value = !showLoginForm.value

                    }
                    .padding(start = 5.dp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.secondaryVariant)

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
    val modifier = Modifier
        .height(250.dp)
        .background(MaterialTheme.colors.background)
        .verticalScroll(rememberScrollState())


    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isCreateAccount) Text(text = stringResource(R.string.RegisterAcc))

        EmailInput(
            emailState = email, enabled = !loading,
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
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
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



