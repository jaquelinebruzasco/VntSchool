package com.jaquelinebruzasco.vntschool_aula18_notifications

import android.Manifest.permission.*
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.jaquelinebruzasco.vntschool_aula18_notifications.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainNotification = MainNotification()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainNotification.createNotificationChannel(this)

        val notificationLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    mainNotification.showNotification(this)
                } else {
                    Toast.makeText(
                        this,
                        resources.getString(R.string.notification_no_permission),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        binding.btnNotification.setOnClickListener {
            checkNotificationPermission(notificationLauncher)
        }

        val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.notification_permission_received),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.notification_permission_not_granted),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.btnPermission.setOnClickListener {
            requestPermission(permissionLauncher, STORAGE_PERMISSION)
        }
    }

    private fun checkNotificationPermission(requestPermission: ActivityResultLauncher<String>) {
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestPermission(
        requestPermission: ActivityResultLauncher<String>,
        STORAGE_PERMISSION: String,
    ) = when {
        ContextCompat
            .checkSelfPermission(this, NOTIFICATION_PERMISSION) == PackageManager.PERMISSION_GRANTED -> {
            // Use API
            }
        shouldShowRequestPermissionRationale(NOTIFICATION_PERMISSION) -> {
            Toast.makeText(
                this,
                resources.getString(R.string.notification_permission_explanation),
                Toast.LENGTH_LONG
            ).show()
        } else -> {
            requestPermission.launch(NOTIFICATION_PERMISSION)
        }
    }

    private companion object {
        private val STORAGE_PERMISSION = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            READ_MEDIA_IMAGES
        } else {
            READ_EXTERNAL_STORAGE
        }

        @RequiresApi(33)
        private const val NOTIFICATION_PERMISSION = POST_NOTIFICATIONS
    }
}