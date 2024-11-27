package com.example.banco_trmaes.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banco_trmaes.R
import com.example.banco_trmaes.api.pojo.Cuenta
import com.example.banco_trmaes.databinding.ItemCuentaClienteBinding

class CuentasAdapter(private val listaCuentas:List<Cuenta>) : RecyclerView.Adapter<CuentasAdapter.CuentasViewHolder>(){

    inner class CuentasViewHolder(view: View):RecyclerView.ViewHolder(view){
        val binding = ItemCuentaClienteBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuentasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cuenta_cliente, parent, false)
        return CuentasViewHolder(view)
    }

    override fun getItemCount(): Int = listaCuentas.size

    override fun onBindViewHolder(holder: CuentasViewHolder, position: Int) {
        val cuenta = listaCuentas[position]
        with(holder) {
            binding.tvSaldoActual.text = cuenta.getSaldoActual().toString()
            binding.tvNumCuenta.text = cuenta.getNumeroCuenta()
            if (cuenta.getSaldoActual()!! < 0){
                binding.tvSaldoActual.setTextColor(Color.RED)
            } else {
                binding.tvSaldoActual.setTextColor(Color.GREEN)
            }
        }
    }

}