package com.example.banco_trmaes.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banco_trmaes.R
import com.example.banco_trmaes.api.pojo.Movimiento
import com.example.banco_trmaes.databinding.ItemMovimientosBinding
import java.text.SimpleDateFormat

class MovementsAdapter (private val listaMovimientos:List<Movimiento>) : RecyclerView.Adapter<MovementsAdapter.MovementsViewHolder>(){

    inner class MovementsViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemMovimientosBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovementsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movimientos, parent, false)
        return MovementsViewHolder(view)
    }

    override fun getItemCount(): Int = listaMovimientos.size

    override fun onBindViewHolder(holder: MovementsViewHolder, position: Int) {
        val movimiento = listaMovimientos[position]
        val formateador = SimpleDateFormat("dd/MM/yyyy")

        with(holder) {
            binding.tvDescripcion.text = movimiento.getDescripcion()
            binding.tvSaldoActual.text = formateador.format(movimiento.getFechaOperacion()) + " Importe:" + movimiento.getImporte().toString() + "€"
        }
    }
}