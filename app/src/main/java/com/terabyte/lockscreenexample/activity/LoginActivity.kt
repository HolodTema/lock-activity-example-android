package com.terabyte.lockscreenexample.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.terabyte.lockscreenexample.PASSWORD_LEN
import com.terabyte.lockscreenexample.R
import com.terabyte.lockscreenexample.databinding.ActivityLoginBinding
import com.terabyte.lockscreenexample.util.PasswordHelper
import com.terabyte.lockscreenexample.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureInsets()

        configureNumberButtons()
        configureBackspaceButton()
        configureFingerprintButton()
        configureIndicators()
    }

    private fun configureInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun configureNumberButtons() {
        val arrayButtons = arrayOf(
            binding.button0,
            binding.button1,
            binding.button2,
            binding.button3,
            binding.button4,
            binding.button5,
            binding.button6,
            binding.button7,
            binding.button8,
            binding.button9
        )

        for(buttonNumber in arrayButtons) {
            buttonNumber.setOnClickListener {
                if (viewModel.liveDataPassword.value!!.length < PASSWORD_LEN) {
                    viewModel.liveDataPassword.value = viewModel.liveDataPassword.value!! + buttonNumber.text
                }
            }
        }
    }

    private fun configureBackspaceButton() {
        binding.buttonBackspace.setOnClickListener {
            val password = viewModel.liveDataPassword.value!!
            if (password.isNotEmpty()) {
                viewModel.liveDataPassword.value = password.substring(0, password.length-1)
            }
        }
    }

    private fun configureFingerprintButton() {
        binding.buttonFingerprint.setOnClickListener {
            // TODO: fingerprint
        }
    }

    private fun configureIndicators() {
        viewModel.liveDataPassword.observe(this) { password ->
            if (password.length == PASSWORD_LEN) {
                checkPassword(password)
            }
            else {
                val arrayIndicators = arrayOf(
                    binding.indicator1,
                    binding.indicator2,
                    binding.indicator3,
                    binding.indicator4,
                    binding.indicator5
                )
                for (i in arrayIndicators.indices) {
                    if (i in password.indices) {
                        arrayIndicators[i].setBackgroundColor(Color.RED)
                    }
                    else {
                        arrayIndicators[i].setBackgroundColor(Color.GRAY)
                    }
                }
            }
        }
    }

    private fun checkPassword(password: String) {
        if (PasswordHelper.checkPassword(applicationContext, password)) {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        else {
            viewModel.liveDataPassword.value = ""
            Toast.makeText(this, "Incorrect password. Try again", Toast.LENGTH_LONG).show()
        }
    }

}