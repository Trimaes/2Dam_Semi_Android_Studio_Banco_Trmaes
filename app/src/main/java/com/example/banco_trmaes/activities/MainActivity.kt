package com.example.banco_trmaes.activities
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.banco_trmaes.databinding.MainActivityBinding
import com.example.banco_trmaes.api.pojo.Cliente


class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Recuperar cliente (Casting "as?"
        val clienteRecuperado = intent.getSerializableExtra("Cliente") as Cliente

        binding.content.bienvenida.append(clienteRecuperado?.getNombre())

        binding.content.salirMainActivity.setOnClickListener {
            finish()
        }
        binding.content.cambiarContrasena?.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            intent.putExtra("Cliente", clienteRecuperado)
            startActivity(intent)
        }
        binding.content.botonTransferencias?.setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            intent.putExtra("Cliente", clienteRecuperado)
            startActivity(intent)
        }
        binding.content.buttonPosGlobal?.setOnClickListener {
            val intent = Intent(this, GlobalPositionActivity::class.java)
            intent.putExtra("Cliente", clienteRecuperado)
            startActivity(intent)
        }
        binding.content.buttonMovements?.setOnClickListener {
            val intent = Intent(this, MovementsActivity::class.java)
            intent.putExtra("Cliente", clienteRecuperado)
            startActivity(intent)
        }
    }
}
