package com.rakarguntara.readgood.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rakarguntara.readgood.R

@Preview
@Composable
fun UserForm(
    loading: Boolean = false,
    isRegister: Boolean = false,
    onDone: (String, String) -> Unit = {email, pwd -> }
){
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable{ mutableStateOf("") }
    val passwordVisibility = rememberSaveable{ mutableStateOf(false) }
    val passwordFocusRequest = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val modifier = Modifier.height(150.dp)
        .background(MaterialTheme.colorScheme.background)
        .verticalScroll(rememberScrollState())
    if(isRegister) Text(stringResource(R.string.input_email_and_password_with_character_more_or_equal_than_6),
        modifier = Modifier.padding(16.dp))
    EmailInput(
        emailState = email,
        enabled = !loading,
        keyboardActions = KeyboardActions {
            passwordFocusRequest.requestFocus() // Request focus correctly
        }
    )
    PasswordInput(modifier = Modifier.focusRequester(passwordFocusRequest),
        passwordState = password,
        labelId = "Password",
        passwordVisibility = passwordVisibility,
        enabled = !loading,
        keyboardAction = KeyboardActions{
            if(!valid) return@KeyboardActions
            onDone(email.value.trim(), password.value.trim())
        })
    SubmitButton(
        textId = if(isRegister) "Register" else "Login",
        loading = loading,
        validInputs = valid,
        onClick = {
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    )
}

@Composable
fun SubmitButton(textId: String, loading: Boolean, validInputs: Boolean,
                 onClick: () -> Unit = {}) {
    Button(onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        enabled = !loading && validInputs,
        shape = CircleShape){
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(textId, modifier = Modifier.padding(5.dp))
    }

}

@Composable
fun PasswordInput(
    modifier: Modifier,
    passwordState: MutableState<String>,
    labelId: String,
    passwordVisibility: MutableState<Boolean>,
    enabled: Boolean,
    imeAction: ImeAction = ImeAction.Done,
    keyboardAction: KeyboardActions = KeyboardActions.Default
) {
    val visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()
    OutlinedTextField(
        value = passwordState.value,
        onValueChange = { passwordState.value = it },
        label = { Text(labelId) },
        singleLine = true,
        modifier = modifier.padding(16.dp).fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        visualTransformation = visualTransformation,
        trailingIcon = { PasswordVisibility(passwordVisibility = passwordVisibility) },
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
        enabled = enabled,
        keyboardActions = keyboardAction
    )
}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible = passwordVisibility.value
    IconButton(onClick = {passwordVisibility.value = !visible}){
        Icons.Default.Close
    }
}

@Composable
fun EmailInput(modifier: Modifier =Modifier, emailState: MutableState<String>,
               labelId: String = "Email",
               enabled: Boolean = true,
               imeAction: ImeAction = ImeAction.Next,
               keyboardActions: KeyboardActions = KeyboardActions.Default
){

    InputField(valueState = emailState, labelId = labelId, enabled = enabled, keyboardType = KeyboardType.Email, imeAction = imeAction,
        keyboardActions = keyboardActions)

}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {newVal -> valueState.value = newVal},
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
        label = { Text(labelId) },
        singleLine = isSingleLine,
        modifier = modifier.padding(horizontal = 16.dp).padding(bottom = 16.dp ).fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        keyboardActions = keyboardActions
    )
}
