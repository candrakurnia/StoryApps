package com.project.storyapps.pref

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.text.TextUtils
import com.google.gson.Gson
import com.project.storyapps.model.LoginResult

internal class UserPreference(context : Context) {
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val NAME = "name"
        private const val TOKEN = "token"
        private const val USERID = "userid"
        private const val IS_LOGIN = "islogin"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val editor = preferences.edit()



    fun getSp(context: Context?): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun getKeyId(context: Context?): String? {
        return getSp(context).getString(USERID, null)
    }

    fun setKeyId(context: Context?, id_profile: String?) {
        getSp(context).edit()
            .putString(USERID, id_profile).apply()
    }

    fun getUser(context: Context?): LoginResult? {
        val user =
            getSp(context).getString(TOKEN, null)
        return if (TextUtils.isEmpty(user)) null else Gson().fromJson(user, LoginResult::class.java)
    }

    fun setUser(context: Context?, user: LoginResult?) {
        val gson = Gson()
        getSp(context).edit()
            .putString(TOKEN, gson.toJson(user))
            .apply()
    }

    fun setIsLogin(context: Context?, isLogin: Boolean?) {
        getSp(context).edit().putBoolean(
            IS_LOGIN,
            isLogin!!
        ).apply()
    }

    fun getIsLogin(context: Context?): Boolean? {
        return getSp(context).getBoolean(
            IS_LOGIN,
            false
        )
    }

    fun logout(context: Context?) {
        setKeyId(context, null)
        setIsLogin(context, false)
        editor.clear()
        editor.commit()
    }
}