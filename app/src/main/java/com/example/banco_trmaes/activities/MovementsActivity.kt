package com.example.banco_trmaes.activities

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_trmaes.R
import com.example.banco_trmaes.adapter.MovementsAdapter
import com.example.banco_trmaes.adapter.OnClickMovement
import com.example.banco_trmaes.api.bd.MiBancoOperacional
import com.example.banco_trmaes.api.pojo.Cliente
import com.example.banco_trmaes.api.pojo.Cuenta
import com.example.banco_trmaes.api.pojo.Movimiento
import com.example.banco_trmaes.databinding.ActivityMovementsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat

class MovementsActivity : AppCompatActivity(), OnClickMovement{

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

                movementsAdapter = MovementsAdapter(listaMovimientos, this@MovementsActivity)

                binding.recyclerMovements.apply {
                    adapter = movementsAdapter
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        movementsAdapter = MovementsAdapter(listaMovimientos, this)
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

    override fun onClick(movement: Movimiento) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_movement, null)
        val formateador = SimpleDateFormat("dd/MM/yyyy")

        val movId = dialogView.findViewById<TextView>(R.id.idMov)
        movId.append(movement.getId().toString())
        val movTipo = dialogView.findViewById<TextView>(R.id.tipoMov)
        movTipo.append(movement.getTipo().toString())
        val movFecha = dialogView.findViewById<TextView>(R.id.fechaMov)
        movFecha.append(movement.getFechaOperacion()?.let { formateador.format(it) })
        val movDesc = dialogView.findViewById<TextView>(R.id.descMov)
        movDesc.append(movement.getDescripcion())

        MaterialAlertDialogBuilder(this)
            .setTitle("Detalles del movimiento")
            .setView(dialogView)
            .setPositiveButton("Aceptar") { dialog, i ->
                dialog.cancel()
            }
            .setCancelable(false)
            .show()
    }
}