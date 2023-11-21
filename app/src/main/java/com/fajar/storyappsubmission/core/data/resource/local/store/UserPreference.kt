package com.fajar.storyappsubmission.core.data.resource.local.store

import android.content.SharedPreferences
import androidx.core.content.edit
import com.fajar.storyappsubmission.core.data.model.User

class UserPreferences(private val sharedPreferences: SharedPreferences) {
    companion object{
        const val USER_ID = "user_id"
        const val USER_NAME = "user_name"
        const val USER_TOKEN = "user token"
    }

    fun saveUser(userModel: User){
        sharedPreferences.edit{
            putString(USER_ID, userModel.userId)
            putString(USER_NAME, userModel.name)
            putString(USER_TOKEN, userModel.token)
        }
    }

    fun getUser():User?{
        val userId = sharedPreferences.getString(USER_ID,null)
        val name = sharedPreferences.getString(USER_NAME, null)
        val token = sharedPreferences.getString(USER_TOKEN, null)
        return if (userId != null && name != null && token != null){
            User(userId,name,token)

        }else{
            null
        }
    }
    fun clearUser(){
        sharedPreferences.edit {
            remove(USER_ID)
            remove(USER_NAME)
            remove(USER_TOKEN)
        }
    }
}