package com.terabyte.lockscreenexample.util

import android.content.Context
import com.terabyte.lockscreenexample.SHARED_PREFERENCES_NAME
import com.terabyte.lockscreenexample.SH_PREFERENCES_KEY_PASSWORD
import androidx.core.content.edit

object PasswordHelper {

    fun checkPassword(context: Context, password: String): Boolean {
        val currentHash = hash(password)
        val expectedHash = getPasswordHash(context)
        return currentHash == expectedHash
    }

    fun savePassword(context: Context, password: String) {
        val shPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val hash = hash(password)
        shPreferences.edit {
            putLong(SH_PREFERENCES_KEY_PASSWORD, hash)
        }
    }

    fun getPasswordHash(context: Context): Long {
        val shPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return shPreferences.getLong(SH_PREFERENCES_KEY_PASSWORD, 0L)
    }


    private fun hash(password: String): Long {
        // p is a prime number
        // m is a large prime number
        val p = 31L
        val m = 1e9.toLong() + 7L

        // to store hash value
        var result = 0L

        // to store p^i
        var pPow: Long = 1

        // Calculating hash value
        for (i in password.indices) {
            result = (result + (password[i] - 'a' + 1) * pPow) % m
            pPow = (pPow * p) % m
        }
        return result
    }
}