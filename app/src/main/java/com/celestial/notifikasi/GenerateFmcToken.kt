package com.celestial.notifikasi

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging

class GenerateFmcToken {

    private val firebaseMessaging = FirebaseMessaging.getInstance()

    fun generateToken(): Unit {
        firebaseMessaging.token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.d("TokenGenerator", "Token: $token")
            } else {
                Log.e("TokenGenerator", "Failed to generate token", task.exception)
            }
        }
    }
}

