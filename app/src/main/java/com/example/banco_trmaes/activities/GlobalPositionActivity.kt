package com.example.banco_trmaes.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.banco_trmaes.R
import com.example.banco_trmaes.api.pojo.Cliente
import com.example.banco_trmaes.api.pojo.Cuenta
import com.example.banco_trmaes.databinding.ActivityGlobalPositionBinding
import com.example.banco_trmaes.fragments.AccountListener
import com.example.banco_trmaes.fragments.FragmentAccountMovements
import com.example.banco_trmaes.fragments.FragmentAccounts


class GlobalPositionActivity : AppCompatActivity(), AccountListener {

    private lateinit var binding: ActivityGlobalPositionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGlobalPositionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val clienteRecuperado = intent.getSerializableExtra("Cliente")

        val fAccountsInstance: FragmentAccounts = FragmentAccounts.newInstance(clienteRecuperado as Cliente)
        supportFragmentManager.beginTransaction().add(R.id.accountsContainer, fAccountsInstance).commit()
        fAccountsInstance.setAccountListener(this)

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    override fun onAccountClicked(cuenta: Cuenta) {
        if(cuenta != null){

            var existMovementsFragment = binding.movementsContainer != null

            if(existMovementsFragment){//Se muestra el contenido en la misma Activity
                Log.d("GlobalPosition", "Dentro del if")

                val movementsFragment = FragmentAccountMovements()
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.movementsContainer, movementsFragment)
                movementsFragment.mostrarMovivmientos(cuenta)
                transaction.commitNow()


            }else{
                Log.d("GlobalPosition", "Dentro del else")
                val intent = Intent(this, GlobalPositionDetailsActivity::class.java)
                intent.putExtra("Cuenta", cuenta)
                startActivity(intent)
            }
       }
    }

    /*private fun getCuentasCliente(cliente:Cliente?) : MutableList<Cuenta> {

        val mbo:MiBancoOperacional? = MiBancoOperacional.getInstance(this)

        val listaCuentas: ArrayList<Cuenta>? = mbo?.getCuentas(cliente) as ArrayList<Cuenta>?
        // Verificar si la lista no es nula

        return listaCuentas as MutableList<Cuenta>

    }*/
}