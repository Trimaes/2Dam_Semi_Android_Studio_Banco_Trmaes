package com.example.banco_trmaes.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.banco_trmaes.api.bd.MiBancoOperacional
import com.example.banco_trmaes.api.pojo.Cliente
import com.example.banco_trmaes.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val clienteRecuperado = intent.getSerializableExtra("Cliente") as? Cliente

        binding.validarContrasea.setOnClickListener {
            val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)
            if (!binding.newPsswrd.text.toString().equals(binding.repNewPsswrd.text.toString())){
                binding.txtInRepPsswrd.error = "La contraseña no coincide"
            } else {
                clienteRecuperado?.setClaveSeguridad(binding.repNewPsswrd.text.toString())
                val p = mbo?.changePassword(clienteRecuperado)
                Toast.makeText(this, (if(p == 1) "Contraseña actualizada" else "No se pudo actualizar la contraseña"), Toast.LENGTH_LONG).show()
            }
        }

        binding.volverMain.setOnClickListener {

            finish()
        }
    }
}