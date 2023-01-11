package com.example.orgs.extensions

import android.os.Build
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import com.example.orgs.R

fun ImageView.tentaCarregarImagem(url: String? = null) {
  val imageLoader = ImageLoader.Builder(context)
    .components {
      if (Build.VERSION.SDK_INT >= 28) {
        add(ImageDecoderDecoder.Factory())
      } else {
        add(GifDecoder.Factory())
      }
    }.build()

  load(url, imageLoader) {
    fallback(R.drawable.erro)
    error(R.drawable.erro)
  }
}