package com.celestial.notifikasi

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    lateinit var pesan: EditText
    lateinit var isi: EditText
    lateinit var btnAll: Button
    lateinit var btnSpesifik: Button
    lateinit var token: EditText
    @SuppressLint("MissingInflatedId")
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


// kirim notifikasi
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



//        untuk kirim suara
        findViewById<Button>(R.id.btnsuara).setOnClickListener(){
            // Ganti dengan token perangkat yang ingin Anda tuju
            val targetDeviceToken = findViewById<EditText>(R.id.token).text.toString()

            // Ganti dengan URL suara yang ingin Anda putar
        val soundUrl = "https://firebasestorage.googleapis.com/v0/b/notifikasi-4724b.appspot.com/o/terompet.mp3?alt=media&token=2ee55430-3e8b-4426-a2d4-5b02d6c71b36"

            val fcmSender = FcmSender(
                targetDeviceToken,
                "play_sound",
                soundUrl,
                applicationContext,
                this
            )
            fcmSender.sendCommand()
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
