package nl.vanderneut.mark.ui.screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.vanderneut.mark.models.user

class LoginScreenViewModel: ViewModel() {
   // val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading
    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean>
        get() = _isError

    val errorHandler = CoroutineExceptionHandler{
            _, error ->
        if(error is Exception){
            _isError.value = true
        }
    }

    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit )
    = viewModelScope.launch{
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        home()
                    }else {
                        _isError.value = true

                    }


                }

        }catch (ex: Exception){
            _isError.value = true

        }


    }



    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        home: () -> Unit) {
        if (_loading.value == false) {
             _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                     if (task.isSuccessful) {
                         //me
                         val displayName = task.result?.user?.email?.split('@')?.get(0)
                         createUser(displayName)
                         home()
                     }else {
                        _isError.value = true


                     }
                    _loading.value = false


                }
        }


    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user = user(userId = userId.toString(),
            displayName = displayName.toString(),
            id = null).toMap()


        FirebaseFirestore.getInstance().collection("users")
            .add(user)
    }


}