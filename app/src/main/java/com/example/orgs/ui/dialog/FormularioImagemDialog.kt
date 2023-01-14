package com.example.orgs.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.orgs.databinding.FormularioImagemBinding
import com.example.orgs.extensions.tentaCarregarImagem

class FormularioImagemDialog(private val context: Context) {

  fun mostra(urlPadrao: String? = null, quandoImagemCarregada: (url: String) -> Unit) {

    FormularioImagemBinding.inflate(LayoutInflater.from(context)).apply {

      urlPadrao?.let {
        formularioImagemImageView.tentaCarregarImagem(it)
        formularioImagemUrl.setText(it)
      }

      formularioImagemBotaoCarregar.setOnClickListener {
        val url = formularioImagemUrl.text.toString()
        formularioImagemImageView.tentaCarregarImagem(url)
      }

      AlertDialog.Builder(context)
        .setView(root)
        .setPositiveButton("Confirmar") { _, _ ->
          val url = formularioImagemUrl.text.toString()
          quandoImagemCarregada(url)
        }
        .setNegativeButton("Cancelar") { _, _ -> }
        .show()

    }
  }
}