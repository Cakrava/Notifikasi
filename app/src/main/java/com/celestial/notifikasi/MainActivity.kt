package com.celestial.notifikasi

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    lateinit var pesan: EditText
    lateinit var isi: EditText
    lateinit var btnAll: Button
    lateinit var btnSpesifik: Button
    lateinit var token: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        generateToken()
        pesan = findViewById(R.id.Pesan)
        isi = findViewById(R.id.isi)
        btnAll = findViewById(R.id.btnAll)
        btnSpesifik = findViewById(R.id.btnSpesifik)
        token = findViewById(R.id.token)



        btnAll.setOnClickListener() {
            if (pesan.text.toString().isNotEmpty() && isi.text.toString().isNotEmpty()) {
                val notificationsSender =
                    FcmNotificationsSender(
                        "/topics/all",
                        pesan.text.toString(),
                        isi.text.toString(),
                        applicationContext,
                        this@MainActivity
                    )
                notificationsSender.sendNotifications()
            } else {
                Toast.makeText(this, "Jangan isi pesan kosong", Toast.LENGTH_SHORT).show()
            }
        }

        btnSpesifik.setOnClickListener() {
            if (pesan.text.toString().isNotEmpty() && isi.text.toString()
                    .isNotEmpty() && token.text.toString().isNotEmpty()
            ) {
                val notificationsSender =
                    FcmNotificationsSender(
                        token.text.toString(),
                        pesan.text.toString(),
                        isi.text.toString(),
                        applicationContext,
                        this@MainActivity
                    )
                notificationsSender.sendNotifications()
            } else {
                Toast.makeText(this, "Masukkan semuanya cok", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val firebaseMessaging = FirebaseMessaging.getInstance()

    fun generateToken(): Unit {
        firebaseMessaging.token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.d("TokenGenerator", "Token: $token")
                findViewById<EditText>(R.id.tmptoken).setText(token)
            } else {
                Log.e("TokenGenerator", "Failed to generate token", task.exception)
                findViewById<EditText>(R.id.tmptoken).setText("Gagal")
            }
        }
    }
}
