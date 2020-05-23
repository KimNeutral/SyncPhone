package com.kimbob.syncphone

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.telephony.SmsMessage
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class SmsListener : BroadcastReceiver() {
    private val preferences: SharedPreferences? = null
    override fun onReceive(context: Context?, intent: Intent) {
        if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
            val bundle = intent.extras //---get the SMS message passed in---
            var msgs: Array<SmsMessage?>? = null
            var msgFrom: String?
            Log.i("MSG", "[CAMED]")
            if (bundle != null) {
                //---retrieve the SMS message received---
                try {
                    val pdus =
                        bundle["pdus"] as Array<Any>?
                    msgs = arrayOfNulls<SmsMessage>(pdus!!.size)
                    for (i in msgs.indices) {
                        msgs!![i] = SmsMessage.createFromPdu(pdus!![i] as ByteArray)
                        msgFrom = msgs[i]?.originatingAddress
                        val msgBody: String? = msgs[i]?.messageBody
                        Log.i("MSG", msgBody)
                        with(NotificationManagerCompat.from(context!!)) {
                            var builder = NotificationCompat.Builder(context!!, "10001")
                                .setSmallIcon(R.drawable.ic_announcement_black_24dp)
                                .setContentTitle(msgFrom)
                                .setContentText(msgBody)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                            notify(12345, builder.build())
                            Log.i("MSG", "NOTIFIED")
                        }

                    }
                } catch (e: Exception) {
                    Log.e("ERROR", e.toString())
                }
            }
        }
    }
}