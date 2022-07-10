package com.poornabhaskar.notes.viewmodel

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poornabhaskar.notes.model.UserRequest
import com.poornabhaskar.notes.model.UserResponse
import com.poornabhaskar.notes.repository.UserRepository
import com.poornabhaskar.notes.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor (private val userRepository: UserRepository):ViewModel() {
    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
    get() = userRepository.userResponseLiveData

    fun registerUser(userRequest: UserRequest){
        viewModelScope.launch {
            userRepository.registerUser(userRequest)
        }
    }

    fun loginUser(userRequest: UserRequest){
        viewModelScope.launch {
            userRepository.loginUser(userRequest)
        }
    }

    fun validateCredentials(username:String,email:String,password:String): Pair<Boolean,String>{
        var result = Pair(true, "")
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ){
            result = Pair(false, "Please enter credentials")
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            result = Pair(false, "Please enter valid email")
        }else if(password.length<=6){
            result = Pair(false, "Password length must be greater than 5")
        }
        return result
    }
}