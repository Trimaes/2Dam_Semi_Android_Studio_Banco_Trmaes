package com.example.banco_trmaes.activities

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_trmaes.R
import com.example.banco_trmaes.adapter.OnClickListener
import com.example.banco_trmaes.api.pojo.Cuenta
import com.example.banco_trmaes.databinding.ActivityGlobalPositionDetailsBinding
import com.example.banco_trmaes.fragments.FragmentAccountMovements

class GlobalPositionDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGlobalPositionDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGlobalPositionDetailsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val cuenta: Cuenta = intent.getSerializableExtra("Cuenta") as Cuenta
        val movements = supportFragmentManager.findFragmentById(R.id.movementsContainer) as FragmentAccountMovements?
        movements?.mostrarMovivmientos(cuenta)

        binding.backButton.setOnClickListener {
            finish()
        }
    }

}