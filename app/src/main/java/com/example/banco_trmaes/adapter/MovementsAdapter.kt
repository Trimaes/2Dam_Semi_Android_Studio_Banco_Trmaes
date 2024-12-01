package com.example.banco_trmaes.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banco_trmaes.R
import com.example.banco_trmaes.api.pojo.Movimiento
import com.example.banco_trmaes.databinding.ItemMovimientosBinding
import java.text.SimpleDateFormat

class MovementsAdapter (private val listaMovimientos:List<Movimiento>, private val listener: OnClickMovement) : RecyclerView.Adapter<MovementsAdapter.MovementsViewHolder>(){

    inner class MovementsViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemMovimientosBinding.bind(view)

        fun setListener(movement: Movimiento){
            binding.root.setOnClickListener {
                listener.onClick(movement)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovementsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movimientos, parent, false)
        return MovementsViewHolder(view)
    }

    override fun getItemCount(): Int = listaMovimientos.size

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: MovementsViewHolder, position: Int) {
        val movimiento = listaMovimientos[position]
        val formateador = SimpleDateFormat("dd/MM/yyyy")

        with(holder) {
            setListener(movimiento)
            binding.tvDescripcion.text = movimiento.getDescripcion()
            binding.tvSaldoActual.text = movimiento.getFechaOperacion()
                ?.let { formateador.format(it) } + " Importe:" + movimiento.getImporte().toString() + "€"
        }
    }
}