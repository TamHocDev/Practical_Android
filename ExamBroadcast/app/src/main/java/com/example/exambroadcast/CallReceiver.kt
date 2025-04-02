package com.example.exambroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log
import java.util.Date
import java.util.HashMap

class CallReceiver : BroadcastReceiver() {

    private val TAG = "CallReceiver"
    private val lastCallMap = HashMap<String, Date>()

    override fun onReceive(context: Context, intent: Intent) {
        // Kiểm tra trạng thái điện thoại
        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            val phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

            Log.d(TAG, "Phone State: $state, Number: $phoneNumber")

            // Kiểm tra xem tính năng có được bật không
            val sharedPrefs = context.getSharedPreferences("AutoMessagePrefs", Context.MODE_PRIVATE)
            val isEnabled = sharedPrefs.getBoolean("enabled", false)

            if (!isEnabled) {
                Log.d(TAG, "Auto-message feature is disabled")
                return
            }

            if (phoneNumber != null) {
                when (state) {
                    TelephonyManager.EXTRA_STATE_RINGING -> {
                        // Cuộc gọi đến - lưu thời gian
                        lastCallMap[phoneNumber] = Date()
                        Log.d(TAG, "Incoming call from: $phoneNumber")
                    }

                    TelephonyManager.EXTRA_STATE_IDLE -> {
                        // Cuộc gọi kết thúc - kiểm tra xem có phải cuộc gọi nhỡ không
                        val lastCallTime = lastCallMap[phoneNumber]
                        if (lastCallTime != null) {
                            val currentTime = Date()
                            val callDuration = currentTime.time - lastCallTime.time

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
            // Lấy nội dung tin nhắn từ SharedPreferences
            val sharedPrefs = context.getSharedPreferences("AutoMessagePrefs", Context.MODE_PRIVATE)
            val message = sharedPrefs.getString(
                "message",
                "Xin lỗi, tôi đang bận. Tôi sẽ gọi lại sau."
            )

            // Gửi tin nhắn
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)

            Log.d(TAG, "Auto-reply SMS sent to $phoneNumber with message: $message")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to send SMS: ${e.message}", e)
        }
    }
}