package com.example.myapplication.login.view

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.replaceFragmentWithNoHistory

class LoginActivity : AppCompatActivity() {

    @VisibleForTesting
    var binding : ActivityLoginBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        replaceFragmentWithNoHistory(LoginFragment(), R.id.containerFragment)
    }

}