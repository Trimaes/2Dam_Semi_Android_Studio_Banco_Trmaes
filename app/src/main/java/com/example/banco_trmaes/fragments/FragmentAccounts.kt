package com.example.banco_trmaes.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco_trmaes.adapter.CuentasAdapter
import com.example.banco_trmaes.adapter.OnClickListener
import com.example.banco_trmaes.api.bd.MiBancoOperacional
import com.example.banco_trmaes.api.pojo.Cliente
import com.example.banco_trmaes.api.pojo.Cuenta
import com.example.banco_trmaes.databinding.FragmentAccountsBinding
import java.io.Serializable

private const val ARG_CLIENTE = "cliente"


class FragmentAccounts : Fragment(), OnClickListener {

    private lateinit var cliente:Cliente

    private lateinit var binding: FragmentAccountsBinding
    private lateinit var accountAdapter: CuentasAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var listener: AccountListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cliente = it.getSerializable(ARG_CLIENTE) as Cliente
            if(cliente != null) Log.d("FragmentAccounts", "El cliente NO es nulo")
            else Log.d("FragmentAccounts", "El cliente ES nulo")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountsBinding.inflate(inflater,container, false)
        val mbo:MiBancoOperacional? = MiBancoOperacional.getInstance(context)
        val accountsList:ArrayList<Cuenta>? = mbo?.getCuentas(cliente as Cliente?) as ArrayList<Cuenta>?

        accountAdapter = CuentasAdapter(accountsList as List<Cuenta>, this)
        linearLayoutManager = LinearLayoutManager(context)

        binding.rvAccounts.apply {
            layoutManager = linearLayoutManager
            adapter = accountAdapter
            Log.d("C", "Fragment apply")
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(cliente: Cliente) =
            FragmentAccounts().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CLIENTE, cliente)
                }
            }
    }

    fun setAccountListener(listener: AccountListener){
        this.listener = listener
    }

    override fun onClick(cuenta: Cuenta) {
        Log.d("C", "onClick Fragment")
        if (listener != null){
            listener.onAccountClicked(cuenta)
        }
    }

}