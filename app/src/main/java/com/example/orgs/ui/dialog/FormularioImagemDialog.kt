package com.example.orgs.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.orgs.databinding.FormularioImagemBinding
import com.example.orgs.extensions.tentaCarregarImagem

class FormularioImagemDialog(private val context: Context) {

  fun mostra(urlPadrao: String? = null, quandoImagemCarregada: (url: String) -> Unit) {
    val binding = FormularioImagemBinding.inflate(LayoutInflater.from(context))

    urlPadrao?.let {
      binding.formularioImagemImageView.tentaCarregarImagem(it)
      binding.formularioImagemUrl.setText(it)
    }

    binding.formularioImagemBotaoCarregar.setOnClickListener {
      val url = binding.formularioImagemUrl.text.toString()
      binding.formularioImagemImageView.tentaCarregarImagem(url)
    }

    AlertDialog.Builder(context)
      .setView(binding.root)
      .setPositiveButton("Confirmar") { _, _ ->
        val url = binding.formularioImagemUrl.text.toString()
        quandoImagemCarregada(url)
      }
      .setNegativeButton("Cancelar") { _, _ -> }
      .show()
  }
}