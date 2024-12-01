package com.example.banco_trmaes.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_trmaes.R
import com.example.banco_trmaes.adapter.CuentasAdapter
import com.example.banco_trmaes.adapter.MovementsAdapter
import com.example.banco_trmaes.adapter.OnClickMovement
import com.example.banco_trmaes.api.bd.MiBancoOperacional
import com.example.banco_trmaes.api.pojo.Cuenta
import com.example.banco_trmaes.api.pojo.Movimiento
import com.example.banco_trmaes.databinding.FragmentAccountMovementsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentAccountMovements : Fragment(), OnClickMovement{

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var movementsAdapter: MovementsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var binding: FragmentAccountMovementsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAccountMovementsBinding.inflate(inflater, container, false)
        linearLayoutManager = LinearLayoutManager(context)

        binding.rvMovements.apply {
            adapter = movementsAdapter
            layoutManager = linearLayoutManager
        }

        return binding.root
    }

    fun mostrarMovivmientos(cuenta:Cuenta){
        val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(context)
        val lista = mbo?.getMovimientos(cuenta) as ArrayList<Movimiento>
        movementsAdapter = MovementsAdapter(lista, this)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentAccountMovements().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @SuppressLint("SimpleDateFormat")
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

        MaterialAlertDialogBuilder(context as Context)
            .setTitle("Detalles del movimiento")
            .setView(dialogView)
            .setPositiveButton("Aceptar") { dialog, i ->
                dialog.cancel()
            }
            .setCancelable(false)
            .show()
    }
}