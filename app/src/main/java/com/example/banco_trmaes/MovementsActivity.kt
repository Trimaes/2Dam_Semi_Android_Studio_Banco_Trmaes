package com.example.banco_trmaes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_trmaes.adapter.CuentasAdapter
import com.example.banco_trmaes.adapter.MovementsAdapter
import com.example.banco_trmaes.api.bd.MiBancoOperacional
import com.example.banco_trmaes.api.pojo.Cliente
import com.example.banco_trmaes.api.pojo.Cuenta
import com.example.banco_trmaes.api.pojo.Movimiento
import com.example.banco_trmaes.databinding.ActivityMovementsBinding

class MovementsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovementsBinding
    private lateinit var movementsAdapter: MovementsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var itemDecoration: DividerItemDecoration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovementsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)


        val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)

        val clienteRecuperado = intent.getSerializableExtra("Cliente") as? Cliente

        //Lista de cuentas y lista de números de cuenta para mostrar en el spinner
        val listaCuentas: MutableList<Cuenta> = mbo?.getCuentas(clienteRecuperado) as MutableList<Cuenta>
        val numsCuentas: MutableList<String?> = mutableListOf<String?>()
        var listaMovimientos: ArrayList<Movimiento> = mbo?.getMovimientos(listaCuentas[0]) as ArrayList<Movimiento>

        //LLenar la lista numsCuenta
        for (cuenta in listaCuentas){
            numsCuentas.add(cuenta.getNumeroCuenta())
        }

        //Mostrar el listado en el spinner
        val adapterSp = ArrayAdapter(this, android.R.layout.simple_spinner_item, numsCuentas)
        adapterSp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spCuentas.adapter = adapterSp

        //Responder al item seleccionado
        binding.spCuentas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                listaMovimientos.clear()
                listaMovimientos = mbo?.getMovimientos(listaCuentas[position]) as ArrayList<Movimiento>

                movementsAdapter = MovementsAdapter(listaMovimientos)

                binding.recyclerMovements.apply {
                    adapter = movementsAdapter
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        movementsAdapter = MovementsAdapter(listaMovimientos)
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.recyclerMovements.apply {
            layoutManager = linearLayoutManager
            adapter = movementsAdapter
            addItemDecoration(itemDecoration)
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}