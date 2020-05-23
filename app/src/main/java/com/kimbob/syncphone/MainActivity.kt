package com.kimbob.syncphone

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    val NOTIFICATION_CHANNEL_ID = "10001"
    val PERMISSIONS_REQUEST_CODE = 1000
    var PERMISSIONS = arrayOf(
        Manifest.permission.RECEIVE_SMS
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()

        var smsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        if ( smsPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( this, PERMISSIONS,
                PERMISSIONS_REQUEST_CODE);
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "MY NOTIFICATION"
            val descriptionText = "MY NOTIFICATION DESCRIPTION"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            notificationChannel.enableLights(true)
            notificationChannel.setLightColor(Color.GREEN)
            notificationChannel.enableVibration(true)
            notificationChannel.setVibrationPattern(longArrayOf(100, 200, 100, 200));

            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)

            var builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_announcement_black_24dp)
                .setContentTitle("My notification")
                .setContentText("heleleleo")
                .setAutoCancel(true)

            notificationManager.notify(12345, builder.build())
            Log.i("MSG", "NOTIFIED")
        }
    }
}
