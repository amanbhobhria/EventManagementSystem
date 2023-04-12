package com.example.eventmanagementsystem.sampledata

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Get the notification message
        val message = remoteMessage.notification?.body

        // Display the message in a toast or notification
        // ...
    }

}
