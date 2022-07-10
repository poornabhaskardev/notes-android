package com.poornabhaskar.notes.validation

import android.text.TextUtils
import android.util.Patterns
import com.poornabhaskar.notes.model.UserRequest
import javax.inject.Inject

public class UserValidation @Inject constructor() {
    fun validateOnRegisterUser(userRequest: UserRequest): Pair<Boolean,String>{
        var result = Pair(true, "")
        if(TextUtils.isEmpty(userRequest.username) || TextUtils.isEmpty(userRequest.email) || TextUtils.isEmpty(userRequest.password) ){
            result = Pair(false, "Please enter credentials")
        }else if(!Patterns.EMAIL_ADDRESS.matcher(userRequest.email).matches()){
            result = Pair(false, "Please enter valid email")
        }else if(userRequest.password.length<=6){
            result = Pair(false, "Password length must be greater than 6")
        }
        return result
    }

    fun validateOnLoginUser(userRequest: UserRequest): Pair<Boolean,String>{
        var result = Pair(true, "")
        if(TextUtils.isEmpty(userRequest.email) || TextUtils.isEmpty(userRequest.password) ){
            result = Pair(false, "Please enter credentials")
        }else if(!Patterns.EMAIL_ADDRESS.matcher(userRequest.email).matches()){
            result = Pair(false, "Please enter valid email")
        }else if(userRequest.password.length<=6){
            result = Pair(false, "Password length must be greater than 6")
        }
        return result
    }
}