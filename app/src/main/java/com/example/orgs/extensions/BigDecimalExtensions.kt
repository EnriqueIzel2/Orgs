package com.example.orgs.extensions

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

fun BigDecimal.formatarMoedaBrasileira(): String {
  val formatador: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

  return formatador.format(this)
}