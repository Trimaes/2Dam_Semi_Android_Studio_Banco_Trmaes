package com.example.banco_trmaes.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.banco_trmaes.databinding.ActivityLoginBinding
import com.example.banco_trmaes.api.bd.MiBancoOperacional
import com.example.banco_trmaes.api.pojo.Cliente


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
            val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)

//---------------------------------------------------------------------------------------------------------------
            var cliente = Cliente()
            cliente.setNif(binding.dniTxtEntry.text.toString())
            cliente.setClaveSeguridad(binding.passwordEntry.text.toString())

            // Logueamos al cliente
            val clienteLogeado = mbo?.login(cliente) ?: -1

            if(binding.dniTxtEntry.text.toString().isEmpty()){

                binding.userDni.error = "Este campo no puede estar vacío"

            }else if(clienteLogeado == -1) {

                Toast.makeText(this, "El cliete no existe", Toast.LENGTH_LONG).show()

            }else{
                //Intent lanza la activity asociada (MainActivity)
                val intent = Intent(this, MainActivity::class.java)
                //Pasa el contenido del TextInputEditText
                intent.putExtra("Cliente", clienteLogeado)
                startActivity(intent)
            }

        }

        binding.bttnSalir.setOnClickListener {
            finishAffinity()
        }
    }
}