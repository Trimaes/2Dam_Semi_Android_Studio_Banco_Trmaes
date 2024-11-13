package com.example.banco_trmaes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.banco_trmaes.databinding.ActivityLoginBinding



class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dniTxtEntry.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus && binding.dniTxtEntry.text.toString().isEmpty()){
                binding.userDni.error = "Este campo no puede estar vacío"
            } else {
                binding.userDni.error = null
            }
        }

        binding.bttnEntrar.setOnClickListener {
            if (!binding.dniTxtEntry.text.toString().isEmpty()){
                //Intent lanza la activity asociada (MainActivity)
                val intent = Intent(this, MainActivity::class.java)
                //Pasa el contenido del TextInputEditText
                intent.putExtra("dni", binding.dniTxtEntry.text.toString())
                startActivity(intent)
            } else {
                binding.userDni.error = "Este campo no puede estar vacío"
            }
        }

        binding.bttnSalir.setOnClickListener {
            finishAffinity()
        }
    }
}