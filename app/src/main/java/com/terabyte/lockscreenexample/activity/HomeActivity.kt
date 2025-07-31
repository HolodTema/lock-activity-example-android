package com.terabyte.lockscreenexample.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.terabyte.lockscreenexample.INTENT_KEY_CHANGE_PASSWORD
import com.terabyte.lockscreenexample.INTENT_KEY_CREATE_PASSWORD
import com.terabyte.lockscreenexample.R
import com.terabyte.lockscreenexample.SH_PREFERENCES_PASSWORD_DEFAULT
import com.terabyte.lockscreenexample.databinding.ActivityHomeBinding
import com.terabyte.lockscreenexample.util.PasswordHelper

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureInsets()
        checkIfPasswordExists()

        binding.buttonLogout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.textChangePassword.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            if (PasswordHelper.getPasswordHash(this) == SH_PREFERENCES_PASSWORD_DEFAULT) {
                intent.putExtra(INTENT_KEY_CREATE_PASSWORD, 0)
            }
            else {
                intent.putExtra(INTENT_KEY_CHANGE_PASSWORD, 0)
            }
            startActivity(intent)
        }
    }

    private fun configureInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun checkIfPasswordExists() {
        if (PasswordHelper.getPasswordHash(this) == SH_PREFERENCES_PASSWORD_DEFAULT) {
            binding.buttonLogout.visibility = View.GONE
            binding.textChangePassword.text = "Create password"
        }
        else {
            binding.buttonLogout.visibility = View.VISIBLE
            binding.textChangePassword.text = "Change password"
        }
    }
}