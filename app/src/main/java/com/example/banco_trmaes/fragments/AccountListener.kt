package com.example.banco_trmaes.fragments

import com.example.banco_trmaes.api.pojo.Cuenta

interface AccountListener {
    fun onAccountClicked(cuenta:Cuenta)
}