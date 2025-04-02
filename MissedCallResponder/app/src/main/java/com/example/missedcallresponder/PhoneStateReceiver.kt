package com.example.missedcallresponder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log
import java.util.*

class PhoneStateReceiver : BroadcastReceiver() {
    private val TAG = "PhoneStateReceiver"
    private val lastCallMap = HashMap<String, Long>()

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            val phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

            Log.d(TAG, "Phone State: $state, Number: $phoneNumber")

            if (phoneNumber != null) {
                when (state) {
                    TelephonyManager.EXTRA_STATE_RINGING -> {
                        // Cuộc gọi đến - lưu thời gian
                        lastCallMap[phoneNumber] = System.currentTimeMillis()
                        Log.d(TAG, "Incoming call from: $phoneNumber")
                    }

                    TelephonyManager.EXTRA_STATE_IDLE -> {
                        // Cuộc gọi kết thúc - kiểm tra xem có phải cuộc gọi nhỡ không
                        val lastCallTime = lastCallMap[phoneNumber]
                        if (lastCallTime != null) {
                            val currentTime = System.currentTimeMillis()
                            val callDuration = currentTime - lastCallTime

                            // Nếu thời gian cuộc gọi quá ngắn (dưới 5 giây), coi như cuộc gọi nhỡ
                            if (callDuration < 5000) {
                                Log.d(TAG, "Missed call detected from: $phoneNumber, duration: $callDuration ms")
                                sendAutoReplyMessage(context, phoneNumber)
                            }

                            // Xóa số điện thoại khỏi map sau khi xử lý
                            lastCallMap.remove(phoneNumber)
                        }
                    }
                }
            }
        }
    }

    private fun sendAutoReplyMessage(context: Context, phoneNumber: String) {
        try {
            val message = "Xin lỗi, tôi đang bận. Tôi sẽ gọi lại bạn sau."

            // Gửi tin nhắn
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)

            Log.d(TAG, "Auto-reply SMS sent to $phoneNumber")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to send SMS: ${e.message}", e)
        }
    }
}