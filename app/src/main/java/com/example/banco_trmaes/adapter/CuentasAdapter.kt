package com.example.banco_trmaes.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banco_trmaes.R
import com.example.banco_trmaes.api.pojo.Cuenta
import com.example.banco_trmaes.databinding.ItemCuentaClienteBinding

class CuentasAdapter(private val listaCuentas:List<Cuenta>, private val listener: OnClickListener) : RecyclerView.Adapter<CuentasAdapter.CuentasViewHolder>(){

    inner class CuentasViewHolder(view: View):RecyclerView.ViewHolder(view){
        val binding = ItemCuentaClienteBinding.bind(view)

        fun setListener(cuenta: Cuenta){
            binding.root.setOnClickListener{
                Log.d("CuentasAdapter", "Click en cuenta: ${cuenta.getNumeroCuenta()}")
                listener.onClick(cuenta)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuentasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cuenta_cliente, parent, false)
        Log.d("CuentasAdapter", "ViewHolder creado")
        return CuentasViewHolder(view)
    }

    override fun getItemCount(): Int = listaCuentas.size

    override fun onBindViewHolder(holder: CuentasViewHolder, position: Int) {
        val cuenta = listaCuentas[position]

        with(holder) {
            Log.d("CuentasAdapter", "ViewHolder vinculado para la cuenta: ${cuenta.getNumeroCuenta()}")
            setListener(cuenta)
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