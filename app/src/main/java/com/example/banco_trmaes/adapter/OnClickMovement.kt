package com.example.banco_trmaes.adapter

import com.example.banco_trmaes.api.pojo.Cuenta
import com.example.banco_trmaes.api.pojo.Movimiento

interface OnClickMovement {
    fun onClick(movement: Movimiento)
}