package com.example.banco_trmaes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.banco_trmaes.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.validarContrasea.setOnClickListener {
            if (!binding.newPsswrd.text.toString().equals(binding.repNewPsswrd.text.toString())){
                binding.txtInRepPsswrd.error = "La contraseña no coincide"
            } else {
                binding.txtInRepPsswrd.error = null
            }
        }

        binding.volverMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}