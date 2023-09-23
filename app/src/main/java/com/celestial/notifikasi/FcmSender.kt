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

class FcmSender(
    private val userFcmToken: String,
    private val command: String,
    private val soundUrl: String,
    private val mContext: Context,
    private val mActivity: Activity
) {

    private lateinit var requestQueue: RequestQueue
    private val postUrl = "https://fcm.googleapis.com/fcm/send"
    private val fcmServerKey = "AAAAJkUyXNs:APA91bGDGi4xYPsd8vsX7vw0KH4JbK1cBpqK-TLREBiKK8FUR2qb5Iu3kfS6WcLnKHduMAreSx0uS7fv5jJzqRl9oFO-_yojf1fIqg3JuoOWMNjQ2VLR4JXh_0YI60hPAGe2bDAe9Guc"

    fun sendCommand() {
        requestQueue = Volley.newRequestQueue(mActivity)
        val mainObj = JSONObject()
        try {
            mainObj.put("to", userFcmToken)
            val dataObject = JSONObject()
            dataObject.put("command", command)
            dataObject.put("sound_url", soundUrl)
            mainObj.put("data", dataObject)

            val request = object : JsonObjectRequest(Method.POST, postUrl, mainObj,
                Response.Listener { response ->
                    // Tambahkan logika jika berhasil mengirim perintah
                },
                Response.ErrorListener { error ->
                    // Tambahkan penanganan kesalahan di sini
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
