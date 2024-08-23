package com.rakarguntara.readgood.screens.update

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rakarguntara.readgood.R
import com.rakarguntara.readgood.components.InputField
import com.rakarguntara.readgood.components.LoadingIndicator
import com.rakarguntara.readgood.components.RatingBar
import com.rakarguntara.readgood.models.BookModel
import com.rakarguntara.readgood.navigation.ReadScreens
import com.rakarguntara.readgood.network.ResponseState
import com.rakarguntara.readgood.utils.formatDate
import com.rakarguntara.readgood.viewmodel.home.HomeViewModel
import com.rakarguntara.readgood.widgets.ReadAppBar
import com.rakarguntara.readgood.widgets.RoundedButton

@Composable
fun UpdateScreen(navController: NavHostController, bookItemId: String?,
                 homeViewModel: HomeViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        ReadAppBar(title = "Update",
            navController = navController,
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            showProfile = false,
            onBackArrowClicked = {navController.popBackStack()})
    }) {
        val bookInfo = produceState<ResponseState<List<BookModel>, Boolean, Exception>>(
            initialValue = ResponseState(data = emptyList(), true, Exception(""))){
            value = homeViewModel.data.value
        }.value

        Surface(modifier = Modifier.padding(it).fillMaxSize()
            .padding(horizontal = 16.dp)) {
            Column(
                modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(bookInfo.loading == true){
                    LoadingIndicator(true)
                    bookInfo.loading = false
                } else {
                    LoadingIndicator(false)
                    Surface(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        shape = CircleShape,
                        shadowElevation = 4.dp
                    ) {
                        ShowBookUpdate(bookInfo = homeViewModel.data.value, bookItemId)
                    }
                    ShowSimpleForm(bookInfo = homeViewModel.data.value.data?.first{id->
                        id.id == bookItemId
                    }, navController)
                }

            }
        }
    }
}

@Composable
fun ShowSimpleForm(bookInfo: BookModel?, navController: NavHostController) {
    val context = LocalContext.current
    val notesText = remember {
        mutableStateOf("")
    }
    val isStartedReading = remember {
        mutableStateOf(false)
    }

    val isFinishedReading = remember {
        mutableStateOf(false)
    }
    val ratingVal = remember {
        mutableIntStateOf(0)
    }
    SimpleForm (
        defaultValue = if(bookInfo?.notes != null) bookInfo.notes.toString()
        else "No thoughts"
    ){ note ->
        notesText.value = note
    }

    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start){
        TextButton(onClick = {
            isStartedReading.value = true
        },
            enabled = bookInfo?.startRead == null){
            if(bookInfo?.startRead == null){
                if(!isStartedReading.value){
                    Text(text = "Start Reading")
                } else {
                    Text(text = "Started Reading",
                        modifier = Modifier.alpha(0.6f),
                        color = Color.Red.copy(0.5f))
                }
            } else {
                 Text("Started on ${formatDate(bookInfo.startRead!!)}")
            }
            Spacer(modifier = Modifier.padding(8.dp))
            TextButton(onClick = {isFinishedReading.value = true},
                enabled = bookInfo?.finishedRead == null){
                if(bookInfo?.finishedRead == null){
                    if(!isFinishedReading.value){
                        Text(text = "Mark as read")
                    } else {
                        Text(text = "Finished Reading" )
                    }
                } else {
                    Text(text = "Finishhed on: ${formatDate(bookInfo.finishedRead!!)}")
                }

            }
        }
        }
    Text("Rating", modifier = Modifier.padding(vertical = 16.dp))
    bookInfo?.rating?.let { rating ->
        RatingBar(rating = rating){ selectedRate ->
            ratingVal.intValue = selectedRate
            Log.d("RATING VALUE", "ShowSimpleForm: ${ratingVal.intValue}")
        }

    }
    Spacer(modifier = Modifier.padding(8.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val changedNotes = bookInfo?.notes != notesText.value
        val changedRating = bookInfo?.rating != ratingVal.intValue
        val isFinishedTimeStamp =
            if (isFinishedReading.value) Timestamp.now() else bookInfo?.finishedRead
        val isStartedTimeStamp =
            if (isStartedReading.value) Timestamp.now() else bookInfo?.startRead
        val bookUpdate =
            changedNotes || changedRating || isStartedReading.value || isFinishedReading.value
        val bookToUpdate = hashMapOf(
            "finishedRead" to isFinishedTimeStamp,
            "startRead" to isStartedTimeStamp,
            "rating" to ratingVal.intValue,
            "notes" to notesText.value
        ).toMap()
        RoundedButton(label = "Update") {
            if(bookUpdate){
                FirebaseFirestore.getInstance().collection("data")
                    .document(bookInfo?.userId!!)
                    .collection("books")
                    .document(bookInfo.id!!)
                    .update(bookToUpdate)
                    .addOnCompleteListener {task ->
                        showToast(context, "Update success!")
                        Log.d("UPDATE SUCCESS", "ShowSimpleForm: ${task.result}")
                        navController.navigate(ReadScreens.HomeScreen.name){
                            popUpTo(ReadScreens.HomeScreen.name){inclusive = true}
                        }
                    }
                    .addOnFailureListener {
                        Log.d("UPDATE ERROR", "ShowSimpleForm: Failed Update")
                    }
            }
        }
        Spacer(modifier = Modifier.padding(32.dp))
        val openDialog = remember {
            mutableStateOf(false)
        }
        if(openDialog.value){
            ShowAlertDialog(message = stringResource(R.string.are_you_sure_want_to_delete),
                openDialog){
                FirebaseFirestore.getInstance().collection("data")
                    .document(FirebaseAuth.getInstance().currentUser!!.uid)
                    .collection("books")
                    .document(bookInfo?.id!!)
                    .delete()
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            openDialog.value = false
                            navController.navigate(ReadScreens.HomeScreen.name){
                                popUpTo(ReadScreens.HomeScreen.name){inclusive = true}
                            }
                        }
                    }
            }
        }
        RoundedButton(label = "Delete") {
            openDialog.value = true
        }
    }
}

@Composable
fun ShowAlertDialog(message: String,
                    openDialog: MutableState<Boolean>,
                    onYesPressed: () -> Unit) {

    if(openDialog.value){
        AlertDialog(onDismissRequest = {},
            title = { Text(stringResource(R.string.warning)) },
            text = { Text(message) },
            confirmButton = {
                TextButton(onClick = {onYesPressed.invoke()}){
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    openDialog.value = false
                }){
                    Text("No")
                }
            })
    }

}


fun showToast(context: Context, message: String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun SimpleForm(
    defaultValue: String = "Great Book!",
    onSearch: (String) -> Unit = {}
){
    val textValue = rememberSaveable{
        mutableStateOf(defaultValue)
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(textValue.value){
        textValue.value.trim().isNotEmpty()
    }

    InputField(
        modifier = Modifier.fillMaxWidth(),
        valueState = textValue, labelId = "Enter your thoughts",
        enabled = true, keyboardActions = KeyboardActions {
            if(!valid) return@KeyboardActions
            onSearch(textValue.value.trim())
            keyboardController?.hide()
        })

}

@Composable
fun ShowBookUpdate(
    bookInfo: ResponseState<List<BookModel>, Boolean, Exception>,
    bookItemId: String?
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Spacer(modifier = Modifier.size(50.dp))
        if(bookInfo.data != null){
            CardListItem(book = bookInfo.data!!.first(){bk ->
                bk.id == bookItemId
            }, onPressDetails = {})

        }
        Spacer(modifier = Modifier.size(50.dp))
    }
}

@Composable
fun CardListItem(book: BookModel, onPressDetails: () -> Unit) {
    Card(modifier = Modifier.padding(16.dp).clip(RoundedCornerShape(20.dp))
        .clickable {  }) {
        AsyncImage(model = book.photoUrl, contentDescription = "Potret",
            modifier = Modifier.height(150.dp).width(100.dp).clip(RoundedCornerShape(20.dp)))
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(book.title.toString(), Modifier.padding(top = 16.dp), fontSize = 14.sp, color = Color.Black,
            fontWeight = FontWeight.Bold)
        Text(book.author.toString(), fontSize = 12.sp,
            fontStyle = FontStyle.Italic,
            color = Color.LightGray,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Text(book.published.toString(), fontSize = 12.sp,
            fontStyle = FontStyle.Italic,
            color = Color.LightGray,
            modifier = Modifier.padding(bottom = 4.dp)
        )
    }
}
