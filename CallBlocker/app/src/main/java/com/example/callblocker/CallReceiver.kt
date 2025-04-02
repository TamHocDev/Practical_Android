package com.example.callblocker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast

class CallReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        if (state == TelephonyManager.EXTRA_STATE_RINGING) {
            val phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
            Log.d("CallReceiver", "Incoming call from: $phoneNumber")

            val blockedNumbersManager = BlockedNumbersManager(context)
            if (blockedNumbersManager.isNumberBlocked(phoneNumber)) {
                Log.d("CallReceiver", "Blocked call from: $phoneNumber")
                Toast.makeText(context, "Cuộc gọi bị chặn từ: $phoneNumber", Toast.LENGTH_SHORT).show()
                endCall(context)
            }
        }
    }

    private fun endCall(context: Context) {
        // Phần chặn cuộc gọi, xem lại các lưu ý ở phần trước.
    }
}