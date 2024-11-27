package com.example.banco_trmaes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_trmaes.adapter.CuentasAdapter
import com.example.banco_trmaes.api.bd.MiBancoOperacional
import com.example.banco_trmaes.api.pojo.Cliente
import com.example.banco_trmaes.api.pojo.Cuenta
import com.example.banco_trmaes.databinding.ActivityGlobalPositionBinding

class GlobalPositionActivity : AppCompatActivity() {

    private lateinit var cuentasAdapter: CuentasAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var binding: ActivityGlobalPositionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGlobalPositionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val clienteRecuperado = intent.getSerializableExtra("Cliente") as? Cliente

        val mbo:MiBancoOperacional? = MiBancoOperacional.getInstance(this)

        val listaCuentas: List<Cuenta> = mbo?.getCuentas(clienteRecuperado) as List<Cuenta>

        cuentasAdapter = CuentasAdapter(listaCuentas)
        linearLayoutManager = LinearLayoutManager(this)

        binding.recyclerCuentas.apply {
            layoutManager = linearLayoutManager
            adapter = cuentasAdapter
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    /*private fun getCuentasCliente(cliente:Cliente?) : MutableList<Cuenta> {

        val mbo:MiBancoOperacional? = MiBancoOperacional.getInstance(this)

        val listaCuentas: ArrayList<Cuenta>? = mbo?.getCuentas(cliente) as ArrayList<Cuenta>?
        // Verificar si la lista no es nula

        return listaCuentas as MutableList<Cuenta>

    }*/
}