package com.example.banco_trmaes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.banco_trmaes.api.bd.MiBancoOperacional
import com.example.banco_trmaes.api.pojo.Cliente
import com.example.banco_trmaes.api.pojo.Cuenta
import com.example.banco_trmaes.databinding.ActivityTransferBinding

class TransferActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransferBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val clienteRecuperado = intent.getSerializableExtra("Cliente") as? Cliente

        val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)

        val listaCuentas: MutableList<Cuenta> = mbo?.getCuentas(clienteRecuperado) as MutableList<Cuenta>
        val numsCuentas: MutableList<String?> = mutableListOf<String?>()

        //LLenar la lista numsCuenta
        for (cuenta in listaCuentas){
            numsCuentas.add(cuenta.getNumeroCuenta())
        }

        val divisas = arrayOf("€", "£", "$", "¥")

        //Adapter cuentas
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, numsCuentas)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinnerCuentas = binding.spinnerCuentas
        spinnerCuentas.adapter = adapter1
        //Cuentas destino
        val spinnerCentasDestino = binding.spinnerCuentaDestino
        spinnerCentasDestino.adapter = adapter1

        //Adapter divisas
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, divisas)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinnerDivisas = binding.spinnerDivisas
        spinnerDivisas.adapter = adapter2

        //Listener Cuenta propia o ajena

        binding.groupRadioButton.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rButtonCuentaPropia.id -> {
                    binding.spinnerCuentaDestino.visibility = View.VISIBLE
                    binding.numCuentaDestino.visibility = View.GONE
                }

                binding.rButtonCuentaAjena.id -> {
                    binding.numCuentaDestino.visibility = View.VISIBLE
                    binding.spinnerCuentaDestino.visibility = View.GONE
                }
            }
        }

        binding.enviarTransfer.setOnClickListener {
            showCustomToast(this)
        }

        binding.cancelarTransfer.setOnClickListener {
            binding.rButtonCuentaAjena.isChecked = false
            binding.rButtonCuentaPropia.isChecked = false
            binding.numCuentaDestino.text = null
            binding.txtEditCantidad.text = null
            binding.checkJustificante.isChecked = false
            binding.spinnerCuentas.setSelection(0)
            binding.spinnerCuentaDestino.setSelection(0)
        }
        binding.backButton.setOnClickListener {
            finish()
        }

        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.spinnerCuentas.apply { decoration }
    }//OnCreate

    fun showCustomToast(context: Context) {

        var cO: String = "Cuenta origen:\n" + binding.spinnerCuentas.selectedItem.toString()

        var cD: String

        if (binding.rButtonCuentaPropia.isChecked) {
            cD = "A cuenta propia:\n" + binding.spinnerCuentaDestino.selectedItem.toString()
        } else {
            cD = "A cuenta propia:\n" + binding.numCuentaDestino.text
        }
        var importe =
            "Importe: " + binding.txtEditCantidad.text + binding.spinnerDivisas.selectedItem.toString()

        var justificante: String

        if (binding.checkJustificante.isChecked) justificante = "Enviar justificante"
        else justificante = "Sin justificante"
        //------------------------------------------------------------------------------------------
        // Inflar el diseño del Toast
        //val inflater = LayoutInflater.from(context)
        val customToastLayout = LayoutInflater.from(context).inflate(R.layout.custom_toast_layout, null)

        // Configurar el texto del Toast
        val toastText1 = customToastLayout.findViewById<TextView>(R.id.toast_text1)
        toastText1.text = cO

        val toastText2 = customToastLayout.findViewById<TextView>(R.id.toast_text2)
        toastText2.text = cD

        val toastText3 = customToastLayout.findViewById<TextView>(R.id.toast_text3)
        toastText3.text = importe

        val toastText4 = customToastLayout.findViewById<TextView>(R.id.toast_text4)
        toastText4.text = justificante

        // Crear y mostrar el Toast
        val toast = Toast(context)
        toast.duration = Toast.LENGTH_LONG
        toast.view = customToastLayout
        toast.show()
    }
}