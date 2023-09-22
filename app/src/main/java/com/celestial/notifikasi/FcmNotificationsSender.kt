package com.celestial.notifikasi

import android.app.Activity
import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class FcmNotificationsSender(
    private val userFcmToken: String,
    private val title: String,
    private val body: String,
    private val mContext: Context,
    private val mActivity: Activity
) {

    private lateinit var requestQueue: RequestQueue
    private val postUrl = "https://fcm.googleapis.com/fcm/send"
    private val fcmServerKey = "AAAAJkUyXNs:APA91bGDGi4xYPsd8vsX7vw0KH4JbK1cBpqK-TLREBiKK8FUR2qb5Iu3kfS6WcLnKHduMAreSx0uS7fv5jJzqRl9oFO-_yojf1fIqg3JuoOWMNjQ2VLR4JXh_0YI60hPAGe2bDAe9Guc"

    fun sendNotifications() {
        requestQueue = Volley.newRequestQueue(mActivity)
        val mainObj = JSONObject()
        try {
            mainObj.put("to", userFcmToken)
            val notiObject = JSONObject()
            notiObject.put("title", title)
            notiObject.put("body", body)
            notiObject.put("icon", R.drawable.icon) // Masukkan ikon yang ada di drawable saja

            mainObj.put("notification", notiObject)

            val request = object : JsonObjectRequest(Method.POST, postUrl, mainObj,
                Response.Listener { response ->
                    // Kode yang dijalankan jika mendapat respons
                },
                Response.ErrorListener { error ->
                    // Kode yang dijalankan jika terjadi kesalahan
                }) {
                override fun getHeaders(): HashMap<String, String> {
                    val header = HashMap<String, String>()
                    header["content-type"] = "application/json"
                    header["authorization"] = "key=$fcmServerKey"
                    return header
                }
            }
            requestQueue.add(request)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}
