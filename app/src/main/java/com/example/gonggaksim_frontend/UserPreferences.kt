package com.example.gonggaksim_frontend
import android.content.Context

object UserPreferences {
    private const val PREFS_NAME = "UserInfo"

    fun saveUserInfo(context: Context, accessToken: String, email: String, nickname: String) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("accessToken", accessToken)
        editor.putString("email", email)
        editor.putString("nickname", nickname)
        editor.apply()
    }

    fun getUserInfo(context: Context): Map<String, String?> {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        return mapOf(
            "accessToken" to sharedPreferences.getString("accessToken", null),
            "email" to sharedPreferences.getString("email", null),
            "nickname" to sharedPreferences.getString("nickname", null)
        )
    }

    fun clearUserInfo(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun isLoggedIn(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("accessToken", null) != null
    }

    fun getAccessToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("accessToken", null)
    }
}